package me.gosimple.nbvcxz.resources;

import java.util.*;

/**
 * @author Adam Brusselback.
 */
public class Feedback
{
    private final String warning;
    private final List<String> suggestions;
    private final Configuration configuration;

    /**
     *
     * @param configuration the {@link Configuration} object.
     */
    public Feedback(final Configuration configuration)
    {
        this.configuration = configuration;
        warning = null;
        suggestions = new ArrayList<>();
    }

    /**
     *
     * @param configuration the {@link Configuration} object.
     * @param warning warning string
     * @param suggestions suggestions
     */
    public Feedback(final Configuration configuration, final String warning, final String... suggestions)
    {
        this.configuration = configuration;
        this.warning = warning;
        this.suggestions = new ArrayList<>();
        Collections.addAll(this.suggestions, suggestions);
    }

    /**
     *
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
     *
     * @return list of suggestions (list is not null)
     */
    public List<String> getSuggestion()
    {
        List<String> convertedSuggestions = new ArrayList<>();
        for(String suggestion : suggestions)
        {
            convertedSuggestions.add(configuration.getFeedbackResource().getString(suggestion));
        }
        return convertedSuggestions;
    }
}
