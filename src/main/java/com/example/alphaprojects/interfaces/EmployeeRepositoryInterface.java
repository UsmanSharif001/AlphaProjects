package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.EmpDTO;
import com.example.alphaprojects.model.Skill;

import java.util.List;

public interface EmployeeRepositoryInterface {

//    Emp getEmp(String email, String password);

    EmpDTO login(String username, String password);

    List<Emp> getAllEmp();

    Emp addEmp(Emp emp);

    void deleteEmp(int empID);

    List<Skill> getSkills();

    Skill addSkill(Skill skill);

}
