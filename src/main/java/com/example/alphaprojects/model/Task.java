package com.example.alphaprojects.model;

import java.time.LocalDate;

import java.util.Date;

public class Task {

    private String name;
    private String description;
    private int estimatedHours;
    private int dedicatedHours;
    private Date deadline;
    private String status;
    private int taskID;
    private int subprojectID;

    public Task(String name, String description, int estimatedHours, int dedicatedHours, Date deadline, String status, int taskID, int subprojectID) {
        this.name = name;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.dedicatedHours = dedicatedHours;
        this.deadline = deadline;
        this.status = status;
        this.taskID = taskID;
        this.subprojectID = subprojectID;
    }

    public Task(){}

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

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public int getDedicatedHours() {
        return dedicatedHours;
    }

    public void setDedicatedHours(int dedicatedHours) {
        this.dedicatedHours = dedicatedHours;
    }

    public java.sql.Date getDeadline() {
        return (java.sql.Date) deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
    }
}
