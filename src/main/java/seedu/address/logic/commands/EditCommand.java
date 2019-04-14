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
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Grade;
import seedu.address.model.person.InterviewScores;
import seedu.address.model.person.JobsApply;
import seedu.address.model.person.KnownProgLang;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.PastJob;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Race;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "ed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_RACE + "RACE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_SCHOOL + "SCHOOL] "
            + "[" + PREFIX_MAJOR + "MAJOR] "
            + "[" + PREFIX_GRADE + "GRADE] "
            + "[" + PREFIX_SCHOOL + "SCHOOL] "
            + "[" + PREFIX_KNOWNPROGLANG + "KNOWNPROGLANG] "
            + "[" + PREFIX_PASTJOB + "PASTJOB] "
            + "[" + PREFIX_JOBSAPPLY + "JOBSAPPLY] "
            + "[" + PREFIX_INTERVIEWSCORES + "INTERVIEWSCORES] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com"
            + PREFIX_NRIC + "S9671597H "
            + PREFIX_GENDER + "Male "
            + PREFIX_RACE + "Indian "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_SCHOOL + "NUS "
            + PREFIX_MAJOR + "Computer Science "
            + PREFIX_GRADE + "4.76 "
            + PREFIX_JOBSAPPLY + "Software Engineer "
            + PREFIX_INTERVIEWSCORES + "5,8,2,4,10 "
            + PREFIX_KNOWNPROGLANG + "Python "
            + PREFIX_PASTJOB + "Software Engineer "
            + "The alias \"ed\" can be used instead.\n";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index                of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        boolean isAllJobsScreen = model.getIsAllJobScreen();
        if (!isAllJobsScreen) {
            throw new CommandException(MESSAGE_COMMAND_CANNOT_USE);
        }
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.revertList();
        model.updateBaseFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Gender updatedGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
        Race updatedRace = editPersonDescriptor.getRace().orElse(personToEdit.getRace());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        School updatedSchool = editPersonDescriptor.getSchool().orElse(personToEdit.getSchool());
        Major updatedMajor = editPersonDescriptor.getMajor().orElse(personToEdit.getMajor());
        Grade updatedGrade = editPersonDescriptor.getGrade().orElse(personToEdit.getGrade());
        InterviewScores updatedInterviewScores = editPersonDescriptor.getInterviewScores()
                .orElse(personToEdit.getInterviewScores());
        Set<KnownProgLang> updatedKnownProgLangs = editPersonDescriptor.getKnownProgLangs()
                .orElse(personToEdit.getKnownProgLangs());
        Set<PastJob> updatedPastJobs = editPersonDescriptor.getPastJobs().orElse(personToEdit.getPastJobs());
        Set<JobsApply> updatedJobsApply = editPersonDescriptor.getJobsApply().orElse(personToEdit.getJobsApply());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        return new Person(updatedName, updatedPhone, updatedEmail, updatedNric, updatedGender, updatedRace,
                updatedAddress, updatedSchool, updatedMajor, updatedGrade, updatedKnownProgLangs, updatedPastJobs,
                updatedJobsApply, updatedInterviewScores, updatedTags);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Nric nric;
        private Gender gender;
        private Race race;
        private Address address;
        private School school;
        private Major major;
        private Grade grade;
        private InterviewScores interviewScores;
        private Set<JobsApply> jobsApply;
        private Set<KnownProgLang> knownProgLangs;
        private Set<PastJob> pastjobs;
        private Set<Tag> tags;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * * A defensive copy of {@code pastjobs} is used internally.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setNric(toCopy.nric);
            setGender(toCopy.gender);
            setRace(toCopy.race);
            setAddress(toCopy.address);
            setSchool(toCopy.school);
            setMajor(toCopy.major);
            setGrade(toCopy.grade);
            setKnownProgLangs(toCopy.knownProgLangs);
            setPastJobs(toCopy.pastjobs);
            setJobsApply(toCopy.jobsApply);
            setInterviewScores(toCopy.interviewScores);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, nric, gender, race, address, school,
                    major, grade, knownProgLangs, pastjobs, jobsApply, interviewScores, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setRace(Race race) {
            this.race = race;
        }

        public Optional<Race> getRace() {
            return Optional.ofNullable(race);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setSchool(School school) {
            this.school = school;
        }

        public Optional<School> getSchool() {
            return Optional.ofNullable(school);
        }

        public void setMajor(Major major) {
            this.major = major;
        }

        public Optional<Major> getMajor() {
            return Optional.ofNullable(major);
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
        }

        public Optional<Grade> getGrade() {
            return Optional.ofNullable(grade);
        }

        public void setInterviewScores(InterviewScores interviewScores) {
            this.interviewScores = interviewScores;
        }

        public Optional<InterviewScores> getInterviewScores() {
            return Optional.ofNullable(interviewScores);
        }

        /**
         * Sets {@code knownProgLangs} to this object's {@code knownProgLangs}.
         * A defensive copy of {@code knownProgLangs} is used internally.
         */
        public void setKnownProgLangs(Set<KnownProgLang> knownProgLangs) {
            this.knownProgLangs = (knownProgLangs != null) ? new HashSet<>(knownProgLangs) : null;
        }

        /**
         * Returns an unmodifiable pastjob set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code pastjobs} is null.
         */
        public Optional<Set<KnownProgLang>> getKnownProgLangs() {
            return (knownProgLangs != null) ? Optional.of(Collections
                    .unmodifiableSet(knownProgLangs)) : Optional.empty();
        }

        /**
         * Sets {@code pastjobs} to this object's {@code pastjobs}.
         * A defensive copy of {@code pastjobs} is used internally.
         */
        public void setPastJobs(Set<PastJob> pastjobs) {
            this.pastjobs = (pastjobs != null) ? new HashSet<>(pastjobs) : null;
        }

        /**
         * Returns an unmodifiable pastjob set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code pastjobs} is null.
         */
        public Optional<Set<PastJob>> getPastJobs() {
            return (pastjobs != null) ? Optional.of(Collections.unmodifiableSet(pastjobs)) : Optional.empty();
        }

        /**
         * Sets {@code jobsApply} to this object's {@code jobsApply}.
         * A defensive copy of {@code jobsApply} is used internally.
         */
        public void setJobsApply(Set<JobsApply> jobsApply) {
            this.jobsApply = (jobsApply != null) ? new HashSet<>(jobsApply) : null;
        }

        /**
         * Returns an unmodifiable jobsApply set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code jobsApply} is null.
         */
        public Optional<Set<JobsApply>> getJobsApply() {
            return (jobsApply != null) ? Optional.of(Collections.unmodifiableSet(jobsApply)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getNric().equals(e.getNric())
                    && getGender().equals(e.getGender())
                    && getRace().equals(e.getRace())
                    && getAddress().equals(e.getAddress())
                    && getSchool().equals(e.getSchool())
                    && getMajor().equals(e.getMajor())
                    && getGrade().equals(e.getGrade())
                    && getKnownProgLangs().equals(e.getKnownProgLangs())
                    && getPastJobs().equals(e.getPastJobs())
                    && getJobsApply().equals(e.getJobsApply())
                    && getInterviewScores().equals(e.getInterviewScores());
        }
    }
}
