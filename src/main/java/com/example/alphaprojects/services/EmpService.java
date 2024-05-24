package com.example.alphaprojects.services;

import com.example.alphaprojects.exceptions.EmpDeleteException;
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


    public EmpDTO login(String email, String password) {
        return empRepository.login(email, password);
    }

    public List<Emp> getAllEmp(){
        return empRepository.getAllEmp();
    }

    public Emp addEmp(Emp emp) {
        return empRepository.addEmp(emp);
    }

    public Emp getEmpFromEmpID(int empId) {
        return empRepository.getEmpFromEmpID(empId);
    }

    public void updateEmp(Emp emp) {
        empRepository.updateEmp(emp);
    }

    public void deleteEmp(int empID) throws EmpDeleteException {
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
