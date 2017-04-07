package me.gosimple.nbvcxz.resources;

public class CharacterCaseUtil
{
    /**
     * Of the characters in the string that have an uppercase form, how many are uppercased?
     *
     * @param input Input string.
     * @return The fraction of uppercased characters, with {@code 0.0d} meaning that all uppercasable characters are in
     * lowercase and {@code 1.0d} that all of them are in uppercase.
     */
    public static double fractionOfStringUppercase(String input)
    {
        if (input == null)
        {
            return 0;
        }

        double upperCasableCharacters = 0;
        double upperCount = 0;
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            char uc = Character.toUpperCase(c);
            char lc = Character.toLowerCase(c);
            // If both the upper and lowercase version of a character are the same, then the character has
            // no distinct uppercase form (e.g., a digit or punctuation). Ignore these.
            if (c == uc && c == lc)
            {
                continue;
            }

            upperCasableCharacters++;
            if (c == uc)
            {
                upperCount++;
            }
        }

        return upperCasableCharacters == 0 ? 0 : upperCount / upperCasableCharacters;
    }
}
