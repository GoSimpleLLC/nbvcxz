package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.DateMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract all the possible dates from a password.
 *
 * @author Adam Brusselback
 */
public final class DateMatcher implements PasswordMatcher
{

    private static final Pattern DATE_WITHOUT_SEPARATOR = Pattern.compile("^\\d{6,8}$");
    private static final Pattern DATE_WITH_SEPARATOR_YEAR_SUFFIX = Pattern.compile(""
            + "^(\\d{1,2})"                        // Day (or month)
            + "(\\s|-|/|\\\\|_|\\.)"              // Separator
            + "(\\d{1,2})"                        // Month (or day)
            + "\\2"                               // Same separator
            + "(19\\d{2}|200\\d|201\\d|\\d{2})$"); // Year
    private static final Pattern DATE_WITH_SEPARATOR_YEAR_PREFIX = Pattern.compile(""
            + "^(19\\d{2}|200\\d|201\\d|\\d{2})"   // Year
            + "(\\s|-|/|\\\\|_|\\.)"              // Separator
            + "(\\d{1,2})"                        // Day (or month)
            + "\\2"                               // Same separator
            + "(\\d{1,2})$");                      // Month (or day)

    /**
     * Extract all the possible dates without separator from a password
     *
     * @param password the password that is analyzed
     * @return the list of all the date without separator found
     */
    private static ArrayList<DateMatch> matchDatesWithoutSeparator(Configuration configuration, String password)
    {

        // Initialize the list of matching dates
        ArrayList<DateMatch> dateMatches = new ArrayList<>();

        // Create all possible subsequences of the password
        for (int start = 0; start < password.length(); start++)
        {
            for (int end = start + 4; end <= password.length(); end++)
            {

                // Get the subsequence
                String passwordChunk = password.substring(start, end);

                // Quick verfication that it is made of numbers
                if (DATE_WITHOUT_SEPARATOR.matcher(passwordChunk).find())
                {

                    // Extract the possible combinaison of dateAndMonth/year from the
                    // subsequence (eg: 121234 => 1212/34 and 12/1234)
                    ArrayList<PartialDateSplit> possiblePartialSplit = new ArrayList<>();
                    int chunkLength = passwordChunk.length();
                    if (chunkLength <= 6)
                    {
                        // start with a 2 digits year
                        possiblePartialSplit.add(new PartialDateSplit(
                                passwordChunk.substring(2),
                                passwordChunk.substring(0, 2)
                        ));
                        // end with a 2 digits year
                        possiblePartialSplit.add(new PartialDateSplit(
                                passwordChunk.substring(0, chunkLength - 2),
                                passwordChunk.substring(chunkLength - 2, chunkLength)
                        ));
                    }
                    if (chunkLength >= 6)
                    {
                        // start with a 4 digits year
                        possiblePartialSplit.add(new PartialDateSplit(
                                passwordChunk.substring(4),
                                passwordChunk.substring(0, 4)
                        ));
                        // end with a 4 digits year
                        possiblePartialSplit.add(new PartialDateSplit(
                                passwordChunk.substring(0, chunkLength - 4),
                                passwordChunk.substring(chunkLength - 4, chunkLength)
                        ));
                    }

                    // For each dateAndMonth/year extract the different possible full date
                    // (eg: 123/1998 => 1/23/1998 and 12/3/1998)
                    ArrayList<FullDateSplit> possibleFullSplit = new ArrayList<>();
                    for (PartialDateSplit split : possiblePartialSplit)
                    {
                        int dateAndMonthLength = split.dateAndMonth.length();
                        if (dateAndMonthLength == 2)
                        {
                            possibleFullSplit.add(new FullDateSplit(
                                    split.dateAndMonth.substring(0, 1),
                                    split.dateAndMonth.substring(1, 2),
                                    split.year
                            ));
                        }
                        else if (dateAndMonthLength == 3)
                        {
                            possibleFullSplit.add(new FullDateSplit(
                                    split.dateAndMonth.substring(0, 1),
                                    split.dateAndMonth.substring(1, 3),
                                    split.year
                            ));
                            possibleFullSplit.add(new FullDateSplit(
                                    split.dateAndMonth.substring(0, 2),
                                    split.dateAndMonth.substring(2, 3),
                                    split.year
                            ));
                        }
                        else if (dateAndMonthLength == 4)
                        {
                            possibleFullSplit.add(new FullDateSplit(
                                    split.dateAndMonth.substring(0, 2),
                                    split.dateAndMonth.substring(2, 4),
                                    split.year
                            ));
                        }
                    }

                    // Add to the final date list all the valid dates
                    for (FullDateSplit split : possibleFullSplit)
                    {
                        ValidDateSplit vSplit = isDateValid(split.date, split.month, split.year);
                        if (vSplit != null)
                        {
                            dateMatches.add(new DateMatch(passwordChunk, configuration, vSplit.date, vSplit.month, vSplit.year, "", start, end - 1));
                        }
                    }

                }
            }
        }

        return dateMatches;

    }

    /**
     * Extract all the possible dates with a separator
     * ("-", "/", " ", "\", "_" or ".") from a password
     *
     * @param password the password that is analyzed
     * @return the list of all the date with separator found
     */
    private static ArrayList<DateMatch> matchDatesWithSeparator(Configuration configuration, String password)
    {

        // Initialize the list of matching dates
        ArrayList<DateMatch> dateMatches = new ArrayList<>();

        // Create all possible subsequences of the password
        for (int start = 0; start < password.length(); start++)
        {
            for (int end = start + 6; end <= password.length(); end++)
            {

                // Get the subsequence
                String passwordChunk = password.substring(start, end);

                // Extract the date (if there is one) with the year as prefix
                Matcher m1 = DATE_WITH_SEPARATOR_YEAR_SUFFIX.matcher(passwordChunk);
                if (m1.matches())
                {
                    ValidDateSplit split = isDateValid(m1.group(1), m1.group(3), m1.group(4));
                    if (split != null)
                    {
                        dateMatches.add(new DateMatch(passwordChunk, configuration, split.date, split.month, split.year, m1.group(2), start, end - 1));
                    }
                }

                // Extract the date (if there is one) with the year as suffix
                Matcher m2 = DATE_WITH_SEPARATOR_YEAR_PREFIX.matcher(passwordChunk);
                if (m2.matches())
                {
                    ValidDateSplit split = isDateValid(m2.group(4), m2.group(3), m2.group(1));
                    if (split != null)
                    {
                        dateMatches.add(new DateMatch(passwordChunk, configuration, split.date, split.month, split.year, m2.group(2), start, end - 1));
                    }
                }

            }
        }

        return dateMatches;

    }

    /**
     * Verify that a date is valid. Year must be
     * two digit or four digit and between 1900 and 2029.
     *
     * @param day   the day of the date
     * @param month the moth of the date
     * @param year  the year of the date
     * @return a valid date split object containing the date information if the
     * date is valid and {@code null} if the date is not valid.
     */
    private static ValidDateSplit isDateValid(String day, String month, String year)
    {
        try
        {
            int dayInt = Integer.parseInt(day);
            int monthInt = Integer.parseInt(month);
            int yearInt = Integer.parseInt(year);
            if (
                    dayInt <= 0 || dayInt > 31 ||
                            monthInt <= 0 || monthInt > 12 ||
                            yearInt <= 0 || (yearInt >= 100 && (yearInt < 1900 || yearInt > 2019))
                    )
            {
                return null;
            }
            return new ValidDateSplit(dayInt, monthInt, yearInt);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    public List<Match> match(final Configuration configuration, final String password)
    {

        List<Match> dateMatches = new ArrayList<>();
        dateMatches.addAll(matchDatesWithoutSeparator(configuration, password));
        dateMatches.addAll(matchDatesWithSeparator(configuration, password));

        return dateMatches;
    }

    // Represent a partial match during the parsing (contains the date and month
    // concatenated and the year)
    private static class PartialDateSplit
    {
        public final String dateAndMonth;
        public final String year;

        public PartialDateSplit(String dateAndMonth, String year)
        {
            this.dateAndMonth = dateAndMonth;
            this.year = year;
        }
    }

    // Represent a complete match during the parsing (date, month and year)
    private static class FullDateSplit
    {
        public final String date;
        public final String month;
        public final String year;

        public FullDateSplit(String date, String month, String year)
        {
            this.date = date;
            this.month = month;
            this.year = year;
        }
    }

    // Represent a valid and parsed match (date, month and year as
    // <code>int</code>)
    private static class ValidDateSplit
    {
        public final int date;
        public final int month;
        public final int year;

        public ValidDateSplit(int date, int month, int year)
        {
            this.date = date;
            this.month = month;
            this.year = year;
        }
    }

}
