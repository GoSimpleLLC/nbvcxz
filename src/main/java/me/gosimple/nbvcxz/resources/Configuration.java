package me.gosimple.nbvcxz.resources;

import me.gosimple.nbvcxz.matching.DictionaryMatcher;
import me.gosimple.nbvcxz.matching.PasswordMatcher;
import me.gosimple.nbvcxz.matching.SpacialMatcher;
import me.gosimple.nbvcxz.matching.YearMatcher;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Used to set any configurable parameters when estimating password strength.
 *
 * @author Adam Brusselback.
 */
public class Configuration
{
    private final List<PasswordMatcher> passwordMatchers;
    private final Map<String, Long> guessTypes;
    private final List<Dictionary> dictionaries;
    private final List<AdjacencyGraph> adjacencyGraphs;
    private final Map<Character, Character[]> leetTable;
    private final Pattern yearPattern;
    private final Double minimumEntropy;
    private final Integer maxLength;
    private final Locale locale;
    private final boolean distanceCalc;
    private final ResourceBundle mainResource;
    private final ResourceBundle feedbackResource;
    private final long combinationAlgorithmTimeout;

    /**
     * @param passwordMatchers            The list of {@link PasswordMatcher}s which will be used for matching
     * @param guessTypes                  Map of types of guesses, and associated guesses/sec
     * @param dictionaries                List of {@link Dictionary} to use for the {@link DictionaryMatcher}
     * @param adjacencyGraphs             List of adjacency graphs to be used by the {@link SpacialMatcher}
     * @param leetTable                   Leet table for use with {@link DictionaryMatcher}
     * @param yearPattern                 Regex {@link Pattern} for use with {@link YearMatcher}
     * @param minimumEntropy              Minimum entropy value passwords should meet
     * @param locale                      Locale for localized text and feedback
     * @param distanceCalc                Enable or disable levenshtein distance calculation for dictionary matches
     * @param combinationAlgorithmTimeout Timeout for the findBestMatches algorithm
     */
    public Configuration(List<PasswordMatcher> passwordMatchers, Map<String, Long> guessTypes, List<Dictionary> dictionaries, List<AdjacencyGraph> adjacencyGraphs, Map<Character, Character[]> leetTable, Pattern yearPattern, Double minimumEntropy, Integer maxLength, Locale locale, boolean distanceCalc, long combinationAlgorithmTimeout)
    {
        this.passwordMatchers = passwordMatchers;
        this.guessTypes = guessTypes;
        this.dictionaries = dictionaries;
        this.adjacencyGraphs = adjacencyGraphs;
        this.leetTable = leetTable;
        this.yearPattern = yearPattern;
        this.minimumEntropy = minimumEntropy;
        this.maxLength = maxLength;
        this.locale = locale;
        this.distanceCalc = distanceCalc;
        ResourceBundle.Control noFallbackControl = ResourceBundle.Control.getNoFallbackControl(
                ResourceBundle.Control.FORMAT_PROPERTIES);
        this.mainResource = ResourceBundle.getBundle("main", locale, noFallbackControl);
        this.feedbackResource = ResourceBundle.getBundle("feedback", locale, noFallbackControl);
        this.combinationAlgorithmTimeout = combinationAlgorithmTimeout;
    }

    /**
     * @return List of {@link PasswordMatcher}s which will be used for matching
     */
    public List<PasswordMatcher> getPasswordMatchers()
    {
        return passwordMatchers;
    }

    /**
     * @return Map of types of guesses, and associated guesses/sec
     */
    public Map<String, Long> getGuessTypes()
    {
        return guessTypes;
    }

    /**
     * @return List of {@link Dictionary} to use for the {@link DictionaryMatcher}
     */
    public List<Dictionary> getDictionaries()
    {
        return dictionaries;
    }

    /**
     * @return List of adjacency graphs to be used by the {@link SpacialMatcher}
     */
    public List<AdjacencyGraph> getAdjacencyGraphs()
    {
        return adjacencyGraphs;
    }

    /**
     * @return Leet table for use with {@link DictionaryMatcher}
     */
    public Map<Character, Character[]> getLeetTable()
    {
        return leetTable;
    }

    /**
     * @return Regex {@link Pattern} for use with {@link YearMatcher}
     */
    public Pattern getYearPattern()
    {
        return yearPattern;
    }

    /**
     * @return Minimum entropy value passwords should meet
     */
    public Double getMinimumEntropy()
    {
        return minimumEntropy;
    }

    /**
     * @return Maximum length to estimate for password, anything longer will be truncated
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    /**
     * @return Locale for localized text and feedback
     */
    public Locale getLocale()
    {
        return locale;
    }

    /**
     * @return If dictionary distance calculations are enabled or not
     */
    public boolean isDistanceCalc()
    {
        return distanceCalc;
    }

    /**
     * @return Return the timeout for the findBestMatches algorithm
     */
    public long getCombinationAlgorithmTimeout()
    {
        return combinationAlgorithmTimeout;
    }

    /**
     * @return Return the resource bundle which contains the text for everything but feedback
     */
    public ResourceBundle getMainResource()
    {
        return mainResource;
    }

    /**
     * @return Return the resource bundle which contains the text for feedback
     */
    public ResourceBundle getFeedbackResource()
    {
        return feedbackResource;
    }
}
