package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.AdjacencyGraph;
import me.gosimple.nbvcxz.resources.AdjacencyGraphUtil;
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
        AdjacencyGraph qwerty = new AdjacencyGraph("Qwerty", AdjacencyGraphUtil.qwerty);
        AdjacencyGraph keypad = new AdjacencyGraph("Keypad", AdjacencyGraphUtil.standardKeypad);

        HashMap<SpacialMatch, Double> expectedMatch = new HashMap<>();
        expectedMatch.put(new SpacialMatch("zxcvbn", configuration, 0, 6, qwerty, 1, 0), 11.07681559705083d);
        expectedMatch.put(new SpacialMatch("qwER43@!", configuration, 0, 6, qwerty, 3, 4), 26.439812245844823d);
        expectedMatch.put(new SpacialMatch("43@!", configuration, 0, 3, qwerty, 1, 1), 12.661778097771986d);
        expectedMatch.put(new SpacialMatch("23.", configuration, 0, 2, keypad, 2, 0), 9.848622940429339d);
        expectedMatch.put(new SpacialMatch("cde", configuration, 0, 2, qwerty, 1, 0), 9.754887502163468d);
        expectedMatch.put(new SpacialMatch("fgh", configuration, 0, 2, qwerty, 1, 0), 9.754887502163468d);

        // Test the fixture
        for (Map.Entry<SpacialMatch, Double> entry : expectedMatch.entrySet())
        {
            SpacialMatch match = entry.getKey();
            double expected = entry.getValue();
            double computed = match.calculateEntropy();

            Assert.assertEquals(match.getToken(), expected, computed, 0.000000000000001);
        }
    }

}
