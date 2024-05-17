package com.example.alphaprojects.controllers;


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
    public String getTasks(@PathVariable int subprojectid,Model model) {
        List<Task> listOfTasks = taskService.getTaskList(subprojectid);
        model.addAttribute("listOfTasks", listOfTasks);
        model.addAttribute("subprojectid", subprojectid);
        return "tasks";
    }


    @GetMapping("/{subprojectid}/addtask")
    public String addTask(@PathVariable int subprojectid, Model model) {
        Task newTask = new Task();
        model.addAttribute("task", newTask);
        model.addAttribute("subprojectID", subprojectid);
        return "addTask";
    }

    @PostMapping("/{subprojectid}/savetask")
    public String saveTask(@ModelAttribute Task newTask, @PathVariable int subprojectid) {
        newTask.setSubprojectID(subprojectid);
        taskService.createTask(newTask);
        return "redirect:/" + subprojectid + "/tasks";
    }
 //Skal have subrojectID med så man ved hvilket subprojekt tasken hører til
    @GetMapping("/{taskid}/edittask")
    public String editTask(@PathVariable int taskid, Model model) {
        Task editTask = taskService.getTaskFromTaskID(taskid);
        int subprojectid = editTask.getSubprojectID(); //Denne her er jeg i tvivl om hvorvidt skal være der fordi subproject id skal med i url
        model.addAttribute("task", editTask);
        model.addAttribute("subprojectid", subprojectid);
        System.out.println(subprojectid);
        return "updateTask";
    }

    @PostMapping("/{subprojectid}/updatetask")
    public String updateTask(@ModelAttribute Task task, @PathVariable int subprojectid) {
        task.setSubprojectID(subprojectid);
        taskService.updateTask(task);
        return "redirect:/" + subprojectid + "/tasks";
    }
    ///Prøvet at give den subprojektid med men virker ikke? Den får ikke subprojectid med
    @GetMapping("/{taskid}/deletetask")
    public String deleteTask(@PathVariable int taskid, Model model) {
        int subprojectID = taskService.getSubprojectIDFromTask(taskid);
        model.addAttribute("subprojectid", taskid);
        taskService.deleteTask(taskid);
        return "redirect:/" + subprojectID + "/tasks";
    }

}
