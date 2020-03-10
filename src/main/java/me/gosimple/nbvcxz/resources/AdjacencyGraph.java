package me.gosimple.nbvcxz.resources;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        double average = 0d;
        for (Map.Entry<Character, String[]> entry : getKeyMap().entrySet())
        {
            average += neighborsNumber(entry.getValue());
        }
        return average / (double) getKeyMap().size();
    }

    /**
     * @return Returns the name
     */
    public String getName()
    {
        return name;
    }


    /**
     * Count how many neighbors (non null values) a key has
     *
     * @param neighbors the neighbors
     * @return the number of neighbors
     */
    private int neighborsNumber(String[] neighbors)
    {
        int sum = 0;
        for (String s : neighbors)
        {
            if (s != null)
            {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Returns a set of neighbors for a specific character.
     *
     * @param key            the character you are getting neighbors for.
     * @return A set of characters which are neighbors to the passed in character.
     */
    public Set<Character> getNeighbors(final Character key)
    {
        final Set<Character> neighbors = new HashSet<>();

        if (getKeyMap().containsKey(key))
        {
            String[] tmp_neighbors = getKeyMap().get(key);
            for (final String tmp_neighbor : tmp_neighbors)
            {
                if (null == tmp_neighbor)
                {
                    continue;
                }
                for (Character character : tmp_neighbor.toCharArray())
                {
                    neighbors.add(character);
                }
            }
        }
        return neighbors;
    }


    /**
     * Returns the number of turns in the part passed in based on the adjacency graph.
     * A turn is the number of times the part changes directions.
     *
     * @param part           the string you are getting turns for.
     * @return the number of turns in this string for the {@code AdjacencyGraph}
     */
    public int getTurns(final String part)
    {
        int direction = 0;
        int turns = 0;
        char[] parts = part.toCharArray();

        for (int i1 = 0; i1 < parts.length; i1++)
        {
            Character character = parts[i1];
            if (i1 + 1 >= parts.length)
            {
                continue;
            }

            Character next_character = parts[i1 + 1];
            if (getKeyMap().containsKey(character))
            {
                String[] tmp_neighbors = getKeyMap().get(character);
                for (int i2 = 0; i2 < tmp_neighbors.length; i2++)
                {
                    if (tmp_neighbors[i2] == null)
                    {
                        continue;
                    }
                    for (Character neighbor_char : tmp_neighbors[i2].toCharArray())
                    {
                        if (next_character.equals(neighbor_char))
                        {
                            if (direction == 0)
                            {
                                direction = i2;
                            }
                            else if (direction != i2)
                            {
                                turns++;
                                direction = i2;
                            }
                        }
                    }
                }
            }
        }
        return turns;
    }

    /**
     * Returns the number of shifts in case in the part passed in.
     * A shift is the number of times the shift key would have to be held to type the part.
     *
     * @param part           the string you are getting shifts for.
     * @return the number of shifts in this string for the {@code AdjacencyGraph}
     */
    public int getShifts(final String part)
    {
        int current_shift = -1;
        int shifts = 0;
        char[] parts = part.toCharArray();

        for (int i1 = 0; i1 < parts.length; i1++)
        {
            Character character = parts[i1];
            if (i1 + 1 >= parts.length)
            {
                continue;
            }

            Character next_character = parts[i1 + 1];

            if (getKeyMap().containsKey(character))
            {
                String[] tmp_neighbors = getKeyMap().get(character);
                for (final String tmp_neighbor : tmp_neighbors)
                {
                    if (tmp_neighbor == null)
                    {
                        continue;
                    }
                    int i = 0;
                    for (Character neighbor_char : tmp_neighbor.toCharArray())
                    {
                        if (next_character.equals(neighbor_char))
                        {
                            if (current_shift == -1)
                            {
                                current_shift = i;
                            }
                            else if (current_shift != i)
                            {
                                shifts++;
                                current_shift = i;
                            }
                        }
                        i++;
                    }
                }
            }
        }
        return shifts;
    }
}
