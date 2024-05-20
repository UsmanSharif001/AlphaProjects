package com.example.alphaprojects;

import com.example.alphaprojects.model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    public void testSetSelectedEmpIDs() {
        // Create a sample list of employee IDs
        List<Integer> selectedEmployeeIds = new ArrayList<>();
        selectedEmployeeIds.add(1);
        selectedEmployeeIds.add(2);
        selectedEmployeeIds.add(3);

        // Create a new Task object
        Task task = new Task();
        // Set the selected employee IDs using the setSelectedEmpIDs method
        task.setSelectedEmpIDs(selectedEmployeeIds);

        // Retrieve the selected employee IDs from the Task object
        List<Integer> retrievedEmployeeIds = task.getSelectedEmpIDs();

        // Compare the retrieved list with the original list to verify correctness
        assertEquals(selectedEmployeeIds, retrievedEmployeeIds);
    }
}
