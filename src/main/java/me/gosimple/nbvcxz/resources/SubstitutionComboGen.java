package me.gosimple.nbvcxz.resources;

import java.util.ArrayList;
import java.util.List;

public class SubstitutionComboGen {
    private final TrieNode trieRoot;

    public SubstitutionComboGen(TrieNode trieRoot) {
        this.trieRoot = trieRoot;
    }

    /**
     * Generates all possible combinations of a string against the root {@link TrieNode}.
     * @param str The string to generate combinations from
     * @param limit Limit number of combinations to generate
     * @return List of combinations
     */
    public List<String> getAllSubCombos(final String str, final int limit) {
        final List<String> combos = new ArrayList<>();
        getAllSubCombos(str, 0, new StringBuilder(), combos, limit);
        return combos;
    }

    private void getAllSubCombos(final String substr, int index, StringBuilder buffer, final List<String> finalPasswords, final int limit)
    {
        if (finalPasswords.size() >= limit) return;

        if (index == substr.length())
        {
            // reached the end; add the contents of the buffer to the list of combinations
            finalPasswords.add(buffer.toString());
            return;
        }

        final char firstChar = substr.charAt(index);

        // first, generate all combos without doing a substitution at this index
        buffer.append(firstChar);
        getAllSubCombos(substr, index + 1, buffer, finalPasswords, limit);
        buffer.setLength(buffer.length() - 1);

        // next, exhaust all possible substitutions at this index
        TrieNode cur = trieRoot;
        for (int i = index; i < substr.length(); i++)
        {
            final char c = substr.charAt(i);
            cur = cur.getChild(c);
            if (cur == null)
            {
                return;
            }

            if (cur.isTerminal())
            {
                String[] subs = cur.getSubs();
                for (String sub : subs)
                {
                    buffer.append(sub);
                    // recursively build the rest of the string
                    getAllSubCombos(substr, i + 1, buffer, finalPasswords, limit);
                    // backtrack by ignoring the added postfix
                    buffer.setLength(buffer.length() - sub.length());

                    if (finalPasswords.size() >= limit) return;
                }
            }
        }
    }
}
