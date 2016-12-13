package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.matching.match.SequenceMatch;
import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Look for every part of the password that is a sequence (abc, 123)
 *
 * @author Adam Brusselback
 */
public final class SequenceMatcher implements PasswordMatcher
{
    public List<Match> match(final Configuration configuration, final String password)
    {
        List<Match> matches = new ArrayList<>();
        char[] characters = password.toCharArray();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < characters.length; i++)
        {
            Character current_character = characters[i];
            if (i + 1 < characters.length)
            {
                Character next_character = characters[i + 1];
                //Alpha upper case
                if (next_character >= 65 && next_character <= 90)
                {
                    //Forward matches
                    if (next_character == current_character + 1)
                    {
                        builder.append(current_character);
                        continue;
                    }
                    if (next_character + 32 == current_character + 1)
                    {
                        builder.append(current_character);
                        continue;
                    }
                    //Reverse matches
                    if (next_character == current_character - 1)
                    {
                        builder.append(current_character);
                        continue;
                    }
                    if (next_character + 32 == current_character - 1)
                    {
                        builder.append(current_character);
                        continue;
                    }
                }
                //Alpha lower case
                if (next_character >= 97 && next_character <= 122)
                {
                    //Forward matches
                    if (next_character == current_character + 1)
                    {
                        builder.append(current_character);
                        continue;
                    }
                    if (next_character - 32 == current_character + 1)
                    {
                        builder.append(current_character);
                        continue;
                    }
                    //Reverse matches
                    if (next_character == current_character - 1)
                    {
                        builder.append(current_character);
                        continue;
                    }
                    if (next_character - 32 == current_character - 1)
                    {
                        builder.append(current_character);
                        continue;
                    }
                }
                //Numeric
                if (next_character >= 48 && next_character <= 57)
                {
                    //Forward matches
                    if (next_character == current_character + 1)
                    {
                        builder.append(current_character);
                        continue;
                    }
                    //Reverse matches
                    if (next_character == current_character - 1)
                    {
                        builder.append(current_character);
                        continue;
                    }
                }
            }

            if (builder.length() > 0)
            {
                builder.append(current_character);
                if (builder.length() > 2)
                {
                    matches.add(new SequenceMatch(builder.toString(), configuration, i - builder.length() + 1, i));
                }
                builder.setLength(0);
            }
        }

        return matches;
    }
}
