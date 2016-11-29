package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.DigitMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Look for every part of the password that is digits, and longer than three digits in a row.
 *
 * @author Adam Brusselback
 */
public final class DigitMatcher implements PasswordMatcher
{
    public List<Match> match(final Configuration configuration, final String password)
    {
        Pattern pattern = Pattern.compile("\\d{3,}");
        Matcher matcher = pattern.matcher(password);

        List<Match> digitMatches = new ArrayList<>();

        while (matcher.find())
        {
            digitMatches.add(new DigitMatch(matcher.group(), configuration, matcher.start(), matcher.end() - 1));
        }

        return digitMatches;
    }
}
