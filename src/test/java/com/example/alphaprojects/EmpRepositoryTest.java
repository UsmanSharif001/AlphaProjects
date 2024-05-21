package com.example.alphaprojects;


import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.EmpDTO;
import com.example.alphaprojects.model.Role;
import com.example.alphaprojects.model.Skill;
import com.example.alphaprojects.repositories.EmpRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
public class EmpRepositoryTest {

    @Autowired
    EmpRepository empRepository;

    @Test
    void loginCorrect(){
    String email = "Nikolaj@gmail.com";
    String password = "123";
    EmpDTO emp = empRepository.login(email, password);
        Assertions.assertNotNull(emp);

    }

    @Test
    void loginWrongEmail(){
        String email = "Niko@gmail.com";
        String password = "123";
        EmpDTO emp = empRepository.login(email, password);
        Assertions.assertNull(emp);
    }

    @Test
    void loginWrongPassword(){
        String email = "Nikolaj@gmail.com";
        String password = "Forkert kode";
        EmpDTO emp = empRepository.login(email, password);
        Assertions.assertNull(emp);
    }

    @Test
    void getAllEmp(){
        int actualSize = empRepository.getAllEmp().size();
        int expectedSize = 4;
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void getEmpByCorrectEmpID(){
        int idForNikolaj = 4;
        Emp expectedEmp = empRepository.getEmpFromEmpID(idForNikolaj);
        assertNotNull(expectedEmp);
    }

    @Test
    void getEmpByIncorrectEmpID(){
        int incorrectEmpID = 10;
        Emp expectedIncorrectEmp = empRepository.getEmpFromEmpID(incorrectEmpID);
        assertNull(expectedIncorrectEmp);
    }

    @Test
    void getEmpName(){
        int idForNikolaj = 4;
        String expectedName = "Nikolaj";
        Emp emp = empRepository.getEmpFromEmpID(idForNikolaj);
        String actualName = emp.getName();
        assertEquals(expectedName,actualName);

    }

    @Test
    void getEmpEmail(){
        int idForNikolaj = 4;
        String expectedEmail = "Nikolaj@gmail.com";
        Emp emp = empRepository.getEmpFromEmpID(idForNikolaj);
        String actualName = emp.getEmail();
        assertEquals(expectedEmail,actualName);
    }

    @Test
    void getEmpPassword(){
        int idForNikolaj = 4;
        String expectedPassword = "123";
        Emp emp = empRepository.getEmpFromEmpID(idForNikolaj);
        String actualName = emp.getPassword();
        assertEquals(expectedPassword,actualName);
    }

    @Test
    void getEmpRoleID(){
        int idForNikolaj = 4;
        int expectedRoleID = 1;
        Emp emp = empRepository.getEmpFromEmpID(idForNikolaj);
        int actualRoleID = emp.getRoleID();
        assertEquals(expectedRoleID,actualRoleID);
    }

    @Test
    void getEmpRoleName(){
        int idforNikolaj = 4;
        String expectedRoleName = "Admin";
        Emp emp = empRepository.getEmpFromEmpID(idforNikolaj);
        String actualRoleName = emp.getRoleName();
        assertEquals(expectedRoleName,actualRoleName);
    }

    @Test
    void getEmpSkillList(){
        int idForNikolaj = 4;
        List<Skill> epectedSkillList = new ArrayList<>(List.of(new Skill(1,"Scrum Master"),new Skill(2,"Project Manager"),new Skill(3,"Java developer")));
        Emp emp = empRepository.getEmpFromEmpID(idForNikolaj);
        List<Skill> actualSkillList = emp.getSkillList();
        assertEquals(epectedSkillList,actualSkillList);
    }

    @Test
    void createEmp(){
    Emp emp = new Emp();
    emp.setName("Mogens");
    emp.setEmail("mogens@Alphasolutions.com");
    emp.setPassword("123");
    emp.setRoleID(1);
    emp.setSkillList(List.of(new Skill(1,"Scrum Master")));
    empRepository.addEmp(emp);
    assertNotNull(emp);
    }

    @Test
    void updateEmp(){
    String expectedName = "Mogens";
    String expectedEmail = "Mogens@Alphasolutions.com";
    String expectedPassword = "987";
    int expectedRoleID = 2;

    Emp emp = empRepository.getEmpFromEmpID(4);
    emp.setName(expectedName);
    emp.setEmail(expectedEmail);
    emp.setPassword(expectedPassword);
    emp.setRoleID(expectedRoleID);

    empRepository.updateEmp(emp);

    String actualName = emp.getName();
    String actualEmail = emp.getEmail();
    String actualPassword = emp.getPassword();
    int actualRoleID = emp.getRoleID();

    assertEquals(expectedName,actualName);
    assertEquals(expectedEmail,actualEmail);
    assertEquals(expectedPassword,actualPassword);
    assertEquals(expectedRoleID,actualRoleID);
    }

    @Test
    void getRoles(){
        int expectedSize = 2;
        int actualSize = empRepository.getRoles().size();
        assertEquals(expectedSize,actualSize);
    }

    @Test
    void getSkills(){
        int expectedSize = 4;
        int actualSize = empRepository.getSkills().size();
        assertEquals(expectedSize,actualSize);
    }

    @Test
    void addSkill(){
        Skill skill = new Skill();
        skill.setSkillName("Test Skill");
        empRepository.addSkill(skill);
        assertNotNull(skill);

    }


}
