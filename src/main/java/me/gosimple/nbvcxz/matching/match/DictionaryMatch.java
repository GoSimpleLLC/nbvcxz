package me.gosimple.nbvcxz.matching.match;

import me.gosimple.nbvcxz.resources.Configuration;
import me.gosimple.nbvcxz.scoring.BruteForceUtil;

import java.util.List;
import java.util.ResourceBundle;


/**
 * @author Adam Brusselback
 */
public final class DictionaryMatch extends BaseMatch
{
    private static final String START_UPPER = "^[A-Z][^A-Z]+";
    private static final String END_UPPER = "^[^A-Z]+[A-Z]$";
    private static final String ALL_UPPER = "^[^a-z]+$";
    private static final String ALL_LOWER = "^[^A-Z]+$";
    private final String dictionary_name;
    private final String dictionary_value;
    private final int rank;
    private final boolean excluded;
    private final List<Character[]> leetSubstitution;
    private final boolean reversed;
    private final int distance;

    /**
     * Create a new {@code DictionaryMatch}
     *
     * @param match            the {@code String} we are creating the {@code DictionaryMatch} from.
     * @param configuration    the {@link Configuration} object.
     * @param start_index      the start index in the password for this match.
     * @param end_index        the end index in the password for this match.
     * @param rank             The rank of the match in the dictionary
     * @param leetSubstitution If leet substitution was used or not
     * @param excluded         if the dictionary was an exclusion dictionary
     * @param reversed         if the password was reversed to match
     * @param dictionary_name  the name of the dictionary matched
     */
    public DictionaryMatch(final String match, Configuration configuration, final int start_index, final int end_index, final String dictionary_value, final int rank, final List<Character[]> leetSubstitution, final boolean excluded, final boolean reversed, final String dictionary_name, final int distance)
    {
        super(match, configuration, start_index, end_index);
        this.dictionary_value = dictionary_value;
        this.rank = rank;
        this.leetSubstitution = leetSubstitution;
        this.excluded = excluded;
        this.dictionary_name = dictionary_name;
        this.reversed = reversed;
        this.distance = distance;
    }

    @Override
    public double calculateEntropy()
    {
        // If this is an excluded password, return with no entropy.
        if (excluded)
        {
            return 0d;
        }

        // First the base entropy based on the rank
        double baseEntropy = log2(rank);

        return Math.max(0, baseEntropy + uppercaseEntropy() + leetEntropy() + reversedEntropy() + distanceEntropy());
    }

    /**
     * @return the additional entropy provided by distance between the token and dictionaryValue
     */
    private double distanceEntropy()
    {
        if (getDistance() == 0)
        {
            return 0d;
        }
        else
        {
            int len_diff = getToken().length() - getDictionaryValue().length();
            int char_shift = getDistance() - Math.abs(len_diff);

            if (len_diff + char_shift <= 0)
            // if the length is shortened, give a little entropy
            {
                return 1d;
            }
            else
            // if the length is not shortened then we can add even more
            {
                return log2(BruteForceUtil.getBrutForceCardinality(getToken()) * (len_diff + char_shift));
            }
        }
    }

    /**
     * @return the additional entropy provided by uppercase
     */
    private double uppercaseEntropy()
    {
        String password = getToken();

        // Common uppercase pattern
        if (password.matches(ALL_LOWER))
        {
            return 0d;
        }
        if (password.matches(START_UPPER))
        {
            return 1d;
        }
        if (password.matches(ALL_UPPER))
        {
            return 1d;
        }
        if (password.matches(END_UPPER))
        {
            return 1d;
        }

        // Count the number of upper case characters
        int upperCount = 0;
        for (char c : password.toCharArray())
        {
            if (Character.isUpperCase(c))
            {
                upperCount++;
            }
        }

        // Count the number of lower case characters
        int lowerCount = 0;
        for (char c : password.toCharArray())
        {
            if (Character.isLowerCase(c))
            {
                lowerCount++;
            }
        }

        // Calculate all the possibilies
        int possiblities = 0;
        int totalCase = upperCount + lowerCount;
        int minCase = Math.min(upperCount, lowerCount);
        for (int i = 0; i <= minCase; i++)
        {
            possiblities += nCk(totalCase, i);
        }

        return Math.max(log2(possiblities), 1);
    }

    /**
     * @return the additional entropy provided by leet formatting
     */
    private double leetEntropy()
    {
        if (!isLeet())
        {
            return 0d;
        }

        int possibilities = 0;
        for (Character[] sub : leetSubstitution)
        {
            char original = sub[0];
            char newChar = sub[1];
            int substitutionCount = 0;
            int unSubstitutionCount = 0;
            for (char c : getToken().toCharArray())
            {
                if (c == newChar)
                {
                    substitutionCount++;
                }
                if (c == original)
                {
                    unSubstitutionCount++;
                }
            }
            int totalSub = substitutionCount + unSubstitutionCount;
            int minSub = Math.min(substitutionCount, unSubstitutionCount);
            for (int i = 0; i <= minSub; i++)
            {
                possibilities += nCk(totalSub, i);
            }
        }

        return Math.max(1, log2(possibilities)); // 1 bit of entropy (instead of 0)
        // for single-letter substitution
        // like 4pple -> apple
    }

    /**
     * Returns an extra bit of entropy if it was a reverse match, because it would likely double the key space to check.
     *
     * @return the extra entropy bits if it's reversed
     */
    private double reversedEntropy()
    {
        if (isReversed())
        {
            return 1D;
        }
        else
        {
            return 0D;
        }
    }


    /**
     * @return the rank of the password in the dictionary
     */
    public int getRank()
    {
        return rank;
    }


    /**
     * @return true if the password is written in leet, false otherwise
     */
    public boolean isLeet()
    {
        return leetSubstitution.size() > 0;
    }

    /**
     * @return the dictionary name that created this match
     */
    public String getDictionaryName()
    {
        return dictionary_name;
    }


    /**
     * @return the {@code ArrayList} of the leet substitution
     */
    public List<Character[]> getLeetSubstitution()
    {
        return leetSubstitution;
    }

    /**
     * @return true if the reversed password matches the dictionary entry
     */
    public boolean isReversed()
    {
        return reversed;
    }

    /**
     * @return the distance from the dictionary entry the match was made with
     */
    public int getDistance()
    {
        return distance;
    }

    /**
     * @return the value in the dictionary that the token matched (may not match exactly)
     */
    public String getDictionaryValue()
    {
        return dictionary_value;
    }

    public String getDetails()
    {
        ResourceBundle mainResource = configuration.getMainResource();
        StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append(super.getDetails());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.dictionary")).append(" ").append(getDictionaryName());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.dictionaryValue")).append(" ").append(getDictionaryValue());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.rank")).append(" ").append(getRank());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.length")).append(" ").append(getLength());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.leetSub")).append(" ").append(isLeet());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.reversed")).append(" ").append(isReversed());
        detailBuilder.append("\n");
        detailBuilder.append(mainResource.getString("main.match.distance")).append(" ").append(getDistance());
        return detailBuilder.toString();
    }


}
