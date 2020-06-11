package me.gosimple.nbvcxz.resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents password permutations, using a list of candidate substrings. For instance, the first permutation
 * of the full password would be the 0th string from each part of the chain. This class is used to exhaust
 * password munging combinations.
 */
public class PasswordChain {
    // substring candidates
    private List<String[]> parts;
    // a bijection with the list above, where each index specifies if the candidate parts at an index have
    // already been completely unmunged
    private List<Boolean> converted;

    /**
     * Creates a password chain using an initial String value.
     */
    public PasswordChain(String originalPassword) {
        this.parts = new ArrayList<>();
        this.converted = new ArrayList<>();

        add(0, new String[] {originalPassword}, false);
    }

    /**
     * Gets the password part at an index, if it contains the munge key
     * and has not been substituted yet.
     * @param index Index of the part to return
     * @param key The munge key
     * @return Single password part, containing the munge key, or null if the requirements were not met
     */
    public String getPartIfContainsKey(int index, String key) {
        String[] subParts = parts.get(index);
        String firstPart = subParts[0];
        if (!converted.get(index) && firstPart.contains(key)) {
            return firstPart;
        }
        else {
            return null;
        }
    }

    /**
     * Replaces a part of the chain with possible substitutes.
     * @param index Index of the part of the chain to replace
     * @param subs Possible password munging substitutes
     * @param converted Should be true if subs is the result of unmunging a part of the original password, false otherwise
     */
    public void replace(int index, String[] subs, boolean converted) {
        parts.set(index, subs);
        this.converted.set(index, converted);
    }

    /**
     * Adds a list of possible password unmunge substitutes to a specific index in the chain.
     * @param index Index to insert the candidates at
     * @param subs Possible password munging substitutes
     * @param converted Should be true if subs is the result of unmunging a part of the original password, false otherwise
     */
    public void add(int index, String[] subs, boolean converted) {
        parts.add(index, subs);
        this.converted.add(index, converted);
    }

    /**
     * @return 2D string array representation of this password chain
     */
    public String[][] getParts() {
        // convert List of String[] to String[][]
        String[][] replacements = new String[parts.size()][];
        for (int i = 0; i < parts.size(); i++) {
            replacements[i] = parts.get(i);
        }

        return replacements;
    }

    /**
     * @return Size of the chain
     */
    public int size() {
        return parts.size();
    }
}