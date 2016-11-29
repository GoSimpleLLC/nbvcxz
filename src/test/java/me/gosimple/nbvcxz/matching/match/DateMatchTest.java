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
public class DateMatchTest
{

    public DateMatchTest()
    {
    }

    /**
     * Test of guessEntropy method, of class DateMatch.
     */
    @Test
    public void testCalculateEntropy()
    {
        System.out.println("Test of guessEntropy method, of class DateMatch");
        Configuration configuration = new ConfigurationBuilder().createConfiguration();

        HashMap<DateMatch, Double> expectedMatches = new HashMap<>();
        expectedMatches.put(new DateMatch("0090", configuration, 0, 0, 1990, null, 0, 3), 15.550386066531285);
        expectedMatches.put(new DateMatch("0112", configuration, 1, 0, 2012, null, 0, 3), 15.550386066531285);
        expectedMatches.put(new DateMatch("12121990", configuration, 12, 12, 1990, null, 0, 7), 15.550386066531285);
        expectedMatches.put(new DateMatch("141023", configuration, 23, 10, 2014, null, 0, 5), 15.550386066531285);
        expectedMatches.put(new DateMatch("20141023", configuration, 10, 23, 2014, null, 0, 7), 15.550386066531285);
        expectedMatches.put(new DateMatch("0.0.1990", configuration, 0, 0, 1990, ".", 0, 7), 17.550386066531285);
        expectedMatches.put(new DateMatch("01/00/2012", configuration, 1, 0, 2012, "/", 0, 9), 17.550386066531285);
        expectedMatches.put(new DateMatch("1900-12-12", configuration, 12, 12, 1900, "-", 0, 9), 17.550386066531285);
        expectedMatches.put(new DateMatch("23,10,2014", configuration, 23, 10, 2014, ",", 0, 9), 17.550386066531285);
        expectedMatches.put(new DateMatch("10 23 14", configuration, 10, 23, 2014, " ", 0, 7), 17.550386066531285);

        // Test the fixture
        for (Map.Entry<DateMatch, Double> entry : expectedMatches.entrySet())
        {
            DateMatch match = entry.getKey();
            double expected = entry.getValue();
            double computed = match.calculateEntropy();
            Assert.assertEquals(match.getToken(), expected, computed, 0.000000000000001);
        }
    }

}
