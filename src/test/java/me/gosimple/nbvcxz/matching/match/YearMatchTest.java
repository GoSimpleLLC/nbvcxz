package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam Brusselback
 */
public class YearMatchTest
{

    public YearMatchTest()
    {
    }

    /**
     * Test of guessEntropy method, of class YearMatch.
     */
    @Test
    public void testCalculateEntropy()
    {
        System.out.println("Test of guessEntropy method, of class YearMatch");

        HashMap<String, Double> expectedMatch = new HashMap<>();
        expectedMatch.put("1900", 7.011227255423254d);
        expectedMatch.put("1982", 7.011227255423254d);
        expectedMatch.put("1990", 7.011227255423254d);
        expectedMatch.put("1993", 7.011227255423254d);
        expectedMatch.put("2000", 7.011227255423254d);
        expectedMatch.put("2007", 7.011227255423254d);
        expectedMatch.put("2012", 7.011227255423254d);
        expectedMatch.put("2016", 7.011227255423254d);
        expectedMatch.put("2019", 7.011227255423254d);

        // Test the fixture
        for (Map.Entry<String, Double> entry : expectedMatch.entrySet())
        {
            String password = entry.getKey();
            double expected = entry.getValue();
            double computed = new YearMatch(password, new ConfigurationBuilder().createConfiguration(), 0, password.length() - 1).calculateEntropy();
            Assert.assertEquals(password, expected, computed, 0.000000000000001);
        }
    }

}
