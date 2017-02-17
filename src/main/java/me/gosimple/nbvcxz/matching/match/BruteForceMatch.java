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
     * @param configuration the {@link Configuration} object.
     * @param index         the index in the password for this match.
     */
    public BruteForceMatch(char match, Configuration configuration, int index)
    {
        super(Character.toString(match), configuration, index, index);
        super.setEntropy(getEntropy(match));
    }

    private double getEntropy(char character)
    {
        int cardinality = BruteForceUtil.getBrutForceCardinality(character);
        return Math.max(0, log2(cardinality * getToken().length()));
    }
}
