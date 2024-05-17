package com.example.alphaprojects.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Subproject {

    private int subprojectID;
    private int projectID;
    private String subprojectName;
    private String subprojectDescription;
    private int subprojectTimeEstimate;
    private int subprojectDedicatedHours;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate subprojectDeadline;
    private String subprojectStatus;

    public Subproject(int subprojectID, int projectID, String subprojectName, String subprojectDescription, int subprojectTimeEstimate, int subprojectDedicatedHours, LocalDate subprojectDeadline, String status) {
        this.subprojectID = subprojectID;
        this.projectID = projectID;
        this.subprojectName = subprojectName;
        this.subprojectDescription = subprojectDescription;
        this.subprojectTimeEstimate = subprojectTimeEstimate;
        this.subprojectDedicatedHours = subprojectDedicatedHours;
        this.subprojectDeadline = subprojectDeadline;
        this.subprojectStatus = status;
    }

    public Subproject(){
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getSubprojectName() {
        return subprojectName;
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName;
    }

    public String getSubprojectDescription() {
        return subprojectDescription;
    }

    public void setSubprojectDescription(String subprojectDescription) {
        this.subprojectDescription = subprojectDescription;
    }

    public int getSubprojectTimeEstimate() {
        return subprojectTimeEstimate;
    }

    public void setSubprojectTimeEstimate(int subprojectTimeEstimate) {
        this.subprojectTimeEstimate = subprojectTimeEstimate;
    }

    public int getSubprojectDedicatedHours() {
        return subprojectDedicatedHours;
    }

    public void setSubprojectDedicatedHours(int subprojectDedicatedHours) {
        this.subprojectDedicatedHours = subprojectDedicatedHours;
    }

    public LocalDate getSubprojectDeadline() {
        return subprojectDeadline;
    }

    public void setSubprojectDeadline(LocalDate subprojectDeadline) {
        this.subprojectDeadline = subprojectDeadline;
    }

    public String getSubprojectStatus() {
        return subprojectStatus;
    }

    public void setSubprojectStatus(String subprojectStatus) {
        this.subprojectStatus = subprojectStatus;
    }
}
