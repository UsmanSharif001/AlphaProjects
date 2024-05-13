package com.example.alphaprojects.model;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill skill)) return false;
        return skillID == skill.skillID && Objects.equals(skillName, skill.skillName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillID, skillName);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skillID=" + skillID +
                ", skillName='" + skillName + '\'' +
                '}';
    }
}
