package me.gosimple.nbvcxz.resources;

/**
 * Util class for brute force calculations.
 *
 * @author Adam Brusselback
 */
public class BruteForceUtil
{
    /**
     * Calculates the brute force cardinality of a given password.
     * The brut force cardinality is the estimated range of character a brute
     * force method would use to crack the password.
     *
     * @param password the password we are estimating the brute force cardinality
     * @return the brute force cardinality
     */
    public static int getBrutForceCardinality(final String password)
    {

        boolean lower = false,
                upper = false,
                digits = false,
                symbols = false,
                unicode = false;

        for (char c : password.toCharArray())
        {
            if (0x30 <= c && c <= 0x39)
            {
                digits = true;
            }
            else if (0x41 <= c && c <= 0x5a)
            {
                upper = true;
            }
            else if (0x61 <= c && c <= 0x7a)
            {
                lower = true;
            }
            else if (c <= 0x7f)
            {
                symbols = true;
            }
            else
            {
                unicode = true;
            }
        }

        int cardinality = 0;
        if (digits)
        {
            cardinality += 10;
        }
        if (upper)
        {
            cardinality += 26;
        }
        if (lower)
        {
            cardinality += 26;
        }
        if (symbols)
        {
            cardinality += 33;
        }
        if (unicode)
        {
            cardinality += 100;
        }

        return cardinality;
    }

    /**
     * Calculates the brute force cardinality of a given password.
     * The brut force cardinality is the estimated range of character a brute
     * force method would use to crack the password.
     *
     * @param character the password we are estimating the brute force cardinality
     * @return the brute force cardinality
     */
    public static int getBrutForceCardinality(final char character)
    {

        boolean lower = false,
                upper = false,
                digits = false,
                symbols = false,
                unicode = false;

        if (0x30 <= character && character <= 0x39)
        {
            digits = true;
        }
        else if (0x41 <= character && character <= 0x5a)
        {
            upper = true;
        }
        else if (0x61 <= character && character <= 0x7a)
        {
            lower = true;
        }
        else if (character <= 0x7f)
        {
            symbols = true;
        }
        else
        {
            unicode = true;
        }

        int cardinality = 0;
        if (digits)
        {
            cardinality += 10;
        }
        if (upper)
        {
            cardinality += 26;
        }
        if (lower)
        {
            cardinality += 26;
        }
        if (symbols)
        {
            cardinality += 33;
        }
        if (unicode)
        {
            cardinality += 100;
        }

        return cardinality;
    }
}
