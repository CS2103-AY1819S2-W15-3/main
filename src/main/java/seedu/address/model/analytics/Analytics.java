package seedu.address.model.analytics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import seedu.address.model.person.JobsApply;
import seedu.address.model.person.PastJob;
import seedu.address.model.person.Person;

/**
 * Provides analytics data on job applicants, based on their data fields obtained from {@code Person}
 * */

public class Analytics {
    private List<Person> personList;
    private int numPeople;

    public Analytics(List<Person> list) {
        personList = list;
        numPeople = list.size();
    }

    /**
     * Generate full analytics report
     * */

    public String generate() {
        Float meanGrade = meanGrade();
        ArrayList<Float> meanInterviewSores = meanInterviewScores();
        ArrayList<Integer> genderBreakdown = genderBreakdown();
        ArrayList<Integer> raceBreakdown = raceBreakdown();
        HashMap<String, Integer> majorBreakdown = majorBreakdown();
        HashMap<String, Integer> schoolBreakdown = schoolBreakdown();
        HashMap<String, Integer> pastJobBreakdown = pastJobBreakdown();
        HashMap<String, Integer> jobApplicationBreakdown = jobApplicationBreakdown();
        DecimalFormat df = new DecimalFormat("#.00");

        String finalReport;
        String reportMeanGrade;
        String reportMeanInterviewScores;
        String reportGender;
        String reportRace;
        String reportMajor;
        String reportSchool;
        String reportPastJob;
        String reportJobApply;

        reportMeanGrade = "Average Grade: " + df.format(meanGrade);
        reportMeanInterviewScores = "Average Interview Scores:" + "\n" + "Q1: " + df.format(meanInterviewSores.get(0))
                + "\n" + "Q2: " + df.format(meanInterviewSores.get(1))
                + "\n" + "Q3: " + df.format(meanInterviewSores.get(2))
                + "\n" + "Q4: " + df.format(meanInterviewSores.get(3))
                + "\n" + "Q5: " + df.format(meanInterviewSores.get(4));
        reportGender = "Gender \n" + "Female: " + genderBreakdown.get(0) + "\n" + "Male: " + genderBreakdown.get(1)
                + "\n" + "Others: " + genderBreakdown.get(2);
        reportRace = "Race \n" + "Chinese: " + raceBreakdown.get(0) + "\n" + "Malay: " + raceBreakdown.get(1) + "\n"
                + "Indian: " + raceBreakdown.get(2) + "\n" + "Others: " + raceBreakdown.get(3);
        reportMajor = "Major \n" + generateStringFromSet(majorBreakdown);
        reportSchool = "School \n" + generateStringFromSet(schoolBreakdown);
        reportPastJob = "Past Jobs \n" + generateStringFromSet(pastJobBreakdown);
        reportJobApply = "Job Applications \n" + generateStringFromSet(jobApplicationBreakdown);

        finalReport = reportJobApply + "\n\n" + reportMeanGrade + "\n\n" + reportMeanInterviewScores + "\n\n"
                + reportGender + "\n\n" + reportRace + "\n\n" + reportMajor + "\n\n" + reportSchool + "\n\n"
                + reportPastJob;

        return finalReport;
    }

    /**
     * Returns the average grade of all applicants in the list
     * */

    private Float meanGrade() {
        Float sumGrade = 0F;

        for (int i = 0; i < numPeople; i++) {
            sumGrade += Float.valueOf(personList.get(i).getGrade().value);
        }

        return sumGrade / numPeople;
    }

    /**
     * Provides the number of applicants for each open job role
     * A {@code Person} with more than one job applied will be counted multiple times, once for each role.
     * */

    private HashMap<String, Integer> jobApplicationBreakdown() {
        HashMap<String, Integer> jobApplicantsCounter = new HashMap<>();

        for (int i = 0; i < numPeople; i++) {
            Iterator<JobsApply> itr = personList.get(i).getJobsApply().iterator();
            while (itr.hasNext()) {
                JobsApply job = itr.next();
                if (!jobApplicantsCounter.containsKey(job.value)) {
                    jobApplicantsCounter.put(job.value, 1);
                } else {
                    jobApplicantsCounter.put(job.value, jobApplicantsCounter.get(job.value) + 1);
                }
            }
        }
        return jobApplicantsCounter;
    }

    /**
     * Provides an average score for each interview question from selected list of applicants
     * Applicants not required to all have interview scores
     * */

    private ArrayList<Float> meanInterviewScores() {
        ArrayList<Float> averageScores = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            averageScores.add(0F);
        }
        int interviewed = numPeople;

        for (int i = 0; i < numPeople; i++) {
            Person curr = personList.get(i);
            if (curr.getInterviewScores().value == "No Record") {
                interviewed--;
            } else {
                String[] personScores;
                personScores = curr.getInterviewScores().value.split(",");
                for (int j = 0; j < 5; j++) {
                    averageScores.set(j, averageScores.get(j) + Integer.valueOf(personScores[j]));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            averageScores.set(i, averageScores.get(i) / interviewed);
        }
        return averageScores;
    }

    /**
     * Returns the number of applicants for each gender category
     * */

    private ArrayList<Integer> genderBreakdown() {
        ArrayList<Integer> genderCount = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            genderCount.add(0);
        }

        for (int i = 0; i < 3; i++) {
            genderCount.set(i, 0);
        }

        for (int i = 0; i < numPeople; i++) {
            String currGender = personList.get(i).getGender().value;

            if (currGender.equals("Female")) {
                genderCount.set(0, genderCount.get(0) + 1);
            } else if (currGender.equals("Male")) {
                genderCount.set(1, genderCount.get(1) + 1);
            } else {
                genderCount.set(2, genderCount.get(2) + 1);
            }
        }
        return genderCount;
    }

    /**
     * Returns the number of applicants for each race category
     * */

    private ArrayList<Integer> raceBreakdown() {
        ArrayList<Integer> raceCount = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            raceCount.add(0);
        }

        for (int i = 0; i < numPeople; i++) {
            String currRace = personList.get(i).getRace().value;

            if (currRace.equals("Chinese")) {
                raceCount.set(0, raceCount.get(0) + 1);
            } else if (currRace.equals("Malay")) {
                raceCount.set(1, raceCount.get(1) + 1);
            } else if (currRace.equals("Indian")) {
                raceCount.set(2, raceCount.get(2) + 1);
            } else {
                raceCount.set(3, raceCount.get(3) + 1);
            }
        }
        return raceCount;
    }

    /**
     * Provides the number of applicants studying in each major
     * */

    private HashMap<String, Integer> majorBreakdown() {
        HashMap<String, Integer> majorCounter = new HashMap<>();

        for (int i = 0; i < numPeople; i++) {
            String currMajor = personList.get(i).getMajor().value;
            if (!majorCounter.containsKey(currMajor)) {
                majorCounter.put(currMajor, 1);
            } else {
                majorCounter.put(currMajor, majorCounter.get(currMajor) + 1);
            }
        }
        return majorCounter;
    }

    /**
     * Provides the number of applicants studying in each school
     * */

    private HashMap<String, Integer> schoolBreakdown() {
        HashMap<String, Integer> schoolCounter = new HashMap<>();

        for (int i = 0; i < numPeople; i++) {
            String currSchool = personList.get(i).getSchool().value;
            if (!schoolCounter.containsKey(currSchool)) {
                schoolCounter.put(currSchool, 1);
            } else {
                schoolCounter.put(currSchool, schoolCounter.get(currSchool) + 1);
            }
        }
        return schoolCounter;
    }

    /**
     * Provides the number of applicants for each past job
     * A {@code Person} with more than one past job applied will be counted multiple times, once for each job.
     * */

    private HashMap<String, Integer> pastJobBreakdown() {
        HashMap<String, Integer> pastJobCounter = new HashMap<>();

        for (int i = 0; i < numPeople; i++) {
            Iterator<PastJob> itr = personList.get(i).getPastJobs().iterator();
            while (itr.hasNext()) {
                PastJob job = itr.next();
                if (!pastJobCounter.containsKey(job.value)) {
                    pastJobCounter.put(job.value, 1);
                } else {
                    pastJobCounter.put(job.value, pastJobCounter.get(job.value) + 1);
                }
            }
        }
        return pastJobCounter;
    }

    /**
     * Generates as string output the key and values of any Hashmap with a String key and Integer value
     * */

    private String generateStringFromSet(HashMap<String, Integer> map) {
        String output = "";
        Iterator<String> itr = map.keySet().iterator();
        while (itr.hasNext()) {
            String curr = itr.next();
            output += curr + ": " + map.get(curr) + "\n";
        }
        return output;
    }
}
