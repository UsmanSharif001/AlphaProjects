package com.example.alphaprojects.controllers;

import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    private String getProjects(Model model) {
        List<Project> projectList = projectService.getListOfProjects();
        model.addAttribute("projects", projectList);
        return "projects";
    }
}
