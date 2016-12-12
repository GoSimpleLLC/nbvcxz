package me.gosimple.nbvcxz;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import me.gosimple.nbvcxz.matching.PasswordMatcher;
import me.gosimple.nbvcxz.matching.match.BruteForceMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import me.gosimple.nbvcxz.resources.Feedback;
import me.gosimple.nbvcxz.resources.FeedbackUtil;
import me.gosimple.nbvcxz.scoring.Result;
import me.gosimple.nbvcxz.scoring.TimeEstimate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class allows you to do estimates on passwords.  It can be instantiated and configured once, and the same
 * instance should be used for subsequent password estimates.
 *
 * @author Adam Brusselback
 *
 */
public class Nbvcxz
{
    private Configuration configuration;

    /**
     * Creates new instance with a default configuration.
     */
    public Nbvcxz()
    {
        this.configuration = new ConfigurationBuilder().createConfiguration();
    }

    /**
     * Creates a new instance with a custom configuration.
     * @param configuration a {@code Configuration} to be used in all estimates.
     */
    public Nbvcxz(Configuration configuration)
    {
        this.configuration = configuration;
    }

    /**
     * Gets the current configuration.
     * @return returns {@code Configuration}
     */
    public Configuration getConfiguration()
    {
        return configuration;
    }

    /**
     * Sets the configuration.
     * @param configuration a {@code Configuration} to be used in all estimates.
     */
    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

    /**
     * Guess the entropy of a password with the configuration provided.
     * @param password The password you would like to attempt to estimate on.
     * @return Result object that contains info about the password.
     */
    public Result estimate(final String password)
    {
        return guessEntropy(this.configuration, password);
    }

    /**
     * Calculates the minimum entropy for a given password and returns that as a Result.
     * <br><br>
     * This method attempts to find the minimum entropy at each position of the password, and then does
     * a backwards pass to remove overlapping matches.  The end result is a list of matches that when
     * their tokens are added up, should equal the original password.
     * <br><br>
     * The result object is guaranteed to match the original password, or throw an exception if it doesn't.
     *
     * @param configuration the configuration file used to estimate entropy.
     * @param password the password you are guessing entropy for.
     * @return the {@code Result} of this estimate.
     */
    private static Result guessEntropy(final Configuration configuration, final String password)
    {
        Result final_result = new Result(configuration, password, getBestCombination(configuration, password));

        return final_result;
    }


    /**
     * Returns the best combination of matches based on multiple methods.  We run the password through the
     * {@code findGoodEnoughCombination} method test to see if is considered "random".  If it isn't, we
     * run it through the {@code findBestCombination} method, which is much more expensive for large
     * passwords.
     * @param configuration the configuration
     * @param password the password
     * @return the best list of matches, sorted by start index.
     */
    private static List<Match> getBestCombination(final Configuration configuration, final String password)
    {
        final List<Match> all_matches = getAllMatches(configuration, password);
        if (all_matches == null || all_matches.size() == 0 || isRandom(password, findGoodEnoughCombination(configuration, password, all_matches)))
        {
            List<Match> brute_force_matches = new ArrayList<>();
            backfillBruteForce(configuration, password, brute_force_matches);
            brute_force_matches.sort(new startIndexComparator());
            return brute_force_matches;
        }
        Collections.sort(all_matches, new startIndexComparator());

        return findBestCombination(configuration, password, all_matches);
    }

    /**
     * This is the original algorithm for finding the best matches.  It was much faster, but had the possibility of returning
     * non-optimal lists of matches.  I kept it around to run preliminarily to pass the results to {@code isRandom} so we can
     * see if the password is random and short circuit the more expensive calculations
     * @param configuration the configuration
     * @param password the password
     * @param all_matches all matches which have been found for this password
     * @return a list of matches which is good enough for most uses
     */
    private static List<Match> findGoodEnoughCombination(final Configuration configuration, final String password, final List<Match> all_matches)
    {
        int length = password.length();
        Match[] match_at_index = new Match[length];
        List<Match> match_list = new ArrayList<>();

        // First pass through the password forward.
        // Set the match to be the lowest average entropy for the length the part of the password takes.
        for (int k = 0; k < length; k++)
        {
            for (Match match : all_matches)
            {
                if (match.getEndIndex() == k)
                {
                    if (match_at_index[k] == null || match_at_index[k].calculateEntropy() / match_at_index[k].getLength() > match.calculateEntropy() / match.getLength())
                    {
                        match_at_index[k] = match;
                    }
                }
            }
        }


        // Now go backwards through the password.
        // Fill in any empty matches with brute force matches, add all matches to the match_list in reverse order.
        int k = length - 1;
        while (k >= 0)
        {
            Match match = match_at_index[k];
            if (match == null)
            {
                match_list.add(createBruteForceMatch(password, configuration, k, k));
                k--;
                continue;
            }
            match_list.add(match);
            k = match.getStartIndex() - 1;
        }

        // Reverse the order of the list so it's now first to last.
        Collections.reverse(match_list);
        return match_list;
    }

    /**
     * Finds the most optimal matches by recursively building out every combination possible and returning the best.
     * @param configuration the configuration
     * @param password the password
     * @param all_matches all matches which have been found for this password
     * @return the best possible combination of matches for this password
     */
    private static List<Match> findBestCombination(final Configuration configuration, final String password, final List<Match> all_matches)
    {
        final Map<Match, Range> range_map = new HashMap<>();
        final Map<Match, Set<Match>> non_intersecting_matches = new HashMap<>();

        for (Match match : all_matches)
        {
            range_map.put(match, Range.closed(match.getStartIndex(), match.getEndIndex()));
        }

        for(Match match : all_matches)
        {
            RangeSet range_set = TreeRangeSet.create();
            Range range = range_map.get(match);
            range_set.add(range);
            Set<Match> forward_non_intersecting_match_set = new HashSet<>();
            for(Match next_match : all_matches)
            {
                if(next_match.getStartIndex() > match.getEndIndex())
                {
                    if (!range_set.intersects(range_map.get(next_match)))
                    {
                        forward_non_intersecting_match_set.add(next_match);
                    }
                }
            }
            non_intersecting_matches.put(match, forward_non_intersecting_match_set);
        }


        List<Match> lowest_entropy_matches = new ArrayList<>();
        for(Match match : all_matches)
        {
            generateMatches(configuration, password, match, non_intersecting_matches, range_map, new ArrayList<>(), lowest_entropy_matches);
        }

        lowest_entropy_matches.sort(new startIndexComparator());

        return lowest_entropy_matches;
    }

    /**
     * Recursive function to generate match combinations to get an optimal match.
     * @param configuration the configuration
     * @param password the password
     * @param match a match to start with (or the next match in line)
     * @param non_intersecting_matches map of all non-intersecting matches
     * @param range_map map of all match ranges
     * @param matches the list of matches being built
     * @param lowest_entropy_matches the lowest entropy match will be set to this variable
     */
    private static void generateMatches(final Configuration configuration, final String password, final Match match, final Map<Match, Set<Match>> non_intersecting_matches, final Map<Match, Range> range_map, final List<Match> matches, List<Match> lowest_entropy_matches)
    {
        if(matches.contains(match))
            return;

        matches.add(match);

        if(!lowest_entropy_matches.isEmpty() && calcEntropy(matches) > calcEntropy(lowest_entropy_matches))
            return;

        RangeSet range_set = TreeRangeSet.create();
        for(Match m : matches)
        {
            range_set.add(range_map.get(m));
        }

        boolean found_next = false;
        for(Match next_match : non_intersecting_matches.get(match))
        {
            if(range_set.intersects(range_map.get(next_match)))
                continue;
            generateMatches(configuration, password, next_match, non_intersecting_matches, range_map, new ArrayList<>(matches), lowest_entropy_matches);
            found_next = true;
        }

        if(!found_next)
        {
            backfillBruteForce(configuration, password, matches);
            if (lowest_entropy_matches.isEmpty() || calcEntropy(matches) < calcEntropy(lowest_entropy_matches))
            {
                lowest_entropy_matches.clear();
                lowest_entropy_matches.addAll(matches);
            }

        }
    }

    /**
     * Method to determine if the password should be considered random, and to just use brute force matches.
     *
     * We determine a password to be random if the matches cover less than 50% of the password, or if they cover less than 80%
     * but the max length for a match is no more than 25% of the total length of the password.
     * @param password the password
     * @param matches the final list of matches
     * @return true if determined to be random
     */
    private static boolean isRandom(final String password, final List<Match> matches)
    {
        int matched_length = 0;
        int max_matched_length = 0;
        for(Match match : matches)
        {
            if(!(match instanceof BruteForceMatch))
            {
                matched_length += match.getLength();
                if(match.getLength() > max_matched_length)
                    max_matched_length = match.getLength();
            }
        }

        if(matched_length < (password.length() * 0.5))
            return true;
        else if (matched_length < (password.length() * 0.8) && password.length() * 0.25 > max_matched_length)
            return true;
        else
            return false;
    }

    /**
     * Helper method to calculate entropy from a list of matches.
     * @param matches the list of matches
     * @return the sum of the entropy in the list passed in
     */
    private static double calcEntropy(List<Match> matches)
    {
        double entropy = 0;
        for(Match match : matches)
        {
            entropy += match.calculateEntropy();
        }
        return entropy;
    }

    /**
     * Fills in the matches array passed in with {@link BruteForceMatch} in every missing spot.
     * Returns them unsorted.
     * @param configuration the configuration
     * @param password the password
     * @param matches the list of matches to fill in
     */
    private static void backfillBruteForce(final Configuration configuration, final String password, final List<Match> matches)
    {
        Set<Match> brute_force_matches = new HashSet<>();
        int index = 0;
        while (index < password.length())
        {
            boolean has_match = false;
            for(Match match : matches)
            {
                if(index >= match.getStartIndex() && index <= match.getEndIndex())
                {
                    has_match = true;
                }
            }
            if (!has_match)
            {
                brute_force_matches.add(createBruteForceMatch(password, configuration, index, index));
            }
            index++;
        }
        matches.addAll(brute_force_matches);
    }

    /**
     * Sorts matches by starting index, and average entropy per character
     */
    private static class startIndexComparator implements Comparator<Match>
    {
            public int compare(Match match_1, Match match_2)
            {
                if (match_1.getStartIndex() < match_2.getStartIndex())
                {
                    return -1;
                }
                else if (match_1.getStartIndex() > match_2.getStartIndex())
                {
                    return 1;
                }
                else if (match_1.getStartIndex() == match_2.getStartIndex())
                {
                    if(match_1.calculateEntropy() / match_1.getToken().length() < match_2.calculateEntropy() / match_2.getToken().length())
                        return -1;
                    else
                        return 1;
                }
                return 0;
            }
    }

    /**
     * Gets all matches for a given password.
     * @param configuration the configuration file used to estimate entropy.
     * @param password the password to get matches for.
     * @return a {@code List} of {@code Match} objects for the supplied password.
     */
    private static List<Match> getAllMatches(final Configuration configuration, final String password)
    {
        List<Match> matches = new ArrayList<>();

        for(PasswordMatcher passwordMatcher : configuration.getPasswordMatchers())
        {
            matches.addAll(passwordMatcher.match(configuration, password));
        }

        return matches;
    }

    /**
     * Creates a brute force match for a portion of the password.
     *
     * @param password the password to create brute match for
     * @param start_index the index start of the password part that needs a {@code Match}
     * @param end_index the index end of the password part that needs a {@code Match}
     * @return a {@code Match} object
     */
    private static Match createBruteForceMatch(final String password, final Configuration configuration, final int start_index, final int end_index)
    {
        return new BruteForceMatch(password.substring(start_index, end_index + 1), configuration, start_index, end_index);
    }

    /**
     * Gets the entropy from the number of guesses passed in.
     *
     * @param guesses a {@code BigDecimal} representing the number of guesses.
     *
     * @return entropy {@code Double} that is calculated based on the guesses.
     */
    public static Double getEntropyFromGuesses(final BigDecimal guesses)
    {
        Double guesses_tmp = guesses.doubleValue();
        guesses_tmp = guesses_tmp.isInfinite() ? Double.MAX_VALUE : guesses_tmp;
        return Math.log(guesses_tmp) / Math.log(2);
    }

    /**
     * Gets the number of guesses from the entropy passed in.
     *
     * @param entropy a {@code Double} representing the number of guesses.
     *
     * @return guesses {@code BigDecimal} that is calculated based on the entropy.
     */
    public static BigDecimal getGuessesFromEntropy(final Double entropy)
    {
        final Double guesses_tmp = Math.pow(2, entropy);
        return new BigDecimal(guesses_tmp.isInfinite() ? Double.MAX_VALUE : guesses_tmp).setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * Console application which will run with default configurations.
     * @param args arguments which are ignored!
     */
    public static void main(String... args)
    {
        Configuration configuration = new ConfigurationBuilder().createConfiguration();
        Nbvcxz nbvcxz = new Nbvcxz(configuration);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("main", nbvcxz.getConfiguration().getLocale());
        Scanner scanner = new Scanner(System.in);
        System.out.println(resourceBundle.getString("main.howToQuit"));

        String password;

        while(true)
        {
            System.out.println(resourceBundle.getString("main.startPrompt"));
            password = scanner.nextLine();
            if("\\quit".equals(password))
                break;
            printEstimationInfo(nbvcxz, password);
        }
        System.out.println(resourceBundle.getString("main.quitPrompt") + " ");

    }

    private static void printEstimationInfo(final Nbvcxz nbvcxz, final String password)
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("main", nbvcxz.getConfiguration().getLocale());

        long start = System.currentTimeMillis();
        Result result = nbvcxz.estimate(password);
        long end = System.currentTimeMillis();

        System.out.println("----------------------------------------------------------");
        System.out.println(resourceBundle.getString("main.timeToCalculate") + " " + (end - start) + " ms");
        System.out.println(resourceBundle.getString("main.password") + " " + password);
        System.out.println(resourceBundle.getString("main.entropy") + " " + result.getEntropy());
        Feedback feedback = FeedbackUtil.getFeedback(result);
        if(feedback.getWarning() != null)
            System.out.println(resourceBundle.getString("main.feedback.warning") + " " + feedback.getWarning());
        for(String suggestion : feedback.getSuggestion())
        {
            System.out.println(resourceBundle.getString("main.feedback.suggestion") + " " + suggestion);
        }
        Map<String, Long> sortedMap =
                result.getConfiguration().getGuessTypes().entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        for(Map.Entry<String, Long> guessType : sortedMap.entrySet())
        {
            System.out.println(resourceBundle.getString("main.timeToCrack") + " " + guessType.getKey() + ": " + TimeEstimate.getTimeToCrackFormatted(result, guessType.getKey()));
        }
        for(Match match : result.getMatches())
        {
            System.out.println("-----------------------------------");
            System.out.println(match.getDetails());
        }
        System.out.println("----------------------------------------------------------");
    }
}
