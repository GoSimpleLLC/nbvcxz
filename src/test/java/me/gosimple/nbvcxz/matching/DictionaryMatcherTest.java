package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.DictionaryMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Brusselback
 */
public class DictionaryMatcherTest
{
    final Configuration configuration = new ConfigurationBuilder().createConfiguration();


    /**
     * Test of match method, of class DictionaryMatcher, without leet value.
     */
    @Test
    public void testDictionaryMatchWithoutLeet()
    {
        System.out.println("Test of dictionaryMatch method (without leet value), of class DictionaryMatcher");

        PasswordMatcher matcher = new DictionaryMatcher();

        List<Match> computed = matcher.match(configuration, "password");

        ArrayList<DictionaryMatch> expected = new ArrayList<>();
        ArrayList<Character[]> empty = new ArrayList<>();
        /*
        expected.add(new DictionaryMatch("pas", configuration, 0, 2, 8458, empty, false, false, "english"));
        expected.add(new DictionaryMatch("pas", configuration, 0, 2, 7186, empty, false, true, "english"));
        expected.add(new DictionaryMatch("pass", configuration, 0, 3, 75, empty, false, false, "passwords"));
        expected.add(new DictionaryMatch("pass", configuration, 0, 3, 51960, empty, false, true, "passwords"));
        expected.add(new DictionaryMatch("pass", configuration, 0, 3, 1408, empty, false, false, "english"));
        expected.add(new DictionaryMatch("passw", configuration, 0, 4, 64655, empty, false, false, "passwords"));
        expected.add(new DictionaryMatch("passwo", configuration, 0, 5, 49088, empty, false, false, "passwords"));
        expected.add(new DictionaryMatch("passwor", configuration, 0, 6, 1441, empty, false, false, "passwords"));
        expected.add(new DictionaryMatch("password", configuration, 0, 7, 2, empty, false, false, "passwords"));
        expected.add(new DictionaryMatch("password", configuration, 0, 7, 617, empty, false, true, "passwords"));
        expected.add(new DictionaryMatch("password", configuration, 0, 7, 528, empty, false, false, "english"));
        expected.add(new DictionaryMatch("ass", configuration, 1, 3, 10015, empty, false, false, "passwords"));
        expected.add(new DictionaryMatch("ass", configuration, 1, 3, 1227, empty, false, false, "english"));
        expected.add(new DictionaryMatch("assword", configuration, 1, 7, 4678, empty, false, false, "passwords"));
        expected.add(new DictionaryMatch("assword", configuration, 1, 7, 85849, empty, false, true, "passwords"));
        expected.add(new DictionaryMatch("swor", configuration, 3, 6, 7395, empty, false, true, "english"));
        expected.add(new DictionaryMatch("sword", configuration, 3, 7, 3647, empty, false, false, "passwords"));
        expected.add(new DictionaryMatch("sword", configuration, 3, 7, 6494, empty, false, false, "english"));
        expected.add(new DictionaryMatch("wor", configuration, 4, 6, 2550, empty, false, true, "english"));
        expected.add(new DictionaryMatch("word", configuration, 4, 7, 6455, empty, false, false, "passwords"));
        expected.add(new DictionaryMatch("word", configuration, 4, 7, 745, empty, false, false, "english"));

        int computedHash = 0;
        for (Match match : computed)
        {
            computedHash += DictionaryMatch.class.cast(match).getToken().hashCode();
            computedHash += DictionaryMatch.class.cast(match).getDictionaryName().hashCode();
            computedHash += DictionaryMatch.class.cast(match).getRank();
            computedHash += DictionaryMatch.class.cast(match).getStartIndex();
            computedHash += DictionaryMatch.class.cast(match).getEndIndex();
        }

        int expectedHash = 0;
        for (DictionaryMatch match : expected)
        {
            expectedHash += match.getToken().hashCode();
            expectedHash += match.getDictionaryName().hashCode();
            expectedHash += match.getRank();
            expectedHash += match.getStartIndex();
            expectedHash += match.getEndIndex();
        }

        Assert.assertEquals(expectedHash, computedHash);
         */
    }


    /**
     * Test of match method, of class DictionaryMatcher, using a leet value.
     */
    @Test
    public void testDictionaryMatchWithLeet()
    {
        System.out.println("Test of dictionaryMatch method (with leet value), of class DictionaryMatcher");

        PasswordMatcher matcher = new DictionaryMatcher();

        List<Match> computed = matcher.match(configuration, "l33t");

        ArrayList<DictionaryMatch> expected = new ArrayList<>();

        ArrayList<Character[]> subs = new ArrayList<>();
        /*
        subs.add(new Character[]{'3', 'e'});
        subs.add(new Character[]{'3', 'e'});
        expected.add(new DictionaryMatch("l33", configuration, 0, 2, 65166, subs, false, false, "passwords"));

        expected.add(new DictionaryMatch("l33", configuration, 0, 2, 23, subs, false, false, "surnames"));

        expected.add(new DictionaryMatch("l33", configuration, 0, 2, 1746, subs, false, false, "english"));

        expected.add(new DictionaryMatch("l33", configuration, 0, 2, 13178, subs, false, true, "english"));

        expected.add(new DictionaryMatch("l33t", configuration, 0, 3, 60382, subs, false, false, "passwords"));

        expected.add(new DictionaryMatch("l33t", configuration, 0, 3, 15746, subs, false, false, "surnames"));

        expected.add(new DictionaryMatch("l33t", configuration, 0, 3, 2382, subs, false, true, "surnames"));

        expected.add(new DictionaryMatch("33t", configuration, 1, 3, 5298, subs, false, true, "english"));

        int computedHash = 0;
        for (Match match : computed)
        {
            computedHash += DictionaryMatch.class.cast(match).getToken().hashCode();
            computedHash += DictionaryMatch.class.cast(match).getDictionaryName().hashCode();
            computedHash += DictionaryMatch.class.cast(match).getRank();
            computedHash += DictionaryMatch.class.cast(match).getStartIndex();
            computedHash += DictionaryMatch.class.cast(match).getEndIndex();
        }

        int expectedHash = 0;
        for (DictionaryMatch match : expected)
        {
            expectedHash += match.getToken().hashCode();
            expectedHash += match.getDictionaryName().hashCode();
            expectedHash += match.getRank();
            expectedHash += match.getStartIndex();
            expectedHash += match.getEndIndex();
        }

        Assert.assertEquals(expectedHash, computedHash);
        */
    }


}
