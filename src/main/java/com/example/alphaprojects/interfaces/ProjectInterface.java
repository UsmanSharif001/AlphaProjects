package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.model.ProjectManagerDTO;
import com.example.alphaprojects.model.Status;

import java.util.List;

public interface ProjectInterface {

    List<Project> getListOfProjects();

    String getProjectManagerName(int empID);

    int calculateProjectDedicatedHours(int projectID);

    int getManagerID(String managerName);

    void addNewProject(Project newProject);

    void editProject(Project project);

    List<ProjectManagerDTO> getProjectManagers();

    Project getProjectFromProjectID(int projectID);
}
