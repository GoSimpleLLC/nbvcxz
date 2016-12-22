package me.gosimple.nbvcxz.scoring;

import me.gosimple.nbvcxz.resources.BruteForceUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam Brusselback
 */
public class BruteForceUtilTest
{

    /**
     * Test of calculateBrutForceCardinality method, of class BruteForceUtil.
     */
    @Test
    public void testCalculateBrutForceCardinality()
    {
        System.out.println("Test of calculateBrutForceCardinality method, "
                + "of class BruteForceUtil");

        HashMap<String, Integer> fixture = new HashMap<>();

        // Testing sets of characters
        fixture.put("abcdefghijklmnopqrstuvwxyz", 26);
        fixture.put("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 26);
        fixture.put("0123456789", 10);
        fixture.put(" !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~", 33);
        fixture.put("¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚ" +
                "ÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚ" +
                "ěĜĝĞğĠġĢģĤĥĦħĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿŀŁłŃńŅņŇňŉŊŋŌōŎŏŐőŒœŔŕŖŗŘřŚ" +
                "śŜŝŞşŠšŢţŤťŦŧŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽž\u1234", 100);

        // One set of characters
        fixture.put("a", 26);
        fixture.put("ajkfdsh", 26);
        fixture.put("F", 26);
        fixture.put("FASLFJDA", 26);
        fixture.put("0", 10);
        fixture.put("2398572", 10);
        fixture.put("@", 33);
        fixture.put("#@(%*$(", 33);
        fixture.put("é", 100);
        fixture.put("Êéøîïúì", 100);

        // Two sets of characters
        fixture.put("aB", 52);
        fixture.put("aKfhsD", 52);
        fixture.put("a1", 36);
        fixture.put("a123jh1k", 36);
        fixture.put("a@", 59);
        fixture.put("a^fs*#", 59);
        fixture.put("aé", 126);
        fixture.put("aÉdìaÏ", 126);
        fixture.put("A1", 36);
        fixture.put("A123JH1K", 36);
        fixture.put("A@", 59);
        fixture.put("A^FS*#", 59);
        fixture.put("Aé", 126);
        fixture.put("AÉDìAÏ", 126);
        fixture.put("0@", 43);
        fixture.put("0^27*#", 43);
        fixture.put("2é", 110);
        fixture.put("6É8ì2Ï", 110);
        fixture.put("@é", 133);
        fixture.put("@É^ì&Ï", 133);

        // Three sets of characters
        fixture.put("aB1", 62);
        fixture.put("a3Kfh6sD", 62);
        fixture.put("aB$", 85);
        fixture.put("a^Kfh%sD", 85);
        fixture.put("aBé", 152);
        fixture.put("aèKfhîsD", 152);
        fixture.put("a1$", 69);
        fixture.put("a^1fh%s6", 69);
        fixture.put("a5é", 136);
        fixture.put("aè8fhîs9", 136);
        fixture.put("a@é", 159);
        fixture.put("aè^fhîs_", 159);
        fixture.put("D1$", 69);
        fixture.put("S^1KD%I6", 69);
        fixture.put("L5é", 136);
        fixture.put("Dè8WKîU9", 136);
        fixture.put("L_é", 159);
        fixture.put("Dè@WKîU)", 159);
        fixture.put("4_é", 143);
        fixture.put("2è@67î0)", 143);

        // Four sets of characters
        fixture.put("C4%é", 169);
        fixture.put("D9é(6SÈ%", 169);
        fixture.put("s4%é", 169);
        fixture.put("g9é(6wÈ%", 169);
        fixture.put("Cs%é", 185);
        fixture.put("Dhé(wSÈ%", 185);
        fixture.put("Cs3é", 162);
        fixture.put("Dhé8wSÈ2", 162);
        fixture.put("Cs3_", 95);
        fixture.put("Dh-8wS!2", 95);

        // Five sets of characters
        fixture.put("aD3%é", 195);
        fixture.put("d@DÌ2k#ér6Vï-8jC", 195);


        // Test the fixture
        for (Map.Entry<String, Integer> entry : fixture.entrySet())
        {
            String password = entry.getKey();
            Integer expectedCardinality = entry.getValue();
            Integer calcCardinality = BruteForceUtil.getBrutForceCardinality(password);
            Assert.assertEquals(password, expectedCardinality, calcCardinality);
        }
    }

}
