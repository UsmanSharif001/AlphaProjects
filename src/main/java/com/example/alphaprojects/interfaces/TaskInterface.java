package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.model.Task;

import java.util.List;

public interface TaskInterface {
    void addTask(Task newTask);


    void editTask(Task task);

    Task getTask(int taskID);

    List<Task> getTasks(int subprojectID);

    void deleteTask(int taskID);
}
