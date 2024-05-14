//package com.example.alphaprojects.util;
//
//import com.example.alphaprojects.model.Skill;
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class StringToSkillConverter implements Converter<String, Skill> {
//
//    @Override
//    public Skill convert(String source) {
//        String[] tokens = source.split(";");
//        int skillID = Integer.parseInt(tokens[0]);
//        String name = tokens[1];
//        Skill skill = new Skill(skillID,name);
//        return skill;
//    }
//
//
//}
