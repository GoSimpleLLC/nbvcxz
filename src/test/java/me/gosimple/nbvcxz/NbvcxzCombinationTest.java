package me.gosimple.nbvcxz;

import me.gosimple.nbvcxz.matching.PasswordMatcher;
import me.gosimple.nbvcxz.matching.match.BruteForceMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import me.gosimple.nbvcxz.scoring.Result;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Brusselback
 */
public class NbvcxzCombinationTest
{
    /**
     * Works out the lowest entropy tiling by walking every combination of matches.  Slow, but it
     * makes no assumptions about the algorithm under test.
     */
    private static double exhaustiveMinimumEntropy(final Configuration configuration, final String password)
    {
        final List<Match> all_matches = new ArrayList<>();
        for (final PasswordMatcher matcher : configuration.getPasswordMatchers())
        {
            all_matches.addAll(matcher.match(configuration, password));
        }
        for (int i = 0; i < password.length(); i++)
        {
            all_matches.add(new BruteForceMatch(password.charAt(i), configuration, i));
        }
        return walk(all_matches, password.length(), 0);
    }

    private static double walk(final List<Match> all_matches, final int length, final int index)
    {
        if (index == length)
        {
            return 0d;
        }
        double best = Double.POSITIVE_INFINITY;
        for (final Match match : all_matches)
        {
            if (match.getStartIndex() == index)
            {
                final double total = match.calculateEntropy() + walk(all_matches, length, match.getEndIndex() + 1);
                if (total < best)
                {
                    best = total;
                }
            }
        }
        return best;
    }

    private static double bruteForceEntropy(final Configuration configuration, final String password)
    {
        double entropy = 0d;
        for (int i = 0; i < password.length(); i++)
        {
            entropy += new BruteForceMatch(password.charAt(i), configuration, i).calculateEntropy();
        }
        return entropy;
    }

    /**
     * Test of getBestCombination method, of class Nbvcxz, against an exhaustive search.
     */
    @Test
    public void testCombinationIsOptimal()
    {
        final Configuration configuration = new ConfigurationBuilder().createConfiguration();
        final Nbvcxz nbvcxz = new Nbvcxz(configuration);

        final String[] passwords = {
                "scorpions", "ScoRpi0ns", "temppass22", "briansmith", "thx1138",
                "baseball2014", "saratovHammer1994", "helpimaliveinhere", "a.b.c.defy",
                "zxcvbnm", "1qaz2wsx3edc", "pinto", "gorillaz", "p@ssw0rd",
                "monkey!banana", "damnwindows", "selmer50cent1953"
        };

        for (final String password : passwords)
        {
            Assert.assertEquals(password, exhaustiveMinimumEntropy(configuration, password), nbvcxz.estimate(password).getEntropy(), 0.0000001d);
        }
    }

    /**
     * Passwords whose matches barely cover them are charged as brute force instead.
     */
    @Test
    public void testRandomLookingPasswordsAreChargedAsBruteForce()
    {
        final Configuration configuration = new ConfigurationBuilder().createConfiguration();
        final Nbvcxz nbvcxz = new Nbvcxz(configuration);

        final String[] passwords = {"Tr0ub4dor&3", "5fa83b7e1r39xfa8hmiz0"};

        for (final String password : passwords)
        {
            final double brute_force = bruteForceEntropy(configuration, password);
            Assert.assertEquals(password, brute_force, nbvcxz.estimate(password).getEntropy(), 0.0000001d);
            Assert.assertTrue(password, exhaustiveMinimumEntropy(configuration, password) < brute_force);
        }
    }

    /**
     * The estimate must not depend on how busy the machine is.
     */
    @Test
    public void testResultIsDeterministic()
    {
        final Nbvcxz nbvcxz = new Nbvcxz();

        final String[] passwords = {
                "correcthorsebatterystaple", "treefrog!svetlana29041989",
                "thereisneveragoodmonday", "forgetthatchristmaspartytheotheryear",
                "6c891879ed0a0bbf701d5ca8af39a766"
        };

        for (final String password : passwords)
        {
            final double first = nbvcxz.estimate(password).getEntropy();
            for (int i = 0; i < 25; i++)
            {
                Assert.assertEquals(password, first, nbvcxz.estimate(password).getEntropy(), 0d);
            }
        }
    }

    /**
     * Passwords which used to be scored far too highly.
     */
    @Test
    public void testKnownOverEstimates()
    {
        final Nbvcxz nbvcxz = new Nbvcxz();

        Assert.assertTrue(nbvcxz.estimate("treefrog!svetlana29041989").getEntropy() < 45d);
        Assert.assertTrue(nbvcxz.estimate("officialaidafreee").getEntropy() < 35d);
        Assert.assertTrue(nbvcxz.estimate("saratovHammer1994").getEntropy() < 30d);
        Assert.assertTrue(nbvcxz.estimate("primera17021995gorillaz").getEntropy() < 43d);
    }

    /**
     * The matches returned must always tile the password exactly.
     */
    @Test
    public void testEdgeCases()
    {
        final Nbvcxz nbvcxz = new Nbvcxz();

        final String[] passwords = {"", "a", "1", "!", "aa", "éèê", "😀😀", "        ", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"};

        for (final String password : passwords)
        {
            final Result result = nbvcxz.estimate(password);
            final StringBuilder builder = new StringBuilder();
            for (final Match match : result.getMatches())
            {
                builder.append(match.getToken());
            }
            Assert.assertEquals(password, password, builder.toString());
            Assert.assertTrue(password, result.getEntropy() >= 0d);
        }
    }

    /**
     * Brute forcing every character is always an option, so entropy must never exceed it.
     */
    @Test
    public void testNeverWorseThanBruteForce()
    {
        final Configuration configuration = new ConfigurationBuilder().createConfiguration();
        final Nbvcxz nbvcxz = new Nbvcxz(configuration);

        final String[] passwords = {
                "correcthorsebatterystaple", "scorpions", "a.b.c.defy", "thx1138",
                "primera17021995gorillaz", "lawrence!orangesalleycat", "abcde10121979andre"
        };

        for (final String password : passwords)
        {
            Assert.assertTrue(password, nbvcxz.estimate(password).getEntropy() <= bruteForceEntropy(configuration, password) + 0.0000001d);
        }
    }
}
