package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.analytics.Analytics;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Analytics information should be shown to user*/

    private Analytics analytics;

    private JobName job;

    private String interviews;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;


    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public CommandResult(String feedbackToUser, Analytics results) {
        this(feedbackToUser, false, false);
        if (isSuccessfulAnalytics()) {
            analytics = results;
        }

    }

    public CommandResult(String feedbackToUser, JobName results) {
        this(feedbackToUser, false, false);
        if (isSuccessfulDisplayJob()) {
            job = results;
        }

    }

    public CommandResult(String feedbackToUser, String results) {
        this(feedbackToUser, false, false);
        if (isSuccessfulInterviews()) {
            interviews = results;
        }
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isSuccessfulAnalytics() {
        return feedbackToUser.equals("Analytics generated!");
    }

    public boolean isSuccessfulInterviews() {
        return feedbackToUser.equals("Interviews shown");
    }

    public boolean isSuccessfulDisplayJob() { return feedbackToUser.equals("Displaying job");}

    //remember to handle null later
    public Analytics getAnalytics() {
        return analytics;
    }

    public JobName getJob() {return job;}

    public String getInterviews() {
        return interviews;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
