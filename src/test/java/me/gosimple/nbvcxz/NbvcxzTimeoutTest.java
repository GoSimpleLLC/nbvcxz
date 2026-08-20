package me.gosimple.nbvcxz;

import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import me.gosimple.nbvcxz.scoring.Result;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adam Brusselback
 */
public class NbvcxzTimeoutTest
{
    private static String longPassword(final int length)
    {
        final StringBuilder builder = new StringBuilder();
        while (builder.length() < length)
        {
            builder.append("correcthorsebatterystaplemonkeybanana1234");
        }
        return builder.substring(0, length);
    }

    /**
     * A password which cannot be estimated in time must never be reported as acceptable, whatever entropy
     * the partial estimate happened to arrive at.
     */
    @Test
    public void testTimedOutResultFailsClosed()
    {
        final Configuration configuration = new ConfigurationBuilder()
                .setMaxLength(4096)
                .setEstimateTimeout(1L)
                .setMinimumEntropy(1d)
                .createConfiguration();

        final Result result = new Nbvcxz(configuration).estimate(longPassword(4096));

        Assert.assertTrue("estimate should have timed out", result.isTimedOut());
        Assert.assertFalse("timed out estimate must not meet minimum entropy", result.isMinimumEntropyMet());
        Assert.assertEquals("timed out estimate must score 0", 0, result.getBasicScore());
        Assert.assertTrue("entropy is well above the minimum of 1", result.getEntropy() > 1d);
    }

    /**
     * The timeout has to actually bound the work, not just report on it after the fact.
     */
    @Test
    public void testTimeoutBoundsTheEstimate()
    {
        final Configuration bounded = new ConfigurationBuilder()
                .setMaxLength(4096)
                .setEstimateTimeout(100L)
                .createConfiguration();

        final String password = longPassword(4096);
        final long start = System.currentTimeMillis();
        new Nbvcxz(bounded).estimate(password);
        final long elapsed = System.currentTimeMillis() - start;

        Assert.assertTrue("estimate took " + elapsed + "ms with a 100ms budget", elapsed < 10000L);
    }

    /**
     * Stopping early can only find fewer matches, and fewer matches can only raise the entropy.
     */
    @Test
    public void testTimingOutNeverLowersEntropy()
    {
        final String password = longPassword(512);

        final Result complete = new Nbvcxz(new ConfigurationBuilder()
                .setMaxLength(512).setEstimateTimeout(0L).createConfiguration()).estimate(password);
        final Result partial = new Nbvcxz(new ConfigurationBuilder()
                .setMaxLength(512).setEstimateTimeout(1L).createConfiguration()).estimate(password);

        Assert.assertFalse(complete.isTimedOut());
        Assert.assertTrue(partial.isTimedOut());
        Assert.assertTrue("partial estimate must not come out lower than the complete one",
                partial.getEntropy() >= complete.getEntropy());
    }

    /**
     * Ordinary passwords must never trip the timeout at the default settings.
     */
    @Test
    public void testNormalPasswordsDoNotTimeOut()
    {
        final Nbvcxz nbvcxz = new Nbvcxz();

        final String[] passwords = {
                "correcthorsebatterystaple", "Tr0ub4dor&3", "helpimaliveinhere",
                "treefrog!svetlana29041989", "thereisneveragoodmonday",
                "6c891879ed0a0bbf701d5ca8af39a766", longPassword(256)
        };

        for (final String password : passwords)
        {
            final Result result = nbvcxz.estimate(password);
            Assert.assertFalse(password, result.isTimedOut());
        }
    }

    /**
     * A timeout of 0 means estimates are not bounded at all.
     */
    @Test
    public void testZeroTimeoutDisablesTheDeadline()
    {
        final Configuration configuration = new ConfigurationBuilder()
                .setMaxLength(1024)
                .setEstimateTimeout(0L)
                .createConfiguration();

        Assert.assertFalse(new Nbvcxz(configuration).estimate(longPassword(1024)).isTimedOut());
    }

    /**
     * The deprecated setter still has to work for callers who have not moved off it.
     */
    @Test
    public void testDeprecatedSetterStillApplies()
    {
        final Configuration configuration = new ConfigurationBuilder()
                .setCombinationAlgorithmTimeout(1234L)
                .createConfiguration();

        Assert.assertEquals(1234L, configuration.getEstimateTimeout());
        Assert.assertEquals(1234L, configuration.getCombinationAlgorithmTimeout());
    }
}
