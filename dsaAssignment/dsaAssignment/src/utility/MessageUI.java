package utility;
/**
 *
 * @author LUM GANG LI
 */
import entity.Job;

public class MessageUI {

    public void displayExitMessage() {
        System.out.println("Exiting system. Goodbye!");
    }

    public void displayInvalidChoiceMessage() {
        System.out.println("Invalid Input. Please try again.");
    }

    public void jobUpdateSuccessfullMessage() {
        System.out.println("Job updated successfully.");
    }

    public void jobNotFoundMessage() {
        System.out.println("Job not found.");
    }

    public void jobRemoveSuccessfully() {
        System.out.println("Job remove successfully.");
    }

    public void displayNoJobPostingsMessage() {
        System.out.println("No job postings available.");
    }

    public void displayJobUpdatedMessage() {
        System.out.println("Job updated.");
    }

    public void displayJobNotFoundMessage() {
        System.out.println("Job not found.");
    }

    public void displayRemoveCancelledMessage() {
        System.out.println("Remove cancelled.");
    }

    public void displayJobIdNotFoundMessage() {
        System.out.println("Job ID not found.");
    }

    public void displayRemovedJobMessage(Job job) {
        System.out.println("Removed: " + job.getJobId() + " | " + job.getTitle() + " | " + job.getCompanyName());
    }

    public void displayFilterCancelledMessage() {
        System.out.println("Filter cancelled.");
    }

    public void displayNoJobsFoundForFilter(String filterType, String filterValue) {
        System.out.println("No jobs found for " + filterType + ": " + filterValue);
    }

}
