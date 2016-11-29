package me.gosimple.nbvcxz.resources;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam Brusselback
 */
public class AdjacencyGraphUtilTest
{

    /**
     * Test of neighborsNumber method, of class AdjacencyGraphUtil.
     */
    @Test
    public void testNeighborsNumber()
    {
        System.out.println("Test of neighborsNumber method, of class AdjacencyGraphUtil");

        HashMap<String[], Integer> fixture = new HashMap<>();
        fixture.put(new String[]{}, 0);
        fixture.put(new String[]{null}, 0);
        fixture.put(new String[]{"a"}, 1);
        fixture.put(new String[]{""}, 1);
        fixture.put(new String[]{"a", "rsa", ""}, 3);
        fixture.put(new String[]{"a", null, "b"}, 2);
        fixture.put(new String[]{null, null, null}, 0);
        fixture.put(new String[]{null, "a", null, "b", null, null}, 2);

        // Test the fixture
        for (Map.Entry<String[], Integer> entry : fixture.entrySet())
        {
            String[] neighbors = entry.getKey();
            int expected = entry.getValue();
            int computed = AdjacencyGraphUtil.neighborsNumber(neighbors);
            Assert.assertEquals(expected, computed);
        }
    }

    /**
     * Test of calcAverageDegree method, of class AdjacencyGraphUtil.
     */
    @Test
    public void testCalcAverageDegree()
    {
        System.out.println("Test of calcAverageDegree method, of class AdjacencyGraphUtil");

        HashMap<HashMap<Character, String[]>, Double> fixture = new HashMap<>();
        fixture.put(AdjacencyGraphUtil.qwerty, 4.595744680851064);
        fixture.put(AdjacencyGraphUtil.standardKeypad, 5.066666666666666);

        // Test the fixture
        for (Map.Entry<HashMap<Character, String[]>, Double> entry : fixture.entrySet())
        {
            HashMap<Character, String[]> keys = entry.getKey();
            double expected = entry.getValue();
            double computed = AdjacencyGraphUtil.calcAverageDegree(keys);
            Assert.assertEquals(expected, computed, 0.000000000000001);
        }
    }

}
