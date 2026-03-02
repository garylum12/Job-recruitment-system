package boundary;

/**
 *
 * @author CHANG JIA JIN
 */
import control.SearchJobControl;
import entity.Search;
import java.util.Scanner;

public class SearchJobUI {

    private final SearchJobControl searchControl;
    private final Scanner scanner = new Scanner(System.in);

    public SearchJobUI(SearchJobControl control) {
        this.searchControl = control;
    }

    public void displaySearchMenu() {
        while (true) {
            System.out.println("\n===== Job Search Menu =====");
            System.out.println("1. Search by keyword");
            System.out.println("2. Show All Jobs");
            System.out.println("3. View Job Search Report");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();
            if (!searchControl.MenuChoice(choice, scanner, this)) {
                break;
            }
        }
    }

    public String searchKeyword(Scanner scanner) {
        System.out.print("Enter keyword to search (or 0 to back): ");
        return scanner.nextLine();
    }

    public void displayJobs(Search[] jobArray) {
        if (jobArray == null || jobArray.length == 0) {
            System.out.println("No matching jobs found.");
            return;
        }

        System.out.println("\n===== Search Results (" + jobArray.length + ") =====\n");
        int count = 1;
        for (Search job : jobArray) {
            if (job == null) {
                continue;
            }

            System.out.println("------------------------------");
            System.out.println("          Job #" + count++);
            System.out.println("------------------------------");
            System.out.println("Title       : " + job.getTitle());
            System.out.println("Description : " + job.getDescription());
            System.out.println("Company     : " + job.getCompanyName());
            System.out.println("Location    : " + job.getLocation());
            System.out.println("Job Type    : " + job.getJobType());
            System.out.println("Salary      : " + job.getSalary());
            System.out.println("------------------------------\n");
        }
    }
}
