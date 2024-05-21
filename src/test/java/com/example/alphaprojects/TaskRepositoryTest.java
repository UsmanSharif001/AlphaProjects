package com.example.alphaprojects;
// <editor-fold desc="Task Test Packages and Import of libraries">

import com.example.alphaprojects.model.Task;
import com.example.alphaprojects.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

// </editor-fold>


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("h2")

public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;


    @Test
    void getTasksForSubproject() {

        // Arrange - metoden bruger et subprojectID
        int subprojectId = 1;

        // Act
        List<Task> tasksForSubproject = taskRepository.getTasks(subprojectId);

        // Assert
        assertNotNull(tasksForSubproject);
        assertFalse(tasksForSubproject.isEmpty());
        assertEquals(2, tasksForSubproject.size());

    }

    @Test
    void addTasks() {

        //Arrange - Creating a testTask
        Task testTask = new Task(1,1,"Create x method", "You can do it", 5, LocalDate.of(2024,2,12),"Done");
        //Act - brug af AddTask metode
        taskRepository.addTask(testTask);
        //Assert
        assertNotNull(testTask);
    }

    @Test
    void editTask() {
        // Arrange: Opretter en task der senere skal editeres
        Task initialTask = new Task(1, 1, "Create x method", "You can do it", 5, LocalDate.of(2024, 2, 12), "Done");
        taskRepository.addTask(initialTask);

        // Act: Ændre tiden fra 5 til 10
        Task updatedTask = new Task(1, 1, "Create x method", "You can do it", 10, LocalDate.of(2024, 2, 12), "Done");
        taskRepository.editTask(updatedTask);
        Task actualTask = taskRepository.getTaskFromTaskID(1);

        // Assert: Tjekker om tiden er blevet opdateret.
        assertEquals(10, actualTask.getTaskEstimate());

    }










}
