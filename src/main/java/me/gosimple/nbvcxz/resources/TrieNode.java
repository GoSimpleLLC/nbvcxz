package me.gosimple.nbvcxz.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a node in a trie of possible string substitutions.
 */
public class TrieNode
{
    private final Map<Character, TrieNode> children;
    private String[] subs;

    public TrieNode()
    {
        children = new HashMap<>();
    }

    /**
     * Adds a list of possible substitutions for a given string to the node.
     * @param key The string that can be substituted
     * @param subs An array of possible substitutions
     * @return The same node, for method chaining
     */
    public TrieNode addSub(String key, String...subs)
    {
        final char firstChar = key.charAt(0);
        if (!children.containsKey(firstChar))
        {
            children.put(firstChar, new TrieNode());
        }
        TrieNode cur = children.get(firstChar);
        for (int i = 1; i < key.length(); i++)
        {
            final char c = key.charAt(i);
            if (!cur.hasChild(c))
            {
                cur.addChild(c);
            }
            cur = cur.getChild(c);
        }
        cur.setSubs(subs);

        return this;
    }

    public TrieNode getChild(Character child) {
        return children.get(child);
    }

    public boolean isTerminal()
    {
        return subs != null;
    }

    public String[] getSubs() {
        return subs;
    }

    private void setSubs(String[] sub)
    {
        this.subs = sub;
    }

    private void addChild(Character child)
    {
        if (!hasChild(child)) {
            children.put(child, new TrieNode());
        }
    }

    private boolean hasChild(Character child)
    {
        return children.containsKey(child);
    }
}
