package com.example.alphaprojects.services;

import com.example.alphaprojects.Exceptions.ProjectAddException;
import com.example.alphaprojects.Exceptions.ProjectEditException;
import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.model.ProjectManagerDTO;
import com.example.alphaprojects.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getListOfProjects() {
        return projectRepository.getListOfProjects();
    }

    public String getProjectManager(int empID) {
        return projectRepository.getProjectManagerName(empID);
    }

    public void addNewProject(Project newProject) throws ProjectAddException {
        projectRepository.addNewProject(newProject);
    }

    public List<ProjectManagerDTO> getProjectManagers() {
        return projectRepository.getProjectManagers();
    }

    public void editProject(Project updateProject) throws ProjectEditException {
        projectRepository.editProject(updateProject);
    }

    public Project getProjectFromProjectID(int projectID) {
        return projectRepository.getProjectFromProjectID(projectID);
    }

    public List<Project> getListOfArchivedProjects() {
        return projectRepository.getListOfArchivedProjects();
    }

    public void editDescription(Project updateProject) throws ProjectEditException {
        projectRepository.editDescription(updateProject);
    }
}
