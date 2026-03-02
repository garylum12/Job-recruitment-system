package entity;

/**
 *
 * @author LUM GANG LI
 */


import adt.LinkedListInterface;

public class Applicant {
    private String applicantId;
    private String name;
    private String email;
    private String location;
    private LinkedListInterface<String> skillsList;
    private LinkedListInterface<String> desiredJobTypesList;

    public Applicant(String applicantId,
                     String name,
                     String email,
                     String location,
                     LinkedListInterface<String> skillsList,
                     LinkedListInterface<String> desiredJobTypesList) {
        this.applicantId = applicantId;
        this.name = name;
        this.email = email;
        this.location = location;
        this.skillsList = skillsList;
        this.desiredJobTypesList = desiredJobTypesList;
    }

    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String applicantId) { this.applicantId = applicantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LinkedListInterface<String> getSkillsList() { return skillsList; }
    public void setSkillsList(LinkedListInterface<String> skillsList) {
        this.skillsList = skillsList;
    }

    public LinkedListInterface<String> getDesiredJobTypesList() {
        return desiredJobTypesList;
    }
    public void setDesiredJobTypesList(LinkedListInterface<String> desiredJobTypesList) {
        this.desiredJobTypesList = desiredJobTypesList;
    }
}
