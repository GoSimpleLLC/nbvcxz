package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.DictionaryMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.Dictionary;

import java.util.*;

/**
 * Look for every part of the password that match an entry in our dictionaries
 *
 * @author Adam Brusselback
 */
public final class DictionaryMatcher implements PasswordMatcher
{
    /**
     * Removes all munge substitutions from the password and returns a list of plain text versions.
     *
     * @param configuration the configuration file used to estimate entropy.
     * @param password      the password to unmunge.
     * @return a list of all combinations of possible unmunged translations for the password with all munges removed.
     */
    private static List<String> getUnmungedVariations(final Configuration configuration, final String password)
    {
        MungeTable mungeTable = configuration.getMungeTable();
        PasswordChain chain = new PasswordChain(password);

        for (String mungeKey : mungeTable.getKeys()) { // keys sorted largest to smallest
            for (int i = 0; i < chain.size(); i++) {
                // remove this password part if it can be split up further
                String unsplit = chain.removeIfUnsplit(i);
                if (unsplit != null) {
                    // split password segment by the munge key
                    String[] splitParts = mungeTable.getKeyPattern(mungeKey).split(unsplit);
                    for (int j = 0; j < splitParts.length; j++) {
                        String sp = splitParts[j];
                        // add the subs back into the chain, or just add the segment back if the munge key wasn't present
                        chain.add(i + j, mungeTable.getSubsOrOriginal(sp), sp.equals(mungeKey));
                    }
                }
            }
        }

        final List<String> translations = new ArrayList<>();

        if (chain.size() > 1)
        {
            // recursively generate all password permutations using the discovered munges
            replaceAtIndex(chain.getParts(), 0, new int[chain.size()], translations);
        }

        return translations;
    }

    /**
     * Internal function to recursively build the list of possible unmunged password permutations.
     *
     * @param replacements    List of possible password replacement substrings
     * @param index           Next index to generate all permutations at
     * @param usingIndexes     Bijection of replacements, where usingIndexes[i] specifies the
     *                         substring to use from replacements at the next iteration
     * @param finalPasswords Full list of generated password permutations
     */
    private static void replaceAtIndex(final List<String[]> replacements, int index, int[] usingIndexes, final List<String> finalPasswords)
    {
        if (index == replacements.size()) {
            // reached the end; build the password using the current index permutation
            StringBuilder passwordBuilder = new StringBuilder();
            for (int i = 0; i < replacements.size(); i++) {
                passwordBuilder.append(replacements.get(i)[usingIndexes[i]]);
            }

            finalPasswords.add(passwordBuilder.toString());
            return;
        }

        // exhaust all of the substring permutations at the current index
        for (int i = 0; i < replacements.get(index).length; i++)
        {
            if (finalPasswords.size() < 100)
            {
                usingIndexes[index] = i;
                replaceAtIndex(replacements, index + 1, usingIndexes, finalPasswords);
            }
            else {
                // Give up if we've already made 100 replacements
                return;
            }
        }
    }


    /**
     * Gets the substitutions for the password.
     *
     * @param password        the password to get leet substitutions for.
     * @param unleet_password the password to get leet substitutions for.
     * @return a {@code List} of {@code Character[]} that are the leet substitutions for the password.
     */
    private static List<Character[]> getLeetSub(final String password, final String unleet_password)
    {
        List<Character[]> leet_subs = new ArrayList<>();
        for (int i = 0; i < unleet_password.length(); i++)
        {
            if (password.charAt(i) != unleet_password.charAt(i))
            {
                leet_subs.add(new Character[]{password.charAt(i), unleet_password.charAt(i)});
            }
        }
        return leet_subs;
    }

    private static int distance(CharSequence left, CharSequence right, int threshold)
    {
        if (left == null || right == null)
        {
            throw new IllegalArgumentException("Strings must not be null");
        }
        if (threshold < 0)
        {
            throw new IllegalArgumentException("Threshold must not be negative");
        }

        /*
         * This implementation only computes the distance if it's less than or
         * equal to the threshold value, returning -1 if it's greater. The
         * advantage is performance: unbounded distance is O(nm), but a bound of
         * k allows us to reduce it to O(km) time by only computing a diagonal
         * stripe of width 2k + 1 of the cost table. It is also possible to use
         * this to compute the unbounded Levenshtein distance by starting the
         * threshold at 1 and doubling each time until the distance is found;
         * this is O(dm), where d is the distance.
         *
         * One subtlety comes from needing to ignore entries on the border of
         * our stripe eg. p[] = |#|#|#|* d[] = *|#|#|#| We must ignore the entry
         * to the left of the leftmost member We must ignore the entry above the
         * rightmost member
         *
         * Another subtlety comes from our stripe running off the matrix if the
         * strings aren't of the same size. Since string s is always swapped to
         * be the shorter of the two, the stripe will always run off to the
         * upper right instead of the lower left of the matrix.
         *
         * As a concrete example, suppose s is of length 5, t is of length 7,
         * and our threshold is 1. In this case we're going to walk a stripe of
         * length 3. The matrix would look like so:
         *
         * <pre>
         *    1 2 3 4 5
         * 1 |#|#| | | |
         * 2 |#|#|#| | |
         * 3 | |#|#|#| |
         * 4 | | |#|#|#|
         * 5 | | | |#|#|
         * 6 | | | | |#|
         * 7 | | | | | |
         * </pre>
         *
         * Note how the stripe leads off the table as there is no possible way
         * to turn a string of length 5 into one of length 7 in edit distance of
         * 1.
         *
         * Additionally, this implementation decreases memory usage by using two
         * single-dimensional arrays and swapping them back and forth instead of
         * allocating an entire n by m matrix. This requires a few minor
         * changes, such as immediately returning when it's detected that the
         * stripe has run off the matrix and initially filling the arrays with
         * large values so that entries we don't compute are ignored.
         *
         * See Algorithms on Strings, Trees and Sequences by Dan Gusfield for
         * some discussion.
         */

        int n = left.length(); // length of left
        int m = right.length(); // length of right

        // if one string is empty, the edit distance is necessarily the length
        // of the other
        if (n == 0)
        {
            return m <= threshold ? m : -1;
        }
        else if (m == 0)
        {
            return n <= threshold ? n : -1;
        }

        if (n > m)
        {
            // swap the two strings to consume less memory
            final CharSequence tmp = left;
            left = right;
            right = tmp;
            n = m;
            m = right.length();
        }

        int[] p = new int[n + 1]; // 'previous' cost array, horizontally
        int[] d = new int[n + 1]; // cost array, horizontally
        int[] tempD; // placeholder to assist in swapping p and d

        // fill in starting table values
        final int boundary = Math.min(n, threshold) + 1;
        for (int i = 0; i < boundary; i++)
        {
            p[i] = i;
        }
        // these fills ensure that the value above the rightmost entry of our
        // stripe will be ignored in following loop iterations
        Arrays.fill(p, boundary, p.length, Integer.MAX_VALUE);
        Arrays.fill(d, Integer.MAX_VALUE);

        // iterates through t
        for (int j = 1; j <= m; j++)
        {
            final char rightJ = right.charAt(j - 1); // jth character of right
            d[0] = j;

            // compute stripe indices, constrain to array size
            final int min = Math.max(1, j - threshold);
            final int max = j > Integer.MAX_VALUE - threshold ? n : Math.min(
                    n, j + threshold);

            // the stripe may lead off of the table if s and t are of different
            // sizes
            if (min > max)
            {
                return -1;
            }

            // ignore entry left of leftmost
            if (min > 1)
            {
                d[min - 1] = Integer.MAX_VALUE;
            }

            // iterates through [min, max] in s
            for (int i = min; i <= max; i++)
            {
                if (left.charAt(i - 1) == rightJ)
                {
                    // diagonally left and up
                    d[i] = p[i - 1];
                }
                else
                {
                    // 1 + minimum of cell to the left, to the top, diagonally
                    // left and up
                    d[i] = 1 + Math.min(Math.min(d[i - 1], p[i]), p[i - 1]);
                }
            }

            // copy current distance counts to 'previous row' distance counts
            tempD = p;
            p = d;
            d = tempD;
        }

        // if p[n] is greater than the threshold, there's no guarantee on it
        // being the correct
        // distance
        if (p[n] <= threshold)
        {
            return p[n];
        }
        return -1;
    }

    public List<Match> match(final Configuration configuration, final String password)
    {
        final List<Match> matches = new ArrayList<>();

        // Create all possible sub-sequences of the password
        for (int start = 0; start < password.length(); start++)
        {
            for (int end = start + 1; end <= password.length(); end++)
            {
                final String split_password = password.substring(start, end);

                // Iterate through all our dictionaries
                for (final Dictionary dictionary : configuration.getDictionaries())
                {
                    // Match on lower
                    final String lower_part = split_password.toLowerCase();
                    {
                        final Integer lower_rank = dictionary.getDictonary().get(lower_part);
                        if (lower_rank != null)
                        {
                            matches.add(new DictionaryMatch(split_password, configuration, start, end - 1, lower_part, lower_rank, new ArrayList<Character[]>(), dictionary.isExclusion(), false, dictionary.getDictionaryName(), 0));
                            continue;
                        }
                    }

                    // Only do reversed if it's different than the regular lower.
                    final String reversed_part = new StringBuilder(lower_part).reverse().toString();
                    {
                        final Integer reversed_rank = dictionary.getDictonary().get(reversed_part);
                        if (reversed_rank != null)
                        {
                            matches.add(new DictionaryMatch(split_password, configuration, start, end - 1, reversed_part, reversed_rank, new ArrayList<Character[]>(), dictionary.isExclusion(), true, dictionary.getDictionaryName(), 0));
                            continue;
                        }
                    }

                    // Only do unleet if it's different than the regular lower.
                    final List<String> unleet_list = getUnmungedVariations(configuration, lower_part);
                    for (final String unleet_part : unleet_list)
                    {
                        final Integer unleet_rank = dictionary.getDictonary().get(unleet_part);
                        if (unleet_rank != null)
                        {
                            final List<Character[]> subs = getLeetSub(lower_part, unleet_part);
                            matches.add(new DictionaryMatch(split_password, configuration, start, end - 1, unleet_part, unleet_rank, subs, dictionary.isExclusion(), false, dictionary.getDictionaryName(), 0));
                            continue;
                        }

                        // Only do reversed if it's different than unleet.
                        final String reversed_unleet_part = new StringBuilder(unleet_part).reverse().toString();
                        {
                            final Integer reversed_unleet_rank = dictionary.getDictonary().get(reversed_unleet_part);
                            if (reversed_unleet_rank != null)
                            {
                                final List<Character[]> subs = getLeetSub(reversed_part, reversed_unleet_part);
                                matches.add(new DictionaryMatch(split_password, configuration, start, end - 1, reversed_unleet_part, reversed_unleet_rank, subs, dictionary.isExclusion(), true, dictionary.getDictionaryName(), 0));
                                continue;
                            }
                        }
                    }

                    // Run distance match
                    // Only if we haven't found a match yet
                    {
                        if (!configuration.isDistanceCalc())
                        {
                            continue;
                        }

                        // Do not continue unless matching the whole password
                        if (!(start == 0 && end == password.length()))
                        {
                            continue;
                        }

                        // Weed out false positives for very short values
                        if (password.length() < 3)
                        {
                            continue;
                        }


                        // How far off the distance is allowed to be
                        final int threshold = password.length() / 4;

                        // Indexes to iterate over only a portion of the dictionary
                        final int start_index;
                        if (dictionary.getSortedDictionaryLengthLookup().containsKey(password.length() - threshold))
                        {
                            start_index = dictionary.getSortedDictionaryLengthLookup().get(password.length() - threshold);
                        }
                        else
                        {
                            start_index = dictionary.getSortedDictionary().size();
                        }
                        final int end_index;
                        if (dictionary.getSortedDictionaryLengthLookup().containsKey(password.length() + threshold + 1))
                        {
                            end_index = dictionary.getSortedDictionaryLengthLookup().get(password.length() + threshold + 1);
                        }
                        else
                        {
                            end_index = dictionary.getSortedDictionary().size();
                        }

                        // Values for the matches found
                        int dist_min = Integer.MAX_VALUE;
                        String dist_val = null;
                        Integer dist_rank = null;

                        // Iterate over the subset of the dictionary (based on length) which could
                        // possibly contain matches for the password
                        for (final String key : dictionary.getSortedDictionary().subList(start_index, end_index))
                        {
                            int dist_curr = distance(password, key, threshold);
                            if (dist_curr != -1)
                            {
                                final Integer dist_curr_rank = dictionary.getDictonary().get(key);
                                // If true, set the vars which contain the lowest values found so far
                                if (dist_curr <= dist_min && (dist_rank == null || dist_curr_rank <= dist_rank))
                                {
                                    dist_min = dist_curr;
                                    dist_val = key;
                                    dist_rank = dist_curr_rank;
                                }
                            }
                        }

                        // Add the match if one was found
                        if (dist_rank != null)
                        {
                            matches.add(new DictionaryMatch(split_password, configuration, start, end - 1, dist_val, dist_rank, new ArrayList<Character[]>(), dictionary.isExclusion(), false, dictionary.getDictionaryName(), dist_min));
                            continue;
                        }
                    }

                }
            }
        }
        // Return all the matches
        return matches;
    }
}

/**
 * Represents password permutations, using a list of candidate substrings. For instance, the first permutation
 * of the full password would be the 0th string from each part of the chain. This class is used to exhaust
 * password munging combinations.
 */
class PasswordChain {
    // substring candidates
    private List<String[]> parts;
    // a bijection with the list above, where each index specifies if the candidate parts at an index have
    // already been completely unmunged
    private List<Boolean> converted;

    /**
     * Creates a password chain using an initial String value.
     */
    public PasswordChain(String originalPassword) {
        this.parts = new LinkedList<>();
        this.converted = new LinkedList<>();

        add(0, new String[] {originalPassword}, false);
    }

    /**
     * Adds a list of candidate substrings to a specific index in the chain.
     * @param index Index to insert the candidates at
     * @param subs Candidate substrings for that index
     * @param converted Should be true if subs is the result of unmunging a part of the original password, false otherwise
     */
    public void add(int index, String[] subs, boolean converted) {
        parts.add(index, subs);
        this.converted.add(index, converted);
    }

    /**
     * Removes a list of candidate substrings from the chain, only if it has yet to be split up.
     * @param index Index of the candidate substrings
     * @return The candidate substring (since the removed array was of length 1). Null if that index has been split already.
     */
    public String removeIfUnsplit(int index) {
        if (parts.get(index).length == 1 && !converted.get(index)) { // unsplit AND not yet unmunged
            // return and remove the candidate part
            converted.remove(index);
            return parts.remove(index)[0];
        }

        return null;
    }

    /**
     * @return List of candidate substring parts
     */
    public List<String[]> getParts() {
        return parts;
    }

    /**
     * @return Size of the chain
     */
    public int size() {
        return parts.size();
    }
}