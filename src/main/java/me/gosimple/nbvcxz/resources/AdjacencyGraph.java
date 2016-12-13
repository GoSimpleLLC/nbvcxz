package me.gosimple.nbvcxz.resources;

import java.util.HashMap;

/**
 * @author Adam Brusselback.
 */
public class AdjacencyGraph
{
    private final HashMap<Character, String[]> keyMap;
    private final String name;

    /**
     * @param name   the name of the graph
     * @param keyMap the keyMap for the graph
     */
    public AdjacencyGraph(String name, HashMap<Character, String[]> keyMap)
    {
        this.name = name;
        this.keyMap = keyMap;
    }

    /**
     * @return The key map for this adjacency graph
     */
    public HashMap<Character, String[]> getKeyMap()
    {
        return keyMap;
    }


    /**
     * Calculates the average "degree" of a keyboard or keypad. On the qwerty
     * keyboard, 'g' has degree 6, being adjacent to 'ftyhbv' and '\' has degree 1.
     *
     * @return the average degree for this keyboard or keypad
     */
    public double getAverageDegree()
    {
        return AdjacencyGraphUtil.calcAverageDegree(keyMap);
    }

    /**
     * @return Returns the name
     */
    public String getName()
    {
        return name;
    }
}
