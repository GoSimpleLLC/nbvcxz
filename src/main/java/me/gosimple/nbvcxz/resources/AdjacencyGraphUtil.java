package me.gosimple.nbvcxz.resources;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Contains hard coded adjacency graphs for different keyboard types.
 *
 * @author Adam Brusselback
 */
public class AdjacencyGraphUtil
{

    /**
     * Adjacency graph for the qwerty keyboard
     */
    public static final HashMap<Character, String[]> qwerty;
    /**
     * Adjacency graph for the dvorak keyboard
     */
    public static final HashMap<Character, String[]> dvorak;
    /**
     * Adjacency graph for the keypad
     */
    public static final HashMap<Character, String[]> standardKeypad;
    /**
     * Adjacency graph for the macKeypad
     */
    public static final HashMap<Character, String[]> macKeypad;

    static
    {
        qwerty = new HashMap<>();
        qwerty.put('0', new String[]{"9(", null, null, "-_", "pP", "oO"});
        qwerty.put('1', new String[]{"`~", null, null, "2@", "qQ", null});
        qwerty.put('2', new String[]{"1!", null, null, "3#", "wW", "qQ"});
        qwerty.put('3', new String[]{"2@", null, null, "4$", "eE", "wW"});
        qwerty.put('4', new String[]{"3#", null, null, "5%", "rR", "eE"});
        qwerty.put('5', new String[]{"4$", null, null, "6^", "tT", "rR"});
        qwerty.put('6', new String[]{"5%", null, null, "7&", "yY", "tT"});
        qwerty.put('7', new String[]{"6^", null, null, "8*", "uU", "yY"});
        qwerty.put('8', new String[]{"7&", null, null, "9(", "iI", "uU"});
        qwerty.put('9', new String[]{"8*", null, null, "0)", "oO", "iI"});
        qwerty.put('!', new String[]{"`~", null, null, "2@", "qQ", null});
        qwerty.put('"', new String[]{";:", "[{", "]}", null, null, "/?"});
        qwerty.put('#', new String[]{"2@", null, null, "4$", "eE", "wW"});
        qwerty.put('$', new String[]{"3#", null, null, "5%", "rR", "eE"});
        qwerty.put('%', new String[]{"4$", null, null, "6^", "tT", "rR"});
        qwerty.put('&', new String[]{"6^", null, null, "8*", "uU", "yY"});
        qwerty.put('\'', new String[]{";:", "[{", "]}", null, null, "/?"});
        qwerty.put('(', new String[]{"8*", null, null, "0)", "oO", "iI"});
        qwerty.put(')', new String[]{"9(", null, null, "-_", "pP", "oO"});
        qwerty.put('*', new String[]{"7&", null, null, "9(", "iI", "uU"});
        qwerty.put('+', new String[]{"-_", null, null, null, "]}", "[{"});
        qwerty.put(',', new String[]{"mM", "kK", "lL", ".>", null, null});
        qwerty.put('-', new String[]{"0)", null, null, "=+", "[{", "pP"});
        qwerty.put('.', new String[]{",<", "lL", ";:", "/?", null, null});
        qwerty.put('/', new String[]{".>", ";:", "'\"", null, null, null});
        qwerty.put(':', new String[]{"lL", "pP", "[{", "'\"", "/?", ".>"});
        qwerty.put(';', new String[]{"lL", "pP", "[{", "'\"", "/?", ".>"});
        qwerty.put('<', new String[]{"mM", "kK", "lL", ".>", null, null});
        qwerty.put('=', new String[]{"-_", null, null, null, "]}", "[{"});
        qwerty.put('>', new String[]{",<", "lL", ";:", "/?", null, null});
        qwerty.put('?', new String[]{".>", ";:", "'\"", null, null, null});
        qwerty.put('@', new String[]{"1!", null, null, "3#", "wW", "qQ"});
        qwerty.put('A', new String[]{null, "qQ", "wW", "sS", "zZ", null});
        qwerty.put('B', new String[]{"vV", "gG", "hH", "nN", null, null});
        qwerty.put('C', new String[]{"xX", "dD", "fF", "vV", null, null});
        qwerty.put('D', new String[]{"sS", "eE", "rR", "fF", "cC", "xX"});
        qwerty.put('E', new String[]{"wW", "3#", "4$", "rR", "dD", "sS"});
        qwerty.put('F', new String[]{"dD", "rR", "tT", "gG", "vV", "cC"});
        qwerty.put('G', new String[]{"fF", "tT", "yY", "hH", "bB", "vV"});
        qwerty.put('H', new String[]{"gG", "yY", "uU", "jJ", "nN", "bB"});
        qwerty.put('I', new String[]{"uU", "8*", "9(", "oO", "kK", "jJ"});
        qwerty.put('J', new String[]{"hH", "uU", "iI", "kK", "mM", "nN"});
        qwerty.put('K', new String[]{"jJ", "iI", "oO", "lL", ",<", "mM"});
        qwerty.put('L', new String[]{"kK", "oO", "pP", ";:", ".>", ",<"});
        qwerty.put('M', new String[]{"nN", "jJ", "kK", ",<", null, null});
        qwerty.put('N', new String[]{"bB", "hH", "jJ", "mM", null, null});
        qwerty.put('O', new String[]{"iI", "9(", "0)", "pP", "lL", "kK"});
        qwerty.put('P', new String[]{"oO", "0)", "-_", "[{", ";:", "lL"});
        qwerty.put('Q', new String[]{null, "1!", "2@", "wW", "aA", null});
        qwerty.put('R', new String[]{"eE", "4$", "5%", "tT", "fF", "dD"});
        qwerty.put('S', new String[]{"aA", "wW", "eE", "dD", "xX", "zZ"});
        qwerty.put('T', new String[]{"rR", "5%", "6^", "yY", "gG", "fF"});
        qwerty.put('U', new String[]{"yY", "7&", "8*", "iI", "jJ", "hH"});
        qwerty.put('V', new String[]{"cC", "fF", "gG", "bB", null, null});
        qwerty.put('W', new String[]{"qQ", "2@", "3#", "eE", "sS", "aA"});
        qwerty.put('X', new String[]{"zZ", "sS", "dD", "cC", null, null});
        qwerty.put('Y', new String[]{"tT", "6^", "7&", "uU", "hH", "gG"});
        qwerty.put('Z', new String[]{null, "aA", "sS", "xX", null, null});
        qwerty.put('[', new String[]{"pP", "-_", "=+", "]}", "'\"", ";:"});
        qwerty.put('\\', new String[]{"]}", null, null, null, null, null});
        qwerty.put(']', new String[]{"[{", "=+", null, "\\|", null, "'\""});
        qwerty.put('^', new String[]{"5%", null, null, "7&", "yY", "tT"});
        qwerty.put('_', new String[]{"0)", null, null, "=+", "[{", "pP"});
        qwerty.put('`', new String[]{null, null, null, "1!", null, null});
        qwerty.put('a', new String[]{null, "qQ", "wW", "sS", "zZ", null});
        qwerty.put('b', new String[]{"vV", "gG", "hH", "nN", null, null});
        qwerty.put('c', new String[]{"xX", "dD", "fF", "vV", null, null});
        qwerty.put('d', new String[]{"sS", "eE", "rR", "fF", "cC", "xX"});
        qwerty.put('e', new String[]{"wW", "3#", "4$", "rR", "dD", "sS"});
        qwerty.put('f', new String[]{"dD", "rR", "tT", "gG", "vV", "cC"});
        qwerty.put('g', new String[]{"fF", "tT", "yY", "hH", "bB", "vV"});
        qwerty.put('h', new String[]{"gG", "yY", "uU", "jJ", "nN", "bB"});
        qwerty.put('i', new String[]{"uU", "8*", "9(", "oO", "kK", "jJ"});
        qwerty.put('j', new String[]{"hH", "uU", "iI", "kK", "mM", "nN"});
        qwerty.put('k', new String[]{"jJ", "iI", "oO", "lL", ",<", "mM"});
        qwerty.put('l', new String[]{"kK", "oO", "pP", ";:", ".>", ",<"});
        qwerty.put('m', new String[]{"nN", "jJ", "kK", ",<", null, null});
        qwerty.put('n', new String[]{"bB", "hH", "jJ", "mM", null, null});
        qwerty.put('o', new String[]{"iI", "9(", "0)", "pP", "lL", "kK"});
        qwerty.put('p', new String[]{"oO", "0)", "-_", "[{", ";:", "lL"});
        qwerty.put('q', new String[]{null, "1!", "2@", "wW", "aA", null});
        qwerty.put('r', new String[]{"eE", "4$", "5%", "tT", "fF", "dD"});
        qwerty.put('s', new String[]{"aA", "wW", "eE", "dD", "xX", "zZ"});
        qwerty.put('t', new String[]{"rR", "5%", "6^", "yY", "gG", "fF"});
        qwerty.put('u', new String[]{"yY", "7&", "8*", "iI", "jJ", "hH"});
        qwerty.put('v', new String[]{"cC", "fF", "gG", "bB", null, null});
        qwerty.put('w', new String[]{"qQ", "2@", "3#", "eE", "sS", "aA"});
        qwerty.put('x', new String[]{"zZ", "sS", "dD", "cC", null, null});
        qwerty.put('y', new String[]{"tT", "6^", "7&", "uU", "hH", "gG"});
        qwerty.put('z', new String[]{null, "aA", "sS", "xX", null, null});
        qwerty.put('{', new String[]{"pP", "-_", "=+", "]}", "'\"", ";:"});
        qwerty.put('|', new String[]{"]}", null, null, null, null, null});
        qwerty.put('}', new String[]{"[{", "=+", null, "\\|", null, "'\""});
        qwerty.put('~', new String[]{null, null, null, "1!", null, null});
    }

    static
    {
        dvorak = new HashMap<>();
        dvorak.put('0', new String[]{"9(", null, null, "[{", "lL", "rR"});
        dvorak.put('1', new String[]{"`~", null, null, "2@", "'\"", null});
        dvorak.put('2', new String[]{"1!", null, null, "3#", ",<", "'\""});
        dvorak.put('3', new String[]{"2@", null, null, "4$", ".>", ",<"});
        dvorak.put('4', new String[]{"3#", null, null, "5%", "pP", ".>"});
        dvorak.put('5', new String[]{"4$", null, null, "6^", "yY", "pP"});
        dvorak.put('6', new String[]{"5%", null, null, "7&", "fF", "yY"});
        dvorak.put('7', new String[]{"6^", null, null, "8*", "gG", "fF"});
        dvorak.put('8', new String[]{"7&", null, null, "9(", "cC", "gG"});
        dvorak.put('9', new String[]{"8*", null, null, "0)", "rR", "cC"});
        dvorak.put('!', new String[]{"`~", null, null, "2@", "'\"", null});
        dvorak.put('"', new String[]{null, "1!", "2@", ",<", "aA", null});
        dvorak.put('#', new String[]{"2@", null, null, "4$", ".>", ",<"});
        dvorak.put('$', new String[]{"3#", null, null, "5%", "pP", ".>"});
        dvorak.put('%', new String[]{"4$", null, null, "6^", "yY", "pP"});
        dvorak.put('&', new String[]{"6^", null, null, "8*", "gG", "fF"});
        dvorak.put('\'', new String[]{null, "1!", "2@", ",<", "aA", null});
        dvorak.put('(', new String[]{"8*", null, null, "0)", "rR", "cC"});
        dvorak.put(')', new String[]{"9(", null, null, "[{", "lL", "rR"});
        dvorak.put('*', new String[]{"7&", null, null, "9(", "cC", "gG"});
        dvorak.put('+', new String[]{"/?", "]}", null, "\\|", null, "-_"});
        dvorak.put(',', new String[]{"'\"", "2@", "3#", ".>", "oO", "aA"});
        dvorak.put('-', new String[]{"sS", "/?", "=+", null, null, "zZ"});
        dvorak.put('.', new String[]{",<", "3#", "4$", "pP", "eE", "oO"});
        dvorak.put('/', new String[]{"lL", "[{", "]}", "=+", "-_", "sS"});
        dvorak.put(':', new String[]{null, "aA", "oO", "qQ", null, null});
        dvorak.put(';', new String[]{null, "aA", "oO", "qQ", null, null});
        dvorak.put('<', new String[]{"'\"", "2@", "3#", ".>", "oO", "aA"});
        dvorak.put('=', new String[]{"/?", "]}", null, "\\|", null, "-_"});
        dvorak.put('>', new String[]{",<", "3#", "4$", "pP", "eE", "oO"});
        dvorak.put('?', new String[]{"lL", "[{", "]}", "=+", "-_", "sS"});
        dvorak.put('@', new String[]{"1!", null, null, "3#", ",<", "'\""});
        dvorak.put('A', new String[]{null, "'\"", ",<", "oO", ";:", null});
        dvorak.put('B', new String[]{"xX", "dD", "hH", "mM", null, null});
        dvorak.put('C', new String[]{"gG", "8*", "9(", "rR", "tT", "hH"});
        dvorak.put('D', new String[]{"iI", "fF", "gG", "hH", "bB", "xX"});
        dvorak.put('E', new String[]{"oO", ".>", "pP", "uU", "jJ", "qQ"});
        dvorak.put('F', new String[]{"yY", "6^", "7&", "gG", "dD", "iI"});
        dvorak.put('G', new String[]{"fF", "7&", "8*", "cC", "hH", "dD"});
        dvorak.put('H', new String[]{"dD", "gG", "cC", "tT", "mM", "bB"});
        dvorak.put('I', new String[]{"uU", "yY", "fF", "dD", "xX", "kK"});
        dvorak.put('J', new String[]{"qQ", "eE", "uU", "kK", null, null});
        dvorak.put('K', new String[]{"jJ", "uU", "iI", "xX", null, null});
        dvorak.put('L', new String[]{"rR", "0)", "[{", "/?", "sS", "nN"});
        dvorak.put('M', new String[]{"bB", "hH", "tT", "wW", null, null});
        dvorak.put('N', new String[]{"tT", "rR", "lL", "sS", "vV", "wW"});
        dvorak.put('O', new String[]{"aA", ",<", ".>", "eE", "qQ", ";:"});
        dvorak.put('P', new String[]{".>", "4$", "5%", "yY", "uU", "eE"});
        dvorak.put('Q', new String[]{";:", "oO", "eE", "jJ", null, null});
        dvorak.put('R', new String[]{"cC", "9(", "0)", "lL", "nN", "tT"});
        dvorak.put('S', new String[]{"nN", "lL", "/?", "-_", "zZ", "vV"});
        dvorak.put('T', new String[]{"hH", "cC", "rR", "nN", "wW", "mM"});
        dvorak.put('U', new String[]{"eE", "pP", "yY", "iI", "kK", "jJ"});
        dvorak.put('V', new String[]{"wW", "nN", "sS", "zZ", null, null});
        dvorak.put('W', new String[]{"mM", "tT", "nN", "vV", null, null});
        dvorak.put('X', new String[]{"kK", "iI", "dD", "bB", null, null});
        dvorak.put('Y', new String[]{"pP", "5%", "6^", "fF", "iI", "uU"});
        dvorak.put('Z', new String[]{"vV", "sS", "-_", null, null, null});
        dvorak.put('[', new String[]{"0)", null, null, "]}", "/?", "lL"});
        dvorak.put('\\', new String[]{"=+", null, null, null, null, null});
        dvorak.put(']', new String[]{"[{", null, null, null, "=+", "/?"});
        dvorak.put('^', new String[]{"5%", null, null, "7&", "fF", "yY"});
        dvorak.put('_', new String[]{"sS", "/?", "=+", null, null, "zZ"});
        dvorak.put('`', new String[]{null, null, null, "1!", null, null});
        dvorak.put('a', new String[]{null, "'\"", ",<", "oO", ";:", null});
        dvorak.put('b', new String[]{"xX", "dD", "hH", "mM", null, null});
        dvorak.put('c', new String[]{"gG", "8*", "9(", "rR", "tT", "hH"});
        dvorak.put('d', new String[]{"iI", "fF", "gG", "hH", "bB", "xX"});
        dvorak.put('e', new String[]{"oO", ".>", "pP", "uU", "jJ", "qQ"});
        dvorak.put('f', new String[]{"yY", "6^", "7&", "gG", "dD", "iI"});
        dvorak.put('g', new String[]{"fF", "7&", "8*", "cC", "hH", "dD"});
        dvorak.put('h', new String[]{"dD", "gG", "cC", "tT", "mM", "bB"});
        dvorak.put('i', new String[]{"uU", "yY", "fF", "dD", "xX", "kK"});
        dvorak.put('j', new String[]{"qQ", "eE", "uU", "kK", null, null});
        dvorak.put('k', new String[]{"jJ", "uU", "iI", "xX", null, null});
        dvorak.put('l', new String[]{"rR", "0)", "[{", "/?", "sS", "nN"});
        dvorak.put('m', new String[]{"bB", "hH", "tT", "wW", null, null});
        dvorak.put('n', new String[]{"tT", "rR", "lL", "sS", "vV", "wW"});
        dvorak.put('o', new String[]{"aA", ",<", ".>", "eE", "qQ", ";:"});
        dvorak.put('p', new String[]{".>", "4$", "5%", "yY", "uU", "eE"});
        dvorak.put('q', new String[]{";:", "oO", "eE", "jJ", null, null});
        dvorak.put('r', new String[]{"cC", "9(", "0)", "lL", "nN", "tT"});
        dvorak.put('s', new String[]{"nN", "lL", "/?", "-_", "zZ", "vV"});
        dvorak.put('t', new String[]{"hH", "cC", "rR", "nN", "wW", "mM"});
        dvorak.put('u', new String[]{"eE", "pP", "yY", "iI", "kK", "jJ"});
        dvorak.put('v', new String[]{"wW", "nN", "sS", "zZ", null, null});
        dvorak.put('w', new String[]{"mM", "tT", "nN", "vV", null, null});
        dvorak.put('x', new String[]{"kK", "iI", "dD", "bB", null, null});
        dvorak.put('y', new String[]{"pP", "5%", "6^", "fF", "iI", "uU"});
        dvorak.put('z', new String[]{"vV", "sS", "-_", null, null, null});
        dvorak.put('{', new String[]{"0)", null, null, "]}", "/?", "lL"});
        dvorak.put('|', new String[]{"=+", null, null, null, null, null});
        dvorak.put('}', new String[]{"[{", null, null, null, "=+", "/?"});
        dvorak.put('~', new String[]{null, null, null, "1!", null, null});
    }

    static
    {
        standardKeypad = new HashMap<>();
        standardKeypad.put('0', new String[]{null, "1", "2", "3", ".", null, null, null});
        standardKeypad.put('1', new String[]{null, null, "4", "5", "2", "0", null, null});
        standardKeypad.put('2', new String[]{"1", "4", "5", "6", "3", ".", "0", null});
        standardKeypad.put('3', new String[]{"2", "5", "6", null, null, null, ".", "0"});
        standardKeypad.put('4', new String[]{null, null, "7", "8", "5", "2", "1", null});
        standardKeypad.put('5', new String[]{"4", "7", "8", "9", "6", "3", "2", "1"});
        standardKeypad.put('6', new String[]{"5", "8", "9", "+", null, null, "3", "2"});
        standardKeypad.put('7', new String[]{null, null, null, "/", "8", "5", "4", null});
        standardKeypad.put('8', new String[]{"7", null, "/", "*", "9", "6", "5", "4"});
        standardKeypad.put('9', new String[]{"8", "/", "*", "-", "+", null, "6", "5"});
        standardKeypad.put('*', new String[]{"/", null, null, null, "-", "+", "9", "8"});
        standardKeypad.put('+', new String[]{"9", "*", "-", null, null, null, null, "6"});
        standardKeypad.put('-', new String[]{"*", null, null, null, null, null, "+", "9"});
        standardKeypad.put('.', new String[]{"0", "2", "3", null, null, null, null, null});
        standardKeypad.put('/', new String[]{null, null, null, null, "*", "9", "8", "7"});
    }

    static
    {
        macKeypad = new HashMap<>();
        macKeypad.put('0', new String[]{null, "1", "2", "3", ".", null, null, null});
        macKeypad.put('1', new String[]{null, null, "4", "5", "2", "0", null, null});
        macKeypad.put('2', new String[]{"1", "4", "5", "6", "3", ".", "0", null});
        macKeypad.put('3', new String[]{"2", "5", "6", "+", null, null, ".", "0"});
        macKeypad.put('4', new String[]{null, null, "7", "8", "5", "2", "1", null});
        macKeypad.put('5', new String[]{"4", "7", "8", "9", "6", "3", "2", "1"});
        macKeypad.put('6', new String[]{"5", "8", "9", "-", "+", null, "3", "2"});
        macKeypad.put('7', new String[]{null, null, null, "=", "8", "5", "4", null});
        macKeypad.put('8', new String[]{"7", null, "=", "/", "9", "6", "5", "4"});
        macKeypad.put('9', new String[]{"8", "=", "/", "*", "-", "+", "6", "5"});
        macKeypad.put('*', new String[]{"/", null, null, null, null, null, "-", "9"});
        macKeypad.put('+', new String[]{"6", "9", "-", null, null, null, null, "3"});
        macKeypad.put('-', new String[]{"9", "/", "*", null, null, null, "+", "6"});
        macKeypad.put('.', new String[]{"0", "2", "3", null, null, null, null, null});
        macKeypad.put('/', new String[]{"=", null, null, null, "*", "-", "9", "8"});
        macKeypad.put('=', new String[]{null, null, null, null, "/", "9", "8", "7"});
    }


    /**
     * Calculates the average "degree" of a keyboard or keypad. On the qwerty
     * keyboard, 'g' has degree 6, being adjacent to 'ftyhbv' and '\' has degree
     * 1.
     *
     * @param keys a keyboard or keypad
     * @return the average degree for this keyboard or keypad
     */
    public static double calcAverageDegree(HashMap<Character, String[]> keys)
    {
        double average = 0d;
        for (Map.Entry<Character, String[]> entry : keys.entrySet())
        {
            average += neighborsNumber(entry.getValue());
        }
        return average / (double) keys.size();
    }


    /**
     * Count how many neighbors a key has
     *
     * @param neighbors the neighbors
     * @return the number of neighbors
     */
    public static int neighborsNumber(String[] neighbors)
    {
        int sum = 0;
        for (String s : neighbors)
        {
            if (s != null)
            {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Returns a set of neighbors for a specific character.
     *
     * @param adjacencyGraph the graph you are using to get the neighbors.
     * @param key            the character you are getting neighbors for.
     * @return A set of characters which are neighbors to the passed in character.
     */
    public static Set<Character> getNeighbors(final AdjacencyGraph adjacencyGraph, final Character key)
    {
        final Set<Character> neighbors = new HashSet<>();

        if (adjacencyGraph.getKeyMap().containsKey(key))
        {
            String[] tmp_neighbors = adjacencyGraph.getKeyMap().get(key);
            for (final String tmp_neighbor : tmp_neighbors)
            {
                if (null == tmp_neighbor)
                {
                    continue;
                }
                for (Character character : tmp_neighbor.toCharArray())
                {
                    neighbors.add(character);
                }
            }
        }
        return neighbors;
    }

    /**
     * Returns the number of turns in the part passed in based on the adjacency graph.
     *
     * @param adjacencyGraph the graph you are using to get the neighbors.
     * @param part           the string you are getting turns for.
     * @return the number of turns in this string for the {@code AdjacencyGraph}
     */
    public static int getTurns(final AdjacencyGraph adjacencyGraph, final String part)
    {
        int direction = 0;
        int turns = 1;
        char[] parts = part.toCharArray();

        for (int i1 = 0; i1 < parts.length; i1++)
        {
            Character character = parts[i1];
            if (i1 + 1 >= parts.length)
            {
                continue;
            }

            Character next_character = parts[i1 + 1];
            if (adjacencyGraph.getKeyMap().containsKey(character))
            {
                String[] tmp_neighbors = adjacencyGraph.getKeyMap().get(character);
                for (int i2 = 0; i2 < tmp_neighbors.length; i2++)
                {
                    if (tmp_neighbors[i2] == null)
                    {
                        continue;
                    }
                    for (Character neighbor_char : tmp_neighbors[i2].toCharArray())
                    {
                        if (next_character.equals(neighbor_char))
                        {
                            if (direction == 0)
                            {
                                direction = i2;
                            }
                            else if (direction != i2)
                            {
                                turns++;
                                direction = i2;
                            }
                        }
                    }
                }
            }
        }
        return turns;
    }

    /**
     * Returns the number of shifts in case in the part passed in.
     *
     * @param adjacencyGraph the graph you are using to get the neighbors
     * @param part           the string you are getting shifts for.
     * @return the number of shifts in this string for the {@code AdjacencyGraph}
     */
    public static int getShifts(final AdjacencyGraph adjacencyGraph, final String part)
    {
        int current_shift = -1;
        int shifts = 0;
        char[] parts = part.toCharArray();

        for (int i1 = 0; i1 < parts.length; i1++)
        {
            Character character = parts[i1];
            if (i1 + 1 >= parts.length)
            {
                continue;
            }

            Character next_character = parts[i1 + 1];

            if (adjacencyGraph.getKeyMap().containsKey(character))
            {
                String[] tmp_neighbors = adjacencyGraph.getKeyMap().get(character);
                for (final String tmp_neighbor : tmp_neighbors)
                {
                    if (tmp_neighbor == null)
                    {
                        continue;
                    }
                    int i = 0;
                    for (Character neighbor_char : tmp_neighbor.toCharArray())
                    {
                        if (next_character.equals(neighbor_char))
                        {
                            if (current_shift == -1)
                            {
                                current_shift = i;
                            }
                            else if (current_shift != i)
                            {
                                shifts++;
                                current_shift = i;
                            }
                        }
                        i++;
                    }
                }
            }
        }
        return shifts;
    }
}
