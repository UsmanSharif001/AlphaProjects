package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.Skill;

import java.util.List;

public interface EmployeeInterface {

    Emp getEmp(String email, String password);

    List<Skill> getSkills();
}
