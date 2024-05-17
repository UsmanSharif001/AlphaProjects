package com.example.alphaprojects.controllers;


import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.model.Task;
import com.example.alphaprojects.services.ProjectService;
import com.example.alphaprojects.services.SubprojectService;
import com.example.alphaprojects.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class TaskController {

    private final SubprojectService subprojectService;
    private final ProjectService projectService;
    private final TaskService taskService;

    public TaskController(TaskService taskService, SubprojectService subprojectService, ProjectService projectService) {
        this.taskService = taskService;
        this.subprojectService = subprojectService;
        this.projectService = projectService;
    }

    // Det her må kunne gøres smartere:
    @GetMapping("/{subprojectid}/tasks")
    public String getTasks(@PathVariable int subprojectid, Model model) {
        List<Task> listOfTasks = taskService.getTaskList(subprojectid);
        Subproject subproject = subprojectService.getSubprojectFromSubprojectID(subprojectid);
        int projectID = subproject.getProjectID();
        Project project = projectService.getProjectFromProjectID(projectID);
        String projectName = project.getProjectName();
        String subprojectName = subproject.getSubprojectName();
        model.addAttribute("listOfTasks", listOfTasks);
        model.addAttribute("subprojectid", subprojectid);
        model.addAttribute("projectName", projectName);
        model.addAttribute("projectID", projectID);
        model.addAttribute("subprojectName", subprojectName);
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
        return "editTask";
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
