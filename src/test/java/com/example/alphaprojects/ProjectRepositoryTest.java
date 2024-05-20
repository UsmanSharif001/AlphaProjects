package com.example.alphaprojects;

import com.example.alphaprojects.Exceptions.ProjectAddException;
import com.example.alphaprojects.Exceptions.ProjectEditException;
import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.repositories.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("h2")
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void getListOfProjects(){
        List<Project> projectList = projectRepository.getListOfProjects();
        int expectedNumberOfProjects = 1;
        Assertions.assertEquals(expectedNumberOfProjects, projectList.size());
    }

    // Test projects:
    Project testProject = new Project(2, 3, "Usman", "testName", "this is a testproject", 0, 0, LocalDate.now(), "DONE");
    Project updatedTestProject = new Project(2, 3, "Usman", "newTestName", "this is a testproject", 0, 0, LocalDate.now(), "DONE");

    @Test // Er den her ok?
    void addProject() throws ProjectAddException {
        projectRepository.addNewProject(testProject);
        Project foundProject = projectRepository.getProjectFromProjectID(testProject.getProjectID());
        Assertions.assertEquals(testProject.getProjectName(), foundProject.getProjectName());
    }


    @Test // Er det her godt nok til at teste edit?
    void editProject() throws ProjectEditException {
        projectRepository.editProject(updatedTestProject);
        Assertions.assertEquals(updatedTestProject.getProjectName(), "newTestName");
    }

    @Test // Er den for lang?
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
    void getProjectManagerName(){
        int idForUsman = 3;
        String expectedName = "Usman";
        String actualName = projectRepository.getProjectManagerName(idForUsman);

        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void getManagerID(){
        String projectManagerName = "Usman";
        int expectedID = 3;
        int actualID = projectRepository.getManagerID(projectManagerName);

        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    void getProjectFromProjectID(){
        int projectIDforAlphaProject = 1;
        Project project = projectRepository.getProjectFromProjectID(projectIDforAlphaProject);

        String expectedProjectName = "Alpha Project";
        String actualProjectName = project.getProjectName();

        Assertions.assertEquals(expectedProjectName, actualProjectName);
    }

    @Test // TODO: Tilføj et arkiveret projekt til H2 DB
    void getListOfArchivedProjects() throws ProjectAddException {
        testProject.setProjectStatus("archived");
        projectRepository.addNewProject(testProject);

        int expectedSize = 1;
        int actualSize = projectRepository.getListOfArchivedProjects().size();

        Assertions.assertEquals(expectedSize, actualSize);

    }

}
