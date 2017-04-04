package me.gosimple.nbvcxz.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Adam Brusselback.
 */
public class Feedback
{
    private final String result;
    private final String warning;
    private final List<String> suggestions;
    private final Configuration configuration;

    /**
     * @param configuration the {@link Configuration} object.
     */
    public Feedback(final Configuration configuration, final String result)
    {
        this.configuration = configuration;
        this.result = result;
        this.warning = null;
        this.suggestions = new ArrayList<>();
    }

    /**
     * @param configuration the {@link Configuration} object.
     * @param warning       warning string
     * @param suggestions   suggestions
     */
    public Feedback(final Configuration configuration, final String result, final String warning, final String... suggestions)
    {
        this.configuration = configuration;
        this.result = result;
        this.warning = warning;
        this.suggestions = new ArrayList<>();
        Collections.addAll(this.suggestions, suggestions);
    }

    /**
     * @return if the password was secure enough or not (not null)
     */
    public String getResult()
    {
        return result;
    }

    /**
     * Get the raw untranslated warning key.
     *
     * @return the warning key (nullable)
     */
    public String getWarningKey()
    {
        return warning;
    }

    /**
     * @return the warning (nullable)
     */
    public String getWarning()
    {
        try
        {
            return configuration.getFeedbackResource().getString(warning);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * Get the raw untranslated suggestion keys.
     *
     * @return list of suggestion keys (list is not null)
     */
    public List<String> getSuggestionKeys()
    {
        return suggestions;
    }

    /**
     * @return list of suggestions (list is not null)
     */
    public List<String> getSuggestion()
    {
        List<String> convertedSuggestions = new ArrayList<>();
        for (String suggestion : suggestions)
        {
            convertedSuggestions.add(configuration.getFeedbackResource().getString(suggestion));
        }
        return convertedSuggestions;
    }
}
