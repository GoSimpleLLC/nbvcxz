package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Adam Brusselback
 */
public class BaseMatchTest
{


    @Rule
    public final ExpectedException illegalArgumentException = ExpectedException.none();


    /**
     * Test of the constructor of class BaseMatch.
     */
    @Test
    public void testContructor()
    {
        System.out.println("Test of the constructor of class BaseMatch");

        illegalArgumentException.expect(IllegalArgumentException.class);
        illegalArgumentException.expectMessage("Null String");
        BaseMatch instanceWithNull = new BaseMatchImpl(null);

        illegalArgumentException.expect(IllegalArgumentException.class);
        illegalArgumentException.expectMessage("Empty String");
        BaseMatch instanceWithEmpty = new BaseMatchImpl(null);
    }


    /**
     * Test of getToken method, of class BaseMatch.
     */
    @Test
    public void testGetToken()
    {
        System.out.println("Test of getToken method, of class BaseMatch");
        BaseMatch instance = new BaseMatchImpl("dummyToken");
        String result = instance.getToken();
        Assert.assertEquals("dummyToken", result);
    }


    /**
     * Test of log2 method, of class BaseMatch.
     */
    @Test
    public void testLog2()
    {
        System.out.println("Test of log2 method, of class BaseMatch");
        Assert.assertEquals(Double.NaN, BaseMatch.log2(Double.NEGATIVE_INFINITY), 0.000000000000001);
        Assert.assertEquals(Double.NaN, BaseMatch.log2(-1), 0.000000000000001);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, BaseMatch.log2(0), 0.000000000000001);
        Assert.assertEquals(-1d, BaseMatch.log2(0.5), 0.000000000000001);
        Assert.assertEquals(-2d, BaseMatch.log2(0.25), 0.000000000000001);
        Assert.assertEquals(0d, BaseMatch.log2(1), 0.000000000000001);
        Assert.assertEquals(1d, BaseMatch.log2(2), 0.000000000000001);
        Assert.assertEquals(10d, BaseMatch.log2(1024), 0.000000000000001);
        Assert.assertEquals(Double.POSITIVE_INFINITY, BaseMatch.log2(Double.POSITIVE_INFINITY), 0.000000000000001);
    }

    /**
     * Test of nCk method, of class BaseMatch.
     */
    @Test
    public void testNCk()
    {
        System.out.println("Test of nCk method, of class BaseMatch.");
        Assert.assertEquals(0L, BaseMatch.nCk(2, 3));
        Assert.assertEquals(1L, BaseMatch.nCk(10, 0));
        Assert.assertEquals(120L, BaseMatch.nCk(10, 3));
        Assert.assertEquals(120L, BaseMatch.nCk(10, 7));
        Assert.assertEquals(1646492110120L, BaseMatch.nCk(80, 10));
        Assert.assertEquals(26252279997448736L, BaseMatch.nCk(58, 27));
    }

    public class BaseMatchImpl extends BaseMatch
    {

        public BaseMatchImpl(String s)
        {
            super(s, new ConfigurationBuilder().createConfiguration(), 0, s == null ? 0 : s.length() - 1);
        }
    }


}
