package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.DateMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import org.junit.Test;

import java.util.List;

/**
 * @author Adam Brusselback
 */
public class DateMatcherTest
{
    final Configuration configuration = new ConfigurationBuilder().createConfiguration();

    /**
     * Test of match method, of class DateMatcher.
     */
    @Test
    public void testMatcher()
    {
        List<Match> matches;
        String password;
        PasswordMatcher matcher = new DateMatcher();

        password = "1900-12-12";
        matches = matcher.match(configuration, password);

        assert DateMatch.class.cast(matches.get(0)).getDay() == 1;
        assert DateMatch.class.cast(matches.get(0)).getMonth() == 12;
        assert DateMatch.class.cast(matches.get(0)).getYear() == 1900;
        assert DateMatch.class.cast(matches.get(0)).getSeparator().equals("-");
        assert DateMatch.class.cast(matches.get(0)).getStartIndex() == 0;
        assert DateMatch.class.cast(matches.get(0)).getEndIndex() == 8;
        assert DateMatch.class.cast(matches.get(0)).getLength() == 9;
        assert matches.get(0).getToken().equals("1900-12-1");

        assert DateMatch.class.cast(matches.get(1)).getDay() == 12;
        assert DateMatch.class.cast(matches.get(1)).getMonth() == 12;
        assert DateMatch.class.cast(matches.get(1)).getYear() == 1900;
        assert DateMatch.class.cast(matches.get(1)).getSeparator().equals("-");
        assert DateMatch.class.cast(matches.get(1)).getStartIndex() == 0;
        assert DateMatch.class.cast(matches.get(1)).getEndIndex() == 9;
        assert DateMatch.class.cast(matches.get(1)).getLength() == 10;
        assert matches.get(1).getToken().equals("1900-12-12");

        password = "12-12-1900";
        matches = matcher.match(configuration, password);

        assert DateMatch.class.cast(matches.get(3)).getDay() == 12;
        assert DateMatch.class.cast(matches.get(3)).getMonth() == 12;
        assert DateMatch.class.cast(matches.get(3)).getYear() == 1900;
        assert DateMatch.class.cast(matches.get(3)).getSeparator().equals("-");
        assert DateMatch.class.cast(matches.get(3)).getStartIndex() == 0;
        assert DateMatch.class.cast(matches.get(3)).getEndIndex() == 9;
        assert DateMatch.class.cast(matches.get(3)).getLength() == 10;
        assert matches.get(3).getToken().equals("12-12-1900");


        password = "0090";
        matches = matcher.match(configuration, password);

        assert matches.size() == 0;


        password = "12121990";
        matches = matcher.match(configuration, password);

        assert DateMatch.class.cast(matches.get(1)).getDay() == 12;
        assert DateMatch.class.cast(matches.get(1)).getMonth() == 12;
        assert DateMatch.class.cast(matches.get(1)).getYear() == 1990;
        assert DateMatch.class.cast(matches.get(1)).getSeparator().equals("");
        assert DateMatch.class.cast(matches.get(1)).getStartIndex() == 0;
        assert DateMatch.class.cast(matches.get(1)).getEndIndex() == 7;
        assert DateMatch.class.cast(matches.get(1)).getLength() == 8;
        assert matches.get(1).getToken().equals("12121990");


        assert DateMatch.class.cast(matches.get(2)).getDay() == 2;
        assert DateMatch.class.cast(matches.get(2)).getMonth() == 12;
        assert DateMatch.class.cast(matches.get(2)).getYear() == 1990;
        assert DateMatch.class.cast(matches.get(2)).getSeparator().equals("");
        assert DateMatch.class.cast(matches.get(2)).getStartIndex() == 1;
        assert DateMatch.class.cast(matches.get(2)).getEndIndex() == 7;
        assert DateMatch.class.cast(matches.get(2)).getLength() == 7;
        assert matches.get(2).getToken().equals("2121990");
    }

}
