package com.example.alphaprojects;

import com.example.alphaprojects.exceptions.ProjectAddException;
import com.example.alphaprojects.exceptions.ProjectEditException;
import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.repositories.ProjectRepositoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("h2")
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepositoryRepository projectRepository;

    // Test projects:
    Project testProject = new Project(2, 3, "Usman", "testName", "this is a testproject", 0, 0, LocalDate.now(), "ARCHIVED");
    Project updatedTestProject = new Project(2, 3, "Usman", "newTestName", "this is a testproject", 0, 0, LocalDate.now(), "DONE");

    @Test
    @Order(1)
    void getListOfProjects(){
        List<Project> projectList = projectRepository.getListOfProjects();
        int expectedNumberOfProjects = 1;
        Assertions.assertEquals(expectedNumberOfProjects, projectList.size());
    }

    @Test
    @Order(6)
    void addProject() throws ProjectAddException {
        projectRepository.addNewProject(testProject);
        Project foundProject = projectRepository.getProjectFromProjectID(testProject.getProjectID());
        Assertions.assertEquals(testProject.getProjectName(), foundProject.getProjectName());
    }

    @Test
    @Order(8)
    void editProject() throws ProjectEditException {
        projectRepository.editProject(updatedTestProject);
        Assertions.assertEquals(updatedTestProject.getProjectName(), "newTestName");
    }

    @Test
    @Order(2)
    void getListOfProjectManagers(){
        int expectedSize = 2;
        int actualSize = projectRepository.getProjectManagers().size();
        String expectedName = "Usman";
        String actualName = projectRepository.getProjectManagers().get(0).getProjectManagerName();
        int expectedID = 3;
        int actualID = projectRepository.getProjectManagers().get(0).getProjectManagerID();

        Assertions.assertEquals(expectedSize, actualSize);
        Assertions.assertEquals(expectedName, actualName);
        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    @Order(3)
    void getProjectManagerName(){
        int idForUsman = 3;
        String expectedName = "Usman";
        String actualName = projectRepository.getProjectManagerName(idForUsman);

        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    @Order(4)
    void getManagerID(){
        String projectManagerName = "Usman";
        int expectedID = 3;
        int actualID = projectRepository.getManagerID(projectManagerName);

        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    @Order(5)
    void getProjectFromProjectID(){
        int projectIDforAlphaProject = 1;
        Project project = projectRepository.getProjectFromProjectID(projectIDforAlphaProject);

        String expectedProjectName = "Alpha Project";
        String actualProjectName = project.getProjectName();

        Assertions.assertEquals(expectedProjectName, actualProjectName);
    }
    @Test // TODO: Tilføj et arkiveret projekt til H2 DB
    @Order(7)
    void getListOfArchivedProjects() throws ProjectAddException {
        testProject.setProjectStatus("archived");
        projectRepository.addNewProject(testProject);

        int expectedSize = 1;
        int actualSize = projectRepository.getListOfArchivedProjects().size();

        Assertions.assertEquals(expectedSize, actualSize);
    }

}
