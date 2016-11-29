package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import org.junit.Test;

import java.util.List;

/**
 * @author Adam Brusselback
 */
public class SequenceMatcherTest
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
        PasswordMatcher matcher = new SequenceMatcher();

        password = "abcdefy";
        matches = matcher.match(configuration, password);

        assert matches.get(0).getToken().equals("abcdef");


        password = "aW3ennbfT6y";
        matches = matcher.match(configuration, password);

        assert matches.size() == 0;
    }

}
