package me.gosimple.nbvcxz.scoring;

import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

        // Use hard coded values here so we don't have to change the test every time we change the configuration based
        // on new hardware, or the Moore's multiplier taking effect.
        Map<String, Long> guessTypes = new HashMap<>();
        guessTypes.put("OFFLINE_MD5", BigDecimal.valueOf(250375000000L).longValue());
        guessTypes.put("OFFLINE_SHA1", BigDecimal.valueOf(85963750000L).longValue());
        guessTypes.put("OFFLINE_SHA512", BigDecimal.valueOf(10780875000L).longValue());
        guessTypes.put("OFFLINE_BCRYPT_5", BigDecimal.valueOf(130875L).longValue());
        guessTypes.put("OFFLINE_BCRYPT_10", BigDecimal.valueOf(4129L).longValue());
        guessTypes.put("OFFLINE_BCRYPT_12", BigDecimal.valueOf(1033L).longValue());
        guessTypes.put("OFFLINE_BCRYPT_14", BigDecimal.valueOf(259L).longValue());
        guessTypes.put("ONLINE_UNTHROTTLED", 100L);
        guessTypes.put("ONLINE_THROTTLED", 2L);

        Configuration configuration = new ConfigurationBuilder().setGuessTypes(guessTypes).createConfiguration();
        final Nbvcxz nbvcxz = new Nbvcxz(configuration);

        try
        {
            password = "p4ssword";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(1), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(4), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
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
            Assert.assertEquals(new BigDecimal(1), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(4), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(214), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "correcthorsebatterystaple";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(24), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(96), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(386), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(1000), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(50000), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "helpimaliveinhere";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(5), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(16), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(132), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(10907940), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(345743920), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(1381971586L), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(5511878952L), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(14275766485L), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(713788324296L), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "a.b.c.defy";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(3), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(272352), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(8632629), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(34505448), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(137622115), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(356441280), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(17822064000L), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "helphere";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal(0), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal(2), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal(9), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal(24), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal(1245), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));

            password = "6c891879ed0a0bbf701d5ca8af39a766";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(new BigDecimal("99097468725067720012868392"), TimeEstimate.getTimeToCrack(result, OFFLINE_MD5));
            Assert.assertEquals(new BigDecimal("288627808024182639754802735"), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA1));
            Assert.assertEquals(new BigDecimal("2301439236800243987452031832"), TimeEstimate.getTimeToCrack(result, OFFLINE_SHA512));
            Assert.assertEquals(new BigDecimal("189581881429141015459193304192368"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_5));
            Assert.assertEquals(new BigDecimal("6009089060798941728801628405467730"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_10));
            Assert.assertEquals(new BigDecimal("24018904871286379862751136191845363"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_12));
            Assert.assertEquals(new BigDecimal("95797408231810155977690825043151583"), TimeEstimate.getTimeToCrack(result, OFFLINE_BCRYPT_14));
            Assert.assertEquals(new BigDecimal("248115287320388303982219236861762600"), TimeEstimate.getTimeToCrack(result, ONLINE_UNTHROTTLED));
            Assert.assertEquals(new BigDecimal("12405764366019415199110961843088130048"), TimeEstimate.getTimeToCrack(result, ONLINE_THROTTLED));
            
        }
        catch (Exception e)
        {
            assert false;
        }
    }

}
