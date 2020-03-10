package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ResourceBundle;

/**
 * Abstract class which takes care of a lot of the boiler plate for implementing {@link Match}.
 *
 * @author Adam Brusselback
 */
public abstract class BaseMatch implements Match
{
    // Precomputed log values used during etropy calculation
    protected static final double LOG_2 = Math.log(2d);
    protected static final double LOG_10 = log2(10d);
    protected static final double LOG_26 = log2(26d);
    protected static final double LOG_129 = log2(129d);
    protected static final double LOG_37200 = log2(37200d);
    protected static final double LOG_47988 = log2(47988d);
    protected final Configuration configuration;
    private final String token;
    private final int start_index;
    private final int end_index;
    private double entropy;


    /**
     * Create a new {@code BaseMatch}
     *
     * @param match         the {@code String} we are creating the {@code BaseMatch} from.
     * @param configuration the {@link Configuration} object.
     * @param start_index   the start index in the password for this match.
     * @param end_index     the end index in the password for this match.
     */
    public BaseMatch(String match, Configuration configuration, int start_index, int end_index)
    {
        if (match == null)
        {
            throw new IllegalArgumentException("Null String");
        }
        if (match.isEmpty())
        {
            throw new IllegalArgumentException("Empty String");
        }
        this.token = match;
        this.configuration = configuration;
        this.start_index = start_index;
        this.end_index = end_index;
    }

    /**
     * Calculate the base 2 logarithm of a value
     *
     * @param value the {@code double} we are calculating the log from
     * @return double
     */
    protected static double log2(double value)
    {
        return Math.log(value) / LOG_2;
    }

    /**
     * Calculate binomial coefficients (the number of possible "choose k among n")
     *
     * @param n the total size of the set
     * @param k the size of the selection
     * @return the binomial coefficient
     */
    protected static long nCk(int n, int k)
    {
        if (k > n)
        {
            return 0;
        }
        long result = 1;
        for (int i = 1; i <= k; i++)
        {
            result *= n--;
            result /= i;
        }
        return result;
    }

    protected void setEntropy(double entropy)
    {
        this.entropy = entropy;
    }

    @Override
    final public double calculateEntropy()
    {
        return Math.max(0, entropy);
    }

    @Override
    public double getAverageEntropy()
    {
        return calculateEntropy() / getLength();
    }

    @Override
    public String getToken()
    {
        return this.token;
    }

    @Override
    public int getStartIndex()
    {
        return this.start_index;
    }

    @Override
    public int getEndIndex()
    {
        return this.end_index;
    }

    @Override
    public int getLength()
    {
        return this.token.length();
    }

    public String getDetails()
    {
        ResourceBundle mainResource = configuration.getMainResource();
        StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append(mainResource.getString("main.match.matchType")).append(" ").append(this.getClass().getSimpleName());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.entropy")).append(" ").append(calculateEntropy());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.token")).append(" ").append(getToken());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.startIndex")).append(" ").append(getStartIndex());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.endIndex")).append(" ").append(getEndIndex());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.length")).append(" ").append(getLength());
        return detailBuilder.toString();
    }
}
