package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.AdjacencyGraph;
import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ResourceBundle;

/**
 * @author Adam Brusselback
 */
public final class SpacialMatch extends BaseMatch
{


    private final AdjacencyGraph adjacencyGraph;
    private final int turns;
    private final int shiftedCount;


    /**
     * Create a new {@code SpacialMatch} which is a sequence of characters
     * following themselves on the keyboard/keypad
     *
     * @param match          the part of the password that was matched.
     * @param configuration  the {@link Configuration} object.
     * @param start_index    the starting index of this {@code SpacialMatch}.
     * @param end_index      the ending index of this {@code SpacialMatch}.
     * @param adjacencyGraph the graph used for this {@code SpacialMatch}.
     * @param turns          the number of turns in this {@code SpacialMatch}.
     * @param shiftedNumber  the number of shifts in this {@code SpacialMatch}.
     */
    public SpacialMatch(String match, Configuration configuration, int start_index, int end_index, AdjacencyGraph adjacencyGraph, int turns, int shiftedNumber)
    {
        super(match, configuration, start_index, end_index);
        this.adjacencyGraph = adjacencyGraph;
        this.turns = turns;
        this.shiftedCount = shiftedNumber;

        super.setEntropy(this.getEntropy());
    }

    private double getEntropy()
    {
        // Size of the keyboard used
        int size = adjacencyGraph.getKeyMap().size();

        // Average degree of the keyboard used
        double avgDegree = adjacencyGraph.getAverageDegree();

        double entropy = 0;
        // Estimated the number of possible patterns for the token length and
        // the number of turn
        long possibilities = 0;
        int length = getToken().length();
        for (int i = 2; i <= length; i++)
        {
            int possibleTurns = Math.min(turns, i - 1);
            for (int j = 1; j <= possibleTurns; j++)
            {
                possibilities += nCk(i - 1, j - 1) * size * Math.pow(avgDegree, j);
            }
        }
        entropy += Math.max(0, log2(possibilities));

        // Add extra entropy for the shifted keys
        possibilities = 0;
        if (shiftedCount > 0)
        {
            int unshiftedCount = length - shiftedCount;
            int min = Math.min(shiftedCount, unshiftedCount);
            for (int i = 0; i <= min; i++)
            {
                possibilities += nCk(length, i);
            }
        }
        entropy += Math.max(0, log2(possibilities));

        return entropy;
    }


    /**
     * @return the {@code AdjacencyGraph} used in this {@code SpacialMatch}.
     */
    public AdjacencyGraph getAdjacencyGraph()
    {
        return adjacencyGraph;
    }


    /**
     * @return the number of turns the user makes on the keyboard. 'zxcv' has a
     * turn of 1, 'zxcvfr' has a turn of 2, 'zxcvfrewq' has a turn of 3, etc.
     */
    public int getTurns()
    {
        return turns;
    }


    /**
     * @return the number of key that are shifted (% instead of 5, A instead of a)
     */
    public int getShiftedNumber()
    {
        return shiftedCount;
    }

    public String getDetails()
    {
        ResourceBundle mainResource = configuration.getMainResource();
        StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append(super.getDetails());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.spacialType")).append(" ").append(getAdjacencyGraph().getName());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.turns")).append(" ").append(getTurns());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.shifts")).append(" ").append(getShiftedNumber());
        return detailBuilder.toString();
    }
}
