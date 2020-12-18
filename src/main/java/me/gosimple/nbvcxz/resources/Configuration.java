package me.gosimple.nbvcxz.resources;

import me.gosimple.nbvcxz.matching.*;

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
    private final MungeTable mungeTable;
    private final Pattern yearPattern;
    private final Double minimumEntropy;
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
     * @param mungeTable                   Munge table for use with {@link DictionaryMatcher}
     * @param yearPattern                 Regex {@link Pattern} for use with {@link YearMatcher}
     * @param minimumEntropy              Minimum entropy value passwords should meet
     * @param locale                      Locale for localized text and feedback
     * @param distanceCalc                Enable or disable levenshtein distance calculation for dictionary matches
     * @param combinationAlgorithmTimeout Timeout for the findBestMatches algorithm
     */
    public Configuration(List<PasswordMatcher> passwordMatchers, Map<String, Long> guessTypes, List<Dictionary> dictionaries, List<AdjacencyGraph> adjacencyGraphs, MungeTable mungeTable, Pattern yearPattern, Double minimumEntropy, Locale locale, boolean distanceCalc, long combinationAlgorithmTimeout)
    {
        this.passwordMatchers = passwordMatchers;
        this.guessTypes = guessTypes;
        this.dictionaries = dictionaries;
        this.adjacencyGraphs = adjacencyGraphs;
        this.mungeTable = mungeTable;
        this.yearPattern = yearPattern;
        this.minimumEntropy = minimumEntropy;
        this.locale = locale;
        this.distanceCalc = distanceCalc;
        this.mainResource = ResourceBundle.getBundle("main", locale);
        this.feedbackResource = ResourceBundle.getBundle("feedback", locale);
        this.combinationAlgorithmTimeout = combinationAlgorithmTimeout;
    }

    /**
     * @return The list of {@link PasswordMatcher}s which will be used for matching
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
    public MungeTable getMungeTable()
    {
        return mungeTable;
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
     * @return Locale for localized text and feedback
     */
    public Locale getLocale()
    {
        return locale;
    }

    /**
     * @return if dictionary distance calculations are enabled or not
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
