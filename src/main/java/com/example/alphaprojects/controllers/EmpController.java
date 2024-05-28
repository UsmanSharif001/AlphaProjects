package com.example.alphaprojects.controllers;


import com.example.alphaprojects.exceptions.EmpDeleteException;
import com.example.alphaprojects.exceptions.UniqueLoginException;
import com.example.alphaprojects.model.EmpDTO;
import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.Role;
import com.example.alphaprojects.model.Skill;
import com.example.alphaprojects.services.EmpService;
import jakarta.servlet.http.HttpSession;
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

    private void isAdmin(HttpSession session, Model model) {
        Emp emp = (Emp) session.getAttribute("emp");
        if(emp.getRoleID() == 1){
            model.addAttribute("isAdmin", true);
        }
    }

    /*-----------------------------Login--------------------------*/

    @GetMapping(value = {"","/","/login"})
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession session, Model model) {
        Emp emp = empService.login(email,password);
        if (emp != null){
            session.setAttribute("emp", emp);
            session.setMaxInactiveInterval(1200);
            return "redirect:/projekter";
        }
        model.addAttribute("wrongCredentials",true);
        return "login";
    }

    /*-----------------------------Admin--------------------------*/

    @GetMapping("/admin")
    public String admin(HttpSession session, Model model){
        if (isLoggedIn(session)){
            isAdmin(session,model);
            return "admin";
            }
        return "login";

    }

    /*-----------------------------Emp Overview--------------------------*/

    @GetMapping("/medarbejdere")
    public String getListofEmployees(HttpSession session, Model model){
        if(isLoggedIn(session)){
            isAdmin(session,model);
            List<EmpDTO> empList = empService.getAllEmp();
            model.addAttribute("empList", empList);
            return "employeelist";
        }
        return "redirect:login";
    }

    /*-----------------------------Add Emp--------------------------*/

    @GetMapping("/tilføjmedarbejder")
    public String addEmp(HttpSession session, Model model) {
    if(isLoggedIn(session)){
        isAdmin(session,model);
        model.addAttribute("emp", new EmpDTO());
        List<Skill> skillList = empService.getSkills();
        model.addAttribute("listOfSkills", skillList);
        List<Role> roleList = empService.getRoles();
        model.addAttribute("listOfRoles", roleList);
        return "addEmp";
    }
    return "redirect:login";
    }

    @PostMapping("/gemmedarbejder")
    public String saveEmp(@ModelAttribute EmpDTO empDTO, HttpSession session) {
        if(isLoggedIn(session)){
        empService.addEmp(empDTO);
        return "redirect:/medarbejdere";
        }
        return "redirect:login";
    }

    /*-----------------------------Update Emp--------------------------*/

    @GetMapping("/{empID}/redigermedarbejder")
    private String editEmp(@PathVariable int empID, HttpSession session, Model model) {
        if(isLoggedIn(session)){
            isAdmin(session,model);
            EmpDTO editEmp = empService.getEmpFromEmpID(empID);
            model.addAttribute("emp", editEmp);
            List<Skill> skillList = empService.getSkills();
            model.addAttribute("listOfSkills", skillList);
            List<Role> roleList = empService.getRoles();
            model.addAttribute("listOfRoles", roleList);
            return "editEmp";
        }
        return "redirect:login";
    }

    @PostMapping("/{empID}/opdatermedarbejder")
    public String updateEmp(@ModelAttribute EmpDTO empDTO, HttpSession session) {
        if(isLoggedIn(session)){
            empService.updateEmp(empDTO);
            return "redirect:/medarbejdere";
        }
        return "redirect:login";
    }

    /*-----------------------------Delete Emp--------------------------*/

    @GetMapping("/{empID}/sletmedarbejder")
    public String deleteEmp(@PathVariable int empID, HttpSession session){
        if(isLoggedIn(session)){
            empService.deleteEmp(empID);
            return "redirect:/medarbejdere";
        }

        return "redirect:login";
    }


    /*-----------------------------Skills--------------------------*/


    @GetMapping("/skills")
    public String getSkills(HttpSession session,Model model){
        if(isLoggedIn(session)){
            isAdmin(session,model);
            List<Skill> skillList = empService.getSkills();
            model.addAttribute("skillList", skillList);
            return "skillList";
        }
        return "redirect:login";
    }



    @GetMapping("/tilføjskill")
    public String addSKill(HttpSession session, Model model) {
        if(isLoggedIn(session)){
            isAdmin(session,model);
            model.addAttribute("skill", new Skill());
            return "addSkill";
        }
        return "redirect:login";
    }


    @PostMapping("/gemskill")
    public String saveSkill(@ModelAttribute Skill skill, HttpSession session) {
        if(isLoggedIn(session)){
            empService.addSkill(skill);
            return "redirect:/skills";
        }
        return "redirect:login";
    }

    /*-----------------------------Logout--------------------------*/

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    /*-----------------------------Exception handling--------------------------*/

    @ExceptionHandler(EmpDeleteException.class)
    public String handleAddError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        System.out.println(exception.getMessage());
        return "error/empDeleteError";
    }

    @ExceptionHandler(UniqueLoginException.class)
    public String handleUniqueLoginError(Model model,Exception exception){
        model.addAttribute("message", exception.getMessage());
        System.out.println(exception.getMessage());
        return "error/uniqueLoginError";
    }

}
