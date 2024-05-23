package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.exceptions.TaskAddException;
import com.example.alphaprojects.exceptions.TaskEditException;
import com.example.alphaprojects.model.EmpSkillDTO;
import com.example.alphaprojects.model.Task;

import java.util.List;

public interface TaskInterface {
    void addTask(Task newTask) throws TaskAddException;


    void editTask(Task task) throws TaskEditException;

    List<Task> getTasks(int subprojectID);

    void deleteTask(int taskID);

    Task getTaskFromTaskID(int taskid);

    int getSubprojectIDFromTask(int taskID);

    List<EmpSkillDTO> getAllEmployeesWithSkills();


    List<EmpSkillDTO> getEmployeesForTask(int taskID);
}
