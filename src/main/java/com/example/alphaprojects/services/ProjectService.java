package com.example.alphaprojects.services;

import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.model.ProjectManagerDTO;
import com.example.alphaprojects.model.Status;
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

    public String getProjectManagers(int empID) {
        return projectRepository.getProjectManagerName(empID);
    }

    public void addNewProject(Project newProject) {
        projectRepository.addNewProject(newProject);
    }

    public List<ProjectManagerDTO> getProjectManagers() {
        return projectRepository.getProjectManagers();
    }

    public List<Status> getStatuses() {
        return projectRepository.getStatuses();
    }
}
