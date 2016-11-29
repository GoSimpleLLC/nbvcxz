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
public class DigitMatchTest
{

    public DigitMatchTest()
    {
    }

    /**
     * Test of guessEntropy method, of class DigitMatch.
     */
    @Test
    public void testCalculateEntropy()
    {
        System.out.println("Test of guessEntropy method, of class DigitMatch");
        Configuration configuration = new ConfigurationBuilder().createConfiguration();

        HashMap<String, Double> fixture = new HashMap<>();
        fixture.put("2", 3.3219280948873626);
        fixture.put("45", 6.643856189774725);
        fixture.put("296", 9.965784284662087);
        fixture.put("2954", 13.28771237954945);
        fixture.put("01678", 16.609640474436812);
        fixture.put("394870", 19.931568569324174);
        fixture.put("9486034", 23.25349666421154);
        fixture.put("10037235", 26.5754247590989);
        fixture.put("923874291", 29.897352853986263);
        fixture.put("9041957412", 33.219280948873624);

        // Test the fixture
        for (Map.Entry<String, Double> entry : fixture.entrySet())
        {
            String password = entry.getKey();
            double expected = entry.getValue();
            double computed = new DigitMatch(password, configuration, 0, password.length() - 1).calculateEntropy();
            Assert.assertEquals(password, expected, computed, 0.000000000000001);
        }
    }

}
