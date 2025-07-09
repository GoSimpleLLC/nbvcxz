package me.gosimple.nbvcxz.resources;

import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.scoring.Result;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.HashSet;

/**
 * Test to verify that explicitly setting a locale in Configuration 
 * always returns feedback in that language, regardless of JVM default locale.
 * This addresses the bug where setting Locale.ENGLISH would still return
 * feedback in the JVM's default language due to ResourceBundle fallback behavior.
 */
public class LocaleTest {

    private Locale originalLocale;

    private static final Locale[] SUPPORTED_LOCALES = {
            Locale.ENGLISH,
            new Locale("de"),
            new Locale("es"),
            new Locale("fr"),
            new Locale("it"),
            new Locale("ru"),
            new Locale("pt"),
            new Locale("nl"),
            new Locale("sv")
    };

    @Before
    public void setUp() {
        originalLocale = Locale.getDefault();
    }

    @After
    public void tearDown() {
        Locale.setDefault(originalLocale);
    }

    /**
     * Test that Configuration uses the explicitly set locale regardless of JVM default
     */
    @Test
    public void testConfigurationUsesExplicitLocale() {
        for (Locale configuredLocale : SUPPORTED_LOCALES) {
            for (Locale jvmLocale : SUPPORTED_LOCALES) {
                if (configuredLocale.equals(jvmLocale)) continue;

                Locale.setDefault(jvmLocale);

                Configuration configuration = new ConfigurationBuilder()
                        .setLocale(configuredLocale)
                        .createConfiguration();

                Assert.assertEquals("Configuration should use explicitly set locale, not JVM default",
                        configuredLocale, configuration.getLocale());

                String expectedLocaleString = getExpectedResourceBundleLocale(configuredLocale);
                Assert.assertEquals("Main ResourceBundle should use configured locale",
                        expectedLocaleString, configuration.getMainResource().getLocale().toString());
                Assert.assertEquals("Feedback ResourceBundle should use configured locale",
                        expectedLocaleString, configuration.getFeedbackResource().getLocale().toString());
            }
        }
    }

    private String getExpectedResourceBundleLocale(Locale locale) {
        return Locale.ENGLISH.equals(locale) ? "" : locale.toString();
    }

    /**
     * Test that the same password produces different feedback text when using different locales
     */
    @Test
    public void testFeedbackVariesByLocale() {
        String testPassword = "password";
        Set<String> uniqueFeedbackMessages = new HashSet<>();

        for (Locale locale : SUPPORTED_LOCALES) {
            Locale.setDefault(SUPPORTED_LOCALES[0].equals(locale) ?
                    SUPPORTED_LOCALES[1] : SUPPORTED_LOCALES[0]);

            Configuration configuration = new ConfigurationBuilder()
                    .setLocale(locale)
                    .createConfiguration();

            Nbvcxz nbvcxz = new Nbvcxz(configuration);
            Result result = nbvcxz.estimate(testPassword);
            String warning = result.getFeedback().getWarning();

            if (warning != null) {
                uniqueFeedbackMessages.add(warning);
            }
        }

        Assert.assertTrue("Should have at least 2 different feedback messages across locales, but got: " +
                        uniqueFeedbackMessages.size() + " unique messages: " + uniqueFeedbackMessages,
                uniqueFeedbackMessages.size() >= 2);
    }

    /**
     * Test that unsupported locales fall back to English, not to the JVM default locale
     */
    @Test
    public void testUnsupportedLocaleFallsBackToEnglish() {
        Locale.setDefault(new Locale("it"));

        Configuration englishConfig = new ConfigurationBuilder()
                .setLocale(Locale.ENGLISH)
                .createConfiguration();
        Nbvcxz englishNbvcxz = new Nbvcxz(englishConfig);
        Result englishResult = englishNbvcxz.estimate("password");
        String englishWarning = englishResult.getFeedback().getWarning();

        Configuration unsupportedConfig = new ConfigurationBuilder()
                .setLocale(new Locale("ja"))
                .createConfiguration();
        Nbvcxz unsupportedNbvcxz = new Nbvcxz(unsupportedConfig);
        Result unsupportedResult = unsupportedNbvcxz.estimate("password");
        String unsupportedWarning = unsupportedResult.getFeedback().getWarning();

        Assert.assertEquals("Unsupported locale should fall back to English, not JVM default",
                englishWarning, unsupportedWarning);
    }

    /**
     * Test ResourceBundle loading behavior directly
     */
    @Test
    public void testResourceBundleLoading() {
        ResourceBundle.Control noFallbackControl = ResourceBundle.Control.getNoFallbackControl(
                ResourceBundle.Control.FORMAT_PROPERTIES);

        for (Locale locale : SUPPORTED_LOCALES) {
            Locale.setDefault(locale.equals(Locale.ENGLISH) ?
                    new Locale("fr") : Locale.ENGLISH);

            try {
                ResourceBundle mainBundle = ResourceBundle.getBundle("main", locale, noFallbackControl);
                ResourceBundle feedbackBundle = ResourceBundle.getBundle("feedback", locale, noFallbackControl);

                String expectedLocaleString = getExpectedResourceBundleLocale(locale);
                Assert.assertEquals("Main bundle should use specified locale",
                        expectedLocaleString, mainBundle.getLocale().toString());
                Assert.assertEquals("Feedback bundle should use specified locale",
                        expectedLocaleString, feedbackBundle.getLocale().toString());

                Assert.assertTrue("Main bundle should contain basic keys",
                        mainBundle.containsKey("main.password"));
                Assert.assertTrue("Feedback bundle should contain basic keys",
                        feedbackBundle.containsKey("feedback.dictionary.warning.passwords.veryCommon"));
            } catch (Exception e) {
                Assert.fail("Failed to load ResourceBundle for locale " + locale + ": " + e.getMessage());
            }
        }
    }

    /**
     * Test that locale settings are isolated between different Configuration instances
     */
    @Test
    public void testLocaleIsolationBetweenInstances() {
        Locale.setDefault(new Locale("de"));

        Configuration englishConfig = new ConfigurationBuilder()
                .setLocale(Locale.ENGLISH)
                .createConfiguration();

        Configuration italianConfig = new ConfigurationBuilder()
                .setLocale(new Locale("it"))
                .createConfiguration();

        Configuration frenchConfig = new ConfigurationBuilder()
                .setLocale(new Locale("fr"))
                .createConfiguration();

        String testPassword = "password";

        Nbvcxz englishNbvcxz = new Nbvcxz(englishConfig);
        Nbvcxz italianNbvcxz = new Nbvcxz(italianConfig);
        Nbvcxz frenchNbvcxz = new Nbvcxz(frenchConfig);

        String englishWarning = englishNbvcxz.estimate(testPassword).getFeedback().getWarning();
        String italianWarning = italianNbvcxz.estimate(testPassword).getFeedback().getWarning();
        String frenchWarning = frenchNbvcxz.estimate(testPassword).getFeedback().getWarning();

        Assert.assertNotNull("English warning should not be null", englishWarning);
        Assert.assertNotNull("Italian warning should not be null", italianWarning);
        Assert.assertNotNull("French warning should not be null", frenchWarning);

        Assert.assertNotEquals("English and Italian should be different", englishWarning, italianWarning);
        Assert.assertNotEquals("English and French should be different", englishWarning, frenchWarning);
        Assert.assertNotEquals("Italian and French should be different", italianWarning, frenchWarning);

        Assert.assertEquals(Locale.ENGLISH, englishConfig.getLocale());
        Assert.assertEquals(new Locale("it"), italianConfig.getLocale());
        Assert.assertEquals(new Locale("fr"), frenchConfig.getLocale());
    }

    /**
     * Test that feedback keys are consistent across locales
     */
    @Test
    public void testFeedbackKeysConsistentAcrossLocales() {
        String testPassword = "password";
        String expectedWarningKey = null;
        Set<String> expectedSuggestionKeys = null;

        for (Locale locale : SUPPORTED_LOCALES) {
            Configuration config = new ConfigurationBuilder()
                    .setLocale(locale)
                    .createConfiguration();

            Nbvcxz nbvcxz = new Nbvcxz(config);
            Result result = nbvcxz.estimate(testPassword);
            Feedback feedback = result.getFeedback();

            if (expectedWarningKey == null) {
                expectedWarningKey = feedback.getWarningKey();
                expectedSuggestionKeys = new HashSet<>(feedback.getSuggestionKeys());
            } else {
                Assert.assertEquals("Warning key should be consistent across locales for same password",
                        expectedWarningKey, feedback.getWarningKey());
                Assert.assertEquals("Suggestion keys should be consistent across locales for same password",
                        expectedSuggestionKeys, new HashSet<>(feedback.getSuggestionKeys()));
            }
        }
    }

    /**
     * Test the specific bug scenario: JVM locale is Italian, but Configuration is set to English
     */
    @Test
    public void testBugScenario_ItalianJVMEnglishConfig() {
        Locale.setDefault(new Locale("it", "IT"));

        Configuration englishConfig = new ConfigurationBuilder()
                .setLocale(Locale.ENGLISH)
                .createConfiguration();

        Nbvcxz nbvcxz = new Nbvcxz(englishConfig);
        Result result = nbvcxz.estimate("password");

        String actualWarning = result.getFeedback().getWarning();

        ResourceBundle.Control noFallbackControl = ResourceBundle.Control.getNoFallbackControl(
                ResourceBundle.Control.FORMAT_PROPERTIES);
        ResourceBundle englishBundle = ResourceBundle.getBundle("feedback", Locale.ENGLISH, noFallbackControl);
        String expectedWarning = englishBundle.getString(result.getFeedback().getWarningKey());

        Assert.assertEquals("Should get English feedback when Configuration locale is English, " +
                "even when JVM default is Italian", expectedWarning, actualWarning);

        Assert.assertEquals("Configuration locale should be English", Locale.ENGLISH, englishConfig.getLocale());

        Assert.assertEquals("ResourceBundle should use English (root) bundle",
                "", englishConfig.getFeedbackResource().getLocale().toString());
    }

    /**
     * Test with null locale (should use system default)
     */
    @Test
    public void testNullLocale() {
        Locale.setDefault(Locale.ENGLISH);

        Configuration config = new ConfigurationBuilder()
                .setLocale(null)
                .createConfiguration();

        Assert.assertEquals("Null locale should use system default",
                Locale.getDefault(), config.getLocale());

        Nbvcxz nbvcxz = new Nbvcxz(config);
        Result result = nbvcxz.estimate("password");
        Assert.assertNotNull("Should get feedback even with null locale",
                result.getFeedback().getWarning());
    }

    /**
     * Test locale variants (e.g., en_US vs en_GB)
     */
    @Test
    public void testLocaleVariants() {
        Locale.setDefault(new Locale("fr"));

        Locale[] englishVariants = {
                Locale.ENGLISH,
                Locale.US,
                Locale.UK,
                new Locale("en", "CA")
        };

        String baselineWarning = null;

        for (Locale variant : englishVariants) {
            Configuration config = new ConfigurationBuilder()
                    .setLocale(variant)
                    .createConfiguration();

            Nbvcxz nbvcxz = new Nbvcxz(config);
            Result result = nbvcxz.estimate("password");
            String warning = result.getFeedback().getWarning();

            if (baselineWarning == null) {
                baselineWarning = warning;
            } else {
                Assert.assertEquals("All English variants should produce same feedback",
                        baselineWarning, warning);
            }

            Assert.assertEquals("Configuration should preserve requested locale variant",
                    variant, config.getLocale());
        }
    }

    /**
     * Test behavior when JVM default locale is unsupported and no explicit locale is set
     */
    @Test
    public void testUnsupportedJVMDefaultLocale() {
        Locale.setDefault(new Locale("ja", "JP"));

        try {
            Configuration config = new ConfigurationBuilder()
                    .createConfiguration();

            Nbvcxz nbvcxz = new Nbvcxz(config);
            Result result = nbvcxz.estimate("password");
            String warning = result.getFeedback().getWarning();

            Assert.assertNotNull("Should get some feedback even with unsupported default locale", warning);

        } catch (Exception e) {
            Assert.assertTrue("Should be MissingResourceException for unsupported locale",
                    e.getCause() instanceof java.util.MissingResourceException ||
                            e instanceof java.util.MissingResourceException);
        }
    }

    /**
     * Test that ConfigurationBuilder handles unsupported default locale gracefully
     */
    @Test
    public void testConfigurationBuilderWithUnsupportedDefault() {
        Locale originalDefault = Locale.getDefault();

        try {
            Locale.setDefault(new Locale("hi", "IN"));

            Configuration config = new ConfigurationBuilder()
                    .createConfiguration();

            Assert.assertEquals("Configuration should use JVM default even if unsupported",
                    Locale.getDefault(), config.getLocale());

        } finally {
            Locale.setDefault(originalDefault);
        }
    }

    /**
     * Test ResourceBundle behavior with completely invalid locale
     */
    @Test
    public void testInvalidLocaleHandling() {
        Locale weirdLocale = new Locale("xyz");

        ResourceBundle.Control noFallbackControl = ResourceBundle.Control.getNoFallbackControl(
                ResourceBundle.Control.FORMAT_PROPERTIES);

        ResourceBundle bundle = ResourceBundle.getBundle("feedback", weirdLocale, noFallbackControl);

        Assert.assertEquals("Invalid locale should fall back to root bundle",
                "", bundle.getLocale().toString());

        Assert.assertTrue("Root bundle should contain expected keys",
                bundle.containsKey("feedback.dictionary.warning.passwords.veryCommon"));
    }

    /**
     * Test that noFallbackControl prevents fallback to JVM default but allows root fallback
     */
    @Test
    public void testNoFallbackControlBehavior() {
        Locale.setDefault(new Locale("it"));

        ResourceBundle.Control noFallbackControl = ResourceBundle.Control.getNoFallbackControl(
                ResourceBundle.Control.FORMAT_PROPERTIES);

        Locale nonExistentLocale = new Locale("xyz");

        ResourceBundle bundle = ResourceBundle.getBundle("feedback", nonExistentLocale, noFallbackControl);

        Assert.assertEquals("Should use root bundle, not JVM default",
                "", bundle.getLocale().toString());

        Assert.assertNotEquals("Should not fall back to JVM default (Italian)",
                "it", bundle.getLocale().toString());
    }
}