package com.example.alphaprojects.model;

import java.util.List;

public class EmpSkillDTO {
    private int empID;
    private String empName;
    private List<Skill> skills;

    public EmpSkillDTO(int empID, String empName, List<Skill> skills) {
        this.empID = empID;
        this.empName = empName;
        this.skills = skills;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
