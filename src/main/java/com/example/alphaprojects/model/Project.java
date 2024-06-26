package com.example.alphaprojects.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Project {

private int projectID;
private int projectManagerID;
private String projectManagerName;
private String projectName;
private String projectDescription;
private int projectTimeEstimate;
private int projectDedicatedHours;
@DateTimeFormat(pattern = "yyyy-MM-dd")
private LocalDate projectDeadline;
private String projectStatus;

//Ingen parametre
public Project(){
}

//Alle parametre
    public Project(int projectID, int projectManagerID, String projectManagerName, String projectName, String projectDescription, int projectTimeEstimate, int projectDedicatedHours, LocalDate projectDeadline, String projectStatus) {
        this.projectID = projectID;
        this.projectManagerID = projectManagerID;
        this.projectManagerName = projectManagerName;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectTimeEstimate = projectTimeEstimate;
        this.projectDedicatedHours = projectDedicatedHours;
        this.projectDeadline = projectDeadline;
        this.projectStatus = projectStatus;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getProjectManagerID() {
        return projectManagerID;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public void setProjectManagerID(int projectManagerID) {
        this.projectManagerID = projectManagerID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public int getProjectTimeEstimate() {
        return projectTimeEstimate;
    }

    public void setProjectTimeEstimate(int projectTimeEstimate) {
        this.projectTimeEstimate = projectTimeEstimate;
    }

    public int getProjectDedicatedHours() {
        return projectDedicatedHours;
    }

    public void setProjectDedicatedHours(int projectDedicatedHours) {
        this.projectDedicatedHours = projectDedicatedHours;
    }

    public LocalDate getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(LocalDate projectDeadline) {
        this.projectDeadline = projectDeadline;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
}
