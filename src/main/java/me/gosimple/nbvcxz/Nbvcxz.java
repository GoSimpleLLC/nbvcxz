package me.gosimple.nbvcxz;

import me.gosimple.nbvcxz.matching.PasswordMatcher;
import me.gosimple.nbvcxz.matching.match.BruteForceMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.resources.ConfigurationBuilder;
import me.gosimple.nbvcxz.resources.Feedback;
import me.gosimple.nbvcxz.resources.FeedbackUtil;
import me.gosimple.nbvcxz.scoring.Result;
import me.gosimple.nbvcxz.scoring.TimeEstimate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class allows you to do estimates on passwords.  It can be instantiated and configured once, and the same
 * instance should be used for subsequent password estimates.
 *
 * @author Adam Brusselback
 *
 */
public class Nbvcxz
{
    private Configuration configuration;

    /**
     * Creates new instance with a default configuration.
     */
    public Nbvcxz()
    {
        this.configuration = new ConfigurationBuilder().createConfiguration();
    }

    /**
     * Creates a new instance with a custom configuration.
     * @param configuration a {@code Configuration} to be used in all estimates.
     */
    public Nbvcxz(Configuration configuration)
    {
        this.configuration = configuration;
    }

    /**
     * Gets the current configuration.
     * @return returns {@code Configuration}
     */
    public Configuration getConfiguration()
    {
        return configuration;
    }

    /**
     * Sets the configuration.
     * @param configuration a {@code Configuration} to be used in all estimates.
     */
    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

    /**
     * Guess the entropy of a password with the configuration provided.
     * @param password The password you would like to attempt to estimate on.
     * @return Result object that contains info about the password.
     */
    public Result estimate(final String password)
    {
        return guessEntropy(this.configuration, password);
    }

    /**
     * Calculates the minimum entropy for a given password and returns that as a Result.
     * <br><br>
     * This method attempts to find the minimum entropy at each position of the password, and then does
     * a backwards pass to remove overlapping matches.  The end result is a list of matches that when
     * their tokens are added up, should equal the original password.
     * <br><br>
     * The result object is guaranteed to match the original password, or throw an exception if it doesn't.
     *
     * @param configuration the configuration file used to estimate entropy.
     * @param password the password you are guessing entropy for.
     * @return the {@code Result} of this estimate.
     */
    private static Result guessEntropy(final Configuration configuration, final String password)
    {
        List<Match> matches = getMatches(configuration, password);
        int length = password.length();
        Match[] match_at_index = new Match[length];
        List<Match> match_list = new ArrayList<>();

        // First pass through the password forward.
        // Set the match to be the lowest average entropy for the length the part of the password takes.
        for (int k = 0; k < length; k++)
        {
            for (Match match : matches)
            {
                if (match.getEndIndex() == k)
                {
                    if (match_at_index[k] == null || match_at_index[k].calculateEntropy() / match_at_index[k].getLength() > match.calculateEntropy() / match.getLength())
                    {
                        match_at_index[k] = match;
                    }
                }
            }
        }


        // Now go backwards through the password.
        // Fill in any empty matches with brute force matches, add all matches to the match_list in reverse order.
        int k = length - 1;
        while (k >= 0)
        {
            Match match = match_at_index[k];
            if (match == null)
            {
                match_list.add(createBruteForceMatch(password, configuration, k, k));
                k--;
                continue;
            }
            match_list.add(match);
            k = match.getStartIndex() - 1;
        }

        // Reverse the order of the list so it's now first to last.
        Collections.reverse(match_list);
        return new Result(configuration, password, match_list);
    }

    /**
     * Gets all matches for a given password.
     * @param configuration the configuration file used to estimate entropy.
     * @param password the password to get matches for.
     * @return a {@code List} of {@code Match} objects for the supplied password.
     */
    private static List<Match> getMatches(final Configuration configuration, final String password)
    {
        List<Match> matches = new ArrayList<>();

        for(PasswordMatcher passwordMatcher : configuration.getPasswordMatchers())
        {
            matches.addAll(passwordMatcher.match(configuration, password));
        }

        return matches;
    }

    /**
     * Creates a brute force match for a portion of the password.
     *
     * @param password the password to create brute match for
     * @param start_index the index start of the password part that needs a {@code Match}
     * @param end_index the index end of the password part that needs a {@code Match}
     * @return a {@code Match} object
     */
    private static Match createBruteForceMatch(final String password, final Configuration configuration, final int start_index, final int end_index)
    {
        return new BruteForceMatch(password.substring(start_index, end_index + 1), configuration, start_index, end_index);
    }

    /**
     * Gets the entropy from the number of guesses passed in.
     *
     * @param guesses a {@code BigDecimal} representing the number of guesses.
     *
     * @return entropy {@code Double} that is calculated based on the guesses.
     */
    public static Double getEntropyFromGuesses(final BigDecimal guesses)
    {
        Double guesses_tmp = guesses.doubleValue();
        guesses_tmp = guesses_tmp.isInfinite() ? Double.MAX_VALUE : guesses_tmp;
        return Math.log(guesses_tmp) / Math.log(2);
    }

    /**
     * Gets the number of guesses from the entropy passed in.
     *
     * @param entropy a {@code Double} representing the number of guesses.
     *
     * @return guesses {@code BigDecimal} that is calculated based on the entropy.
     */
    public static BigDecimal getGuessesFromEntropy(final Double entropy)
    {
        final Double guesses_tmp = Math.pow(2, entropy);
        return new BigDecimal(guesses_tmp.isInfinite() ? Double.MAX_VALUE : guesses_tmp).setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * Console application which will run with default configurations.
     * @param args arguments which are ignored!
     */
    public static void main2(String... args)
    {
        Nbvcxz nbvcxz = new Nbvcxz();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("main", nbvcxz.getConfiguration().getLocale());
        Scanner scanner = new Scanner(System.in);
        System.out.println(resourceBundle.getString("main.howToQuit"));

        String password;

        while(true)
        {
            System.out.println(resourceBundle.getString("main.startPrompt"));
            password = scanner.nextLine();
            if("\\quit".equals(password))
                break;
            printExstimationInfo(nbvcxz, password);
        }
        System.out.println(resourceBundle.getString("main.quitPrompt") + " ");

    }

    private static void printExstimationInfo(final Nbvcxz nbvcxz, final String password)
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("main", nbvcxz.getConfiguration().getLocale());

        long start = System.currentTimeMillis();
        Result result = nbvcxz.estimate(password);
        long end = System.currentTimeMillis();

        System.out.println("----------------------------------------------------------");
        System.out.println(resourceBundle.getString("main.timeToCalculate") + " " + (end - start) + " ms");
        System.out.println(resourceBundle.getString("main.password") + " " + password);
        System.out.println(resourceBundle.getString("main.entropy") + " " + result.getEntropy());
        Feedback feedback = FeedbackUtil.getFeedback(result);
        if(feedback.getWarning() != null)
            System.out.println(resourceBundle.getString("main.feedback.warning") + " " + feedback.getWarning());
        for(String suggestion : feedback.getSuggestion())
        {
            System.out.println(resourceBundle.getString("main.feedback.suggestion") + " " + suggestion);
        }
        Map<String, Long> sortedMap =
                result.getConfiguration().getGuessTypes().entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        for(Map.Entry<String, Long> guessType : sortedMap.entrySet())
        {
            System.out.println(resourceBundle.getString("main.timeToCrack") + " " + guessType.getKey() + ": " + TimeEstimate.getTimeToCrackFormatted(result, guessType.getKey()));
        }
        for(Match match : result.getMatches())
        {
            System.out.println("-----------------------------------");
            System.out.println(match.getDetails());
        }
        System.out.println("----------------------------------------------------------");
    }

    public static void main(String... args)
    {

        Nbvcxz nbvcxz = new Nbvcxz();

        long start = System.currentTimeMillis();
        double entropyCalculated = 0;
        String val = "password";
        for(int i = 0; i < 1000; i++)
        {
            Result result = nbvcxz.estimate(val);
            entropyCalculated += result.getEntropy();
        }
        long end = System.currentTimeMillis();

        System.out.println("Entropy calculated: " + entropyCalculated);
        System.out.println("Time to calculate: " + (end - start) + " ms");
    }
}
