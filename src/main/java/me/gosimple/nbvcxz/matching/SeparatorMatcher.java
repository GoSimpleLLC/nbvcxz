package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.matching.match.SeparatorMatch;
import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract any likely separator within the password.
 *
 * @author Adam Brusselback
 */
public final class SeparatorMatcher implements PasswordMatcher
{

    private static final Pattern NON_ALPHA_NUMERIC = Pattern.compile("[^a-zA-Z\\d]");

    public List<Match> match(final Configuration configuration, final String password)
    {
        Matcher matcher = NON_ALPHA_NUMERIC.matcher(password);

        List<Match> matches = new ArrayList<>();

        if (password.length() <= 5)
        {
            return matches;
        }

        List<String> occurrences = new ArrayList<>();

        while (matcher.find())
        {
            if (matcher.start() != 0 && matcher.end() != password.length() - 1)
            {
                occurrences.add(matcher.group());
            }
        }

        int count = 0;
        String token = null;
        for (String new_token : new HashSet<>(occurrences))
        {
            int new_count = Collections.frequency(occurrences, new_token);
            if (new_count > count)
            {
                count = new_count;
                token = new_token;
            }
        }

        matcher.reset();
        while (matcher.find())
        {
            if (matcher.group().equals(token) && matcher.start() != 0 && matcher.end() != password.length() - 1)
            {
                matches.add(new SeparatorMatch(matcher.group(), configuration, matcher.start(), matcher.end() - 1));
            }
        }


        return matches;
    }
}
