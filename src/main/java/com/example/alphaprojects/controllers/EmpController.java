package com.example.alphaprojects.controllers;


import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.services.EmpService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class EmpController {
    private EmpService empService;

    public EmpController(EmpService empService) {
        this.empService = empService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("emp") != null;
    }

    /*-----------------------------Login--------------------------*/

    @GetMapping()
    public String loginForm(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession session, Model model) {
        Emp emp = empService.getEmp(email,password);
        if (emp != null){
            session.setAttribute("emp", emp);
            session.setMaxInactiveInterval(1200);
            return "redirect:/projekter";
        }
        model.addAttribute("wrongCredentials",true);
        return "login";
    }



}
