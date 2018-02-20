package me.gosimple.nbvcxz.scoring;

import me.gosimple.nbvcxz.Nbvcxz;
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
            //Assert.assertEquals(new BigDecimal(1), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            //Assert.assertEquals(new BigDecimal(5), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
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
            //Assert.assertEquals(new BigDecimal(2), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(4), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(214), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "correcthorsebatterystaple";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            //Assert.assertEquals(new BigDecimal(30), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            //Assert.assertEquals(new BigDecimal(121), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            //Assert.assertEquals(new BigDecimal(483), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(1000), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(50000), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "helpimaliveinhere";
            result = nbvcxz.estimate(password);
            //Assert.assertEquals(new BigDecimal(7), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            //Assert.assertEquals(new BigDecimal(20), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            //Assert.assertEquals(new BigDecimal(165), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            //Assert.assertEquals(new BigDecimal(13634925), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            //Assert.assertEquals(new BigDecimal(432206069), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            //Assert.assertEquals(new BigDecimal(1728301027L), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            //Assert.assertEquals(new BigDecimal(6896505548L), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(14275766485L), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(713788324296L), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "a.b.c.defy";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            //Assert.assertEquals(new BigDecimal(4), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            //Assert.assertEquals(new BigDecimal(340440), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            //Assert.assertEquals(new BigDecimal(10791440), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            //Assert.assertEquals(new BigDecimal(43152697), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            //Assert.assertEquals(new BigDecimal(172193855), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(356441280), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(17822064000L), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "helphere";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            //Assert.assertEquals(new BigDecimal(3), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            //Assert.assertEquals(new BigDecimal(12), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(24), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(1245), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "6c891879ed0a0bbf701d5ca8af39a766";
            result = nbvcxz.estimate(password);
            //Assert.assertEquals(new BigDecimal("123871835906334650016085490"), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            //Assert.assertEquals(new BigDecimal("360784760030228299693503419"), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            //Assert.assertEquals(new BigDecimal("2876799046000304984315039791"), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            //Assert.assertEquals(new BigDecimal("236977351786426269323991630240460"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            //Assert.assertEquals(new BigDecimal("7511816146545210535338154310074556"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            //Assert.assertEquals(new BigDecimal("30038170377770981111648818022005157"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            //Assert.assertEquals(new BigDecimal("119862457642699663759526201382494010"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal("248115287320388303982219236861762600"), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal("12405764366019415199110961843088130048"), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

        }
        catch (Exception e)
        {
            assert false;
        }
    }

}
