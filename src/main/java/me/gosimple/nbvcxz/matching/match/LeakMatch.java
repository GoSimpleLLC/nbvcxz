package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ResourceBundle;

/**
 * @author Adam Brusselback
 */
public final class LeakMatch extends BaseMatch
{
    private int count;
    public LeakMatch(String match, Configuration configuration, int count, int start_index, int end_index)
    {
        super(match, configuration, start_index, end_index);

        this.count = count;

        super.setEntropy(getEntropy());
    }

    private double getEntropy()
    {
        return 0d;
    }

    public int getCount()
    {
        return count;
    }

    public String getDetails()
    {
        ResourceBundle mainResource = configuration.getMainResource();
        StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append(super.getDetails());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.count")).append(" ").append(getCount());
        detailBuilder.append("\n");
        return detailBuilder.toString();
    }


}
