package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam Brusselback
 */
public class SpacialMatchTest
{
    final Configuration configuration = new ConfigurationBuilder().createConfiguration();

    /**
     * Test of guessEntropy method, of class SpacialMatch.
     */
    @Test
    public void testCalculateEntropy()
    {
        System.out.println("Test of guessEntropy method, of class SpacialMatch");

        HashMap<SpacialMatch, Double> fixture = new HashMap<>();
        /*fixture.put(new SpacialMatch("zxcvbn", 0, 6, true, 1, 0), 11.07681559705083d);
        fixture.put(new SpacialMatch("qwER43@!", 0, 0, true, 3, 4), 26.439812245844823d);
        fixture.put(new SpacialMatch("43@!", 0, 0, true, 1, 2), 13.799281621521923d);
        fixture.put(new SpacialMatch("cth", 0, 0, true, 2, 0), 12.239001757765582d);
        fixture.put(new SpacialMatch("23.", 0, 0, false, 2, 0), 9.848622940429339d);
        fixture.put(new SpacialMatch("cde", 0, 0, true, 1, 0), 9.754887502163468d);
        fixture.put(new SpacialMatch("fgh", 0, 0, true, 1, 0), 9.754887502163468d);
        fixture.put(new SpacialMatch("ijk", 0, 0, true, 2, 0), 12.239001757765582d);
        fixture.put(new SpacialMatch("987", 0, 0, false, 1, 0), 7.247927513443586d);
        fixture.put(new SpacialMatch("654", 0, 0, false, 1, 0), 7.247927513443586d);
        fixture.put(new SpacialMatch("987654321", 0, 0, true, 1, 0), 11.75488750216347d);
        fixture.put(new SpacialMatch("123654789", 0, 0, true, 1, 0), 11.75488750216347d);
        fixture.put(new SpacialMatch("321", 0, 0, true, 1, 0), 9.754887502163468d);
        fixture.put(new SpacialMatch("asdfghju7654rewq", 0, 0, true, 5, 0), 29.782050791593228d);
        fixture.put(new SpacialMatch("BgbH", 0, 0, true, 3, 1), 17.5580170513842d);*/

        // Test the fixture
        for (Map.Entry<SpacialMatch, Double> entry : fixture.entrySet())
        {
            SpacialMatch match = entry.getKey();
            double expected = entry.getValue();
            double computed = match.calculateEntropy();

            Assert.assertEquals(match.getToken(), expected, computed, 0.000000000000001);
        }
    }

}
