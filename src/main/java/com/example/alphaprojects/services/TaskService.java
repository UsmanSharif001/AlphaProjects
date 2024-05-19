package com.example.alphaprojects.services;

import com.example.alphaprojects.model.EmpSkillDTO;
import com.example.alphaprojects.model.Task;
import com.example.alphaprojects.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(Task task) {
        taskRepository.addTask(task);
    }

    public void updateTask(Task task) {
        taskRepository.editTask(task);
    }

    public Task getTask(int taskID) {
        return taskRepository.getTask(taskID);
    }

    public List<Task> getTaskList(int subprojectID) {
        return taskRepository.getTasks(subprojectID);
    }
    public void deleteTask(int taskID) {
        taskRepository.deleteTask(taskID);
    }
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
}

