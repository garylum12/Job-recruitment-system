package control;
/**
 *
 * @author SENG YUAN JUN
 */
import adt.LinkedList;
import adt.LinkedListInterface;

import boundary.JobManagementUI;
import entity.Job;
import utility.MessageUI;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JobManage {
    
    private LinkedListInterface<Job> jobList;
    private JobManagementUI ui;
    private Scanner scanner = new Scanner(System.in);
    private MessageUI messageUI = new MessageUI();
    private boolean dataInitialized = false;

    
    public JobManage() {
        jobList = new LinkedList<>();
        ui = new JobManagementUI();
    }

    public void run() {
        if(!dataInitialized){
            
        jobList.add(new Job("J001", "Software Developer", "Develop and maintain Java applications.", "TechSoft", "Kuala Lumpur", "Full-Time", 1500));
        jobList.add(new Job("J002", "App Developer", "Develop and maintain mobile applications.", "MobileApp", "Penang", "Full-Time", 1000));
        jobList.add(new Job("J003", "Web Developer", "Develop and maintain websites.", "WebTech", "Johor Bahru", "Full-Time", 1200));
        jobList.add(new Job("J004", "System Analyst", "Analyze and design systems for clients.", "SysTech", "Kuala Lumpur", "Part-Time", 1300));
        jobList.add(new Job("J005", "Database Administrator", "Manage and maintain databases.", "DBSolutions", "Penang", "Full-Time", 1600));
        dataInitialized = true;
        }
        boolean quit = false;
        while (!quit) {
            ui.jobManagementMenu();
            int choice = readInt();
            switch (choice) {
                case 1 -> listAllJobs();
                case 2 -> handleAddJob();
                case 3 -> handleUpdateJob();
                case 4 -> handleRemoveJob();
                case 5 -> handleFilterJob();
                case 6 -> generateJobReport();
                case 0 -> quit = true;
                default -> messageUI.displayInvalidChoiceMessage();
            }
        }
    }

    private int readInt() {
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

    public void addJob(Job job) {
        jobList.add(job);
        System.out.println("Job added successfully.");
    }

    public void listAllJobs() {
        if (jobList.isEmpty()) {
            messageUI.displayNoJobPostingsMessage();
        } else {
            for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
                ui.displayJob(jobList.getEntry(i), i);
            }
        }
    }

    private void handleAddJob() {
        boolean addMore = true;
        while (addMore) {
            Job job = getJobDetails();
            addJob(job);
            System.out.print("Add another? (Y/N): ");
            String c = scanner.nextLine();
            if (!c.equalsIgnoreCase("Y")) {
                addMore = false;
            }
        }
    }

    private void handleUpdateJob() {
        System.out.print("Enter Job ID to update: ");
        String id = scanner.nextLine();
        Job job = findJobById(id);
        if (job == null) {
            messageUI.displayJobNotFoundMessage();
            return;
        }
        boolean editing = true;
        while (editing) {
            ui.updateOption();
            int opt = readInt();
            switch (opt) {
                case 1 -> {
                    System.out.print("New Title: ");
                    job.setTitle(scanner.nextLine());
                }
                case 2 -> {
                    System.out.print("New Description: ");
                    job.setDescription(scanner.nextLine());
                }
                case 3 -> {
                    System.out.print("New Company: ");
                    job.setCompanyName(scanner.nextLine());
                }
                case 4 -> {
                    System.out.print("New Location: ");
                    job.setLocation(scanner.nextLine());
                }
                case 5 -> {
                    System.out.print("New Job Type: ");
                    job.setJobType(scanner.nextLine());
                }
                case 6 -> {
                    System.out.print("New Salary: RM ");
                    if (scanner.hasNextDouble()) {
                        job.setSalary(scanner.nextDouble());
                        scanner.nextLine();
                    } else {
                        messageUI.displayInvalidChoiceMessage();
                        scanner.nextLine();
                    }
                }
                case 0 -> editing = false;
                default -> messageUI.displayInvalidChoiceMessage();
            }
        }
        messageUI.displayJobUpdatedMessage();
    }

    private void handleRemoveJob() {
        if (jobList.isEmpty()) {
            messageUI.displayNoJobPostingsMessage();
            return;
        }
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            ui.displayJob(jobList.getEntry(i), i);
        }
        while (true) {
            System.out.print("Enter Job ID to remove (or 0 to cancel): ");
            String id = scanner.nextLine();
            if (id.equals("0")) {
                messageUI.displayRemoveCancelledMessage();
                return;
            }
            Job toRemove = findJobById(id);
            if (toRemove == null) {
                messageUI.displayJobIdNotFoundMessage();
            } else {
                System.out.print("Confirm remove \"" + id + "\"? (Y/N): ");
                String c = scanner.nextLine();
                if (c.equalsIgnoreCase("Y")) {
                    for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
                        if (jobList.getEntry(i).getJobId().equalsIgnoreCase(id)) {
                            jobList.remove(i);
                            messageUI.displayRemovedJobMessage(toRemove);
                            updateJobIds();
                            return;
                        }
                    }
                } else {
                    messageUI.displayRemoveCancelledMessage();
                    return;
                }
            }
        }
    }

    private void handleFilterJob() {
        ui.filterOption();
        int choice = readInt();
        switch (choice) {
            case 1 -> {
                System.out.print("Location (or 0 to cancel): ");
                filterByLocation(scanner.nextLine());
            }
            case 2 -> {
                System.out.print("Company  (or 0 to cancel): ");
                filterByCompany(scanner.nextLine());
            }
            case 3 -> {
                System.out.print("Job Type (or 0 to cancel): ");
                filterByType(scanner.nextLine());
            }
            case 4 -> {
                double min = readSalary("Min Salary");
                double max = readSalary("Max Salary");
                filterBySalary(min, max);
            }
            case 0 -> messageUI.displayFilterCancelledMessage();
        }
    }

    private Job findJobById(String id) {
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            if (jobList.getEntry(i).getJobId().equalsIgnoreCase(id)) {
                return jobList.getEntry(i);
            }
        }
        return null;
    }

    private void filterByLocation(String loc) {
        if (loc.equals("0")) {
            messageUI.displayFilterCancelledMessage();
            return;
        }
        boolean found = false;
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job j = jobList.getEntry(i);
            if (j.getLocation().equalsIgnoreCase(loc)) {
                ui.displayJob(j, i);
                found = true;
            }
        }
        if (!found) {
            messageUI.displayNoJobsFoundForFilter("location", loc);
        }
    }

    private void filterByCompany(String comp) {
        if (comp.equals("0")) {
            messageUI.displayFilterCancelledMessage();
            return;
        }
        boolean found = false;
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job j = jobList.getEntry(i);
            if (j.getCompanyName().equalsIgnoreCase(comp)) {
                ui.displayJob(j, i);
                found = true;
            }
        }
        if (!found) {
            messageUI.displayNoJobsFoundForFilter("company", comp);
        }
    }

    private void filterByType(String type) {
        if (type.equals("0")) {
            messageUI.displayFilterCancelledMessage();
            return;
        }
        boolean found = false;
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job j = jobList.getEntry(i);
            if (j.getJobType().equalsIgnoreCase(type)) {
                ui.displayJob(j, i);
                found = true;
            }
        }
        if (!found) {
            messageUI.displayNoJobsFoundForFilter("job type", type);
        }
    }

    private void filterBySalary(double min, double max) {
        boolean found = false;
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job j = jobList.getEntry(i);
            if (j.getSalary() >= min && j.getSalary() <= max) {
                ui.displayJob(j, i);
                found = true;
            }
        }
        if (!found) {
            messageUI.displayNoJobsFoundForFilter("salary range", "RM " + min + " - RM " + max);
        }
    }

    private Job getJobDetails() {
        String id = generateJobId();
        System.out.println("Generated Job ID: " + id);
        System.out.print("Title: ");
        String t = scanner.nextLine();
        System.out.print("Description: ");
        String d = scanner.nextLine();
        System.out.print("Company: ");
        String c = scanner.nextLine();
        System.out.print("Location: ");
        String l = scanner.nextLine();
        System.out.print("Job Type: ");
        String jt = scanner.nextLine();
        double s = readSalary("Salary");
        return new Job(id, t, d, c, l, jt, s);
    }

    private double readSalary(String p) {
        while (true) {
            System.out.print(p + " (RM): ");
            try {
                double v = scanner.nextDouble();
                scanner.nextLine();
                return v;
            } catch (InputMismatchException e) {
                messageUI.displayInvalidChoiceMessage();
                scanner.nextLine();
            }
        }
    }

    private String generateJobId() {
        return String.format("J%03d", jobList.getNumberOfEntries() + 1);
    }

    private void updateJobIds() {
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            jobList.getEntry(i).setJobId(String.format("J%03d", i));
        }
    }

    public void generateJobReport() {
        System.out.println("\n|                                Job Summary Report                                |");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("| Job ID | Title                 | Company        | Type      | Salary    |");
        System.out.println("-----------------------------------------------------------------------------------");
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job j = jobList.getEntry(i);
            System.out.printf("| %-6s | %-20s | %-13s | %-9s | RM %-7.2f |\n",
                    j.getJobId(), j.getTitle(), j.getCompanyName(), j.getJobType(), j.getSalary());
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }
    
    
    public LinkedListInterface<Job> getAllJobs(){
        return  jobList; 
    }

    public static void main(String[] args) {
        new JobManage().run();
    }
}