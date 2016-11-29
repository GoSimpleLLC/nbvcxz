package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.Configuration;

/**
 * Contains only digits
 *
 * @author Adam Brusselback
 *
 */
public final class DigitMatch extends BaseMatch
{

    /**
     * Create a new {@code DigitMatch}
     *
     * @param match the {@code String} we are creating the {@code DigitMatch} from.
     * @param configuration the {@link Configuration} object.
     * @param start_index the start index in the password for this match.
     * @param end_index the end index in the password for this match.
     */
    public DigitMatch(String match, Configuration configuration, int start_index, int end_index)
    {
        super(match, configuration, start_index, end_index);
    }


    @Override
    public double calculateEntropy()
    {
        return Math.max(0, log2(Math.pow(10, getLength())));
    }
}
