package com.example.alphaprojects.services;

import com.example.alphaprojects.exceptions.EmpDeleteException;
import com.example.alphaprojects.interfaces.EmployeeRepositoryInterface;
import com.example.alphaprojects.model.EmpDTO;
import com.example.alphaprojects.model.Emp;
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


    public Emp login(String email, String password) {
        return empRepository.login(email, password);
    }

    public List<EmpDTO> getAllEmp(){
        return empRepository.getAllEmp();
    }

    public EmpDTO addEmp(EmpDTO empDTO) {
        return empRepository.addEmp(empDTO);
    }

    public EmpDTO getEmpFromEmpID(int empId) {
        return empRepository.getEmpFromEmpID(empId);
    }

    public void updateEmp(EmpDTO empDTO) {
        empRepository.updateEmp(empDTO);
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
