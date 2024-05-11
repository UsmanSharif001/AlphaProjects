package com.example.alphaprojects.model;

import java.time.LocalDate;
import java.util.Date;

public class Subproject {

    private int subprojectID;
    private int projectID;
    private String name;
    private String description;
    private int timeEstimate;
    private int dedicatedHours;
    private LocalDate deadline;
    private String status;

    public Subproject(int subprojectID, int projectID, String name, String description, int timeEstimate, int dedicatedHours, LocalDate deadline, String status) {
        this.subprojectID = subprojectID;
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        this.timeEstimate = timeEstimate;
        this.dedicatedHours = dedicatedHours;
        this.deadline = deadline;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public int getDedicatedHours() {
        return dedicatedHours;
    }

    public void setDedicatedHours(int dedicatedHours) {
        this.dedicatedHours = dedicatedHours;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
