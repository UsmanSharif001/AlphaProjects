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
        return "subproject";
}

@GetMapping("/{subprojectid}/createsubproject")
    public String createSubproject(@PathVariable int subprojectid, Model model){
    model.addAttribute("subproject", new Subproject());
    model.addAttribute("subprojectid", subprojectid);
    return "createSubproject";
}

@PostMapping("/{subprojectid}/savesubproject")
    public String saveSubproject(@PathVariable int subprojectid, @ModelAttribute Subproject newSubproject){
    newSubproject.setSubprojectID(subprojectid);
    subprojectService.createSubproject(newSubproject);
    return "redirect:/" + newSubproject.getProjectID() + "/subprojects";
}

@GetMapping("/{subprojectid}/editsubproject")
    public String editSubproject(@PathVariable int subprojectid, Model model){
    Subproject editSubproject = subprojectService.getSubprojectFromSubprojectID(subprojectid);
    model.addAttribute("subproject", editSubproject);
    return "editsubproject";
}

@PostMapping("/{subprojectid}/updatesubproject")
    public String updateSubproject(@ModelAttribute Subproject subproject, @PathVariable int subprojectid){
    subproject.setSubprojectID(subprojectid);
    subprojectService.editSubproject(subproject);
    return "redirect:/" + subproject.getProjectID() + "/subproject"; // CANNOT FIND?
}

@GetMapping("/{subprojectid}/deletesubproject")
    public String deleteSubproject(@PathVariable int subprojectid, Model model){
    subprojectService.deleteSubproject(subprojectid);
    //model.addAttribute("subprojectid", subprojectid);
    return "redirect:/" + subprojectid + "/subproject";
}

}
