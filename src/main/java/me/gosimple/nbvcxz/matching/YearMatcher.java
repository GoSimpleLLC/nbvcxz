package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.matching.match.YearMatch;
import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Look for every part of the password that matches the year pattern.
 *
 * @author Adam Brusselback
 */
public final class YearMatcher implements PasswordMatcher
{
    public List<Match> match(final Configuration configuration, final String password)
    {
        Pattern pattern = configuration.getYearPattern();
        Matcher matcher = pattern.matcher(password);

        List<Match> matches = new ArrayList<>();

        while (matcher.find())
        {
            matches.add(new YearMatch(matcher.group(), configuration, matcher.start(), matcher.end() - 1));
        }

        return matches;
    }
}
