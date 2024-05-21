package com.example.alphaprojects;

import com.example.alphaprojects.Exceptions.SubprojectAddException;
import com.example.alphaprojects.Exceptions.SubprojectEditException;
import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.repositories.SubprojectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
public class SubprojectRepositoryTest {

    @Autowired
    SubprojectRepository subprojectRepository;

    @Test
    void getSubprojects(){
        //Arrange
        int projectIdForProject1 = 1;

        //Act
        List<Subproject> subprojectsWithProjectId1 = subprojectRepository.getSubprojects(projectIdForProject1);
        List<Subproject> expectedSubprojectsFound = new ArrayList<>(List.of(new Subproject(1,1,"Backend", "New funky backend", 25, 0, LocalDate.of(2024,12,12), " In_progress")));

        //Assert
        assertEquals(expectedSubprojectsFound.size(), subprojectsWithProjectId1.size());

        //Sammenligning af nøjagtige elementer i subprojects liste
        for (int i = 0; i < expectedSubprojectsFound.size(); i++) {
            Subproject expectedSubproject = expectedSubprojectsFound.get(i);
            Subproject actualSubproject = subprojectsWithProjectId1.get(i);

            assertEquals(expectedSubproject.getSubprojectID(), actualSubproject.getSubprojectID(), "Subproject ID mismatch at index " + i);
            assertEquals(expectedSubproject.getProjectID(), actualSubproject.getProjectID(), "Project ID mismatch at index " + i);
            assertEquals(expectedSubproject.getSubprojectName(), actualSubproject.getSubprojectName(), "Subproject Name mismatch at index " + i);
            assertEquals(expectedSubproject.getSubprojectDescription(), actualSubproject.getSubprojectDescription(), "Subproject description mismatch at index " + i);
            assertEquals(expectedSubproject.getSubprojectTimeEstimate(), actualSubproject.getSubprojectTimeEstimate(), "Subproject Timeestimate mismatch at index " + i);
            assertEquals(expectedSubproject.getSubprojectDedicatedHours(), actualSubproject.getSubprojectDedicatedHours(), "Subproject Dedicated Hours mismatch at index " + i);
            assertEquals(expectedSubproject.getSubprojectDeadline(), actualSubproject.getSubprojectDeadline(), "Subproject Deadline  mismatch at index " + i);
            assertEquals(expectedSubproject.getSubprojectStatus(), actualSubproject.getSubprojectStatus(), "Subproject status  mismatch at index " + i);
        }
    }

    @Test
    void createSubproject() throws SubprojectAddException {
        //Arrange
        Subproject testSubproject = new Subproject(1,1,"Backend", "New funky backend", 25, 0, LocalDate.of(2024,12,12), " In_progress");
        //Act
        subprojectRepository.createSubproject(testSubproject);
        //Assert
        assertNotNull(testSubproject);
    }

    @Test
    void editSubproject() throws SubprojectEditException {
        //Arrange
        Subproject testSubproject = new Subproject(1,1,"Backend", "New funky backend", 25, 0, LocalDate.of(2024,12,12), " In_progress");
        testSubproject.setSubprojectTimeEstimate(30);

        //Act
        subprojectRepository.editSubproject(testSubproject);
        Subproject actualSubproject = subprojectRepository.getSubprojectFromSubprojectID(1);

        //Assert
        assertEquals(testSubproject.getSubprojectTimeEstimate(), actualSubproject.getSubprojectTimeEstimate());
    }

    @Test
    void deleteSubproject(){
        //Arrange
        Subproject testSubproject = new Subproject(1,1,"Backend", "New funky backend", 25, 0, LocalDate.of(2024,12,12), " In_progress");

        //Act
        subprojectRepository.deleteSubproject(testSubproject.getSubprojectID());
        Subproject actualSubproject = subprojectRepository.getSubprojectFromSubprojectID(testSubproject.getSubprojectID());

        //Assert
        assertNull(actualSubproject);
    }

    /*@Test
    void deleteTasksWithSubprojectID(){
        //Arrange
        Subproject testSubproject = new Subproject(1,1,"Backend", "New funky backend", 25, 0, LocalDate.of(2024,12,12), " In_progress");

        //Act
        subprojectRepository.deleteSubproject(testSubproject.getSubprojectID());
        Subproject actualSubproject = subprojectRepository.getSubprojectFromSubprojectID(testSubproject.getSubprojectID());

        //Assert
        assertNull(actualSubproject);

    }*/

    @Test
    void getSubprojectFromSubprojectID(){
        //Arrange
        int existingSubprojectID = 1;
        int notExistingSubprojectID = 0;
        //Act
        Subproject found = subprojectRepository.getSubprojectFromSubprojectID(existingSubprojectID);
        Subproject notFound = subprojectRepository.getSubprojectFromSubprojectID(notExistingSubprojectID);
        //Assert
        assertNotNull(found);
        assertNull(notFound);
    }

}
