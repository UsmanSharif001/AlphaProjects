package com.example.alphaprojects.services;

import com.example.alphaprojects.interfaces.EmployeeInterface;
import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.Skill;
import com.example.alphaprojects.repositories.EmpRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {
    private EmployeeInterface empRepository;

    public EmpService(EmployeeInterface empRepository) {
        this.empRepository = empRepository;
    }

    public Emp getEmp(String username, String password) {
      return empRepository.getEmp(username,password);
    }

    public List<Skill> getSkills(){
        return empRepository.getSkills();
    }
}
