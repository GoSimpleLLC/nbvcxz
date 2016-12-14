package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ResourceBundle;

/**
 * @author Adam Brusselback
 */
public final class DateMatch extends BaseMatch
{


    private final int day;
    private final int month;
    private final int year;
    private final String separator;


    /**
     * Create a new {@code DateMatch}
     *
     * @param match         the {@code String} we are creating the {@code DateMatch} from.
     * @param configuration the {@link Configuration} object.
     * @param day           the day of month
     * @param month         the month
     * @param year          the year
     * @param separator     separator used
     * @param start_index   the start index in the password for this match.
     * @param end_index     the end index in the password for this match.
     */
    public DateMatch(String match, Configuration configuration, int day, int month, int year, String separator, int start_index, int end_index)
    {
        super(match, configuration, start_index, end_index);
        this.day = day;
        this.month = month;
        this.year = year;
        this.separator = separator;

        super.setEntropy(getEntropy());
    }

    private double getEntropy()
    {
        double entropy;

        // Two digits year
        if (getYear() < 100)
        {
            entropy = LOG_37200; // 31 * 12 * 100
        }
        // Four digits year
        else
        {
            entropy = LOG_47988; // 31 * 12 * 129
        }

        // Add two bits of entropy if there is a separator
        if (!(separator == null || separator.isEmpty()))
        {
            entropy += 2;
        }

        return entropy;
    }


    /**
     * @return the {@code int} day of the match.
     */
    public int getDay()
    {
        return day;
    }


    /**
     * @return the {@code int} month of the match.
     */
    public int getMonth()
    {
        return month;
    }


    /**
     * @return the {@code int} year of the match.
     */
    public int getYear()
    {
        return year;
    }


    /**
     * @return the {@code String} character between each date component
     */
    public String getSeparator()
    {
        return separator;
    }

    public String getDetails()
    {
        ResourceBundle mainResource = configuration.getMainResource();
        StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append(super.getDetails());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.year")).append(" ").append(getYear());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.month")).append(" ").append(getMonth());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.day")).append(" ").append(getDay());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.separator")).append(" ").append(getSeparator());
        return detailBuilder.toString();
    }


}
