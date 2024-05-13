package com.example.alphaprojects.model;

import java.util.List;

public class Emp {

    private int empID;
    private String name;
    private String email;
    private String password;
    private List<Skill> skillList;


    public Emp() {

    }

    public Emp(int empID, String name, String email, String password,  List<Skill> skillList) {
        this.empID = empID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.skillList = skillList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public void addSkill(Skill skill) {
        skillList.add(skill);
    }
}
