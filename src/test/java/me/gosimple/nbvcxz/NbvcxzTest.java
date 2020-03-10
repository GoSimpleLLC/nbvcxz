package me.gosimple.nbvcxz;

import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import me.gosimple.nbvcxz.resources.Dictionary;
import me.gosimple.nbvcxz.resources.DictionaryBuilder;
import me.gosimple.nbvcxz.scoring.Result;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Adam Brusselback
 */
public class NbvcxzTest
{

    @Test
    public void testExcludeDictionary()
    {

        String password;
        Result result;
        final double tolerance = 0.00000001;

        List<Dictionary> dictionaryList = ConfigurationBuilder.getDefaultDictionaries();
        dictionaryList.add(new DictionaryBuilder()
                .setDictionaryName("exclude")
                .setExclusion(true)
                .addWord("Halshauser", 0)
                .createDictionary());

        Configuration configuration = new ConfigurationBuilder()
                .setDictionaries(dictionaryList)
                .createConfiguration();

        final Nbvcxz nbvcxz = new Nbvcxz(configuration);
        try
        {
            password = "halshauser";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(0D, result.getEntropy(), 0D * tolerance);

            password = "alshauser";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(0D, result.getEntropy(), 0D * tolerance);


            password = "Halsauser";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(0D, result.getEntropy(), 0D * tolerance);


            password = "1Halshauser";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(0D, result.getEntropy(), 0D * tolerance);


            password = "1halshauser";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(0D, result.getEntropy(), 0D * tolerance);


            password = "halsha6user";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(0D, result.getEntropy(), 0D * tolerance);


            password = "halshauser5696311";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(18.702389159976338D, result.getEntropy(), 18.702389159976338D * tolerance);


            password = "halsHauser5696311";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(18.702389159976338D, result.getEntropy(), 18.702389159976338D * tolerance);


        }
        catch (Exception e)
        {
            assert false;
        }
    }

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

        password = "correcthorsebatterystaple";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(16.60965490131509D, result.getEntropy(), 16.60965490131509D * tolerance);

        password = "a.b.c.defy";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(35.05294537608871D, result.getEntropy(), 35.05294537608871D * tolerance);

        password = "helpimaliveinhere";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(40.376705346635696D, result.getEntropy(), 40.376705346635696D * tolerance);

        password = "damnwindowsandpaper";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(31.086623767089435D, result.getEntropy(), 31.086623767089435D * tolerance);

        password = "zxcvbnm";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(5.321928094887363D, result.getEntropy(), 5.321928094887363D * tolerance);

        password = "1qaz2wsx3edc";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(10.523561956057012D, result.getEntropy(), 10.523561956057012D * tolerance);

        password = "temppass22";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(16.892495383759368D, result.getEntropy(), 16.892495383759368D * tolerance);

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

        password = "thereisneveragoodmonday";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(44.52675492064834D, result.getEntropy(), 44.52675492064834D * tolerance);

        password = "forgetthatchristmaspartytheotheryear";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(42.69087661112469D, result.getEntropy(), 42.69087661112469D * tolerance);

        password = "A Fool and His Money Are Soon Parted";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(84.88322715518174D, result.getEntropy(), 84.88322715518174D * tolerance);

        password = "6c891879ed0a0bbf701d5ca8af39a766";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(124.22235013869417D, result.getEntropy(), 124.22235013869417D * tolerance);

        password = "ef00623ced862e84ea15a6f97cb3fbb9f177bd6f23e54459a96ca5926c28c653";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(247.06618865413472D, result.getEntropy(), 247.06618865413472D * tolerance);

        password = "Arvest#1";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(28.98030587016991D, result.getEntropy(), 28.98030587016991D * tolerance);

        password = "Arvest#2";
        result = nbvcxz.estimate(password);
        Assert.assertEquals(28.98030587016991D, result.getEntropy(), 28.98030587016991D * tolerance);
    }

}
