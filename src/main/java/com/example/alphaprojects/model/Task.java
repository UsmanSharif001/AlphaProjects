package com.example.alphaprojects.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class Task {

    private int taskID;
    private int subprojectID;
    private String taskName;
    private String taskDescription;
    private int taskEstimate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taskDeadline;
    private String taskStatus;
    private List<Integer> selectedEmpIDs;
    private List<EmpDTO> assignedEmployees;
    private List<EmpSkillDTO> assignedEmployeesWithSkills;

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

    public Task(int taskID, int subprojectID, String taskName, String taskDescription, int taskEstimate, LocalDate taskDeadline, String taskStatus, List<Integer> selectedEmpIDs, List<EmpDTO> assignedEmployees, List<EmpSkillDTO> assignedEmployeesWithSkills) {
        this.taskID = taskID;
        this.subprojectID = subprojectID;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskEstimate = taskEstimate;
        this.taskDeadline = taskDeadline;
        this.taskStatus = taskStatus;
        this.selectedEmpIDs = selectedEmpIDs;
        this.assignedEmployees = assignedEmployees;
        this.assignedEmployeesWithSkills = assignedEmployeesWithSkills;
    }

    public List<EmpSkillDTO> getAssignedEmployeesWithSkills() {
        return assignedEmployeesWithSkills;
    }

    public void setAssignedEmployeesWithSkills(List<EmpSkillDTO> assignedEmployeesWithSkills) {
        this.assignedEmployeesWithSkills = assignedEmployeesWithSkills;
    }

    public List<EmpDTO> getAssignedEmployees() {
        return assignedEmployees;
    }

    public void setAssignedEmployees(List<EmpDTO> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }

    public List<Integer> getSelectedEmpIDs() {
        return selectedEmpIDs;
    }

    public void setSelectedEmpIDs(List<Integer> selectedEmpIDs) {
        this.selectedEmpIDs = selectedEmpIDs;
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
