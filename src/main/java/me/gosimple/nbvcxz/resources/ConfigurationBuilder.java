package me.gosimple.nbvcxz.resources;

import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.matching.*;
import me.gosimple.nbvcxz.matching.match.Match;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Builder class for creating {@link Configuration}.
 *
 * @author Adam Brusselback.
 */
public class ConfigurationBuilder
{
    private static final double YEAR = 365.2422 * 24 * 60 * 60 * 1000; // Average year length
    private static final long START = 1533096000000L; // Date values were chosen: 2018-08-01    
    
    private static final List<Dictionary> defaultDictionaries = new ArrayList<>();
    private static final List<PasswordMatcher> defaultPasswordMatchers = new ArrayList<>();
    private static final List<AdjacencyGraph> defaultAdjacencyGraphs = new ArrayList<>();
    private static final MungeTable defaultMungeTable = new MungeTable();
    
    static 
    {
        defaultPasswordMatchers.add(new DateMatcher());
        defaultPasswordMatchers.add(new YearMatcher());
        defaultPasswordMatchers.add(new RepeatMatcher());
        defaultPasswordMatchers.add(new SequenceMatcher());
        defaultPasswordMatchers.add(new SpacialMatcher());
        defaultPasswordMatchers.add(new DictionaryMatcher());
        defaultPasswordMatchers.add(new SeparatorMatcher());

        defaultDictionaries.add(new Dictionary("passwords", DictionaryUtil.loadRankedDictionary(DictionaryUtil.passwords), false));
        defaultDictionaries.add(new Dictionary("male_names", DictionaryUtil.loadRankedDictionary(DictionaryUtil.male_names), false));
        defaultDictionaries.add(new Dictionary("female_names", DictionaryUtil.loadRankedDictionary(DictionaryUtil.female_names), false));
        defaultDictionaries.add(new Dictionary("surnames", DictionaryUtil.loadRankedDictionary(DictionaryUtil.surnames), false));
        defaultDictionaries.add(new Dictionary("english", DictionaryUtil.loadRankedDictionary(DictionaryUtil.english), false));
        defaultDictionaries.add(new Dictionary("eff_large", DictionaryUtil.loadUnrankedDictionary(DictionaryUtil.eff_large), false));

        defaultAdjacencyGraphs.add(new AdjacencyGraph("Qwerty", AdjacencyGraphUtil.qwerty));
        defaultAdjacencyGraphs.add(new AdjacencyGraph("Standard Keypad", AdjacencyGraphUtil.standardKeypad));
        defaultAdjacencyGraphs.add(new AdjacencyGraph("Mac Keypad", AdjacencyGraphUtil.macKeypad));

        defaultMungeTable
            // simple single character substitutions (mostly leet speak)
            .addSub("4", "a")
            .addSub("@", "a")
            .addSub("8", "b")
            .addSub("(", "c")
            .addSub("{", "c")
            .addSub("[", "c")
            .addSub("<", "c", "k", "v")
            .addSub(">", "v")
            .addSub("3", "e")
            .addSub("9", "g", "q")
            .addSub("6", "d", "g")
            .addSub("&", "g")
            .addSub("#", "f", "h")
            .addSub("!", "i", "l")
            .addSub("1", "i", "l")
            .addSub("|", "i", "l")
            .addSub("0", "o")
            .addSub("$", "s")
            .addSub("5", "s")
            .addSub("+", "t")
            .addSub("7", "t", "l")
            .addSub("%", "x")
            .addSub("2", "z")
            // extra "munged" variations from here: https://en.wikipedia.org/wiki/Munged_password
            .addSub("?", "y") // (y = why?)
            .addSub("uu", "w")
            .addSub("vv", "w")
            .addSub("nn", "m")
            .addSub("2u", "uu", "w")
            .addSub("2v", "vv", "w")
            .addSub("2n", "nn", "m")
            .addSub("2b", "bb")
            .addSub("2d", "dd")
            .addSub("2g", "gg")
            .addSub("2l", "ll")
            .addSub("2p", "pp")
            .addSub("2t", "tt")
            .addSub("\\/\\/", "w")
            .addSub("/\\/\\", "m")
            .addSub("|)", "d")
            .sort();
    }

    private List<PasswordMatcher> passwordMatchers;
    private Map<String, Long> guessTypes;
    private List<Dictionary> dictionaries;
    private List<AdjacencyGraph> adjacencyGraphs;
    private MungeTable leetTable;
    private Pattern yearPattern;
    private Double minimumEntropy;
    private Locale locale;
    private Boolean distanceCalc;
    private Long combinationAlgorithmTimeout;
    private Long crackingHardwareCost;

    /**
     * @return Includes all standard password matchers included with Nbvcxz.
     */
    public static List<PasswordMatcher> getDefaultPasswordMatchers()
    {
        return defaultPasswordMatchers;
    }

    /**
     * Returns the Moore's law multiplier we're using for getDefaultGuessTypes().
     *
     * We will only return a multiplier greater than 1 if it has been more than year since we've updated the constants.
     * The date for this function is: 2018-08-01
     *
     * @return the Moore's Law multiplier
     */
    public static BigDecimal getMooresMultiplier()
    {
        double years = (System.currentTimeMillis() - START) / YEAR;

        // Only use the multiplier if we haven't updated the value date in over a year.
        if(years <= 1d)
        {
            years = 0;
        }

        // the multiplier for Moore's law is 2 to the power of (years / 2)
        return BigDecimal.valueOf(Math.pow(2d, years / 2d));
    }

    /**
     * This list was compiled in August 2018 using a baseline of what could be bought for roughly $20k usd for the offline attack values.
     * <p>
     * In the case this library is no longer maintained (or you choose to stay on an old version of it), we will scale the existing values by Moore's law.
     *
     * @param crackingHardwareCost the hardware cost (USD) to scale the guesses per second
     * @return The default list of guess types and associated values of guesses per second.
     */
    public static Map<String, Long> getDefaultGuessTypes(Long crackingHardwareCost)
    {
        BigDecimal moores_multiplier = getMooresMultiplier();
        BigDecimal cost_multiplier = BigDecimal.valueOf(crackingHardwareCost).divide(BigDecimal.valueOf(20000), 5, RoundingMode.HALF_UP);
        Map<String, Long> guessTypes = new HashMap<>();
        guessTypes.put("OFFLINE_MD5", cost_multiplier.multiply(moores_multiplier.multiply(BigDecimal.valueOf(250375000000L))).longValue());
        guessTypes.put("OFFLINE_SHA1", cost_multiplier.multiply(moores_multiplier.multiply(BigDecimal.valueOf(85963750000L))).longValue());
        guessTypes.put("OFFLINE_SHA512", cost_multiplier.multiply(moores_multiplier.multiply(BigDecimal.valueOf(10780875000L))).longValue());
        guessTypes.put("OFFLINE_BCRYPT_5", cost_multiplier.multiply(moores_multiplier.multiply(BigDecimal.valueOf(130875L))).longValue());
        guessTypes.put("OFFLINE_BCRYPT_10", cost_multiplier.multiply(moores_multiplier.multiply(BigDecimal.valueOf(4129L))).longValue());
        guessTypes.put("OFFLINE_BCRYPT_12", cost_multiplier.multiply(moores_multiplier.multiply(BigDecimal.valueOf(1033L))).longValue());
        guessTypes.put("OFFLINE_BCRYPT_14", cost_multiplier.multiply(moores_multiplier.multiply(BigDecimal.valueOf(259L))).longValue());
        guessTypes.put("ONLINE_UNTHROTTLED", 100L);
        guessTypes.put("ONLINE_THROTTLED", 2L);
        return guessTypes;
    }

    /**
     * This list was compiled in August 2018 using a baseline of what could be bought for roughly $20k usd for the offline attack values.
     * <p>
     * In the case this library is no longer maintained (or you choose to stay on an old version of it), we will scale the existing values by Moore's law.
     *
     * @return The default list of guess types and associated values of guesses per second.
     */
    public static Map<String, Long> getDefaultGuessTypes()
    {
        return getDefaultGuessTypes(getDefaultCrackingHardwareCost());
    }

    /**
     * @return Returns all the dictionaries included with Nbvcxz.
     * Namely there is a dictionary for common passwords, english male names, english female names, english surnames, and common english words.
     */
    public static List<Dictionary> getDefaultDictionaries()
    {
        return defaultDictionaries;
    }

    /**
     * @return Default keyboard adjacency graphs for standard querty, standard keypad, and mac keypad
     */
    public static List<AdjacencyGraph> getDefaultAdjacencyGraphs()
    {
        return defaultAdjacencyGraphs;
    }

    /**
     * @return The default table of common english leet substitutions
     */
    public static MungeTable getDefaultMungeTable()
    {
        return defaultMungeTable;
    }

    /**
     * @return The default pattern for years includes years 1900-2029
     */
    public static Pattern getDefaultYearPattern()
    {
        return Pattern.compile("19\\d\\d|200\\d|201\\d|202\\d");
    }

    /**
     * @return The default value for minimum entropy is 35.
     */
    public static double getDefaultMinimumEntropy()
    {
        return 35D;
    }

    /**
     * @return the default is false
     */
    public static Boolean getDefaultDistanceCalc()
    {
        return true;
    }

    /**
     * @return The default value for combination algorithm timeout is 500 (ms).
     */
    public static long getDefaultCombinationAlgorithmTimeout()
    {
        return 500l;
    }

    /**
     * @return The default value for hardware cost is 20000 usd.
     */
    public static long getDefaultCrackingHardwareCost()
    {
        return 20000;
    }



    /**
     * {@link PasswordMatcher} are what look for different patterns within the password and create an associated {@link Match} object.
     * <br>
     * Users of this library can implement their own {@link PasswordMatcher} and {@link Match} classes, here is where you would register them.
     *
     * @param passwordMatchers List of matchers
     * @return Builder
     */
    public ConfigurationBuilder setPasswordMatchers(List<PasswordMatcher> passwordMatchers)
    {
        this.passwordMatchers = passwordMatchers;
        return this;
    }

    /**
     * Guess types are used to calculate how long an attack would take using that method using guesses/sec.
     *
     * @param guessTypes key is a description of the type of guess, value is how many guesses per second
     * @return Builder
     */
    public ConfigurationBuilder setGuessTypes(Map<String, Long> guessTypes)
    {
        this.guessTypes = guessTypes;
        return this;
    }

    /**
     * Dictionaries are used by the {@link DictionaryMatcher} to find common words, names, and known passwords within the password.
     *
     * @param dictionaries List of dictionaries
     * @return Builder
     */
    public ConfigurationBuilder setDictionaries(List<Dictionary> dictionaries)
    {
        this.dictionaries = dictionaries;
        return this;
    }

    /**
     * {@link AdjacencyGraph}s are used to find spacial patterns within passwords (e.g. asdfghj).
     *
     * @param adjacencyGraphs List of adjacencyGraphs
     * @return Builder
     */
    public ConfigurationBuilder setAdjacencyGraphs(List<AdjacencyGraph> adjacencyGraphs)
    {
        this.adjacencyGraphs = adjacencyGraphs;
        return this;
    }

    /**
     * The leet table is used to check within a password for common character substitutions (e.g. s to $).
     *
     * @param leetTable Map for leetTable
     * @return Builder
     */
    public ConfigurationBuilder setLeetTable(MungeTable leetTable)
    {
        this.leetTable = leetTable;
        return this;
    }

    /**
     * Year patterns are used to look for years within a password.
     *
     * @param yearPattern Pattern for year matching
     * @return Builder
     */
    public ConfigurationBuilder setYearPattern(Pattern yearPattern)
    {
        this.yearPattern = yearPattern;
        return this;
    }

    /**
     * Used to check if the password is secure enough, and give feedback if not.
     *
     * @param minimumEntropy Value for minimumEntropy (should be a positive value)
     * @return Builder
     */
    public ConfigurationBuilder setMinimumEntropy(Double minimumEntropy)
    {
        this.minimumEntropy = minimumEntropy;
        return this;
    }

    /**
     * Sets the minimum entropy based on time to crack, and a specific guess type.
     * <br>
     * If you are specifying a cracking hardware cost, you should set that prior to calling this.
     *
     * @param seconds_to_crack Value in seconds that you want to consider the minimum for a password to be considered good
     * @param guess_type The guess type to use to figure out what the guesses per second are for this calculation
     * @return Builder
     */
    public ConfigurationBuilder setMinimumEntropy(BigDecimal seconds_to_crack, String guess_type)
    {
        BigDecimal guesses_per_second;
        if(guessTypes != null)
            guesses_per_second = BigDecimal.valueOf(guessTypes.get(guess_type));
        else
            guesses_per_second = BigDecimal.valueOf(getDefaultGuessTypes(null != crackingHardwareCost ? crackingHardwareCost : getDefaultCrackingHardwareCost()).get(guess_type));

        BigDecimal guesses = guesses_per_second.multiply(seconds_to_crack);

        minimumEntropy = Nbvcxz.getEntropyFromGuesses(guesses);
        return this;
    }

    /**
     * Supported locales are en, and fr. <br>
     * Default locale is en.
     *
     * @param locale Locale for localization
     * @return Builder
     */
    public ConfigurationBuilder setLocale(Locale locale)
    {
        this.locale = locale;
        return this;
    }

    /**
     * Distance based dictionary calculations which provide support for misspelling
     * detection, at the expense of performance.  This will slow down calculations
     * by an order of magnitude.
     *
     * @param distanceCalc true to enable distance based dictionary calculations
     * @return Builder
     */
    public ConfigurationBuilder setDistanceCalc(final Boolean distanceCalc)
    {
        this.distanceCalc = distanceCalc;
        return this;
    }

    /**
     * Timeout for the findBestCombination algorithm. If there are too many possible matches at each position of
     * the password, the algorithm can take too long to get an answer and we must fall back to a simpler algorithm.
     * <p>
     * To disable the findBestMatches calculation and always fall back to the faster, less accurate one, set to 0.
     *
     * @param combinationAlgorithmTimeout The time in ms to timeout
     * @return Builder
     */
    public ConfigurationBuilder setCombinationAlgorithmTimeout(final Long combinationAlgorithmTimeout)
    {
        this.combinationAlgorithmTimeout = combinationAlgorithmTimeout;
        return this;
    }

    /**
     * Sets the cost of cracking hardware to scale the guesses / second for the default guess types.
     * <br>
     * Does not have any affect if you manually specify the guess types.
     * @param crackingHardwareCost The hardware cost in USD
     * @return Builder
     */
    public ConfigurationBuilder setCrackingHardwareCost(final Long crackingHardwareCost)
    {
        this.crackingHardwareCost = crackingHardwareCost;
        return this;
    }

    /**
     * Creates the {@link Configuration} object using all values set in this builder, or default values if unset.
     *
     * @return Configuration object from builder
     */
    public Configuration createConfiguration()
    {
        if (crackingHardwareCost == null)
        {
            crackingHardwareCost = getDefaultCrackingHardwareCost();
        }
        if (passwordMatchers == null)
        {
            passwordMatchers = getDefaultPasswordMatchers();
        }
        if (guessTypes == null)
        {
            guessTypes = getDefaultGuessTypes(crackingHardwareCost);
        }
        if (dictionaries == null)
        {
            dictionaries = getDefaultDictionaries();
        }
        if (adjacencyGraphs == null)
        {
            adjacencyGraphs = getDefaultAdjacencyGraphs();
        }
        if (leetTable == null)
        {
            leetTable = getDefaultMungeTable();
        }
        if (yearPattern == null)
        {
            yearPattern = getDefaultYearPattern();
        }
        if (minimumEntropy == null)
        {
            minimumEntropy = getDefaultMinimumEntropy();
        }
        if (locale == null)
        {
            locale = Locale.getDefault();
        }
        if (distanceCalc == null)
        {
            distanceCalc = getDefaultDistanceCalc();
        }
        if (combinationAlgorithmTimeout == null)
        {
            combinationAlgorithmTimeout = getDefaultCombinationAlgorithmTimeout();
        }
        return new Configuration(passwordMatchers, guessTypes, dictionaries, adjacencyGraphs, leetTable, yearPattern, minimumEntropy, locale, distanceCalc, combinationAlgorithmTimeout);
    }


}
