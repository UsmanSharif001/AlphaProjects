package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.exceptions.EmpDeleteException;
import com.example.alphaprojects.model.EmpDTO;
import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.Role;
import com.example.alphaprojects.model.Skill;

import java.util.List;

public interface EmployeeRepositoryInterface {



    Emp login(String username, String password);

    List<EmpDTO> getAllEmp();

    EmpDTO addEmp(EmpDTO empDTO);

    void deleteEmp(int empID);

    EmpDTO getEmpFromEmpID(int empID);

    void updateEmp(EmpDTO empDTO);

    List<Role> getRoles();

    List<Skill> getSkills();

    Skill addSkill(Skill skill);

}
