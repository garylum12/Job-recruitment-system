package control;

/**
 *
 * @author CHANG JIA JIN
 */
import adt.LinkedListInterface;
import adt.SortedArrayList;
import boundary.SearchJobUI;
import entity.Job;
import entity.Search;
import entity.SearchResult;
import entity.SearchEntry;

import java.util.Scanner;

public class SearchJobControl {

    public Search job;
    private final SortedArrayList<Search> searchList;
    private final SearchEntry[] trackedJobs = new SearchEntry[100];
    private int trackedCount = 0;
    private final LinkedListInterface<Job> jobList;

    public SearchJobControl(SortedArrayList<Search> searchList, LinkedListInterface<Job> jobList) {
        this.searchList = searchList;
        this.jobList = jobList;

    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        SearchJobUI ui = new SearchJobUI(this);

        while (true) {
            ui.displaySearchMenu();
            String choice = scanner.nextLine().trim();

            if (choice.equals("0")) {
                System.out.println("Exiting search...");
                break;
            }

            MenuChoice(choice, scanner, ui);
        }

        new MainMenu().run();
    }

    public boolean MenuChoice(String choice, Scanner scanner, SearchJobUI ui) {
        switch (choice) {
            case "1" -> {
                Search[] results = searchByKeyword(scanner, ui);
                ui.displayJobs(results);
                return true;
            }
            case "2" -> {
                ui.displayJobs(searchList.getAllEntries());
                return true;
            }
            case "3" -> {
                displaySearchReport(generateSearchReportJobs(), this);
                return true;
            }
            case "0" -> {
                MainMenu mm = new MainMenu();
                mm.run();
                return true;
            }
            default -> {
                System.out.println("Invalid choice.");
                return true;
            }
        }
    }

    private Search[] searchByKeyword(Scanner scanner, SearchJobUI ui) {
        String keyword = ui.searchKeyword(scanner);
        if (keyword.equals("0")) {
            System.out.println("Back to menu.");
            return new Search[0];
        }
        return searchJobs(keyword);
    }

    public Search[] searchJobs(String keyword) {
        SearchResult[] matches = new SearchResult[jobList.getNumberOfEntries()];
        int count = 0;
        String k = keyword.toLowerCase();

        for (Search j : searchList.getAllEntries()) {
            int rank = calculateRelevanceRank(j, k);
            if (rank > 0) {
                matches[count++] = new SearchResult(j, rank);
                updateSearchFrequency(j);
            }
        }

        Search[] output = new Search[count];
        for (int i = 0; i < count; i++) {
            output[i] = matches[i].getJob();
        }
        return output;
    }

    private int calculateRelevanceRank(Search job, String keyword) {
        int rank = 0;

        if (job.getTitle().toLowerCase().contains(keyword)) {
            rank += 3;
        }
        if (job.getDescription().toLowerCase().contains(keyword)) {
            rank += 2;
        }
        if (job.getCompanyName().toLowerCase().contains(keyword)) {
            rank += 1;
        }
        if (job.getLocation().toLowerCase().contains(keyword)) {
            rank += 1;
        }
        if (job.getJobType().toLowerCase().contains(keyword)) {
            rank += 1;
        }
        if (String.valueOf(job.getSalary()).contains(keyword)) {
            rank += 1;
        }

        if (rank == 0) {
            if (searchList.fuzzySearch(keyword, job.getTitle().toLowerCase()) <= 5) {
                rank += 3;
            } else if (searchList.fuzzySearch(keyword, job.getDescription().toLowerCase()) <= 2) {
                rank += 2;
            } else if (searchList.fuzzySearch(keyword, job.getCompanyName().toLowerCase()) <= 2) {
                rank += 1;
            } else if (searchList.fuzzySearch(keyword, job.getLocation().toLowerCase()) <= 2) {
                rank += 1;
            } else if (searchList.fuzzySearch(keyword, job.getJobType().toLowerCase()) <= 2) {
                rank += 1;
            } else if (searchList.fuzzySearch(keyword, String.valueOf(job.getSalary())) <= 2) {
                rank += 1;
            }
        }

        return rank;
    }

    private void updateSearchFrequency(Search job) {
        for (int i = 0; i < trackedCount; i++) {
            if (trackedJobs[i].job.equals(job)) {
                trackedJobs[i].count++;
                return;
            }
        }
        trackedJobs[trackedCount++] = new SearchEntry(job, 1);
    }

    public Search[] generateSearchReportJobs() {
        Search[] report = new Search[trackedCount];
        int[] frequency = new int[trackedCount];

        for (int i = 0; i < trackedCount; i++) {
            report[i] = trackedJobs[i].job;
            frequency[i] = trackedJobs[i].count;
        }

        for (int i = 0; i < trackedCount - 1; i++) {
            for (int j = 0; j < trackedCount - i - 1; j++) {
                if (frequency[j] < frequency[j + 1]) {
                    int tempFreq = frequency[j];
                    frequency[j] = frequency[j + 1];
                    frequency[j + 1] = tempFreq;

                    Search tempJob = report[j];
                    report[j] = report[j + 1];
                    report[j + 1] = tempJob;
                }
            }
        }

        return report;
    }

    public int getSearchFrequency(Search job) {
        for (int i = 0; i < trackedCount; i++) {
            if (trackedJobs[i].job.equals(job)) {
                return trackedJobs[i].count;
            }
        }
        return 0;
    }

    public void displaySearchReport(Search[] jobs, SearchJobControl control) {
        System.out.println();
        System.out.println("                                 Job Search Report");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-15s | %-10s | %-10s | %-13s |\n", "Title", "Company", "Type", "Salary", "Search Count");
        System.out.println("---------------------------------------------------------------------------------------------");

        if (jobs == null || jobs.length == 0) {
            System.out.printf("| %-83s |\n", "No jobs have been searched yet.");
        } else {
            for (Search j : jobs) {
                if (j == null) {
                    continue;
                }
                int count = control.getSearchFrequency(j);
                System.out.printf("| %-20s | %-15s | %-10s | RM %-7.2f | %-13d |\n",
                        j.getTitle(),
                        j.getCompanyName(),
                        j.getJobType(),
                        j.getSalary(),
                        count);
            }
        }

        System.out.println("---------------------------------------------------------------------------------------------");
    }

}
