package me.gosimple.nbvcxz.resources;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Dictionary builder class to help properly build dictionaries.
 */
public class DictionaryBuilder
{
    private String dictionary_name;
    private Map<String, Integer> dictonary = new HashMap<>();
    private boolean exclusion;

    /**
     * Set the dictionary name
     *
     * @param dictionary_name unique name of dictionary.
     * @return the builder
     */
    public DictionaryBuilder setDictionaryName(final String dictionary_name)
    {
        this.dictionary_name = dictionary_name;
        return this;
    }

    /**
     * Set if exclusion dictionary or not.
     *
     * @param exclusion {@code true} when desiring to disallow any password contained in this dictionary; {@code false} otherwise.
     * @return the builder
     */
    public DictionaryBuilder setExclusion(final boolean exclusion)
    {
        this.exclusion = exclusion;
        return this;
    }

    /**
     * Add word to dictionary.
     *
     * @param word key to add to the dictionary, will be lowercased.
     * @param rank the rank of the word in the dictionary.
     *             Should increment from most common to least common if ranked.
     *             If unranked, an example would be if there were 500 values in the dictionary, every word should have a rank of 250.
     *             If exclusion dictionary, rank is unimportant (set to 0).
     * @return the builder
     */
    public DictionaryBuilder addWord(final String word, final int rank)
    {
        this.dictonary.put(word.toLowerCase(), rank);
        return this;
    }

    /**
     * Add a Collection of words to dictionary. All words will be added to dictionary using the same rank.
     * @param words the collection of keys to be added to dictionary
     * @param rank the rank of the word in the dictionary.
     *             Should increment from most common to least common if ranked.
     *             If unranked, an example would be if there were 500 values in the dictionary, every word should have a rank of 250.
     *             If exclusion dictionary, rank is unimportant (set to 0).
     * @return the builder
     */
    public DictionaryBuilder addWords(final Collection<String> words, final int rank)
    {
        for(final String word: words)
        {
            addWord(word, rank);
        }
        return this;
    }

    /**
     * Creates the dictionary.
     *
     * @return the dictionary
     */
    public Dictionary createDictionary()
    {
        return new Dictionary(dictionary_name, dictonary, exclusion);
    }
}