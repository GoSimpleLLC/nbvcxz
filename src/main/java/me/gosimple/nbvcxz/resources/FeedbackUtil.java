package me.gosimple.nbvcxz.resources;

import me.gosimple.nbvcxz.matching.match.DateMatch;
import me.gosimple.nbvcxz.matching.match.DictionaryMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.matching.match.RepeatMatch;
import me.gosimple.nbvcxz.matching.match.SequenceMatch;
import me.gosimple.nbvcxz.matching.match.SpacialMatch;
import me.gosimple.nbvcxz.matching.match.YearMatch;
import me.gosimple.nbvcxz.scoring.Result;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Adam Brusselback.
 */
public class FeedbackUtil
{
    /**
     * @param result result object
     * @return feedback object
     */
    public static Feedback getFeedback(final Result result)
    {
        if (result.isMinimumEntropyMet())
        {
            return new Feedback(result.getConfiguration(), "main.feedback.minimumMet");
        }

        if (result.getPassword().length() == 0)
        {
            return getDefaultFeedback(result.getConfiguration());
        }

        Match longestMatch = null;
        for (Match match : result.getMatches())
        {
            if (longestMatch == null)
            {
                longestMatch = match;
            }

            if (match.getLength() > longestMatch.getLength())
            {
                longestMatch = match;
            }
        }


        return getMatchFeedback(result, longestMatch);
    }

    private static Feedback getMatchFeedback(final Result result, final Match match)
    {
        Configuration configuration = result.getConfiguration();

        String warning;
        final List<String> suggestions = new ArrayList<>();


        if (match instanceof DateMatch)
        {
            return new Feedback(configuration, "main.feedback.insecure", "feedback.date.warning.dates", "feedback.date.suggestions.avoidDates", "feedback.extra.suggestions.addAnotherWord");
        }
        if (match instanceof YearMatch)
        {
            return new Feedback(configuration, "main.feedback.insecure", "feedback.year.warning.recentYears", "feedback.year.suggestions.avoidYears", "feedback.extra.suggestions.addAnotherWord");
        }
        if (match instanceof RepeatMatch && RepeatMatch.class.cast(match).getRepeatingCharacters().length() == 1)
        {
            return new Feedback(configuration, "main.feedback.insecure", "feedback.repeat.warning.likeAAA", "feedback.repeat.suggestions.avoidRepeated", "feedback.extra.suggestions.addAnotherWord");
        }
        if (match instanceof RepeatMatch && RepeatMatch.class.cast(match).getRepeatingCharacters().length() != 1)
        {
            return new Feedback(configuration, "main.feedback.insecure", "feedback.repeat.warning.likeABCABCABC", "feedback.repeat.suggestions.avoidRepeated", "feedback.extra.suggestions.addAnotherWord");
        }
        if (match instanceof SequenceMatch)
        {
            return new Feedback(configuration, "main.feedback.insecure", "feedback.sequence.warning.sequenceWarning", "feedback.sequence.suggestions.avoidSequences", "feedback.extra.suggestions.addAnotherWord");
        }
        if (match instanceof SpacialMatch)
        {
            if (SpacialMatch.class.cast(match).getTurns() > 0)
            {
                return new Feedback(configuration, "main.feedback.insecure", "feedback.spatial.warning.shortKeyboardPatterns", "feedback.spatial.suggestions.UseLongerKeyboardPattern", "feedback.extra.suggestions.addAnotherWord");
            }
            else
            {
                return new Feedback(configuration, "main.feedback.insecure", "feedback.spatial.warning.straightRowsOfKeys", "feedback.spatial.suggestions.UseLongerKeyboardPattern", "feedback.extra.suggestions.addAnotherWord");
            }
        }
        if (match instanceof DictionaryMatch)
        {
            DictionaryMatch dictionaryMatch = DictionaryMatch.class.cast(match);
            String dictionaryName = dictionaryMatch.getDictionaryName();
            for (Dictionary dictionary : result.getConfiguration().getDictionaries())
            {
                if (dictionary.getDictionaryName().equals(dictionaryName))
                {
                    if (dictionary.isExclusion())
                    {
                        return new Feedback(configuration, "main.feedback.insecure", "feedback.dictionary.warning.passwords.notAllowed", "feedback.dictionary.suggestions.passwords.notAllowed");
                    }
                }
            }
            if (dictionaryMatch.getRank() <= 10)
            {
                warning = "feedback.dictionary.warning.passwords.top10";
            }
            else if (dictionaryMatch.getRank() <= 100)
            {
                warning = "feedback.dictionary.warning.passwords.top100";
            }
            else
            {
                warning = "feedback.dictionary.warning.passwords.veryCommon";
            }

            // A generic suggestion in lieu of other more specific suggestions.
            suggestions.add("feedback.extra.suggestions.addAnotherWord");

            if (dictionaryMatch.isReversed())
            {
                suggestions.add("feedback.dictionary.suggestions.reversed");
            }

            if (dictionaryMatch.isLeet())
            {
                suggestions.add("feedback.dictionary.suggestions.leet");
            }

            double capitalizationFraction = CharacterCaseUtil.fractionOfStringUppercase(result.getPassword());
            if (capitalizationFraction > 0.8d)
            {
                // Nearly all characters were capitalized.
                suggestions.add("feedback.dictionary.suggestions.allUppercase");
            }
            else if (capitalizationFraction > 0.0d && capitalizationFraction <= 0.2d)
            {
                // Only a few characters were capitalized.
                suggestions.add("feedback.dictionary.suggestions.capitalization");
            }

            return new Feedback(configuration, "main.feedback.insecure", warning, suggestions.toArray(new String[suggestions.size()]));

        }
        return getDefaultFeedback(configuration);
    }

    private static Feedback getDefaultFeedback(final Configuration configuration)
    {
        return new Feedback(configuration, "main.feedback.insecure", "feedback.default.suggestions.useFewWords", "feedback.default.suggestions.noNeedSymbols");
    }

}
