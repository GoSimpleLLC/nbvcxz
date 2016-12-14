package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ResourceBundle;

/**
 * @author Adam Brusselback
 */
public final class SequenceMatch extends BaseMatch
{
    private final char firstCharacter;


    /**
     * Create a new {@code SequenceMatch}
     *
     * @param match         the {@code String} we are creating the {@code SequenceMatch} from.
     * @param configuration the {@link Configuration} object.
     * @param start_index   the start index in the password for this match.
     * @param end_index     the end index in the password for this match.
     */
    public SequenceMatch(String match, Configuration configuration, int start_index, int end_index)
    {
        super(match, configuration, start_index, end_index);
        this.firstCharacter = match.charAt(0);

        super.setEntropy(this.getEntropy());
    }


    private double getEntropy()
    {
        char firstChar = getFirstCharacter();
        double baseEntropy;

        // A sequence that starts with a 'a' or a '1' is very weak
        if (firstChar == 'a' || firstChar == '1')
        {
            baseEntropy = 1d;
        }
        // Digit sequence don't have a lot of entropy
        else if (Character.isDigit(firstChar))
        {
            baseEntropy = LOG_10;
        }
        // Alpha sequence have more entropy
        else if (Character.isLowerCase(firstChar))
        {
            baseEntropy = LOG_26;
        }
        // We give an extra bit of entropy for upper case sequence
        else
        {
            baseEntropy = LOG_26 + 1d;
        }

        return baseEntropy + log2(getLength());
    }


    /**
     * @return the first {@code char} of the sequence.
     */
    public char getFirstCharacter()
    {
        return firstCharacter;
    }

    public String getDetails()
    {
        ResourceBundle mainResource = configuration.getMainResource();
        StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append(super.getDetails());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.firstCharacter")).append(" ").append(getFirstCharacter());
        return detailBuilder.toString();
    }
}
