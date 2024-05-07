package com.example.alphaprojects.model;

import java.util.List;

public class Emp {

    private int userID;
    private String email;
    private String password;
    private String name;
    private List<Skill> skillList;


    public Emp() {

    }

    public Emp(int userID, String email, String password, String name, List<Skill> skillList) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void addSkill(Skill skill) {
        skillList.add(skill);
    }
}
