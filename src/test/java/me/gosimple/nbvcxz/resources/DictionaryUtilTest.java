package me.gosimple.nbvcxz.resources;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam Brusselback
 */
public class DictionaryUtilTest
{

    /**
     * Test of loadFile method, of class DictionaryUtil.
     */
    @Test
    public void testLoadFile()
    {
        System.out.println("Test of loadFile method, of class DictionaryUtil");

        HashMap<String, Integer> fixture = new HashMap<>();
        fixture.put("123456", 1);
        fixture.put("27sfd83", null);
        fixture.put("dragon", 10);

        // Test the fixture
        for (Map.Entry<String, Integer> entry : fixture.entrySet())
        {
            String value = entry.getKey();
            Integer expected = entry.getValue();
            Integer computed = DictionaryUtil.loadRankedDictionary(DictionaryUtil.passwords).get(value);
            Assert.assertEquals(expected, computed);
        }
    }

}
