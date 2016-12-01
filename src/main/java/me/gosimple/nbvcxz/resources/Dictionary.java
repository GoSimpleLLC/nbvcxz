package me.gosimple.nbvcxz.resources;

import java.util.HashMap;

/**
 * Object used for dictionary matching.  This allows users to implement custom dictionaries for different languages
 * or specialized vocabulary.
 *
 * @author Adam Brusselback.
 */
public class Dictionary
{
    private final String dictionary_name;
    private final HashMap<String, Integer> dictonary;
    private final boolean exclusion;
    private final boolean distance;


    /**
     * Object used for dictionary matching.
     * @param dictionary_name unique name of dictionary.
     * @param dictonary {@code Map} with the word and it's rank.
     * @param exclusion {@code true} when desiring to disallow any password contained in this dictionary; {@code false} otherwise.
     * @param distance {@code true} when desiring to enable Levenshtein distance matching for this dictionary.
     */
    public Dictionary(final String dictionary_name, final HashMap<String, Integer> dictonary, final boolean exclusion, final boolean distance)
    {
        this.dictionary_name = dictionary_name;
        this.dictonary = dictonary;
        this.exclusion = exclusion;
        this.distance = distance;
    }

    /**
     * The values within this dictionary.
     * @return The values in the dictionary
     */
    public HashMap<String, Integer> getDictonary()
    {
        return dictonary;
    }

    /**
     * Returns if this dictionary is used for password exclusion or not. <br> <br>
     * If true, a password which matches to one of the values in the dictionary will always return 0 entropy for the portion which matches.
     * @return true if excluded
     */
    public boolean isExclusion()
    {
        return exclusion;
    }

    /**
     * A description of the values contained in the dictionary.
     * @return The dictionary name
     */
    public String getDictionaryName()
    {
        return this.dictionary_name;
    }

    /**
     *
     * @return true if distance calculation is enabled
     */
    public boolean isDistance()
    {
        return distance;
    }
}
