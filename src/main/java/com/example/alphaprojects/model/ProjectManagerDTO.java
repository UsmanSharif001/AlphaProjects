package com.example.alphaprojects.model;

public class ProjectManagerDTO {

    private int projectManagerID;
    private String projectManagerName;

    public ProjectManagerDTO (int projectManagerID, String projectManagerName){
        this.projectManagerName = projectManagerName;
        this.projectManagerID = projectManagerID;
    }

    public int getProjectManagerID() {
        return projectManagerID;
    }

    public void setProjectManagerID(int projectManagerID) {
        this.projectManagerID = projectManagerID;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    @Override
    public String toString() {
        return projectManagerName;
    }
}