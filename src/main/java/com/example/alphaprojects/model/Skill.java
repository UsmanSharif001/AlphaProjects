package com.example.alphaprojects.model;

public class Skill {

    private int skillID;
    private String skillName;

    public Skill(){

    }

//    public Skill(String skillName){
//        this.skillName = skillName;
//    }

    public Skill(int skillID, String skillName) {
        this.skillID = skillID;
        this.skillName = skillName;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return skillID + ". " + skillName;
    }
}
