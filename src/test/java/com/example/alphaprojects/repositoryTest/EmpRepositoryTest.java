package com.example.alphaprojects.repositoryTest;


import com.example.alphaprojects.exceptions.EmpDeleteException;
import com.example.alphaprojects.model.EmpDTO;
import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.Skill;
import com.example.alphaprojects.repositories.EmpRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmpRepositoryTest {

    @Autowired
    EmpRepository empRepository;


    @Test
    @Order(1)
    void loginCorrect(){
    String email = "Nikolaj@gmail.com";
    String password = "123";
    Emp emp = empRepository.login(email, password);
        assertNotNull(emp);

    }

    @Test
    @Order(2)
    void loginWrongEmail(){
        String email = "Niko@gmail.com";
        String password = "123";
        Emp emp = empRepository.login(email, password);
        assertNull(emp);
    }


    @Test
    @Order(3)
    void loginWrongPassword(){
        String email = "Nikolaj@gmail.com";
        String password = "Forkert kode";
        Emp emp = empRepository.login(email, password);
        assertNull(emp);
    }

    @Test
    @Order(4)
    void getAllEmp(){
        int actualSize = empRepository.getAllEmp().size();
        int expectedSize = 4;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Order(5)
    void getEmpByCorrectEmpID(){
        int idForNikolaj = 4;
        EmpDTO expectedEmpDTO = empRepository.getEmpFromEmpID(idForNikolaj);
        assertNotNull(expectedEmpDTO);
    }

    @Test
    @Order(6)
    void getEmpByIncorrectEmpID(){
        int incorrectEmpID = 10;
        EmpDTO expectedIncorrectEmpDTO = empRepository.getEmpFromEmpID(incorrectEmpID);
        assertNull(expectedIncorrectEmpDTO);
    }

    @Test
    @Order(7)
    void getEmpName(){
        int idForNikolaj = 4;
        String expectedName = "Nikolaj";
        EmpDTO empDTO = empRepository.getEmpFromEmpID(idForNikolaj);
        String actualName = empDTO.getName();
        assertEquals(expectedName,actualName);

    }

    @Test
    @Order(8)
    void getEmpEmail(){
        int idForNikolaj = 4;
        String expectedEmail = "Nikolaj@gmail.com";
        EmpDTO empDTO = empRepository.getEmpFromEmpID(idForNikolaj);
        String actualEmail = empDTO.getEmail();
        assertEquals(expectedEmail,actualEmail);
    }

    @Test
    @Order(9)
    void getEmpPassword(){
        int idForNikolaj = 4;
        String expectedPassword = "123";
        EmpDTO empDTO = empRepository.getEmpFromEmpID(idForNikolaj);
        String actualPassword = empDTO.getPassword();
        assertEquals(expectedPassword,actualPassword);
    }

    @Test
    @Order(10)
    void getEmpRoleID(){
        int idForNikolaj = 4;
        int expectedRoleID = 1;
        EmpDTO empDTO = empRepository.getEmpFromEmpID(idForNikolaj);
        int actualRoleID = empDTO.getRoleID();
        assertEquals(expectedRoleID,actualRoleID);
    }

    @Test
    @Order(11)
    void getEmpRoleName(){
        int idforNikolaj = 4;
        String expectedRoleName = "Admin";
        EmpDTO empDTO = empRepository.getEmpFromEmpID(idforNikolaj);
        String actualRoleName = empDTO.getRoleName();
        assertEquals(expectedRoleName,actualRoleName);
    }

    @Test
    @Order(12)
    void getEmpSkillList(){
        int idForNikolaj = 4;
        List<Skill> expectedSkillList = new ArrayList<>(List.of(new Skill(1,"Scrum Master"),new Skill(2,"Project Manager"),new Skill(3,"Java developer")));
        EmpDTO empDTO = empRepository.getEmpFromEmpID(idForNikolaj);
        List<Skill> actualSkillList = empDTO.getSkillList();
        assertEquals(expectedSkillList,actualSkillList);
    }

    @Test
    @Order(13)
    void createEmp(){
    EmpDTO empDTO = new EmpDTO();
    empDTO.setEmpID(5);
    empDTO.setName("Niels");
    empDTO.setEmail("Niels@Alphasolutions.com");
    empDTO.setPassword("123");
    empDTO.setRoleID(1);
    empDTO.setSkillList(List.of(new Skill(1,"Scrum Master")));
    empRepository.addEmp(empDTO);

    EmpDTO empDTOFound = empRepository.getEmpFromEmpID(empDTO.getEmpID());

    assertNotNull(empDTOFound);
    assertEquals(empDTOFound.getName(), empDTO.getName());
    }

    @Test
    @Order(14)
    void updateEmp(){
    String expectedName = "Mogens";
    String expectedEmail = "Mogens@Alphasolutions.com";
    String expectedPassword = "987";
    int expectedRoleID = 2;

    EmpDTO empDTO = empRepository.getEmpFromEmpID(4);
    empDTO.setName(expectedName);
    empDTO.setEmail(expectedEmail);
    empDTO.setPassword(expectedPassword);
    empDTO.setRoleID(expectedRoleID);

    empRepository.updateEmp(empDTO);

    String actualName = empDTO.getName();
    String actualEmail = empDTO.getEmail();
    String actualPassword = empDTO.getPassword();
    int actualRoleID = empDTO.getRoleID();

    assertEquals(expectedName,actualName);
    assertEquals(expectedEmail,actualEmail);
    assertEquals(expectedPassword,actualPassword);
    assertEquals(expectedRoleID,actualRoleID);
    }

    @Test
    @Order(15)
    void getRoles(){
        int expectedSize = 2;
        int actualSize = empRepository.getRoles().size();
        assertEquals(expectedSize,actualSize);
    }

    @Test
    @Order(16)
    void getSkills(){
        int expectedSize = 4;
        int actualSize = empRepository.getSkills().size();
        assertEquals(expectedSize,actualSize);
    }

    @Test
    @Order(17)
    void addSkill(){
        Skill skill = new Skill();
        skill.setSkillName("Test Skill");
        empRepository.addSkill(skill);
        assertNotNull(skill);

    }

    @Test
    @Order(18)
    void deleteEmp() throws EmpDeleteException {
    int empIDforNikolaj = 4;
    empRepository.deleteEmp(empIDforNikolaj);
    int actualSize = empRepository.getAllEmp().size();
    int expectedSize = 4;
    assertEquals(expectedSize,actualSize);
    }

    @Test
    @Order(19)
    void tryToDeleteProjectManager() {
        int empIDforProjectManager = 1;
       assertThrows(EmpDeleteException.class, () -> empRepository.deleteEmp(empIDforProjectManager));

    }


}
