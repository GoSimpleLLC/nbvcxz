package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;

import java.util.List;

/**
 * Interface for matchers which can stop early when an estimate has run out of time.
 * <br><br>
 * A matcher which does not implement this is still bounded, but only between matchers, so it will
 * always be allowed to finish once it has started.  Implement this for any matcher whose cost grows
 * quickly with password length.
 *
 * @author Adam Brusselback.
 */
public interface DeadlinePasswordMatcher extends PasswordMatcher
{
    /**
     * Creates a {@code List} of {@code Match} from the password, giving up early if the deadline passes.
     * <br><br>
     * Returning early is always safe.  Fewer matches can only raise the entropy of the estimate, never
     * lower it, and the estimate is marked as timed out so the higher value is never treated as final.
     *
     * @param configuration configuration for the matcher.
     * @param password      password to match.
     * @param deadline      the {@code System.currentTimeMillis()} value to stop at, or 0 for no deadline.
     * @return a {@code List} of {@code Match}es that this matcher found before running out of time.
     */
    List<Match> match(final Configuration configuration, final String password, final long deadline);
}
