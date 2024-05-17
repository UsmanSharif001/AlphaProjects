package com.example.alphaprojects.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Task {

    private int taskID;
    private int subprojectID;
    private String taskName;
    private String taskDescription;
    private int taskEstimate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taskDeadline;
    private String taskStatus;

    public Task() {}

    public Task(int taskID, int subprojectID, String taskName, String taskDescription, int taskEstimate, LocalDate taskDeadline, String taskStatus) {
        this.taskID = taskID;
        this.subprojectID = subprojectID;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskEstimate = taskEstimate;
        this.taskDeadline = taskDeadline;
        this.taskStatus = taskStatus;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskEstimate() {
        return taskEstimate;
    }

    public void setTaskEstimate(int taskEstimate) {
        this.taskEstimate = taskEstimate;
    }

    public LocalDate getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(LocalDate taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
