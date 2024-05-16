package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.EmpDTO;
import com.example.alphaprojects.model.Role;
import com.example.alphaprojects.model.Skill;

import java.util.List;

public interface EmployeeRepositoryInterface {



    EmpDTO login(String username, String password);

    List<Emp> getAllEmp();

    Emp addEmp(Emp emp);

    void deleteEmp(int empID);

    //TODO have to figure out if this is needed
    List<Role> getListOfRoleNamesForEmp();

    List<Role> getRoles();

    List<Skill> getSkills();

    Skill addSkill(Skill skill);

}
