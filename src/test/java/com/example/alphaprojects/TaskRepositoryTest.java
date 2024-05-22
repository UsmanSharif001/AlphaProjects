package com.example.alphaprojects;
// <editor-fold desc="Task Test Packages and Import of libraries">

import com.example.alphaprojects.Exceptions.TaskAddException;
import com.example.alphaprojects.Exceptions.TaskEditException;
import com.example.alphaprojects.model.Task;
import com.example.alphaprojects.repositories.TaskRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// </editor-fold>


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("h2")

public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;


   /* @Test
    @Order(1)
    void getTasksForSubproject() {

        // Arrange - metoden bruger et subprojectID
        int subprojectId = 1;

        // Act
        List<Task> tasksForSubproject = taskRepository.getTasks(subprojectId);

        // Assert
        assertNotNull(tasksForSubproject);
        assertFalse(tasksForSubproject.isEmpty());
        assertEquals(2, tasksForSubproject.size());

    } */

   /* @Test
    @Order(2)
    void addTasks() throws TaskAddException {

        //Arrange - Creating a testTask
        Task testTask = new Task(1,1,"Create x method", "You can do it", 5, LocalDate.of(2024,2,12),"Done");
        //Act - brug af AddTask metode
        taskRepository.addTask(testTask);
        //Assert
        assertNotNull(testTask);
    } */
/*
    @Test
    @Order(3)
    void editTask() throws TaskEditException, TaskAddException {
        // Arrange: Opretter en task der senere skal editeres
        Task TestTask = new Task(1, 1, "Create x method", "You can do it", 5, LocalDate.of(2024, 2, 12), "Done");
        taskRepository.addTask(TestTask);

        // Act: Ændre tiden fra 5 til 10
        Task updatedTestTask = new Task(1, 1, "Create x method", "You can do it", 10, LocalDate.of(2024, 2, 12), "Done");
        taskRepository.editTask(updatedTestTask);
        Task actualTask = taskRepository.getTaskFromTaskID(1);

        // Assert: Tjekker om tiden er blevet opdateret.
        assertEquals(10, actualTask.getTaskEstimate());

    } */

    @Test
    @Order(4)
    void getTaskFromTaskID() {

        //Arrange: Opretter et eksisterende og ikke eksisterende taskID
        int existingTaskID = 1;
        int notexistingTaskID = 0;

        //Act: Gøre brug af metoden getTaskFromTaskID og sætter vores test tasks i
        Task found = taskRepository.getTaskFromTaskID(existingTaskID);
        Task notFound = taskRepository.getTaskFromTaskID(notexistingTaskID);

        //Assert
        assertNotNull(found);
        assertNull(notFound);

//Comment in test2
    }

    @Test
    @Order(5)
    void deleteTask() {

        // Arrange: Opretter en task der senere skal slettes
        Task TestTask = new Task(1, 2, "Create ABC method", "You can do it", 10, LocalDate.of(2024, 2, 12), "Done");

        //Act: Sletter tasken
        taskRepository.deleteTask(TestTask.getTaskID());
        Task isThereATask = taskRepository.getTaskFromTaskID(TestTask.getTaskID());

        //Assert: Forventer at den task med det taskID er null - fordi den er blevet slettet
        assertNull(isThereATask);

    }










}
