package com.example.alphaprojects.model;

import java.time.LocalDate;

public class Project {

private int projectID;
private int projectManagerID;
private String projectName;
private String projectDescription;
private int projectTimeEstimate;
private int projectDedicatedHours;
private LocalDate projectDeadline;
private Status projectStatus;

    public Project(int projectID, int projectManagerID, String projectName, String projectDescription, int projectTimeEstimate, int projectDedicatedHours, LocalDate projectDeadline, Status projectStatus) {
        this.projectID = projectID;
        this.projectManagerID = projectManagerID;
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

    public Status getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Status projectStatus) {
        this.projectStatus = projectStatus;
    }
}
