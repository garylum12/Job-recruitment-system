================================
Internship Application Program - ReadMe
================================

Author: Chang Jia Jin, Lum Gang Li, Seng Yuan Jun

=====================
System Entry Point
=====================
Main class: main.InternshipApplicationProgram.java

Users select from the following modules:
1. Job Management Module  
2. Applicant Management Module  
3. Search Module  
0. Exit system

============================
1. Job Management Module
============================
Controller: control.JobManage.java  
Boundary: boundary.JobManagementUI.java  

Features:
- List all jobs  
- Add new job postings  
- Update existing job postings  
- Remove job postings  
- Filter job postings by location, company, job type, or salary range  
- Generate job summary report  

Data stored in:
- entity.Job.java  
- adt.LinkedList<Job>  

===================================
2. Applicant Management Module
===================================
Controller: control.ApplicantManagementControl.java  
Boundary: boundary.ApplicantManagementUI.java  

Features:
- List all applicants  
- Add new applicants  
- Search applicants by location  
- Search applicants by skill  
- Update applicant details  
- Remove applicants  
- Generate applicant summary reports (by location or by skill)  

Data stored in:
- entity.Applicant.java  
- adt.LinkedList<Applicant>  

===========================
3. Search Module
===========================
Controller: control.SearchJobControl.java  
Boundary: boundary.SearchJobUI.java  

Features:
- Search jobs by keyword with relevance ranking  
- Display all jobs  
- View job search frequency report  

Data stored in:
- entity.Search.java  
- entity.SearchResult.java  
- entity.SearchEntry.java  
- adt.SortedArrayList<Search>  

=====================
Utilities
=====================
- utility.MessageUI.java (centralized messages and prompts)

=====================
Data Management
=====================
- All data held in memory using ADTs (LinkedList and SortedArrayList)  
- data resets when the program exits

=====================
How to Run
=====================
1. Open in NetBeans. (recommend NetBeans 25)
2. Run main.InternshipApplicationProgram.java
