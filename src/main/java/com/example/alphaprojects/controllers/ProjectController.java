package com.example.alphaprojects.controllers;

import com.example.alphaprojects.Exceptions.ProjectAddException;
import com.example.alphaprojects.Exceptions.ProjectEditException;
import com.example.alphaprojects.model.*;
import com.example.alphaprojects.services.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            EmpDTO emp = (EmpDTO) session.getAttribute("emp");
            if(emp.getRoleID() == 1) {
                model.addAttribute("isAdmin",true);
            }
            List<Project> projectList = projectService.getListOfProjects();
            model.addAttribute("projects", projectList);
            return "projects";
        }
        return "redirect:/login";
    }

    @GetMapping("/opretprojekt")
    private String createProject(Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            List<ProjectManagerDTO> projectManagers = projectService.getProjectManagers();
            model.addAttribute("newProject", new Project());
            model.addAttribute("projectManagers", projectManagers);
            return "addprojects";
        }
        return "redirect:/login";
    }

    @PostMapping("/gemprojekt")
    private String saveProject(@ModelAttribute Project project, HttpSession session) throws ProjectAddException {
        if (isLoggedIn(session)) {
            projectService.addNewProject(project);
            return "redirect:/projects";
        }
        return "redirect:/login";
    }

    @GetMapping("/{projectID}/redigerprojekt")
    private String updateProject(@PathVariable int projectID, Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            Project updateProject = projectService.getProjectFromProjectID(projectID);
            List<ProjectManagerDTO> projectManagers = projectService.getProjectManagers();
            model.addAttribute("projectID", projectID);
            model.addAttribute("updateProject", updateProject);
            model.addAttribute("projectDeadline", updateProject.getProjectDeadline());
            model.addAttribute("projectManagers", projectManagers);
            return "/editproject";
        }
        return "redirect:/login";
    }

    @PostMapping("/{projectID}/opdaterprojekt")
    public String updateProject(@ModelAttribute Project updateProject, @PathVariable int projectID, HttpSession session) throws ProjectEditException {
        if (isLoggedIn(session)) {
            updateProject.setProjectID(projectID);
            projectService.editProject(updateProject);
            return "redirect:/projects";
        }
        return "redirect:/login";
    }

    @GetMapping("/arkiveredeprojekter")
    private String getArchivedProjects(Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            List<Project> archivedProjectList = projectService.getListOfArchivedProjects();
            model.addAttribute("archivedProjects", archivedProjectList);
            System.out.println(archivedProjectList);
            return "archivedprojects";
        }
        return "redirect:/login";
    }

    @ExceptionHandler(ProjectAddException.class)
    public String handleAddError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        System.out.println(exception.getMessage());
        return "error/projectAddError";
    }

    @ExceptionHandler(ProjectEditException.class)
    public String handleEditError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        System.out.println(exception.getMessage());
        return "error/projectEditError";
    }

}
