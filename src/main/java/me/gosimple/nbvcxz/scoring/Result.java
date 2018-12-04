package me.gosimple.nbvcxz.scoring;

import me.gosimple.nbvcxz.matching.match.BruteForceMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.Feedback;
import me.gosimple.nbvcxz.resources.FeedbackUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * This class contains all info about the entropy calculation.
 *
 * @author Adam Brusselback
 */
public class Result
{
    final Configuration configuration;
    final String password;
    final List<Match> matches;

    /**
     * @param configuration the {@link Configuration} object.
     * @param password      the password this result was generated for
     * @param matches       list of matches which when combined make up the original password
     * @throws IllegalStateException if the matches do not equal the original password, this will be thrown.
     */
    public Result(final Configuration configuration, final String password, final List<Match> matches) throws IllegalStateException
    {
        this.configuration = configuration;
        this.password = password;
        this.matches = matches;

        if (!this.isValid())
        {
            throw new IllegalStateException("There was an unexpected error and all of the matches put together do not equal the original password.");
        }
    }

    /**
     * Checks if the sum of the matches equals the original password.
     *
     * @return {@code true} if valid; {@code false} if invalid.
     */
    private boolean isValid()
    {
        StringBuilder builder = new StringBuilder();
        for (Match match : matches)
        {
            builder.append(match.getToken());
        }

        return password.equals(builder.toString());
    }

    /**
     * Returns the entropy for this {@code Result}.
     *
     * @return the estimated entropy as a {@code double}.
     */
    public Double getEntropy()
    {
        double entropy = 0;
        for (Match match : matches)
        {
            entropy += match.calculateEntropy();
        }
        return entropy;
    }

    /**
     * The estimated number of tries required to crack this password
     *
     * @return the estimated number of guesses as a {@code BigDecimal}
     */
    public BigDecimal getGuesses()
    {
        final Double guesses_tmp = Math.pow(2, getEntropy());
        return new BigDecimal(guesses_tmp.isInfinite() ? Double.MAX_VALUE : guesses_tmp).setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * The matches that comprise this result.
     *
     * @return a {@code List} of {@code Match} that were used in this result estimation.
     */
    public List<Match> getMatches()
    {
        return this.matches;
    }

    /**
     * The original password passed in.
     *
     * @return {@code String} of the original password.
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Returns whether the minimum entropy specified in the config was met.
     *
     * @return {@code true} if minimum entropy is met; {@code false} if not.
     */
    public boolean isMinimumEntropyMet()
    {
        return this.getEntropy().compareTo(configuration.getMinimumEntropy()) >= 0;
    }

    /**
     * Returns whether the password is considered to be random.
     *
     * @return true if the password is considered random, false otherwise.
     */
    public boolean isRandom()
    {
        boolean is_random = true;
        for (Match match : matches)
        {
            if (!(match instanceof BruteForceMatch))
            {
                is_random = false;
                break;
            }
        }
        return is_random;
    }

    /**
     * Returns the configuration used to generate this result.
     *
     * @return {@code Configuration} that was used to generate this {@code Result}.
     */
    public Configuration getConfiguration()
    {
        return configuration;
    }

    /**
     * Returns feedback to the user to suggest ways to improve their password.
     *
     * @return a {@code Feedback} object with suggestions for the user.
     */
    public Feedback getFeedback()
    {
        return FeedbackUtil.getFeedback(this);
    }

    /**
     * This scoring function returns an int from 0-4 to indicate the score of this password
     * using the same semantics as zxcvbn.
     *
     * @return Score
     * <br>0: risky password: "too guessable"
     * <br>1: modest protection from throttled online attacks: "very guessable"
     * <br>2: modest protection from unthrottled online attacks: "somewhat guessable"
     * <br>3: modest protection from offline attacks: "safely unguessable" (assuming a salted, slow hash function)
     * <br>4: strong protection from offline attacks: "very unguessable" (assuming a salted, slow hash function)
     */
    public int getBasicScore()
    {
        final BigDecimal guesses = getGuesses();
        if (guesses.compareTo(BigDecimal.valueOf( 1e3)) == -1)
            return 0;
        else if (guesses.compareTo(BigDecimal.valueOf( 1e6)) == -1)
            return 1;
        else if (guesses.compareTo(BigDecimal.valueOf(1e8)) == -1)
            return 2;
        else if (guesses.compareTo(BigDecimal.valueOf(1e10)) == -1)
            return 3;
        else
            return 4;
    }
}