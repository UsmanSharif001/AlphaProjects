package com.example.alphaprojects.controllers;


import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.model.Task;
import com.example.alphaprojects.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{subprojectid}/tasks")
    public String getTasks(@PathVariable int subprojectid, Model model) {
        List<Task> listOfTasks = taskService.getTaskList(subprojectid);
        model.addAttribute("listOfTasks", listOfTasks);
        model.addAttribute("subprojectid", subprojectid);
        return "opgaver";
    }


    @GetMapping("/{subprojectid}/addtask")
    public String addTask(@PathVariable int subprojectid, Model model) {
        Task newTask = new Task();
        model.addAttribute("task", newTask);
        model.addAttribute("subprojectID", subprojectid);
        return "opretopgave";
    }

    @PostMapping("/{subprojectid}/savetask")
    public String saveTask(@ModelAttribute Task newTask, @PathVariable int subprojectid) {
        newTask.setSubprojectID(subprojectid);
        taskService.createTask(newTask);
        return "redirect:/" + subprojectid + "/tasks";
    }

    @GetMapping("/{taskid}/edittask")
    public String editTask( @PathVariable int taskid, Model model) {
        Task editTask = taskService.getTask(taskid);
        model.addAttribute("taskid", taskid);
        model.addAttribute("task", editTask);
        return "opdateropgave";
    }

    @PostMapping("/{subprojectid}/updatetask")
    public String updateTask(@ModelAttribute Task task, Model model, @PathVariable int subprojectid) {
        task.setSubprojectID(subprojectid);
        model.addAttribute("listOfTasks", taskService.getTaskList(subprojectid));
        //model.addAttribute("task",task);
        taskService.updateTask(task);
        //System.out.println("test");
        return "opgaver";
    }

    @GetMapping("/{subprojectid}/{taskid}/deletetask")
    public String deleteTask(@PathVariable int subprojectid, @PathVariable int taskid, Model model) {
        taskService.deleteTask(taskid);
        model.addAttribute("subprojectID", subprojectid);
        return "redirect:/" + subprojectid + "/opgaver";
    }

}
