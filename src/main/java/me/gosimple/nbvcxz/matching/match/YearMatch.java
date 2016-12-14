package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.Configuration;

/**
 * @author Adam Brusselback
 */
public final class YearMatch extends BaseMatch
{


    /**
     * Create a new {@code YearMatch}
     *
     * @param match         the {@code String} we are creating the {@code YearMatch} from.
     * @param configuration the {@link Configuration} object.
     * @param start_index   the start index in the password for this match.
     * @param end_index     the end index in the password for this match.
     */
    public YearMatch(String match, Configuration configuration, int start_index, int end_index)
    {
        super(match, configuration, start_index, end_index);

        super.setEntropy(this.getEntropy());
    }


    private double getEntropy()
    {
        return LOG_129;
    }
}
