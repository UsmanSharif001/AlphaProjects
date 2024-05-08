package com.example.alphaprojects.controllers;

import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.model.ProjectManagerDTO;
import com.example.alphaprojects.services.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("emp") != null;
    }

    @GetMapping("/projekter")
    private String getProjects(Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            List<Project> projectList = projectService.getListOfProjects();
            model.addAttribute("projects", projectList);
            return "projekter";
        }
        return "redirect:/login";
    }

    @GetMapping("/opretprojekt")
    private String createProject(Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            List<ProjectManagerDTO> projectManagers = projectService.getProjectManagers();
            model.addAttribute("newProject", new Project());
            model.addAttribute("projectManagers", projectManagers);
            return "opretprojekt";
        }
        return "redirect:/login";
    }

    @PostMapping("/gemprojekt")
    private String saveProject(@ModelAttribute Project project, HttpSession session) {
        if (isLoggedIn(session)) {
            projectService.addNewProject(project);
            System.out.println(project);
            return "redirect:/projekter";
        }
        return "redirect:/login";
    }
}
