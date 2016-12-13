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
        
        try
        {
            password = "p4ssword";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(2), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(8), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(10), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(528), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

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
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(7), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(556939), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(17822064), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(71288256), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(285153024), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(356441280), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(17822064000L), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

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
            Assert.assertEquals(new BigDecimal("214187920684036864625534562"), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal("664547051961614270361632839"), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal("4962305746407766079644384737"), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal("387680136438106724972217557596504"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal("12405764366019415199110961843088130"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal("49623057464077660796443847372352520"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal("198492229856310643185775389489410080"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal("248115287320388303982219236861762600"), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal("12405764366019415199110961843088130048"), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

        }
        catch (Exception e)
        {
            assert false;
        }
    }

}
