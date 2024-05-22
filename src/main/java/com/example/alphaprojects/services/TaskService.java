package com.example.alphaprojects.services;
import com.example.alphaprojects.Exceptions.TaskAddException;
import com.example.alphaprojects.Exceptions.TaskEditException;
import com.example.alphaprojects.model.EmpSkillDTO;
import com.example.alphaprojects.model.Task;
import com.example.alphaprojects.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    // <editor-fold desc="Dependency Injection">
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    // </editor-fold>

    // <editor-fold desc="Task CRUD-Management Methods">
    public void createTask(Task task) throws TaskAddException {
        taskRepository.addTask(task);
    }

    public void updateTask(Task task) throws TaskEditException {
        taskRepository.editTask(task);
    }

    public List<Task> getTaskList(int subprojectID) {
        return taskRepository.getTasks(subprojectID);
    }
    public void deleteTask(int taskID) {
        taskRepository.deleteTask(taskID);
    }
    // </editor-fold>

    // <editor-fold desc="Assisting Task Methods">
    public Task getTaskFromTaskID(int taskID) {
        return taskRepository.getTaskFromTaskID(taskID);
    }

    public int getSubprojectIDFromTask(int taskID) {
        return taskRepository.getSubprojectIDFromTask(taskID);
    }

    public List<EmpSkillDTO> getAllEmployeesForTask() {
        return taskRepository.getAllEmployeesWithSkills();
    }

    public List<EmpSkillDTO> getEmployeesForTask(int taskID) {
        return taskRepository.getEmployeesForTask(taskID);
    }
    // </editor-fold>
}

