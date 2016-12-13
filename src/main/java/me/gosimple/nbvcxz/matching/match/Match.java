package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.matching.PasswordMatcher;

/**
 * Object to represent a match found by a {@link PasswordMatcher} which contains the
 * portion of the password, where in the password the match was found (token), how many characters it is.
 * <br><br>
 * It should also be able to calculate a log^2 value of the guesses it would take to exhaust the problem space.
 * <br><br>
 * Additionally it should be able to return a string with details about the match to display to a user with relevant
 * information pertaining to the type of {@code Match}.
 *
 * @author Adam Brusselback
 */
public interface Match
{
    /**
     * @return the {@code String} value of the {@code Match}.
     */
    String getToken();

    /**
     * Calculate the entropy for the current match
     *
     * @return a {@code double} representing the entropy of the current {@code Match}.
     */
    double calculateEntropy();

    /**
     * Returns the start index of this part of the password
     *
     * @return the {@code int} start index of this {@code Match}.
     */
    int getStartIndex();

    /**
     * Returns the end index of this part of the password
     *
     * @return the {@code int} end index of this {@code Match}.
     */
    int getEndIndex();

    /**
     * Returns the length of this part of the password
     *
     * @return the {@code int} length of this {@code Match}.
     */
    int getLength();

    /**
     * Returns details about this match in the form of a String to be printed directly
     *
     * @return all specific details about this {@code Match} in printable String format.
     */
    String getDetails();

}