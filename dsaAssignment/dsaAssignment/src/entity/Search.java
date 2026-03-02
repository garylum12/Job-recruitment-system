package entity;
/**
 *
 * @author CHANG JIA JIN
 */
public class Search implements Comparable<Search> {

    private final String title;
    private final String description;
    private final String companyName;
    private final String location;
    private final String jobType;
    private final double salary;

    public Search(String title, String description, String companyName, String location, String jobType, double salary) {
        this.title = title;
        this.description = description;
        this.companyName = companyName;
        this.location = location;
        this.jobType = jobType;
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    public String getJobType() {
        return jobType;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public int compareTo(Search other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public String toString() {
        return "Job1{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", companyName='" + companyName + '\'' +
                ", location='" + location + '\'' +
                ", jobType='" + jobType + '\'' +
                ", salary=" + salary +
                '}';
    }
}
