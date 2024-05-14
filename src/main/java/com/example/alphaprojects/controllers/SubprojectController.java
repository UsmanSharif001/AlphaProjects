package com.example.alphaprojects.controllers;

import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.services.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class SubprojectController {
    private SubprojectService subprojectService;

public SubprojectController(SubprojectService subprojectService) {
    this.subprojectService = subprojectService;
}

@GetMapping("/{projectid}/subprojects")
    public String getSubprojects(@PathVariable int projectid, Model model){
        List<Subproject> subprojectList = subprojectService.getSubprojects(projectid);
        model.addAttribute("subprojects", subprojectList);
        model.addAttribute("projectid", projectid);
        return "subproject";
}

@GetMapping("/{projectid}/createsubproject")
    public String createSubproject(@PathVariable int projectid, Model model){
    Subproject newSubproject = new Subproject();
    model.addAttribute("subproject", newSubproject);
    model.addAttribute("projectid", projectid);
    return "createSubproject";
}

@PostMapping("/{projectid}/savesubproject")
    public String saveSubproject(@PathVariable int projectid, @ModelAttribute Subproject newSubproject){
    newSubproject.setProjectID(projectid);
    subprojectService.createSubproject(newSubproject);
    return "redirect:/" + projectid + "/subprojects";
}

@GetMapping("/{subprojectid}/editsubproject")
    public String editSubproject(@PathVariable int subprojectid, Model model){
    Subproject editSubproject = subprojectService.getSubprojectFromSubprojectID(subprojectid);
    int projectid = editSubproject.getProjectID();
    model.addAttribute("subproject", editSubproject);
    model.addAttribute("projectid", projectid);
    return "editsubproject";
}

@PostMapping("/{subprojectid}/updatesubproject")
    public String updateSubproject(@ModelAttribute Subproject subproject, @PathVariable int subprojectid){
    subproject.setSubprojectID(subprojectid);
    subprojectService.editSubproject(subproject);
    return "redirect:/" + subproject.getProjectID() + "/subprojects";
}

@GetMapping("/{subprojectid}/deletesubproject")
    public String deleteSubproject(@PathVariable int subprojectid){
    subprojectService.deleteSubproject(subprojectid);
    return "redirect:/" + subprojectid + "/subproject";
}

}
