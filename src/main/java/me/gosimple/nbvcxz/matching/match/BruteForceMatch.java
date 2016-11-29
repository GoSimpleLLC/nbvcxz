package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.scoring.BruteForceUtil;

/**
 *
 * @author Adam Brusselback
 */
public final class BruteForceMatch extends BaseMatch
{
    /**
     * Create a new {@code BruteForceMatch}
     *
     * @param match the {@code String} we are creating the {@code BruteForceMatch} from.
     * @param configuration the {@link Configuration} object.
     * @param start_index the start index in the password for this match.
     * @param end_index the end index in the password for this match.
     */
    public BruteForceMatch(String match, Configuration configuration, int start_index, int end_index)
    {
        super(match, configuration, start_index, end_index);
    }

    @Override
    public double calculateEntropy()
    {
        int cardinality = BruteForceUtil.getBrutForceCardinality(getToken());
        return Math.max(0, log2(cardinality * getToken().length()));
    }
}
