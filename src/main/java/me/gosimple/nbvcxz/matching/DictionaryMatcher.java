package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.DictionaryMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.Dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Look for every part of the password that match an entry in our dictionaries
 *
 * @author Adam Brusselback
 */
public final class DictionaryMatcher implements PasswordMatcher
{
    public List<Match> match(final Configuration configuration, final String password)
    {

        List<Match> matches = new ArrayList<>();

        // Create all possible sub-sequences of the password
        for (int start = 0; start < password.length(); start++)
        {
            for (int end = start + 1; end <= password.length(); end++)
            {
                // For each sub-sequence get all the possible leet substitutions
                String split_password = password.substring(start, end);
                String lower_part = split_password.toLowerCase();
                String unleet_part = translateLeet(configuration, lower_part);
                String reversed_part = new StringBuilder(lower_part).reverse().toString();
                String reversed_unleet_part = new StringBuilder(unleet_part).reverse().toString();
                List<Character[]> subs = getLeetSub(configuration, split_password);

                // Iterate through all our dictionaries
                for (Dictionary dictionary : configuration.getDictionaries())
                {
                    // Only match exclude dictionaries on full passwords
                    if(dictionary.isExclusion() && (start != 0 || end != password.length()))
                        continue;
                    // If there is a match, we add it to the result
                    Integer lower_rank = dictionary.getDictonary().get(lower_part);
                    if (lower_rank != null)
                    {
                        matches.add(new DictionaryMatch(split_password, configuration, start, end - 1, lower_rank, new ArrayList<>(), dictionary.isExclusion(), false, dictionary.getDictionaryName()));
                    }
                    // Only do unleet if it's different than the regular lower.
                    if(!unleet_part.equals(lower_part))
                    {
                        Integer unleet_rank = dictionary.getDictonary().get(unleet_part);
                        if (unleet_rank != null)
                        {
                            matches.add(new DictionaryMatch(split_password, configuration, start, end - 1, unleet_rank, subs, dictionary.isExclusion(), false, dictionary.getDictionaryName()));
                        }
                    }
                    // Only do reversed if it's different than the regular lower.
                    if(!reversed_part.equals(lower_part) && !reversed_part.equals(unleet_part))
                    {
                        Integer reversed_rank = dictionary.getDictonary().get(reversed_part);
                        if (reversed_rank != null)
                        {
                            matches.add(new DictionaryMatch(split_password, configuration, start, end - 1, reversed_rank, new ArrayList<>(), dictionary.isExclusion(), true, dictionary.getDictionaryName()));
                        }
                    }
                    // Only do reversed if it's different than unleet.
                    if(!reversed_unleet_part.equals(lower_part) && !reversed_unleet_part.equals(reversed_part) && !reversed_unleet_part.equals(reversed_part))
                    {
                        Integer reversed_unleet_rank = dictionary.getDictonary().get(reversed_unleet_part);
                        if (reversed_unleet_rank != null)
                        {
                            matches.add(new DictionaryMatch(split_password, configuration, start, end - 1, reversed_unleet_rank, subs, dictionary.isExclusion(), true, dictionary.getDictionaryName()));
                        }
                    }
                }
            }
        }
        // Return all the matches
        return matches;
    }

    /**
     * Removes all leet substitutions from the password and returns a plain text version.
     * @param configuration the configuration file used to estimate entropy.
     * @param password the password to translate from leet.
     * @return the password with all leet removed.
     */
    private static String translateLeet(final Configuration configuration, final String password)
    {
        char[] array = new char[password.length()];
        for (int i = 0; i < password.length(); i++)
        {
            Character replacement = configuration.getLeetTable().get(password.charAt(i));
            array[i] = replacement != null ? replacement : password.charAt(i);
        }
        return new String(array);
    }

    /**
     * Gets the substitutions for the password.
     * @param configuration the configuration file used to estimate entropy.
     * @param password the password to get leet substitutions for.
     * @return a {@code List} of {@code Character[]} that are the leet substitutions for the password.
     */
    private static List<Character[]> getLeetSub(final Configuration configuration, final String password)
    {
        List<Character[]> leet_subs = new ArrayList<>();
        for (int i = 0; i < password.length(); i++)
        {
            Character replacement = configuration.getLeetTable().get(password.charAt(i));
            if(replacement != null)
            {
                leet_subs.add(new Character[] {password.charAt(i), replacement});
            }
        }
        return leet_subs;
    }
}
