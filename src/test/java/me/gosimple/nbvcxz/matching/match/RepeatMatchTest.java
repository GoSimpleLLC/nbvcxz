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
public class RepeatMatchTest
{

    public RepeatMatchTest()
    {
    }

    /**
     * Test of guessEntropy method, of class RepeatMatch.
     */
    @Test
    public void testCalculateEntropy()
    {
        System.out.println("Test of guessEntropy method, of class RepeatMatch");

        HashMap<String, Double> entropyMap = new HashMap<>();
        HashMap<String, String> repeatMap = new HashMap<>();
        entropyMap.put("a", 4.700439718141093);
        repeatMap.put("a", "a");
        entropyMap.put("aaaaaaaaaa", 8.022367813028454);
        repeatMap.put("aaaaaaaaaa", "a");
        entropyMap.put("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 10.022367813028454);
        repeatMap.put("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "a");
        entropyMap.put("A", 4.700439718141093);
        repeatMap.put("A", "A");
        entropyMap.put("AAAAAAAAAA", 8.022367813028454);
        repeatMap.put("AAAAAAAAAA", "A");
        entropyMap.put("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", 10.022367813028454);
        repeatMap.put("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "A");
        entropyMap.put("0", 3.3219280948873626);
        repeatMap.put("0", "0");
        entropyMap.put("0000000000", 6.643856189774725);
        repeatMap.put("0000000000", "0");
        entropyMap.put("0000000000000000000000000000000000000000", 8.643856189774725);
        repeatMap.put("0000000000000000000000000000000000000000", "0");
        entropyMap.put("@", 5.044394119358453);
        repeatMap.put("@", "@");
        entropyMap.put("@@@@@@@@@@", 8.366322214245816);
        repeatMap.put("@@@@@@@@@@", "@");
        entropyMap.put("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", 10.366322214245816);
        repeatMap.put("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "@");
        entropyMap.put("é", 6.643856189774725);
        repeatMap.put("é", "é");
        entropyMap.put("éééééééééé", 9.965784284662087);
        repeatMap.put("éééééééééé", "é");
        entropyMap.put("éééééééééééééééééééééééééééééééééééééééé", 11.965784284662087);
        repeatMap.put("éééééééééééééééééééééééééééééééééééééééé", "é");
        entropyMap.put("igfigf", 7.285402218862249);
        repeatMap.put("igfigf", "igf");
        entropyMap.put("igfigfigf", 6.285402218862249);
        repeatMap.put("igfigfigf", "igf");
        entropyMap.put("6p", 6.169925001442312);
        repeatMap.put("6p", "6p");
        entropyMap.put("6p6p", 6.169925001442312);
        repeatMap.put("6p6p", "6p");
        entropyMap.put("6p6p6p", 7.754887502163469);
        repeatMap.put("6p6p6p", "6p");
        entropyMap.put("6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p", 10.339850002884624);
        repeatMap.put("6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p6p", "6p");

        // Test the fixture
        Configuration configuration = new ConfigurationBuilder().createConfiguration();
        for (Map.Entry<String, Double> entry : entropyMap.entrySet())
        {
            String password = entry.getKey();
            double expected = entry.getValue();
            double computed = new RepeatMatch(password, configuration, repeatMap.get(password), 0, password.length() - 1).calculateEntropy();
            Assert.assertEquals(password, expected, computed, 0.000000000000001);
        }
    }

}
