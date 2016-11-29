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
public class SequenceMatchTest
{

    public SequenceMatchTest()
    {
    }

    /**
     * Test of guessEntropy method, of class SequenceMatch.
     */
    @Test
    public void testCalculateEntropy()
    {
        System.out.println("Test of guessEntropy method, "
                + "of class SequenceMatch");

        HashMap<String, Double> fixtureAsc = new HashMap<>();
        fixtureAsc.put("abcd", 3d);
        fixtureAsc.put("bcde", 6.700439718141093);
        fixtureAsc.put("klmnopqrstuv", 8.285402218862249);
        fixtureAsc.put("ABCD", 7.700439718141093);
        fixtureAsc.put("BCDE", 7.700439718141093);
        fixtureAsc.put("OPQRSTUVWXYZ", 9.285402218862249);
        fixtureAsc.put("1234", 3d);
        fixtureAsc.put("2345", 5.321928094887363);
        fixtureAsc.put("0123456789", 6.643856189774725);

        HashMap<String, Double> fixtureDesc = new HashMap<>();
        fixtureDesc.put("dcba", 6.700439718141093);
        fixtureDesc.put("edcb", 6.700439718141093);
        fixtureDesc.put("vutsrqponmlk", 8.285402218862249);
        fixtureDesc.put("DCBA", 7.700439718141093);
        fixtureDesc.put("EDCB", 7.700439718141093);
        fixtureDesc.put("ZYXWVUTSRQPO", 9.285402218862249);
        fixtureDesc.put("4321", 5.321928094887363);
        fixtureDesc.put("5432", 5.321928094887363);
        fixtureDesc.put("9876543210", 6.643856189774725);

        // Test the asc fixture
        testHelper(fixtureAsc);
        // Test the desc fixture
        testHelper(fixtureDesc);
    }

    private void testHelper(HashMap<String, Double> fixture)
    {
        Configuration configuration = new ConfigurationBuilder().createConfiguration();
        for (Map.Entry<String, Double> entry : fixture.entrySet())
        {
            String password = entry.getKey();
            double expected = entry.getValue();
            double computed = new SequenceMatch(password, configuration, 0, password.length() - 1).calculateEntropy();
            Assert.assertEquals(password, expected, computed, 0.000000000000001);
        }
    }

}
