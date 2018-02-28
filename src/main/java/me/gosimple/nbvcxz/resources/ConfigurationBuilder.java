package me.gosimple.nbvcxz.resources;

import me.gosimple.nbvcxz.matching.DateMatcher;
import me.gosimple.nbvcxz.matching.DictionaryMatcher;
import me.gosimple.nbvcxz.matching.PasswordMatcher;
import me.gosimple.nbvcxz.matching.RepeatMatcher;
import me.gosimple.nbvcxz.matching.SeparatorMatcher;
import me.gosimple.nbvcxz.matching.SequenceMatcher;
import me.gosimple.nbvcxz.matching.SpacialMatcher;
import me.gosimple.nbvcxz.matching.YearMatcher;
import me.gosimple.nbvcxz.matching.match.Match;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private List<PasswordMatcher> passwordMatchers;
    private Map<String, Long> guessTypes;
    private List<Dictionary> dictionaries;
    private List<AdjacencyGraph> adjacencyGraphs;
    private Map<Character, Character[]> leetTable;
    private Pattern yearPattern;
    private Double minimumEntropy;
    private Locale locale;
    private Boolean distanceCalc;
    private Long combinationAlgorithmTimeout;

    /**
     * @return Includes all standard password matchers included with Nbvcxz.
     */
    public static List<PasswordMatcher> getDefaultPasswordMatchers()
    {
        List<PasswordMatcher> passwordMatchers = new ArrayList<>();
        passwordMatchers.add(new DateMatcher());
        passwordMatchers.add(new YearMatcher());
        passwordMatchers.add(new RepeatMatcher());
        passwordMatchers.add(new SequenceMatcher());
        passwordMatchers.add(new SpacialMatcher());
        passwordMatchers.add(new DictionaryMatcher());
        passwordMatchers.add(new SeparatorMatcher());
        return passwordMatchers;
    }

    /**
     * Returns the Moore's law multiplier we're using for getDefaultGuessTypes().
     *
     * We will only return a multiplier greater than 1 if it has been more than year since we've updated the constants.
     * The date for this function is: March 2018
     * @return the Moore's Law multiplier
     */
    public static BigDecimal getMooresMultiplier()
    {
        // The date which the value's below were chosen
        LocalDate value_date = LocalDate.of(2015, 3, 1);
        LocalDate current_date = LocalDate.now();
        double years = Double.valueOf(ChronoUnit.MONTHS.between(value_date, current_date)) / 12;
        // Only use the multiplier if we haven't updated the value date in over a year.
        if(years <= 1d)
        {
            years = 0;
        }

        // the multiplier for Moore's law is 2 to the power of (years / 2)
        return BigDecimal.valueOf(Math.pow(2d, years / 2d));
    }

    /**
     * @return The default list of guess types and associated values of guesses per second.
     * This list was compiled in March 2018 using a baseline of what could be bought for roughly $20k usd for the offline attack values.
     * <p>
     * In the case this library is no longer maintained (or you choose to stay on an old version of it), we will scale the existing values by Moore's law.
     */
    public static Map<String, Long> getDefaultGuessTypes()
    {
        BigDecimal moores_multiplier = getMooresMultiplier();
        Map<String, Long> guessTypes = new HashMap<>();
        guessTypes.put("OFFLINE_MD5", moores_multiplier.multiply(BigDecimal.valueOf(250375000000L)).longValue());
        guessTypes.put("OFFLINE_SHA1", moores_multiplier.multiply(BigDecimal.valueOf(85963750000L)).longValue());
        guessTypes.put("OFFLINE_SHA512", moores_multiplier.multiply(BigDecimal.valueOf(10780875000L)).longValue());
        guessTypes.put("OFFLINE_BCRYPT_5", moores_multiplier.multiply(BigDecimal.valueOf(130875L)).longValue());
        guessTypes.put("OFFLINE_BCRYPT_10", moores_multiplier.multiply(BigDecimal.valueOf(4129L)).longValue());
        guessTypes.put("OFFLINE_BCRYPT_12", moores_multiplier.multiply(BigDecimal.valueOf(1033L)).longValue());
        guessTypes.put("OFFLINE_BCRYPT_14", moores_multiplier.multiply(BigDecimal.valueOf(259L)).longValue());
        guessTypes.put("ONLINE_UNTHROTTLED", 100L);
        guessTypes.put("ONLINE_THROTTLED", 2L);
        return guessTypes;
    }

    /**
     * @return Returns all the dictionaries included with Nbvcxz.
     * Namely there is a dictionary for common passwords, english male names, english female names, english surnames, and common english words.
     */
    public static List<Dictionary> getDefaultDictionaries()
    {
        List<Dictionary> tmpDictionaries = new ArrayList<>();
        tmpDictionaries.add(new Dictionary("passwords", DictionaryUtil.loadRankedDictionary(DictionaryUtil.passwords), false));
        tmpDictionaries.add(new Dictionary("male_names", DictionaryUtil.loadRankedDictionary(DictionaryUtil.male_names), false));
        tmpDictionaries.add(new Dictionary("female_names", DictionaryUtil.loadRankedDictionary(DictionaryUtil.female_names), false));
        tmpDictionaries.add(new Dictionary("surnames", DictionaryUtil.loadRankedDictionary(DictionaryUtil.surnames), false));
        tmpDictionaries.add(new Dictionary("english", DictionaryUtil.loadRankedDictionary(DictionaryUtil.english), false));
        tmpDictionaries.add(new Dictionary("eff_large", DictionaryUtil.loadUnrankedDictionary(DictionaryUtil.eff_large), false));
        return tmpDictionaries;
    }

    /**
     * @return Default keyboard adjacency graphs for standard querty, standard keypad, and mac keypad
     */
    public static List<AdjacencyGraph> getDefaultAdjacencyGraphs()
    {
        List<AdjacencyGraph> tmpAdjacencyGraphs = new ArrayList<>();
        tmpAdjacencyGraphs.add(new AdjacencyGraph("Qwerty", AdjacencyGraphUtil.qwerty));
        tmpAdjacencyGraphs.add(new AdjacencyGraph("Standard Keypad", AdjacencyGraphUtil.standardKeypad));
        tmpAdjacencyGraphs.add(new AdjacencyGraph("Mac Keypad", AdjacencyGraphUtil.macKeypad));
        return tmpAdjacencyGraphs;
    }

    /**
     * @return The default table of common english leet substitutions
     */
    public static Map<Character, Character[]> getDefaultLeetTable()
    {
        Map<Character, Character[]> tmpLeetTable = new HashMap<>();
        tmpLeetTable.put('4', new Character[]{'a'});
        tmpLeetTable.put('@', new Character[]{'a'});
        tmpLeetTable.put('8', new Character[]{'b'});
        tmpLeetTable.put('(', new Character[]{'c'});
        tmpLeetTable.put('{', new Character[]{'c'});
        tmpLeetTable.put('[', new Character[]{'c'});
        tmpLeetTable.put('<', new Character[]{'c'});
        tmpLeetTable.put('3', new Character[]{'e'});
        tmpLeetTable.put('9', new Character[]{'g'});
        tmpLeetTable.put('6', new Character[]{'g'});
        tmpLeetTable.put('&', new Character[]{'g'});
        tmpLeetTable.put('#', new Character[]{'h'});
        tmpLeetTable.put('!', new Character[]{'i', 'l'});
        tmpLeetTable.put('1', new Character[]{'i', 'l'});
        tmpLeetTable.put('|', new Character[]{'i', 'l'});
        tmpLeetTable.put('0', new Character[]{'o'});
        tmpLeetTable.put('$', new Character[]{'s'});
        tmpLeetTable.put('5', new Character[]{'s'});
        tmpLeetTable.put('+', new Character[]{'t'});
        tmpLeetTable.put('7', new Character[]{'t', 'l'});
        tmpLeetTable.put('%', new Character[]{'x'});
        tmpLeetTable.put('2', new Character[]{'z'});
        return tmpLeetTable;
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
     * @return The default value for minimum entropy is 35.
     */
    public static long getDefaultCombinationAlgorithmTimeout()
    {
        return 500l;
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
    public ConfigurationBuilder setLeetTable(Map<Character, Character[]> leetTable)
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
     * <br>
     * Should be a positive value.
     *
     * @param minimumEntropy Value for minimumEntropy
     * @return Builder
     */
    public ConfigurationBuilder setMinimumEntropy(Double minimumEntropy)
    {
        this.minimumEntropy = minimumEntropy;
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
     * Creates the {@link Configuration} object using all values set in this builder, or default values if unset.
     *
     * @return Configuration object from builder
     */
    public Configuration createConfiguration()
    {
        if (passwordMatchers == null)
        {
            passwordMatchers = getDefaultPasswordMatchers();
        }
        if (guessTypes == null)
        {
            guessTypes = getDefaultGuessTypes();
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
            leetTable = getDefaultLeetTable();
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
