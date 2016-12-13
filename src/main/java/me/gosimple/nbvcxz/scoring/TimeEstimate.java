package me.gosimple.nbvcxz.scoring;

import java.math.BigDecimal;
import java.util.ResourceBundle;

/**
 * @author Adam Brusselback
 */
public final class TimeEstimate
{

    /**
     * Gets the estimated time to crack in seconds.
     *
     * @param result     a {@code Result} object to estimate time to crack for.
     * @param guess_type a {@code String} representing the estimate type to get time to crack for (defined in {@code Configuration}.
     * @return time in seconds estimated to crack as a {@code BigDecimal}.
     */
    public static BigDecimal getTimeToCrack(final Result result, final String guess_type)
    {
        BigDecimal guess_per_second = BigDecimal.valueOf(result.getConfiguration().getGuessTypes().get(guess_type));

        return result.getGuesses().divide(guess_per_second, 0, BigDecimal.ROUND_FLOOR);
    }

    /**
     * Gets the estimated time to crack formatted as a string.
     *
     * @param result     a {@code Result} object to estimate time to crack for.
     * @param guess_type a {@code String} representing the estimate type to get time to crack for (defined in {@code Configuration}.
     * @return time estimated to crack as a {@code String} (instant, seconds, minutes, hours, days, months, years, centuries, infinite).
     */
    public static String getTimeToCrackFormatted(final Result result, final String guess_type)
    {
        ResourceBundle mainResource = result.getConfiguration().getMainResource();
        BigDecimal seconds = getTimeToCrack(result, guess_type);
        BigDecimal minutes = new BigDecimal(60);
        BigDecimal hours = minutes.multiply(new BigDecimal(60));
        BigDecimal days = hours.multiply(new BigDecimal(24));
        BigDecimal months = days.multiply(new BigDecimal(30));
        BigDecimal years = months.multiply(new BigDecimal(12));
        BigDecimal centuries = years.multiply(new BigDecimal(100));
        BigDecimal infinite = centuries.multiply(new BigDecimal(100000));

        if (seconds.divide(infinite, 0, BigDecimal.ROUND_FLOOR).compareTo(BigDecimal.ONE) >= 0)
        {
            return mainResource.getString("main.estimate.greaterCenturies");
        }
        else if (seconds.divide(centuries, 0, BigDecimal.ROUND_FLOOR).compareTo(BigDecimal.ONE) >= 0)
        {
            return seconds.divide(centuries, 0, BigDecimal.ROUND_FLOOR) + " " + mainResource.getString("main.estimate.centuries");
        }
        else if (seconds.divide(years, 0, BigDecimal.ROUND_FLOOR).compareTo(BigDecimal.ONE) >= 0)
        {
            return seconds.divide(years, 0, BigDecimal.ROUND_FLOOR) + " " + mainResource.getString("main.estimate.years");
        }
        else if (seconds.divide(months, 0, BigDecimal.ROUND_FLOOR).compareTo(BigDecimal.ONE) >= 0)
        {
            return seconds.divide(months, 0, BigDecimal.ROUND_FLOOR) + " " + mainResource.getString("main.estimate.months");
        }
        else if (seconds.divide(days, 0, BigDecimal.ROUND_FLOOR).compareTo(BigDecimal.ONE) >= 0)
        {
            return seconds.divide(days, 0, BigDecimal.ROUND_FLOOR) + " " + mainResource.getString("main.estimate.days");
        }
        else if (seconds.divide(hours, 0, BigDecimal.ROUND_FLOOR).compareTo(BigDecimal.ONE) >= 0)
        {
            return seconds.divide(hours, 0, BigDecimal.ROUND_FLOOR) + " " + mainResource.getString("main.estimate.hours");
        }
        else if (seconds.divide(minutes, 0, BigDecimal.ROUND_FLOOR).compareTo(BigDecimal.ONE) >= 0)
        {
            return seconds.divide(minutes, 0, BigDecimal.ROUND_FLOOR) + " " + mainResource.getString("main.estimate.minutes");
        }
        else if (seconds.compareTo(BigDecimal.ONE) >= 0)
        {
            return seconds + " " + mainResource.getString("main.estimate.seconds");
        }
        else
        {
            return mainResource.getString("main.estimate.instant");
        }
    }
}
