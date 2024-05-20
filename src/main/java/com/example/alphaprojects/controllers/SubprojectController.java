package com.example.alphaprojects.controllers;

import com.example.alphaprojects.Exceptions.SubprojectAddException;
import com.example.alphaprojects.Exceptions.SubprojectEditException;
import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.services.ProjectService;
import com.example.alphaprojects.services.SubprojectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class SubprojectController {
    private SubprojectService subprojectService;
    private ProjectService projectService;

public SubprojectController(SubprojectService subprojectService, ProjectService projectService, HttpSession session) {
    this.subprojectService = subprojectService;
    this.projectService = projectService;
}

private boolean isLoggedIn(HttpSession session) {
    return session.getAttribute("emp") != null;
}

@GetMapping("/{projectid}/subprojekter")
public String getSubprojects(@PathVariable int projectid, Model model, HttpSession session){
    if (isLoggedIn(session)) {
        Project project = projectService.getProjectFromProjectID(projectid);
        String projectName = project.getProjectName();
        int projectTimeEstimate = subprojectService.getProjectEstimatedHours(projectid);
        List<Subproject> subprojectList = subprojectService.getSubprojects(projectid);
        model.addAttribute("projectName", projectName);
        model.addAttribute("subprojects", subprojectList);
        model.addAttribute("projectid", projectid);
        model.addAttribute("projectTimeEstimate", projectTimeEstimate);
        return "subproject";
    }
    return "redirect:/login";
}

@GetMapping("/{projectid}/opretsubprojekt")
public String createSubproject(@PathVariable int projectid, Model model, HttpSession session){
    if (isLoggedIn(session)) {
        Subproject newSubproject = new Subproject();
        model.addAttribute("subproject", newSubproject);
        model.addAttribute("projectid", projectid);
        return "addSubproject";
    }
    return "redirect:/login";
}

@PostMapping("/{projectid}/gemsubprojekt")
public String saveSubproject(@PathVariable int projectid, @ModelAttribute Subproject newSubproject, HttpSession session) throws SubprojectAddException{
    if (isLoggedIn(session)) {
        newSubproject.setProjectID(projectid);
        subprojectService.createSubproject(newSubproject);
        return "redirect:/" + projectid + "/subprojekter";
    }
    return "redirect:/login";
}

@GetMapping("/{subprojectid}/redigersubprojekt")
public String editSubproject(@PathVariable int subprojectid, Model model, HttpSession session){
    if (isLoggedIn(session)) {
        Subproject editSubproject = subprojectService.getSubprojectFromSubprojectID(subprojectid);
        int projectid = editSubproject.getProjectID();
        model.addAttribute("subproject", editSubproject);
        model.addAttribute("projectid", projectid);
        return "editsubproject";
    }
    return "redirect:/login";
}

@PostMapping("/{subprojectid}/opdatersubprojekt")
public String updateSubproject(@ModelAttribute Subproject subproject, @PathVariable int subprojectid, HttpSession session) throws SubprojectEditException{
    if (isLoggedIn(session)) {
        subproject.setSubprojectID(subprojectid);
        subprojectService.editSubproject(subproject);
        return "redirect:/" + subproject.getProjectID() + "/subprojekter";
    }
    return "redirect:/login";
}

@GetMapping("/{subprojectid}/sletsubprojekt")
public String deleteSubproject(@PathVariable int subprojectid, HttpSession session){
    if (isLoggedIn(session)) {
        Subproject subproject = subprojectService.getSubprojectFromSubprojectID(subprojectid);
        int projectID = subproject.getProjectID();
        subprojectService.deleteSubproject(subprojectid);
        return "redirect:/" + projectID + "/subprojekter";
    }
    return "redirect:/login";
}

@ExceptionHandler(SubprojectAddException.class)
public String handleAddError(Model model, Exception exception){
    model.addAttribute("message", exception.getMessage());
    System.out.println(exception.getMessage());
    return "error/subprojectAddError";
}

@ExceptionHandler(SubprojectEditException.class)
public String handleEditError(Model model, Exception exception){
    model.addAttribute("message", exception.getMessage());
    System.out.println(exception.getMessage());
    return "error/subprojectEditError";
}

}
