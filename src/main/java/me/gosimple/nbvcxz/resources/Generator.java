package me.gosimple.nbvcxz.resources;

import java.security.SecureRandom;

/**
 * Password generation class
 *
 * @author Adam Brusselback.
 */
public class Generator
{
    /**
     * Generates a passphrase from the eff_large standard dictionary with the requested word count.
     *
     * @param delimiter delimiter to place between words
     * @param words     the count of words you want in your passphrase
     * @return the passphrase
     */
    public static String generatePassphrase(final String delimiter, final int words)
    {
        return generatePassphrase(delimiter, words, new Dictionary("eff_large", DictionaryUtil.loadUnrankedDictionary(DictionaryUtil.eff_large), false));
    }

    /**
     * Generates a passphrase from the supplied dictionary with the requested word count.
     *
     * @param delimiter  delimiter to place between words
     * @param words      the count of words you want in your passphrase
     * @param dictionary the dictionary to use for generating this passphrase
     * @return the passphrase
     */
    public static String generatePassphrase(final String delimiter, final int words, final Dictionary dictionary)
    {
        String result = "";
        final SecureRandom rnd = new SecureRandom();
        final int high = dictionary.getSortedDictionary().size();
        for (int i = 1; i <= words; i++)
        {
            result += dictionary.getSortedDictionary().get(rnd.nextInt(high));
            if (i < words)
            {
                result += delimiter;
            }
        }
        return result;
    }

    /**
     * Generates a random password of the specified length with the specified characters.
     *
     * @param characterTypes the types of characters to include in the password
     * @param length         the length of the password
     * @return the password
     */
    public static String generateRandomPassword(final CharacterTypes characterTypes, final int length)
    {
        final StringBuffer buffer = new StringBuffer();
        String characters = "";

        switch (characterTypes)
        {

            case ALPHA:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;

            case ALPHANUMERIC:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;

            case ALPHANUMERICSYMBOL:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()";
                break;

            case NUMERIC:
                characters = "1234567890";
                break;
        }

        final int charactersLength = characters.length();
        final SecureRandom rnd = new SecureRandom();

        for (int i = 0; i < length; i++)
        {
            final double index = rnd.nextInt(charactersLength);
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }

    /**
     * Character types to use for password generation.
     */
    public enum CharacterTypes
    {
        ALPHA,
        ALPHANUMERIC,
        ALPHANUMERICSYMBOL,
        NUMERIC
    }
}