package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Set;

import seedu.address.model.person.JobsApply;
import seedu.address.model.person.KnownProgLang;
import seedu.address.model.person.PastJob;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
            .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code word} in the range of {@code sentence}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean valueInRange(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        float value = Float.parseFloat(word);

        String preppedSentence = sentence.trim();
        checkArgument(!preppedSentence.isEmpty(), "Range parameter cannot be empty");
        String[] rangesInPreppedSentence = preppedSentence.split(";");
        for (String range : rangesInPreppedSentence) {
            checkArgument(range.split("-").length == 2, "Range parameter format wrong");
            String[] values = range.split("-");
            String preppedUpperBound = values[1].trim();
            String preppedLowerBound = values[0].trim();
            checkArgument(preppedLowerBound.matches(
                "\\d" + "." + "\\d+"), "lower bound parameter format wrong");
            checkArgument(preppedUpperBound.matches(
                "\\d" + "." + "\\d+"), "upper bound parameter format wrong");
            if (value >= Float.parseFloat(preppedLowerBound) && value <= Float.parseFloat(preppedUpperBound)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getSetString(Set s) {
        requireNonNull(s);
        StringBuilder stringBuilder = new StringBuilder();
        for (Object obj : s) {
            if (obj instanceof JobsApply) {
                String jobsApply = ((JobsApply) obj).value;
                stringBuilder.append(jobsApply);
            } else if (obj instanceof KnownProgLang) {
                String knownProgLang = ((KnownProgLang) obj).value;
                stringBuilder.append(knownProgLang);
            } else if (obj instanceof PastJob) {
                String pastJob = ((PastJob) obj).value;
                stringBuilder.append(pastJob);
            }
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }


    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     *
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
