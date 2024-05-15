package com.example.alphaprojects.services;

import com.example.alphaprojects.interfaces.EmployeeRepositoryInterface;
import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.EmpDTO;
import com.example.alphaprojects.model.Role;
import com.example.alphaprojects.model.Skill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {
    private EmployeeRepositoryInterface empRepository;

    public EmpService(EmployeeRepositoryInterface empRepository) {
        this.empRepository = empRepository;
    }

//    public Emp getEmp(String username, String password) {
//      return empRepository.getEmp(username,password);
//    }

    public EmpDTO login(String email, String password) {
        return empRepository.login(email, password);
    }

    public List<Emp> getAllEmp(){
        return empRepository.getAllEmp();
    }

    public Emp addEmp(Emp emp) {
        return empRepository.addEmp(emp);
    }

    public void deleteEmp(int empID){
        empRepository.deleteEmp(empID);
    }

    public List<Role> getRoles(){
        return empRepository.getRoles();
    }

    public List<Skill> getSkills(){
        return empRepository.getSkills();
    }

    public Skill addSkill(Skill skill) {
        return empRepository.addSkill(skill);
    }
}
