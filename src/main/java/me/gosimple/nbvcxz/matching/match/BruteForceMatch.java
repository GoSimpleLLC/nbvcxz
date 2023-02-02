package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.BruteForceUtil;
import me.gosimple.nbvcxz.resources.Configuration;

/**
 * @author Adam Brusselback
 */
public final class BruteForceMatch extends BaseMatch
{
    /**
     * Create a new {@code BruteForceMatch}
     *
     * @param match         the {@code String} we are creating the {@code BruteForceMatch} from.
     * @param fullPassword  the full (truncated to max_length) password, used for cardinality calc.
     * @param configuration the {@link Configuration} object.
     * @param start_index         the start index in the password for this match.
     * @param end_index           the end index in the password for this match.
     */
    public BruteForceMatch(String match, String fullPassword, Configuration configuration, int start_index, int end_index)
    {
        super(match, configuration, start_index, end_index);
        super.setEntropy(getEntropy(fullPassword));
    }

    /**
     * Create a new {@code BruteForceMatch}
     *
     * @param match         the {@code String} we are creating the {@code BruteForceMatch} from.
     * @param fullPassword  the full (truncated to max_length) password, used for cardinality calc.
     * @param configuration the {@link Configuration} object.
     * @param index         the index in the password for this match.
     */
    public BruteForceMatch(char match, String fullPassword, Configuration configuration, int index)
    {
        super(Character.toString(match), configuration, index, index);
        super.setEntropy(getEntropy(fullPassword));
    }

    private double getEntropy(String fullPassword)
    {
        int cardinality = BruteForceUtil.getBruteForceCardinality(fullPassword);
        return Math.max(0, log2(cardinality) * getToken().length());
    }
}
