package me.gosimple.nbvcxz;

import me.gosimple.nbvcxz.matching.DeadlinePasswordMatcher;
import me.gosimple.nbvcxz.matching.PasswordMatcher;
import me.gosimple.nbvcxz.matching.match.BruteForceMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.*;
import me.gosimple.nbvcxz.scoring.Result;
import me.gosimple.nbvcxz.scoring.TimeEstimate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * This class allows you to do estimates on passwords.  It can be instantiated and configured once, and the same
 * instance should be used for subsequent password estimates.
 *
 * @author Adam Brusselback
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
     *
     * @param configuration a {@code Configuration} to be used in all estimates.
     */
    public Nbvcxz(Configuration configuration)
    {
        this.configuration = configuration;
    }

    /**
     * Creates a brute force match for a portion of the password.
     *
     * @param password      the password to create brute match for
     * @param configuration the configuration
     * @param index         the index of the password part that needs a {@code BruteForceMatch}
     * @return a {@code Match} object
     */
    private static Match createBruteForceMatch(final Configuration configuration, final String password, final int index)
    {
        return new BruteForceMatch(password.charAt(index), configuration, index);
    }

    /**
     * Returns the truncated password based on the max length.
     *
     * @param configuration
     * @param password
     * @return {@code String} of the truncated password
     */
    private static String getTruncatedPassword(final Configuration configuration, final String password)
    {
        if (configuration.getMaxLength().compareTo(password.length()) > 0)
            return password;
        else
            return password.substring(0, configuration.getMaxLength());
    }

    /**
     * Gets the entropy from the number of guesses passed in.
     *
     * @param guesses a {@code BigDecimal} representing the number of guesses.
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
     * @return guesses {@code BigDecimal} that is calculated based on the entropy.
     */
    public static BigDecimal getGuessesFromEntropy(final Double entropy)
    {
        final Double guesses_tmp = Math.pow(2, entropy);
        return BigDecimal.valueOf(guesses_tmp.isInfinite() ? Double.MAX_VALUE : guesses_tmp).setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * Console application which will run with default configurations.
     *
     * @param args arguments which are ignored!
     */
    public static void main(String... args)
    {
        Configuration configuration = new ConfigurationBuilder().createConfiguration();
        Nbvcxz nbvcxz = new Nbvcxz(configuration);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("main", nbvcxz.getConfiguration().getLocale());
        Scanner scanner = new Scanner(System.in);

        String input;

        while (true)
        {
            System.out.println(resourceBundle.getString("main.startPrompt"));
            System.out.println(resourceBundle.getString("main.enterCommand"));
            input = scanner.nextLine();
            if ("q".equals(input))
            {
                break;
            }
            if ("g".equals(input))
            {
                System.out.println(resourceBundle.getString("main.generatorType"));
                input = scanner.nextLine();
                if ("p".equals(input))
                {
                    System.out.println(resourceBundle.getString("main.delimiterPrompt"));
                    String delimiter = scanner.nextLine();
                    System.out.println(resourceBundle.getString("main.wordsPrompt"));
                    while (!scanner.hasNextInt())
                    {
                        scanner.next();
                    }
                    int words = scanner.nextInt();
                    scanner.nextLine();
                    printGenerationInfo(nbvcxz, Generator.generatePassphrase(delimiter, words));
                }
                if ("r".equals(input))
                {
                    System.out.println(resourceBundle.getString("main.randomType"));
                    Generator.CharacterTypes characterTypes = null;
                    input = scanner.nextLine();
                    if ("1".equals(input))
                    {
                        characterTypes = Generator.CharacterTypes.ALPHA;
                    }
                    if ("2".equals(input))
                    {
                        characterTypes = Generator.CharacterTypes.ALPHANUMERIC;
                    }
                    if ("3".equals(input))
                    {
                        characterTypes = Generator.CharacterTypes.ALPHANUMERICSYMBOL;
                    }
                    if ("4".equals(input))
                    {
                        characterTypes = Generator.CharacterTypes.NUMERIC;
                    }
                    if (characterTypes == null)
                    {
                        continue;
                    }
                    System.out.println(resourceBundle.getString("main.lengthPrompt"));
                    while (!scanner.hasNextInt())
                    {
                        scanner.next();
                    }
                    int length = scanner.nextInt();
                    scanner.nextLine();
                    printGenerationInfo(nbvcxz, Generator.generateRandomPassword(characterTypes, length));
                }
            }
            if ("e".equals(input))
            {
                System.out.println(resourceBundle.getString("main.estimatePrompt"));
                String password = scanner.nextLine();
                printEstimationInfo(nbvcxz, password);
            }
        }
        System.out.println(resourceBundle.getString("main.quitPrompt") + " ");

    }

    private static void printGenerationInfo(final Nbvcxz nbvcxz, final String password)
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("main", nbvcxz.getConfiguration().getLocale());
        System.out.println("----------------------------------------------------------");
        System.out.println(resourceBundle.getString("main.password") + " " + password);
        System.out.println("----------------------------------------------------------");

    }

    private static void printEstimationInfo(final Nbvcxz nbvcxz, final String password)
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("main", nbvcxz.getConfiguration().getLocale());

        long start = System.currentTimeMillis();
        Result result = nbvcxz.estimate(password);
        long end = System.currentTimeMillis();

        System.out.println("----------------------------------------------------------");
        System.out.println(resourceBundle.getString("main.timeToCalculate") + " " + (end - start) + " ms");
        System.out.println(resourceBundle.getString("main.password") + " " + result.getPassword());
        if (result.isTruncated()) {
            System.out.println(resourceBundle.getString("main.fullPassword") + " " + result.getFullPassword());
        }
        System.out.println(resourceBundle.getString("main.entropy") + " " + result.getEntropy());
        Feedback feedback = FeedbackUtil.getFeedback(result);
        System.out.println(resourceBundle.getString(feedback.getResult()));
        if (feedback.getWarning() != null)
        {
            System.out.println(resourceBundle.getString("main.feedback.warning") + " " + feedback.getWarning());
        }
        for (String suggestion : feedback.getSuggestion())
        {
            System.out.println(resourceBundle.getString("main.feedback.suggestion") + " " + suggestion);
        }

        List<Map.Entry<String, Long>> sortedList = new ArrayList<>(result.getConfiguration().getGuessTypes().entrySet());
        Collections.sort(sortedList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> stringLongEntry, Map.Entry<String, Long> t1) {
                return stringLongEntry.getValue().compareTo(t1.getValue());
            }
        });

        for (Map.Entry<String, Long> guessType : sortedList)
        {
            System.out.println(resourceBundle.getString("main.timeToCrack") + " " + guessType.getKey() + ": " + TimeEstimate.getTimeToCrackFormatted(result, guessType.getKey()));
        }
        for (Match match : result.getMatches())
        {
            System.out.println("-----------------------------------");
            System.out.println(match.getDetails());
        }
        System.out.println("----------------------------------------------------------");
    }

    /**
     * Calculates the minimum entropy for a given password and returns that as a Result.
     * <br><br>
     * The password is truncated to the configured max length, and then covered by the lowest entropy
     * combination of matches.  The end result is a list of matches that when their tokens are added
     * up, should equal the truncated password.
     * <br><br>
     * The result object is guaranteed to match the original password, or throw an exception if it doesn't.
     *
     * @param configuration the configuration file used to estimate entropy.
     * @param password      the password you are guessing entropy for.
     * @return the {@code Result} of this estimate.
     */
    private Result guessEntropy(final Configuration configuration, final String password)
    {
        final String truncated_password = getTruncatedPassword(configuration, password);

        final long timeout = configuration.getEstimateTimeout();
        final long deadline = timeout > 0 ? System.currentTimeMillis() + timeout : 0;

        final List<Match> all_matches = getAllMatches(configuration, truncated_password, deadline);
        final boolean timed_out = deadline > 0 && System.currentTimeMillis() > deadline;

        return new Result(configuration, truncated_password, password, getBestCombination(configuration, truncated_password, all_matches), timed_out);
    }

    /**
     * Returns the lowest entropy combination of matches which covers the whole password.  Every match
     * covers an interval and contributes its entropy independently of the others, and the matches must
     * tile the password exactly, so this is a shortest path over the boundaries between characters.
     *
     * @param configuration the configuration
     * @param password      the password
     * @param all_matches   every match found for the password
     * @return the best list of matches, sorted by start index.
     */
    private List<Match> getBestCombination(final Configuration configuration, final String password, final List<Match> all_matches)
    {
        final int length = password.length();

        final List<List<Match>> matches_ending_at = new ArrayList<>(length + 1);
        for (int i = 0; i <= length; i++)
        {
            matches_ending_at.add(new ArrayList<Match>());
        }
        for (final Match match : all_matches)
        {
            matches_ending_at.get(match.getEndIndex() + 1).add(match);
        }

        // cost[i] is the lowest entropy which covers the first i characters of the password.
        final double[] cost = new double[length + 1];
        final Match[] chosen = new Match[length + 1];

        for (int end = 1; end <= length; end++)
        {
            final Match brute_force = createBruteForceMatch(configuration, password, end - 1);
            cost[end] = cost[end - 1] + brute_force.calculateEntropy();
            chosen[end] = brute_force;

            for (final Match match : matches_ending_at.get(end))
            {
                final double candidate = cost[match.getStartIndex()] + match.calculateEntropy();
                if (candidate < cost[end])
                {
                    cost[end] = candidate;
                    chosen[end] = match;
                }
            }
        }

        final List<Match> matches = new ArrayList<>();
        for (int i = length; i > 0; i = chosen[i].getStartIndex())
        {
            matches.add(chosen[i]);
        }
        Collections.reverse(matches);

        if (isRandom(password, matches))
        {
            final List<Match> brute_force_matches = new ArrayList<>();
            for (int i = 0; i < length; i++)
            {
                brute_force_matches.add(createBruteForceMatch(configuration, password, i));
            }
            return brute_force_matches;
        }
        return matches;
    }

    /**
     * Method to determine if the password should be considered random, and to just use brute force matches.
     * <p>
     * We determine a password to be random if the matches cover less than 50% of the password, or if they cover less than 80%
     * but the max length for a match is no more than 25% of the total length of the password.
     *
     * @param password the password
     * @param matches  the final list of matches
     * @return true if determined to be random
     */
    private boolean isRandom(final String password, final List<Match> matches)
    {
        int matched_length = 0;
        int max_matched_length = 0;
        for (Match match : matches)
        {
            if (!(match instanceof BruteForceMatch))
            {
                matched_length += match.getLength();
                if (match.getLength() > max_matched_length)
                {
                    max_matched_length = match.getLength();
                }
            }
        }

        if (matched_length < (password.length() * 0.5))
        {
            return true;
        }
        else if (matched_length < (password.length() * 0.8) && password.length() * 0.25 > max_matched_length)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Gets all matches for a given password, stopping once the deadline has passed.
     * <br><br>
     * A matcher which implements {@link DeadlinePasswordMatcher} is handed the deadline and can give up part
     * way through.  Any other matcher is only checked between matchers, so once it has started it is always
     * allowed to finish.
     *
     * @param configuration the configuration file used to estimate entropy.
     * @param password      the password to get matches for.
     * @param deadline      the {@code System.currentTimeMillis()} value to stop at, or 0 for no deadline.
     * @return a {@code List} of {@code Match} objects for the supplied password.
     */
    private List<Match> getAllMatches(final Configuration configuration, final String password, final long deadline)
    {
        List<Match> matches = new ArrayList<>();

        for (PasswordMatcher passwordMatcher : configuration.getPasswordMatchers())
        {
            if (deadline > 0 && System.currentTimeMillis() > deadline)
            {
                break;
            }
            if (deadline > 0 && passwordMatcher instanceof DeadlinePasswordMatcher)
            {
                matches.addAll(((DeadlinePasswordMatcher) passwordMatcher).match(configuration, password, deadline));
            }
            else
            {
                matches.addAll(passwordMatcher.match(configuration, password));
            }
        }
        return matches;
    }

    /**
     * Gets the current configuration.
     *
     * @return returns {@code Configuration}
     */
    public Configuration getConfiguration()
    {
        return configuration;
    }

    /**
     * Sets the configuration.
     *
     * @param configuration a {@code Configuration} to be used in all estimates.
     */
    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

    /**
     * Guess the entropy of a password with the configuration provided.
     *
     * @param password The password you would like to attempt to estimate on.
     * @return Result object that contains info about the password.
     */
    public Result estimate(final String password)
    {
        return guessEntropy(this.configuration, password);
    }

}
