package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.matching.match.RepeatMatch;
import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Look for every part of the password that is a repeat of the previous character.
 *
 * @author Adam Brusselback
 */
public final class RepeatMatcher implements PasswordMatcher
{
    public List<Match> match(final Configuration configuration, final String password)
    {
        List<Match> matches = new ArrayList<>();

        Pattern greedy = Pattern.compile("(.+)\\1+");
        Pattern lazy = Pattern.compile("(.+?)\\1+");
        Pattern lazyAnchored = Pattern.compile("^(.+?)\\1+$");
        int lastIndex = 0;
        Matcher greedyMatch = greedy.matcher(password);
        Matcher lazyMatch = lazy.matcher(password);
        while (lastIndex < password.length())
        {
            if (!greedyMatch.find())
            {
                break;
            }
            Matcher match;
            String baseToken;
            String repeatCharacters;
            if (greedyMatch.group(0).length() > (lazyMatch.find() ? lazyMatch.group(0).length() : 0))
            {
                match = greedyMatch;
                Matcher matcher = lazyAnchored.matcher(match.group(0));
                baseToken = matcher.find() ? matcher.group(0) : match.group(0);
                repeatCharacters = matcher.find() ? matcher.group(1) : match.group(1);
            }
            else
            {
                match = lazyMatch;
                baseToken = match.group(0);
                repeatCharacters = match.group(1);
            }
            int startIndex = match.start(0);
            int endIndex = match.end(0) - 1;

            Set<Character> character_set = new HashSet<>();
            for (char character : repeatCharacters.toCharArray())
            {
                character_set.add(character);
            }
            if (character_set.size() <= 4)
            {
                matches.add(new RepeatMatch(baseToken, configuration, repeatCharacters, startIndex, endIndex));
            }
            lastIndex = endIndex + 1;
        }
        return matches;
    }
}
