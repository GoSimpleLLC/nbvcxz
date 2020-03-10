package me.gosimple.nbvcxz.resources;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Adam Brusselback
 */
public class AdjacencyGraphTest
{

    /**
     * Test of neighbors method, of class AdjacencyGraph.
     */
    @Test
    public void testNeighbors()
    {
        AdjacencyGraph graph = new AdjacencyGraph("Standard", AdjacencyGraphUtil.qwerty);
        Assert.assertEquals(Set.of('r', 'R', 'c', 'C', 'd', 'D', 't', 'T', 'v', 'V', 'g', 'G'), graph.getNeighbors(Character.valueOf('f')));
    }

    /**
     * Test of getTurns method, of class AdjacencyGraph.
     */
    @Test
    public void testGetTurns()
    {
        AdjacencyGraph graph = new AdjacencyGraph("Standard", AdjacencyGraphUtil.qwerty);
        Assert.assertEquals(2, graph.getTurns("aw3eft6"));
        Assert.assertEquals(0, graph.getTurns("qwerty"));
        Assert.assertEquals(0, graph.getTurns("4rfv"));
    }

    /**
     * Test of getShifts method, of class AdjacencyGraph.
     */
    @Test
    public void testGetShifts()
    {
        AdjacencyGraph graph = new AdjacencyGraph("Standard", AdjacencyGraphUtil.qwerty);
        Assert.assertEquals(0, graph.getShifts("aw3eft6"));
        Assert.assertEquals(3, graph.getShifts("aW3efT6"));
        Assert.assertEquals(3, graph.getShifts("AW3EFT6"));
        Assert.assertEquals(3, graph.getShifts("AW3EfT6"));
    }

    /**
     * Test of getAverageDegree method, of class AdjacencyGraph.
     */
    @Test
    public void testGetAverageDegree()
    {
        AdjacencyGraph graph = new AdjacencyGraph("Standard", AdjacencyGraphUtil.qwerty);
        Assert.assertEquals(4.595744680851064, graph.getAverageDegree(), 0.000000000000001);
    }

}
