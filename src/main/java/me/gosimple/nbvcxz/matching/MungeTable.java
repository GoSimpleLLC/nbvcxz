package me.gosimple.nbvcxz.matching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A table mapping "munged" versions of a password substring to all the possible
 * "unmunged" versions. For example, a password may contain a '1' that is used in place of an 'I'.
 * This class was created to solve issue #45. It allows arbitrary substrings to be replaced with arbitrary strings.
 * For example, "uu" can be used in place of 'w'. This class can also be used to do typical leetspeak substitutions,
 * such as 4 -> A.
 *
 * See here for more on password munging: https://en.wikipedia.org/wiki/Munged_password
 */
public class MungeTable {
    // regex used to split a password by a certain munge string (taking the place of %s)
    private static final String splitRegex = "((?<=\\Q%s\\E)|(?=\\Q%s\\E))";
    // mapping of munged strings -> possible unmunged replacements
    private Map<String, String[]> table;
    // keys from above, that should be sorted in order of descending key length
    private List<String> keys;
    // map of munged strings -> replacement regex
    private Map<String, Pattern> regexPatterns;

    public MungeTable() {
        table = new HashMap<>();
        keys = new ArrayList<>();
        regexPatterns = new HashMap<>();
    }

    /**
     * Adds a munged -> unmunged mapping to the table.
     * @param key Munged string
     * @param subs Possible unmunged replacements
     * @return This object, for use with method chaining
     */
    public MungeTable addSub(String key, String...subs) {
        table.put(key, subs);
        keys.add(key);
        regexPatterns.put(key, Pattern.compile(String.format(splitRegex, key, key)));
        return this;
    }

    /**
     * Sorts the munge keys in descending order, so that larger password munges
     * are replaced first. Shuold be called after all subs are added using {@link MungeTable#addSub(String, String...)}
     */
    public void sort() {
        // sort keys by their length, descending
        keys.sort(
                (String k1, String k2) -> -Integer.compare(k1.length(), k2.length())
        );
    }

    /**
     * @return List of all munge keys. Should be sorted using {@link MungeTable#sort()}
     */
    public List<String> getKeys() {
        return keys;
    }

    /**
     * If the given key is in the table of password munges, returns the list
     * of possible unmunges. Otherwise, just returns the given key.
     */
    public String[] getSubsOrOriginal(String key) {
        return table.getOrDefault(key, new String[] {key});
    }

    /**
     * Returns the compiled regex pattern for a particular munged substring. This saves time,
     * since the pattern doesn't have to be recompiled every time it is used.
     */
    public Pattern getKeyPattern(String key) {
        return regexPatterns.get(key);
    }
}
