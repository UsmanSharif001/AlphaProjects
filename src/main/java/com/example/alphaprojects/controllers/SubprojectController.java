package com.example.alphaprojects.controllers;


import com.example.alphaprojects.services.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class SubprojectController {
    private SubprojectService subprojectService;

public SubprojectController(SubprojectService subprojectService) {
    this.subprojectService = subprojectService;
}



}
