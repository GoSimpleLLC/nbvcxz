package me.gosimple.nbvcxz.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author Adam Brusselback
 */
public class DictionaryUtil
{
    /**
     * Ranked dictionary of common passwords
     */
    public static final String passwords = "passwords.txt";

    /**
     * Ranked dictionary of common male names
     */
    public static final String male_names = "male-names.txt";

    /**
     * Ranked dictionary of common female names
     */
    public static final String female_names = "female-names.txt";

    /**
     * Ranked dictionary of common surnames
     */
    public static final String surnames = "surnames.txt";

    /**
     * Ranked dictionary of common English word
     */
    public static final String english = "english.txt";

    /**
     * Read a resource file with a list of entries (sorted by frequency) and use
     * it to create a ranked dictionary.
     *
     * @param fileName the name of the file
     * @return the ranked dictionary (a {@code HashMap} which associated a
     * rank to each entry
     */
    public static HashMap<String, Integer> loadDictionary(final String fileName)
    {
        HashMap<String, Integer> ranked = new HashMap<>();

        try
        {
            String path = "/dictionaries/" + fileName;
            InputStream is = DictionaryUtil.class.getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line;
            int i = 1;
            while ((line = br.readLine()) != null)
            {
                ranked.put(line, i++);
            }
        }
        catch (IOException e)
        {
            System.out.println("Error while reading " + fileName);
        }

        return ranked;
    }
}
