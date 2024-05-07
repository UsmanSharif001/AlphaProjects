package com.example.alphaprojects.controllers;


import com.example.alphaprojects.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class EmpController {
    private UserService userService;

    public EmpController(UserService userService) {
        this.userService = userService;
    }


}
