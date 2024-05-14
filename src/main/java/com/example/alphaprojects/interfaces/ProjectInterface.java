package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.Exceptions.ProjectAddException;
import com.example.alphaprojects.Exceptions.ProjectEditException;
import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.model.ProjectManagerDTO;

import java.util.List;

public interface ProjectInterface {

    List<Project> getListOfProjects();

    String getProjectManagerName(int empID);

    int calculateProjectDedicatedHours(int projectID);

    int getManagerID(String managerName);

    void addNewProject(Project newProject) throws ProjectAddException;

    void editProject(Project project) throws ProjectAddException, ProjectEditException;

    List<ProjectManagerDTO> getProjectManagers();

    Project getProjectFromProjectID(int projectID);

    List<Project> getListOfArchivedProjects();
}
