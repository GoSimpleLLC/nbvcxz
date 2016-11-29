package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import org.junit.Test;

import java.util.List;

/**
 * @author Adam Brusselback
 */
public class YearMatcherTest
{
    final Configuration configuration = new ConfigurationBuilder().createConfiguration();

    /**
     * Test of match method, of class DateMatcher.
     */
    @Test
    public void testMatch()
    {

        List<Match> matches;
        String password;
        PasswordMatcher matcher = new YearMatcher();

        password = "abcdefy2010";
        matches = matcher.match(configuration, password);

        assert matches.get(0).getToken().equals("2010");


        password = "1950aW3ennbfT6y";
        matches = matcher.match(configuration, password);

        assert matches.get(0).getToken().equals("1950");


        password = "1850aW3ennbfT6y";
        matches = matcher.match(configuration, password);

        assert matches.size() == 0;
    }

}
