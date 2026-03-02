package boundary;
/**
 *
 * @author SENG YUAN JUN
 */
import entity.Job;

public class JobManagementUI {

    public void jobManagementMenu() {
        System.out.println("\n=========== Job Management ===========");
        System.out.println("1. List all jobs");
        System.out.println("2. Add new job posting");
        System.out.println("3. Update existing job posting");
        System.out.println("4. Remove job posting");
        System.out.println("5. Filter job posting");
        System.out.println("6. Generate job report");
        System.out.println("0. Back to Main Menu");
        System.out.println("========================================");
        System.out.print("Enter Choice: ");
    }

    public void displayJob(Job job, int index) {
        System.out.println("\n================ Job " + index + " ================");
        System.out.println("ID          : " + job.getJobId());
        System.out.println("Title       : " + job.getTitle());
        System.out.println("Description : " + job.getDescription());
        System.out.println("Company     : " + job.getCompanyName());
        System.out.println("Location    : " + job.getLocation());
        System.out.println("Type        : " + job.getJobType());
        System.out.printf("Salary      : RM %.2f%n", job.getSalary());
        System.out.println("=======================================");
    }

    public void updateOption() {
        System.out.println("================ Update ================");
        System.out.println("Select what you want to update:");
        System.out.println("1. Title");
        System.out.println("2. Description");
        System.out.println("3. Company Name");
        System.out.println("4. Location");
        System.out.println("5. Job Type");
        System.out.println("6. Salary");
        System.out.println("0. Cancel");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }

    public void filterOption() {
        System.out.println("\n================ Filter ================");
        System.out.println("1. Location");
        System.out.println("2. Company");
        System.out.println("3. Job Type");
        System.out.println("4. Salary Range");
        System.out.println("0. Cancel");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }

    public void displayReportOption() {
        System.out.println("\n=========== Job Report Generation ===========");
        System.out.println("1. Report by Location");
        System.out.println("2. Report by Company");
        System.out.println("3. Report by Job Type");
        System.out.println("4. Report by Salary Range");
        System.out.println("5. Report All Jobs");
        System.out.println("0. Back to Job Management Menu");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }
}
