package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Adam Brusselback
 */
public class DictionaryMatchTest
{


    /**
     * Test of guessEntropy method, of class DictionaryMatch.
     */
    @Test
    public void testCalculateEntropy()
    {
        System.out.println("Test of guessEntropy method, of class DictionaryMatch");

        Configuration configuration = new ConfigurationBuilder().createConfiguration();

        ArrayList<Character[]> sub = new ArrayList<>();
        double entropy;
        double tolerance = 0.01D;
        DictionaryMatch match;
/*
        match = new DictionaryMatch("zxcv", configuration, 0, 3, 1028, sub, false, false, "test");
        entropy = match.calculateEntropy();
        Assert.assertEquals(match.getToken(), 10.005624549193877D, entropy, 10.005624549193877D * tolerance);
        sub.clear();

        match = new DictionaryMatch("zxcvb", configuration, 0, 4, 998, sub, false, false, "test");
        entropy = match.calculateEntropy();
        Assert.assertEquals(match.getToken(), 9.962896005337262D, entropy, 9.962896005337262D * tolerance);
        sub.clear();

        match = new DictionaryMatch("987654321", configuration, 0, 4, 1104, sub, false, false, "test");
        entropy = match.calculateEntropy();
        Assert.assertEquals(match.getToken(), 10.108524456778168D, entropy, 10.108524456778168D * tolerance);
        sub.clear();

        match = new DictionaryMatch("staple", configuration, 0, 4, 14066, sub, false, false, "test");
        entropy = match.calculateEntropy();
        Assert.assertEquals(match.getToken(), 13.779924501967908D, entropy, 13.779924501967908D * tolerance);
        sub.clear();

        sub.add(new Character[] {'3', 'e'});
        sub.add(new Character[] {'4', 'a'});
        match = new DictionaryMatch("R43", configuration, 0, 4, 716, sub, false, false, "test");
        entropy = match.calculateEntropy();
        Assert.assertEquals(match.getToken(), 11.483815777264256D, entropy, 11.483815777264256D * tolerance);
        sub.clear();

        sub.add(new Character[] {'0', 'o'});
        match = new DictionaryMatch("h0rs", configuration, 0, 4, 7646, sub, false, false, "test");
        entropy = match.calculateEntropy();
        Assert.assertEquals(match.getToken(), 13.900489484834651D, entropy, 13.900489484834651D * tolerance);
        sub.clear();
 */
    }


}
