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
    final String full_password;
    final List<Match> matches;
    final boolean timed_out;

    /**
     * @param configuration the {@link Configuration} object.
     * @param password      the password this result was generated for
     * @param full_password the untruncated password this result was generated for
     * @param matches       list of matches which when combined make up the original password
     * @throws IllegalStateException if the matches do not equal the original password, this will be thrown.
     */
    public Result(final Configuration configuration, final String password, final String full_password, final List<Match> matches) throws IllegalStateException
    {
        this(configuration, password, full_password, matches, false);
    }

    /**
     * @param configuration the {@link Configuration} object.
     * @param password      the password this result was generated for
     * @param full_password the untruncated password this result was generated for
     * @param matches       list of matches which when combined make up the original password
     * @param timed_out     whether the estimate ran out of time before it finished looking for matches
     * @throws IllegalStateException if the matches do not equal the original password, this will be thrown.
     */
    public Result(final Configuration configuration, final String password, final String full_password, final List<Match> matches, final boolean timed_out) throws IllegalStateException
    {
        this.configuration = configuration;
        this.password = password;
        this.full_password = full_password;
        this.matches = matches;
        this.timed_out = timed_out;

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
        return BigDecimal.valueOf(guesses_tmp.isInfinite() ? Double.MAX_VALUE : guesses_tmp).setScale(0, RoundingMode.HALF_UP);
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
     * The password passed in truncated to maxLength.
     *
     * @return {@code String} of the truncated password.
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * The original password passed in.
     *
     * @return {@code String} of the original password.
     */
    public String getFullPassword() {
        return full_password;
    }

    /**
     * Returns whether the password was truncated for estimation because it was longer than maxLength.
     *
     * @return {@code true} if the password was truncated; {@code false} if not.
     */
    public boolean isTruncated()
    {
        return !password.equals(full_password);
    }

    /**
     * Returns whether the estimate ran out of time before it finished looking for matches.
     * <br><br>
     * Stopping early can only find fewer matches, and fewer matches can only make the entropy come out too
     * high, so the entropy on a timed out result is an upper bound rather than an answer.  Everything which
     * makes a pass or fail decision from it fails closed while this is {@code true}.
     *
     * @return {@code true} if the estimate timed out; {@code false} if it completed.
     */
    public boolean isTimedOut()
    {
        return timed_out;
    }

    /**
     * Returns whether the minimum entropy specified in the config was met.
     * <br><br>
     * Always {@code false} for a timed out estimate, because the entropy it reports may be higher than the
     * password deserves and there is no way to tell from the result which it is.
     *
     * @return {@code true} if minimum entropy is met; {@code false} if not.
     */
    public boolean isMinimumEntropyMet()
    {
        if (timed_out)
        {
            return false;
        }
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
     * @return Score, always 0 for a timed out estimate
     * <br>0: risky password: "too guessable"
     * <br>1: modest protection from throttled online attacks: "very guessable"
     * <br>2: modest protection from unthrottled online attacks: "somewhat guessable"
     * <br>3: modest protection from offline attacks: "safely unguessable" (assuming a salted, slow hash function)
     * <br>4: strong protection from offline attacks: "very unguessable" (assuming a salted, slow hash function)
     */
    public int getBasicScore()
    {
        if (timed_out)
        {
            return 0;
        }
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