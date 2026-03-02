package control;

import adt.LinkedListInterface;
import adt.SortedArrayList;
import boundary.MainMenuUI;
import boundary.SearchJobUI;
import entity.Job;
import entity.Search;
import java.util.InputMismatchException;
import java.util.Scanner;
import utility.MessageUI;

public class MainMenu {

    private SortedArrayList<Search> sortedJobList;
    private final Scanner scanner = new Scanner(System.in);
    private final MainMenuUI mmUI = new MainMenuUI();
    private final JobManage jm = new JobManage();
    private final MessageUI messageUI = new MessageUI();
    private SearchJobControl search;
    private SearchJobUI searchUI;
    private final ApplicantManagementControl am = new ApplicantManagementControl(true);

    public void run() {
        boolean quit = false;
        while (!quit) {
            mmUI.displayMainMenu();
            int choice = -1;
            boolean validInput = false;

            while (!validInput) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    validInput = true;
                } catch (InputMismatchException e) {
                    messageUI.displayInvalidChoiceMessage();
                    mmUI.displayMainMenu();
                    scanner.nextLine();
                }
            }

            switch (choice) {
                case 1 -> jm.run();

                case 2 -> am.run();

                case 3 -> {
                    if (searchUI != null) {
                        search.run();
                    } else {
                        System.out.println("Please enter jobs manage first");
                    }
                }

                case 0 -> {
                    messageUI.displayExitMessage();
                    quit = true;
                }

                default -> messageUI.displayInvalidChoiceMessage();
            }
            LinkedListInterface<Job> allJobs = jm.getAllJobs();
                    sortedJobList = new SortedArrayList<>();

                    for (int i = 1; i <= allJobs.getNumberOfEntries(); i++) {
                        Job job = allJobs.getEntry(i);
                        Search s = new Search(
                            job.getTitle(),
                            job.getDescription(),
                            job.getCompanyName(),
                            job.getLocation(),
                            job.getJobType(),
                            job.getSalary()
                        );
                        sortedJobList.add(s);
                    }

                    search = new SearchJobControl(sortedJobList, allJobs);
                    searchUI = new SearchJobUI(search);
        }
    }
}
