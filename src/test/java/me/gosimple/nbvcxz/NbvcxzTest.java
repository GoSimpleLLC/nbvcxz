package me.gosimple.nbvcxz;

import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import me.gosimple.nbvcxz.resources.Dictionary;
import me.gosimple.nbvcxz.resources.DictionaryBuilder;
import me.gosimple.nbvcxz.scoring.Result;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Adam Brusselback
 */
public class NbvcxzTest
{

    @Test
    public void testExcludeDictionary()
    {
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


        final List<EntropyTest> tests = new ArrayList<>();
        tests.add(new EntropyTest(nbvcxz,"halshauser",0D));
        tests.add(new EntropyTest(nbvcxz,"Halsauser",0D));
        tests.add(new EntropyTest(nbvcxz,"1Halshauser",0D));
        tests.add(new EntropyTest(nbvcxz,"1halshauser",0D));
        tests.add(new EntropyTest(nbvcxz,"halsha6user",0D));
        tests.add(new EntropyTest(nbvcxz,"halshauser5696311",18.702389159976338D));
        tests.add(new EntropyTest(nbvcxz,"halsHauser5696311",18.702389159976338D));
        try
        {
            for (final EntropyTest test : tests)
            {
                test.run();
                Assert.assertEquals(test.getExpectedEntropy(), test.getEntropy(), test.getDelta());
            }
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
        final Nbvcxz nbvcxz = new Nbvcxz();

        final List<EntropyTest> tests = new ArrayList<>();
        tests.add(new EntropyTest(nbvcxz,"correcthorsebatterystaple",16.60965490131509D));
        tests.add(new EntropyTest(nbvcxz,"a.b.c.defy",35.05294537608871D));
        tests.add(new EntropyTest(nbvcxz,"helpimaliveinhere",40.376705346635696D));
        tests.add(new EntropyTest(nbvcxz,"damnwindowsandpaper",31.086623767089435D));
        tests.add(new EntropyTest(nbvcxz,"zxcvbnm",5.321928094887363D));
        tests.add(new EntropyTest(nbvcxz,"1qaz2wsx3edc",10.523561956057012D));
        tests.add(new EntropyTest(nbvcxz,"temppass22",16.892495383759368D));
        tests.add(new EntropyTest(nbvcxz,"briansmith",4.321928094887363D));
        tests.add(new EntropyTest(nbvcxz,"thx1138",8.049848549450562D));
        tests.add(new EntropyTest(nbvcxz,"baseball2014",10.59618975614441D));
        tests.add(new EntropyTest(nbvcxz,"baseball1994",10.59618975614441D));
        tests.add(new EntropyTest(nbvcxz,"baseball2028",10.59618975614441D));
        tests.add(new EntropyTest(nbvcxz,"scorpions",13.67529549909406D));
        tests.add(new EntropyTest(nbvcxz,"ScoRpions",19.198857455151074D));
        tests.add(new EntropyTest(nbvcxz,"ScoRpi0ns",20.46971136544417D));
        tests.add(new EntropyTest(nbvcxz,"thereisneveragoodmonday",44.52675492064834D));
        tests.add(new EntropyTest(nbvcxz,"forgetthatchristmaspartytheotheryear",42.69087661112469D));
        tests.add(new EntropyTest(nbvcxz,"A Fool and His Money Are Soon Parted",84.88322715518174D));
        tests.add(new EntropyTest(nbvcxz,"6c891879ed0a0bbf701d5ca8af39a766",124.22235013869417D));
        tests.add(new EntropyTest(nbvcxz,"ef00623ced862e84ea15a6f97cb3fbb9f177bd6f23e54459a96ca5926c28c653",247.06618865413472D));

        try
        {
            for (final EntropyTest test : tests)
            {
                test.run();
                Assert.assertEquals(test.getExpectedEntropy(), test.getEntropy(), test.getDelta());
            }
        }
        catch (Exception e)
        {
            assert false;
        }
    }

    @Test
    public void testEstimateConcurrently()
    {
        final Nbvcxz nbvcxz = new Nbvcxz();

        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

        final List<EntropyTest> tests = new ArrayList<>();
        tests.add(new EntropyTest(nbvcxz,"correcthorsebatterystaple",16.60965490131509D));
        tests.add(new EntropyTest(nbvcxz,"a.b.c.defy",35.05294537608871D));
        tests.add(new EntropyTest(nbvcxz,"helpimaliveinhere",40.376705346635696D));
        tests.add(new EntropyTest(nbvcxz,"damnwindowsandpaper",31.086623767089435D));
        tests.add(new EntropyTest(nbvcxz,"zxcvbnm",5.321928094887363D));
        tests.add(new EntropyTest(nbvcxz,"1qaz2wsx3edc",10.523561956057012D));
        tests.add(new EntropyTest(nbvcxz,"temppass22",16.892495383759368D));
        tests.add(new EntropyTest(nbvcxz,"briansmith",4.321928094887363D));
        tests.add(new EntropyTest(nbvcxz,"thx1138",8.049848549450562D));
        tests.add(new EntropyTest(nbvcxz,"baseball2014",10.59618975614441D));
        tests.add(new EntropyTest(nbvcxz,"baseball1994",10.59618975614441D));
        tests.add(new EntropyTest(nbvcxz,"baseball2028",10.59618975614441D));
        tests.add(new EntropyTest(nbvcxz,"scorpions",13.67529549909406D));
        tests.add(new EntropyTest(nbvcxz,"ScoRpions",19.198857455151074D));
        tests.add(new EntropyTest(nbvcxz,"ScoRpi0ns",20.46971136544417D));
        tests.add(new EntropyTest(nbvcxz,"thereisneveragoodmonday",44.52675492064834D));
        tests.add(new EntropyTest(nbvcxz,"forgetthatchristmaspartytheotheryear",42.69087661112469D));
        tests.add(new EntropyTest(nbvcxz,"A Fool and His Money Are Soon Parted",84.88322715518174D));
        tests.add(new EntropyTest(nbvcxz,"6c891879ed0a0bbf701d5ca8af39a766",124.22235013869417D));
        tests.add(new EntropyTest(nbvcxz,"ef00623ced862e84ea15a6f97cb3fbb9f177bd6f23e54459a96ca5926c28c653",247.06618865413472D));

        List<Future> futures = new ArrayList<>();
        try
        {
            // Execute them all
            for (final EntropyTest test : tests)
            {
                futures.add(executor.submit(test));
            }

            // Wait until they are all done
            for (final Future future : futures)
            {
                future.get();
            }

            // Check results
            for (final EntropyTest test : tests)
            {
                Assert.assertEquals(test.getExpectedEntropy(), test.getEntropy(), test.getDelta());
            }
        }
        catch (Exception e)
        {
            assert false;
        }
    }

    private class EntropyTest implements Runnable
    {
        private final double tolerance = 0.00000001;
        private final Nbvcxz nbvcxz;
        private final String password;
        private final Double expectedEntropy;
        private Result result;

        public EntropyTest(Nbvcxz nbvcxz, String password, Double expectedEntropy) {
            this.nbvcxz = nbvcxz;
            this.password = password;
            this.expectedEntropy = expectedEntropy;
        }

        public String getPassword() {
            return password;
        }

        public Double getExpectedEntropy() {
            return expectedEntropy;
        }

        public Double getDelta() {
            return expectedEntropy * tolerance;
        }

        public Double getEntropy() {
            return result.getEntropy();
        }

        public Result getResult() {
            return result;
        }

        @Override
        public void run() {
            result = nbvcxz.estimate(password);
        }
    }
}
