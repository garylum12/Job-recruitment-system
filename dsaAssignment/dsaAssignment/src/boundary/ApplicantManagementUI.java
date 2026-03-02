package boundary;

/**
 *
 * @author LUM GANG LI
 */


import adt.LinkedListInterface;
import entity.Applicant;

public class ApplicantManagementUI {

    public void displayMenu() {
        System.out.println("\n=========== Applicant Management ===========");
        System.out.println("1. List All Applicants");
        System.out.println("2. Add Applicant");
        System.out.println("3. Search by Location");
        System.out.println("4. Search by Skill");
        System.out.println("5. Update Applicant");
        System.out.println("6. Remove Applicant");
        System.out.println("7. Generate Report");
        System.out.println("0. Exit");
        System.out.print("Enter Choice: ");
    }

    public void displayApplicant(Applicant a, int idx) {
        System.out.println("\n================ Applicant " + idx + " ================");
        System.out.println("ID       : " + a.getApplicantId());
        System.out.println("Name     : " + a.getName());
        System.out.println("Email    : " + a.getEmail());
        System.out.println("Location : " + a.getLocation());
        System.out.println("Skills   : " + joinList(a.getSkillsList()));
        System.out.println("JobTypes : " + joinList(a.getDesiredJobTypesList()));
        System.out.println("============================================");
    }

    public void displayUpdateOptions() {
        System.out.println("\n=========== Update Applicant ===========");
        System.out.println("1. Name");
        System.out.println("2. Email");
        System.out.println("3. Location");
        System.out.println("4. Skills");
        System.out.println("5. Desired Job Types");
        System.out.println("0. Cancel");
        System.out.print("Enter choice: ");
    }

    public void displayReportMenu() {
        System.out.println("\n======== Applicant Report Generation ========");
        System.out.println("1. Report All Applicants");
        System.out.println("2. Report by Location");
        System.out.println("3. Report by Skill");
        System.out.println("0. Back to Menu");
        System.out.print("Enter choice: ");
    }

    public void displayReportHeader(String title) {
        System.out.println("\n| " + title + " |");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("| ID   | Name           | Email               | Location | Skills | Job Types |");
        System.out.println("-----------------------------------------------------------------");
    }

    public void displayReportRow(Applicant a) {
        System.out.printf("| %-4s | %-14s | %-19s | %-8s | %-6s | %-9s |\n",
            a.getApplicantId(),
            a.getName(),
            a.getEmail(),
            a.getLocation(),
            joinList(a.getSkillsList()),
            joinList(a.getDesiredJobTypesList())
        );
    }

    public void displayReportFooter() {
        System.out.println("-----------------------------------------------------------------");
    }

    private String joinList(LinkedListInterface<String> list) {
        StringBuilder sb = new StringBuilder();
        int n = list.getNumberOfEntries();
        for (int i = 1; i <= n; i++) {
            sb.append(list.getEntry(i));
            if (i < n) sb.append(", ");
        }
        return sb.toString();
    }
}
