package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.interviews.Interviews.HOUR;
import static seedu.address.model.interviews.Interviews.MILLISECOND;
import static seedu.address.model.interviews.Interviews.MINUTE;
import static seedu.address.model.interviews.Interviews.SECOND;
import static seedu.address.model.job.JobListName.APPLICANT_NAME;
import static seedu.address.model.job.JobListName.APPLICANT_PREFIX;
import static seedu.address.model.job.JobListName.INTERVIEW_NAME;
import static seedu.address.model.job.JobListName.INTERVIEW_PREFIX;
import static seedu.address.model.job.JobListName.KIV_NAME;
import static seedu.address.model.job.JobListName.KIV_PREFIX;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobListName;
import seedu.address.model.job.JobName;
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
import seedu.address.model.person.Phone;
import seedu.address.model.person.Race;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_MAX_INTERVIEWS_A_DAY =
        "Maximum number of interviews a day is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DATE = "Not a valid date.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String value} into a {@code JobListName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code value} is invalid.
     */
    public static JobListName parseJobListName(String value) throws ParseException {
        requireNonNull(value);
        String trimmedName = value.trim().toLowerCase();
        if (!JobListName.isValidJobListName(trimmedName)) {
            throw new ParseException(JobListName.MESSAGE_CONSTRAINTS);
        }
        if (value.equals(APPLICANT_NAME) || value.equals(APPLICANT_PREFIX)) {
            return JobListName.APPLICANT;
        }
        if (value.equals(KIV_NAME) || value.equals(KIV_PREFIX)) {
            return JobListName.KIV;
        }
        if (value.equals(INTERVIEW_NAME) || value.equals(INTERVIEW_PREFIX)) {
            return JobListName.INTERVIEW;
        } else {
            return JobListName.SHORTLIST;
        }

    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String gender} into an {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String grade} into an {@code Grade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String grade) throws ParseException {
        requireNonNull(grade);
        String trimmedGrade = grade.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_CONSTRAINTS);
        }
        return new Grade(trimmedGrade);
    }

    /**
     * Parses a {@code String nric} into an {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code String interviewScores} into an {@code InterviewScores}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code interviewScores} is invalid.
     */
    public static InterviewScores parseInterviewScores(String interviewScores) throws ParseException {
        requireNonNull(interviewScores);
        String trimmedInterviewScores = interviewScores.trim();
        if (!InterviewScores.isValidInterviewScores(trimmedInterviewScores)) {
            throw new ParseException(InterviewScores.MESSAGE_CONSTRAINTS);
        }
        return new InterviewScores(trimmedInterviewScores);
    }

    /**
     * Parses a {@code String major} into an {@code Major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code major} is invalid.
     */
    public static Major parseMajor(String major) throws ParseException {
        requireNonNull(major);
        String trimmedMajor = major.trim();
        if (!Major.isValidMajor(trimmedMajor)) {
            throw new ParseException(Major.MESSAGE_CONSTRAINTS);
        }
        return new Major(trimmedMajor);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String race} into an {@code Race}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code race} is invalid.
     */
    public static Race parseRace(String race) throws ParseException {
        requireNonNull(race);
        String trimmedRace = race.trim();
        if (!Race.isValidRace(trimmedRace)) {
            throw new ParseException(Race.MESSAGE_CONSTRAINTS);
        }
        return new Race(trimmedRace);
    }

    /**
     * Parses a {@code String school} into an {@code School}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code school} is invalid.
     */

    public static School parseSchool(String school) throws ParseException {
        requireNonNull(school);
        String trimmedSchool = school.trim();
        if (!School.isValidSchool(trimmedSchool)) {
            throw new ParseException(School.MESSAGE_CONSTRAINTS);
        }
        return new School(trimmedSchool);
    }

    /**
     * Parses a {@code String knownproglang} into a {@code KnownProgLang}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code knownproglang} is invalid.
     */
    public static KnownProgLang parseKnownProgLang(String knownProgLang) throws ParseException {
        requireNonNull(knownProgLang);
        String trimmedKnownProgLang = knownProgLang.trim();
        if (!KnownProgLang.isValidKnownProgLang(trimmedKnownProgLang)) {
            throw new ParseException(KnownProgLang.MESSAGE_CONSTRAINTS);
        }
        return new KnownProgLang(trimmedKnownProgLang);
    }

    /**
     * Parses {@code Collection<String> knownproglangs} into a {@code Set<KnownProgLang>}.
     */
    public static Set<KnownProgLang> parseKnownProgLangs(Collection<String> knownProgLangs) throws ParseException {
        requireNonNull(knownProgLangs);
        final Set<KnownProgLang> knownProgLangSet = new HashSet<>();
        for (String knownProgLangName : knownProgLangs) {
            knownProgLangSet.add(parseKnownProgLang(knownProgLangName));
        }
        return knownProgLangSet;
    }

    /**
     * Parses a {@code String jobsApply} into a {@code JobsApply}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code jobsApply} is invalid.
     */
    public static JobsApply parseJobsApply(String jobsApply) throws ParseException {
        requireNonNull(jobsApply);
        String trimmedJobsApply = jobsApply.trim();
        if (!JobsApply.isValidJobsApply(trimmedJobsApply)) {
            throw new ParseException(JobsApply.MESSAGE_CONSTRAINTS);
        }
        return new JobsApply(trimmedJobsApply);
    }

    /**
     * Parses {@code Collection<String> jobsApply} into a {@code Set<JobsApply>}.
     */
    public static Set<JobsApply> parseJobsApply(Collection<String> jobsApply) throws ParseException {
        requireNonNull(jobsApply);
        final Set<JobsApply> jobsApplySet = new HashSet<>();
        for (String jobsApplyName : jobsApply) {
            jobsApplySet.add(parseJobsApply(jobsApplyName));
        }
        return jobsApplySet;
    }

    /**
     * Parses a {@code String pastjob} into a {@code PastJob}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code pastjob} is invalid.
     */
    public static PastJob parsePastJob(String pastjob) throws ParseException {
        requireNonNull(pastjob);
        String trimmedPastJob = pastjob.trim();
        if (!PastJob.isValidPastJob(trimmedPastJob)) {
            throw new ParseException(PastJob.MESSAGE_CONSTRAINTS);
        }
        return new PastJob(trimmedPastJob);
    }

    /**
     * Parses {@code Collection<String> pastjobs} into a {@code Set<PastJob>}.
     */
    public static Set<PastJob> parsePastJobs(Collection<String> pastjobs) throws ParseException {
        requireNonNull(pastjobs);
        final Set<PastJob> pastjobSet = new HashSet<>();
        for (String pastjobName : pastjobs) {
            pastjobSet.add(parsePastJob(pastjobName));
        }
        return pastjobSet;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String name} into a {@code JobName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static JobName parseJobName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!JobName.isValidName(trimmedName)) {
            throw new ParseException(JobName.MESSAGE_CONSTRAINTS);
        }
        return new JobName(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code maxInterviewsADay} is invalid.
     */
    public static int parseMaxInterviewsADay(String maxInterviewsADay) throws ParseException {
        requireNonNull(maxInterviewsADay);
        String trimmedMaxInterviewsADay = maxInterviewsADay.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedMaxInterviewsADay)) {
            throw new ParseException(MESSAGE_INVALID_MAX_INTERVIEWS_A_DAY);
        }
        return Integer.parseInt(trimmedMaxInterviewsADay);
    }

    /**
     * Parses a {@code String name} into a {@code List<Calendar>}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code blockOutDates} is invalid.
     */
    public static List<Calendar> parseBlockOutDates(String blockOutDates) throws ParseException {
        requireNonNull(blockOutDates);
        List<Calendar> result = new ArrayList<>();
        String trimmedBlockOutDates = blockOutDates.trim();
        String[] stringArray = trimmedBlockOutDates.split(",");
        for (String date : stringArray) {

            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(3, 5));
            int year = Integer.parseInt(date.substring(6, 10));
            Calendar currentCalendar = new GregorianCalendar(year, month - 1, day, HOUR, MINUTE, SECOND);
            currentCalendar.set(Calendar.MILLISECOND, MILLISECOND);

            if (isValidDateRange(date)) {
                int endDay = Integer.parseInt(date.substring(13, 15));
                int endMonth = Integer.parseInt(date.substring(16, 18));
                int endYear = Integer.parseInt(date.substring(19, 23));
                Calendar endCalendar = new GregorianCalendar(endYear, endMonth - 1, endDay, HOUR, MINUTE, SECOND);
                endCalendar.set(Calendar.MILLISECOND, MILLISECOND);
                while (currentCalendar.compareTo(endCalendar) < 0) {
                    result.add(currentCalendar);
                    currentCalendar = (Calendar) currentCalendar.clone();
                    currentCalendar.add(Calendar.DATE, 1);
                }
                result.add(endCalendar);
            } else if (isValidDate(date)) {
                result.add(currentCalendar);
            } else {
                throw new ParseException(MESSAGE_INVALID_DATE);
            }
        }
        return result;
    }

    /**
     * Returns a boolean testing for validity of date.
     */
    protected static boolean isValidDate(String date) {
        if (!date.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d")) {
            return false;
        }

        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));

        if (month == 2) {
            if (isLeapYear(year)) {
                if (day <= 29) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (day <= 28) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        if (month % 2 == 1 && day <= 31 && day >= 1) {
            return true;
        }

        if (month % 2 == 0 && day <= 30 && day >= 1) {
            return true;
        }

        return false;
    }

    /**
     * Checks if year is a leap year.
     */
    protected static boolean isLeapYear(int year) {
        if ((year % 4 == 0) && (year % 100 != 0)) {
            return true;
        } else if ((year % 4 == 0) && (year % 100 == 0) && (year % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if the date range provided is valid.
     */
    protected static boolean isValidDateRange(String dateRange) {
        if (!dateRange.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d - \\d\\d/\\d\\d/\\d\\d\\d\\d")) {
            return false;
        }
        return isValidDate(dateRange.substring(0, 10)) && isValidDate(dateRange.substring(13, 23));
    }

}
