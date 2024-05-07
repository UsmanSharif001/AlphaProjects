package com.example.alphaprojects.controllers;


import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.services.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
