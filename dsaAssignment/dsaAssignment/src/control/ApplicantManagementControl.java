package control;

/**
 *
 * @author LUM GANG LI
 */
import adt.LinkedList;
import adt.LinkedListInterface;
import boundary.ApplicantManagementUI;
import entity.Applicant;
import utility.MessageUI;

import java.util.Scanner;
import java.util.InputMismatchException;

public class ApplicantManagementControl {

    private final LinkedListInterface<Applicant> applicants;
    private final ApplicantManagementUI ui;
    private final MessageUI messageUI;
    private final Scanner scanner;
    private int nextApplicantNumber;

    public ApplicantManagementControl(boolean loadSampleData) {
        applicants = new LinkedList<>();
        ui = new ApplicantManagementUI();
        messageUI = new MessageUI();
        scanner = new Scanner(System.in);
        nextApplicantNumber = 0;

        if (loadSampleData) {
            initializeSampleData();
            nextApplicantNumber = applicants.getNumberOfEntries();
        }
    }

    public void run() {
        boolean quit = false;
        while (!quit) {
            ui.displayMenu();
            int choice = getNextInt();
            switch (choice) {
                case 1 ->
                    listAllApplicants();
                case 2 ->
                    addApplicant();
                case 3 ->
                    searchByLocation();
                case 4 ->
                    searchBySkill();
                case 5 ->
                    updateApplicant();
                case 6 ->
                    removeApplicant();
                case 7 ->
                    generateReport();
                case 0 -> {
                    messageUI.displayExitMessage();
                    quit = true;
                }
                default ->
                    messageUI.displayInvalidChoiceMessage();
            }
        }
    }

    private void listAllApplicants() {
        if (applicants.isEmpty()) {
            System.out.println("No applicants available.");
        } else {
            for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
                ui.displayApplicant(applicants.getEntry(i), i);
            }
        }
    }

    private void addApplicant() {
        System.out.println("\n=== Add New Applicant ===");
        Applicant newApp = getApplicantDetails();
        applicants.add(newApp);
        System.out.println("Applicant added successfully!");
    }

    private Applicant getApplicantDetails() {
        String id = generateApplicantId();
        System.out.println("Assigned Applicant ID: " + id);

        System.out.print("Enter Name (letters only): ");
        String name = inputValidated("[A-Za-z ]+", "Invalid name.", "Enter Name (letters only): ");

        System.out.print("Enter Email: ");
        String email = inputValidated(
                "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", "Invalid email format.", "Enter Email: ");

        System.out.print("Enter Location (letters only): ");
        String location = inputValidated("[A-Za-z ]+", "Invalid location.", "Enter Location: ");

        var skillsList = new LinkedList<String>();
        System.out.print("How many skills? ");
        int skillCount = getNextInt();
        for (int i = 1; i <= skillCount; i++) {
            System.out.print("Enter Skill " + i + ": ");
            String skill = inputValidated("[A-Za-z ]+", "Invalid skill.", "Enter Skill: ");
            skillsList.add(skill);
        }

        LinkedListInterface<String> jobTypesList = new LinkedList<>();
        int typeChoice;
        do {
            System.out.println("Select Desired Job Type:");
            System.out.println("1. Fulltime");
            System.out.println("2. Parttime");
            System.out.print("Enter choice (1 or 2): ");
            typeChoice = getNextInt();
            if (typeChoice != 1 && typeChoice != 2) {
                messageUI.displayInvalidChoiceMessage();
            }
        } while (typeChoice != 1 && typeChoice != 2);

        if (typeChoice == 1) {
            jobTypesList.add("Fulltime");
        } else {
            jobTypesList.add("Parttime");
        }

        return new Applicant(id, name, email, location, skillsList, jobTypesList);
    }

    private void searchByLocation() {
        System.out.print("Enter Location to search: ");
        String loc = inputValidated("[A-Za-z ]+", "Invalid location.", "Enter Location: ");
        var results = filterByLocation(loc);
        displaySearchResults(results, "Applicants in " + loc);
    }

    private void searchBySkill() {
        System.out.print("Enter Skill to search: ");
        String skill = inputValidated("[A-Za-z ]+", "Invalid skill.", "Enter Skill: ");
        var results = filterBySkill(skill);
        displaySearchResults(results, "Applicants with skill: " + skill);
    }

    private void displaySearchResults(
            LinkedListInterface<Applicant> results,
            String title
    ) {
        System.out.println("\n=== " + title + " ===");
        if (results.isEmpty()) {
            System.out.println("No applicants found.");
        } else {
            for (int i = 1; i <= results.getNumberOfEntries(); i++) {
                ui.displayApplicant(results.getEntry(i), i);
            }
        }
    }

    private void updateApplicant() {
        System.out.print("Enter Applicant ID to update (A###): ");
        String id = inputApplicantId();
        Applicant a = getApplicant(id);
        if (a == null) {
            System.out.println("Applicant not found!");
            return;
        }
        ui.displayApplicant(a, 0);

        boolean editing = true;
        while (editing) {
            ui.displayUpdateOptions();
            int opt = getNextInt();
            switch (opt) {
                case 1 -> {
                    System.out.print("New Name: ");
                    a.setName(inputValidated("[A-Za-z ]+", "Invalid name.", "Enter Name: "));
                }
                case 2 -> {
                    System.out.print("New Email: ");
                    a.setEmail(inputValidated(
                            "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", "Invalid email.", "Enter Email: "));
                }
                case 3 -> {
                    System.out.print("New Location: ");
                    a.setLocation(inputValidated(
                            "[A-Za-z ]+", "Invalid location.", "Enter Location: "));
                }
                case 4 -> {
                    var newSkills = new LinkedList<String>();
                    System.out.print("How many skills? ");
                    int cnt = getNextInt();
                    for (int i = 1; i <= cnt; i++) {
                        System.out.print("Skill " + i + ": ");
                        String s = inputValidated(
                                "[A-Za-z ]+", "Invalid skill.", "Enter Skill: ");
                        newSkills.add(s);
                    }
                    a.setSkillsList(newSkills);
                }
                case 5 -> {
                    var newJobTypes = new LinkedList<String>();
                    int jtChoice;
                    do {
                        System.out.println("Select Desired Job Type:");
                        System.out.println("1. Fulltime");
                        System.out.println("2. Parttime");
                        System.out.print("Enter choice (1 or 2): ");
                        jtChoice = getNextInt();
                        if (jtChoice != 1 && jtChoice != 2) {
                            messageUI.displayInvalidChoiceMessage();
                        }
                    } while (jtChoice != 1 && jtChoice != 2);

                    if (jtChoice == 1) {
                        newJobTypes.add("Fulltime");
                    } else {
                        newJobTypes.add("Parttime");
                    }

                    a.setDesiredJobTypesList(newJobTypes);
                }
                case 0 ->
                    editing = false;
                default ->
                    messageUI.displayInvalidChoiceMessage();
            }
        }
        System.out.println("Applicant updated successfully!");
    }

    private void removeApplicant() {
        System.out.print("Enter Applicant ID to remove: ");
        String id = inputApplicantId();
        int idx = findApplicantIndex(id);
        if (idx == -1) {
            System.out.println("Applicant not found.");
            return;
        }
        System.out.print("Confirm remove \"" + id + "\"? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            applicants.remove(idx);
            System.out.println("Removed: " + id);
            renumberApplicants();
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    private LinkedListInterface<Applicant> filterByLocation(String loc) {
        var res = new LinkedList<Applicant>();
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            if (applicants.getEntry(i).getLocation().equalsIgnoreCase(loc)) {
                res.add(applicants.getEntry(i));
            }
        }
        return res;
    }

    private LinkedListInterface<Applicant> filterBySkill(String skill) {
        var res = new LinkedList<Applicant>();
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            var skills = applicants.getEntry(i).getSkillsList();
            for (int j = 1; j <= skills.getNumberOfEntries(); j++) {
                if (skills.getEntry(j).equalsIgnoreCase(skill)) {
                    res.add(applicants.getEntry(i));
                    break;
                }
            }
        }
        return res;
    }

    private void initializeSampleData() {
        var skills1 = new LinkedList<String>();
        skills1.add("Java");
        skills1.add("SQL");
        var jt1 = new LinkedList<String>();
        jt1.add("Fulltime");
        applicants.add(new Applicant(
                "A001", "John Doe", "john.doe@example.com",
                "Kuala Lumpur", skills1, jt1));

        var skills2 = new LinkedList<String>();
        skills2.add("Python");
        skills2.add("Django");
        var jt2 = new LinkedList<String>();
        jt2.add("Parttime");
        applicants.add(new Applicant(
                "A002", "Jane Smith", "jane.smith@example.com",
                "Penang", skills2, jt2));
    }

    private String generateApplicantId() {
        nextApplicantNumber++;
        return String.format("A%03d", nextApplicantNumber);
    }

    private String inputApplicantId() {
        String id;
        while (true) {
            id = scanner.nextLine().trim();
            if (id.matches("A\\d{3}")) {
                return id;
            }
            System.out.print("Invalid ID. Re-enter (A###): ");
        }
    }

    private int findApplicantIndex(String id) {
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            if (applicants.getEntry(i)
                    .getApplicantId()
                    .equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    private Applicant getApplicant(String id) {
        int idx = findApplicantIndex(id);
        return (idx == -1 ? null : applicants.getEntry(idx));
    }

    private void renumberApplicants() {
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            applicants.getEntry(i).setApplicantId(
                    String.format("A%03d", i));
        }
        nextApplicantNumber = applicants.getNumberOfEntries();
    }

    private int getNextInt() {
        while (true) {
            try {
                int i = scanner.nextInt();
                scanner.nextLine();
                return i;
            } catch (InputMismatchException e) {
                messageUI.displayInvalidChoiceMessage();
                scanner.nextLine();
            }
        }
    }

    private String inputValidated(
            String pattern,
            String error,
            String prompt
    ) {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.matches(pattern)) {
                return input;
            }
            System.out.println(error);
            System.out.print(prompt);
        }
    }

    private void generateReport() {
        boolean back = false;
        while (!back) {
            ui.displayReportMenu();
            int choice = getNextInt();
            switch (choice) {
                case 1 ->
                    reportAllApplicants();
                case 2 ->
                    reportByLocation();
                case 3 ->
                    reportBySkill();
                case 0 ->
                    back = true;
                default ->
                    messageUI.displayInvalidChoiceMessage();
            }
        }
    }

    private void reportAllApplicants() {
        ui.displayReportHeader("Applicant Summary Report");
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            ui.displayReportRow(applicants.getEntry(i));
        }
        ui.displayReportFooter();
    }

    private void reportByLocation() {
        System.out.print("Enter location: ");
        String loc = scanner.nextLine();
        var res = filterByLocation(loc);
        ui.displayReportHeader("Applicants in \"" + loc + "\"");
        if (res.isEmpty()) {
            System.out.println("| No applicants found |");
        } else {
            for (int i = 1; i <= res.getNumberOfEntries(); i++) {
                ui.displayReportRow(res.getEntry(i));
            }
        }
        ui.displayReportFooter();
    }

    private void reportBySkill() {
        System.out.print("Enter skill: ");
        String skill = scanner.nextLine();
        var res = filterBySkill(skill);
        ui.displayReportHeader("Applicants with skill \"" + skill + "\"");
        if (res.isEmpty()) {
            System.out.println("| No applicants found |");
        } else {
            for (int i = 1; i <= res.getNumberOfEntries(); i++) {
                ui.displayReportRow(res.getEntry(i));
            }
        }
        ui.displayReportFooter();
    }
}
