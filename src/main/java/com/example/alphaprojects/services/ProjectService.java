package com.example.alphaprojects.services;

import com.example.alphaprojects.model.Project;
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
}
