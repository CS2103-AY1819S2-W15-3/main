package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_COMMAND_CANNOT_USE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEWSCORES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBSAPPLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KNOWNPROGLANG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASTJOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;

import java.util.Iterator;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobListName;
import seedu.address.model.job.JobName;
import seedu.address.model.person.JobsApply;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book.\n"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + PREFIX_NRIC + "NRIC "
        + PREFIX_GENDER + "GENDER "
        + PREFIX_RACE + "RACE "
        + PREFIX_ADDRESS + "ADDRESS "
        + PREFIX_SCHOOL + "SCHOOL "
        + PREFIX_MAJOR + "MAJOR "
        + PREFIX_GRADE + "GRADE "
        + PREFIX_INTERVIEWSCORES + "INTERVIEWSCORES "
        + PREFIX_JOBSAPPLY + "JOBSAPPLY..."
        + "[" + PREFIX_PASTJOB + "PASTJOB]..."
        + "[" + PREFIX_KNOWNPROGLANG + "KNOWNPROGLANG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_PHONE + "98765432 "
        + PREFIX_EMAIL + "johnd@example.com "
        + PREFIX_NRIC + "S9671597H "
        + PREFIX_GENDER + "Male "
        + PREFIX_RACE + "Indian "
        + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
        + PREFIX_SCHOOL + "NUS "
        + PREFIX_MAJOR + "Computer Science "
        + PREFIX_GRADE + "4.76 "
        + PREFIX_JOBSAPPLY + "Software-Engineer "
        + PREFIX_INTERVIEWSCORES + "1,2,3,4,5 "
        + PREFIX_KNOWNPROGLANG + "Python "
        + PREFIX_PASTJOB + "Software Engineer "
        + "The alias \"a\" can be used instead.\n";

    public static final String MESSAGE_LACK_NAME = "Name field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_ADDRESS = "Address field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_EMAIL = "Email field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_GENDER = "Gender field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_GRADE = "Grade field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_JOBSAPPLY = "Jobs Apply field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_MAJOR = "Major field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_NRIC = "Nric field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_PHONE = "Phone field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_RACE = "Race field should not be empty.\n%1$s";
    public static final String MESSAGE_LACK_SCHOOL = "School field should not be empty.\n%1$s";
    public static final String MESSAGE_INFORMATION_WITHOUT_PREFIX =
        "All information need a prefix for this command. \n%1$s";
    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";


    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        boolean isAllJobsScreen = model.getIsAllJobScreen();
        if (!isAllJobsScreen) {
            throw new CommandException(MESSAGE_COMMAND_CANNOT_USE);
        }
        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        Iterator<JobsApply> itr = toAdd.getJobsApply().iterator();
        while (itr.hasNext()) {
            JobsApply job = itr.next();
            try {
                model.addPersonToJob(new Job(new JobName(job.toString())), toAdd, JobListName.APPLICANT);
            } catch (Exception e) {
                continue;
            }
        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddCommand // instanceof handles nulls
            && toAdd.equals(((AddCommand) other).toAdd));
    }
}
