package me.gosimple.nbvcxz;

import me.gosimple.nbvcxz.scoring.Result;
import org.junit.Assert;
import org.junit.Test;

/**
 * Adam Brusselback
 */
public class NbvcxzTest
{
    /**
     * Test of estimate method, of class Nbvcxz.
     */
    @Test
    public void testEstimate()
    {
        String password;
        Result result;
        final double tolerance = 0.00000001;
        final Nbvcxz nbvcxz = new Nbvcxz();

        try
        {
            password = "correcthorsebatterystaple";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(16.60965490131509D, result.getEntropy(), 16.60965490131509D * tolerance);

            password = "a.b.c.defy";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(40.220343449501975D, result.getEntropy(), 40.220343449501975D * tolerance);

            password = "helpimaliveinhere";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(39.736275149583484D, result.getEntropy(), 39.736275149583484D * tolerance);

            password = "damnwindowsandpaper";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(33.77114539390665D, result.getEntropy(), 33.77114539390665D * tolerance);

            password = "zxcvbnm";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(5.321928094887363D, result.getEntropy(), 5.321928094887363D * tolerance);

            password = "1qaz2wsx3edc";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(10.523561956057012D, result.getEntropy(), 10.523561956057012D * tolerance);

            password = "temppass22";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(22.272273249390718D, result.getEntropy(), 22.272273249390718D * tolerance);

            password = "briansmith";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(4.321928094887363D, result.getEntropy(), 4.321928094887363D * tolerance);

            password = "thx1138";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(8.049848549450562D, result.getEntropy(), 8.049848549450562D * tolerance);

            password = "baseball2014";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(10.59618975614441D, result.getEntropy(), 10.59618975614441D * tolerance);

            password = "baseball1994";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(10.59618975614441D, result.getEntropy(), 10.59618975614441D * tolerance);

            password = "baseball2028";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(10.59618975614441D, result.getEntropy(), 10.59618975614441D * tolerance);

            password = "scorpions";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(13.67529549909406D, result.getEntropy(), 13.67529549909406D * tolerance);

            password = "ScoRpions";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(19.198857455151074D, result.getEntropy(), 19.198857455151074D * tolerance);

            password = "ScoRpi0ns";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(20.46971136544417D, result.getEntropy(), 20.46971136544417D * tolerance);

            password = "6c891879ed0a0bbf701d5ca8af39a766";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(119.05744928118723D, result.getEntropy(), 119.05744928118723D * tolerance);

            password = "ef00623ced862e84ea15a6f97cb3fbb9f177bd6f23e54459a96ca5926c28c653";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(238.52749501268573D, result.getEntropy(), 238.52749501268573D * tolerance);

        }
        catch (Exception e)
        {
            assert false;
        }
    }

}
