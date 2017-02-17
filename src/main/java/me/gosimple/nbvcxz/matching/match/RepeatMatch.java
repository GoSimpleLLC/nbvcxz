package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.BruteForceUtil;
import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ResourceBundle;

/**
 * @author Adam Brusselback
 */
public final class RepeatMatch extends BaseMatch
{
    private final String repeatingCharacters;
    private final int repeat;


    /**
     * Create a new {@code RepeatMatch}
     *
     * @param match               the {@code String} we are creating the {@code RepeatMatch} from.
     * @param configuration       the {@link Configuration} object.
     * @param repeatingCharacters the characters which were repeated
     * @param start_index         the start index in the password for this match.
     * @param end_index           the end index in the password for this match.
     */
    public RepeatMatch(String match, Configuration configuration, String repeatingCharacters, int start_index, int end_index)
    {
        super(match, configuration, start_index, end_index);
        this.repeatingCharacters = repeatingCharacters;
        this.repeat = match.length() / repeatingCharacters.length();

        super.setEntropy(this.getEntropy());
    }


    private double getEntropy()
    {
        int cardinality = BruteForceUtil.getBrutForceCardinality(getRepeatingCharacters());
        if (getRepeat() != getRepeatingCharacters().length())
        {
            return Math.max(0, log2(cardinality * getRepeat() * getRepeatingCharacters().length()));
        }
        else
        {
            return Math.max(0, log2(cardinality * getRepeat()));
        }
    }


    /**
     * @return the {@code char} that is repeated in the match.
     */
    public String getRepeatingCharacters()
    {
        return repeatingCharacters;
    }


    /**
     * @return {@code int} how many time the repeatingCharacters is repeating in the match.
     */
    public int getRepeat()
    {
        return repeat;
    }

    public String getDetails()
    {
        ResourceBundle mainResource = configuration.getMainResource();
        StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append(super.getDetails());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.repeatingCharacters")).append(" ").append(getRepeatingCharacters());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.repeated")).append(" ").append(getRepeat());
        return detailBuilder.toString();
    }
}
