package me.gosimple.nbvcxz.scoring;

import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.resources.Configuration;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Adam Brusselback
 */
public class TimeEstimateTest
{

    /**
     * Test of guessEntropy method, of class CalculateEntropy.
     */
    @Test
    public void testCalculateEntropy()
    {
        String password;
        Result result;
        final String OFFLINE_MD5 = "OFFLINE_MD5";
        final String OFFLINE_SHA1 = "OFFLINE_SHA1";
        final String OFFLINE_SHA512 = "OFFLINE_SHA512";
        final String OFFLINE_BCRYPT_5 = "OFFLINE_BCRYPT_5";
        final String OFFLINE_BCRYPT_10 = "OFFLINE_BCRYPT_10";
        final String OFFLINE_BCRYPT_12 = "OFFLINE_BCRYPT_12";
        final String OFFLINE_BCRYPT_14 = "OFFLINE_BCRYPT_14";
        final String ONLINE_UNTHROTTLED = "ONLINE_UNTHROTTLED";
        final String ONLINE_THROTTLED = "ONLINE_THROTTLED";
        final Nbvcxz nbvcxz = new Nbvcxz();
        final Configuration configuration = nbvcxz.getConfiguration();
        
        try
        {
            password = "p4ssword";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(2), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "1qazxsw2";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(3), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(4), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(214), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "correcthorsebatterystaple";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(1), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(50), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(200), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(800), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(1000), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(50000), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "helpimaliveinhere";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(7), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(24), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(183), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(14309707), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(457910645), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(1831642582), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(7326570331L), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(9158212914L), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(457910645712L), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "a.b.c.defy";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(11), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(34), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(256), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(20014734), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(640471513L), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(2561886055L), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(10247544223L), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(12809430279L), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(640471513968L), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "helphere";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(1), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(4), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(19), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(24), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(1245), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "6c891879ed0a0bbf701d5ca8af39a766";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal("5970420118118596134317701"), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal("18524037563821999576798866"), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal("138322693296571635239872497"), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal("10806460413794659003115038853562"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal("345806733241429088099681243313995"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal("1383226932965716352398724973255983"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal("5532907731862865409594899893023932"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal("6916134664828581761993624866279915"), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal("345806733241429088099681243313995776"), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

        }
        catch (Exception e)
        {
            assert false;
        }
    }

}
