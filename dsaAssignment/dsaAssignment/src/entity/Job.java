package entity;
/**
 *
 * @author SENG YUAN JUN
 */
public class Job implements Comparable<Job>{

    private String jobId;
    private String title;
    private String description;
    private String companyName;
    private String location;
    private String jobType;
    private double salary;

    public Job(String jobId, String title, String description, String companyName, String location, String jobType, double salary) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.companyName = companyName;
        this.location = location;
        this.jobType = jobType;
        this.salary = salary;
    }


    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    @Override
    public int compareTo(Job other) {
        return this.title.compareToIgnoreCase(other.title);
    }
}
