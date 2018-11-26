package me.gosimple.nbvcxz.scoring;

import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Adam Brusselback
 */
public class ScoreTest
{

    /**
     * Test of getBasicScore method, of Result class.
     */
    @Test
    public void testBasicScore()
    {
        String password;
        Result result;

        Configuration configuration = new ConfigurationBuilder().createConfiguration();
        final Nbvcxz nbvcxz = new Nbvcxz(configuration);

        try
        {
            password = "p4ssword";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(1, result.getBasicScore());

            password = "1qazxsw2";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(0, result.getBasicScore());

            password = "correcthorsebatterystaple";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(1, result.getBasicScore());

            password = "helpimaliveinhere";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(4, result.getBasicScore());

            password = "a.b.c.defy";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(4, result.getBasicScore());

            password = "helphere";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(1, result.getBasicScore());

            password = "6c891879ed0a0bbf701d5ca8af39a766";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(4, result.getBasicScore());
            
        }
        catch (Exception e)
        {
            assert false;
        }
    }

}
