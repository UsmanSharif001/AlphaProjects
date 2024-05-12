package com.example.alphaprojects.controllers;


import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.Skill;
import com.example.alphaprojects.services.EmpService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = {"","/","/login"})
    public String loginForm() {
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

    /*-----------------------------Add Emp--------------------------*/

    @GetMapping("/tilføjmedarbejder")
    public String addEmp(HttpSession session, Model model) {
    if(isLoggedIn(session)){
        model.addAttribute("emp", new Emp());
        List<Skill> skillList = empService.getSkills();
        model.addAttribute("skillList", skillList);
        return "addEmp";
    }
    return "redirect:/login";
    }

    @PostMapping("/gemmedarbejder")
    public String saveEmp(@ModelAttribute Emp emp, HttpSession session) {
        if(isLoggedIn(session)){
        empService.addEmp(emp);
        return "redirect:/projekter";
        }
        return "redirect:/login";
    }

    /*-----------------------------Delete Emp--------------------------*/

    //TODO Not tested and have to figure out where it should redirect to
    @GetMapping("/sletmedarbejder")
    public String deleteEmp(HttpSession session){
        if(isLoggedIn(session)){
            Emp emp = (Emp) session.getAttribute("emp");
            empService.deleteEmp(emp.getEmpID());
            return "redirect:/projekter";
        }

        return "redirect:/login";
    }

    /*-----------------------------Skills--------------------------*/
    //TODO have to figure out where these redirect to, not tested

    @GetMapping("/tilføjnyskill")
    public String addSKill(HttpSession session, Model model) {
        if(isLoggedIn(session)){
            model.addAttribute("skill", new Skill());
        }
        return "addSKill";
    }


    @PostMapping("/gemskill")
    public String saveSkill(@ModelAttribute Skill skill, HttpSession session) {
        if(isLoggedIn(session)){
            empService.addSkill(skill);
            return "redirect:/projekter";
        }
        return "redirect:/login";
    }


    /*-----------------------------Logout--------------------------*/

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
