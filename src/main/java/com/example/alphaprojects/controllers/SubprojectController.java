package com.example.alphaprojects.controllers;

import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.services.ProjectService;
import com.example.alphaprojects.services.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class SubprojectController {
    private SubprojectService subprojectService;
    private ProjectService projectService;

public SubprojectController(SubprojectService subprojectService, ProjectService projectService) {
    this.subprojectService = subprojectService;
    this.projectService = projectService;
}

@GetMapping("/{projectid}/subprojekter")
    public String getSubprojects(@PathVariable int projectid, Model model){
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

@GetMapping("/{projectid}/opretsubprojekt")
    public String createSubproject(@PathVariable int projectid, Model model){
    Subproject newSubproject = new Subproject();
    model.addAttribute("subproject", newSubproject);
    model.addAttribute("projectid", projectid);
    return "createSubproject";
}

@PostMapping("/{projectid}/gemsubprojekt")
    public String saveSubproject(@PathVariable int projectid, @ModelAttribute Subproject newSubproject){
    newSubproject.setProjectID(projectid);
    subprojectService.createSubproject(newSubproject);
    return "redirect:/" + projectid + "/subprojekter";
}

@GetMapping("/{subprojectid}/redigersubprojekt")
    public String editSubproject(@PathVariable int subprojectid, Model model){
    Subproject editSubproject = subprojectService.getSubprojectFromSubprojectID(subprojectid);
    int projectid = editSubproject.getProjectID();
    model.addAttribute("subproject", editSubproject);
    model.addAttribute("projectid", projectid);
    return "editsubproject";
}

@PostMapping("/{subprojectid}/opdatersubprojekt")
    public String updateSubproject(@ModelAttribute Subproject subproject, @PathVariable int subprojectid){
    subproject.setSubprojectID(subprojectid);
    subprojectService.editSubproject(subproject);
    return "redirect:/" + subproject.getProjectID() + "/subprojekter";
}

@GetMapping("/{subprojectid}/sletsubprojekt")
    public String deleteSubproject(@PathVariable int subprojectid){
    Subproject subproject = subprojectService.getSubprojectFromSubprojectID(subprojectid);
    int projectID = subproject.getProjectID();
    subprojectService.deleteSubproject(subprojectid);
    return "redirect:/" + projectID + "/subprojekter";
}

}
