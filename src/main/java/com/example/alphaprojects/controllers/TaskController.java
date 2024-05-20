package com.example.alphaprojects.controllers;


import com.example.alphaprojects.model.*;
import com.example.alphaprojects.services.EmpService;
import com.example.alphaprojects.services.ProjectService;
import com.example.alphaprojects.services.SubprojectService;
import com.example.alphaprojects.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class TaskController {

    private final SubprojectService subprojectService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final EmpService empService;

    public TaskController(TaskService taskService, SubprojectService subprojectService, ProjectService projectService, EmpService empService) {
        this.taskService = taskService;
        this.subprojectService = subprojectService;
        this.projectService = projectService;
        this.empService = empService;
    }

    // Det her må kunne gøres smartere:
    @GetMapping("/{subprojectid}/tasks")
    public String getTasks(@PathVariable int subprojectid, Model model) {
        // Retrieve list of tasks for the subproject
        List<Task> listOfTasks = taskService.getTaskList(subprojectid);

        // Fetch project and subproject details
        Subproject subproject = subprojectService.getSubprojectFromSubprojectID(subprojectid);
        int projectID = subproject.getProjectID();
        Project project = projectService.getProjectFromProjectID(projectID);
        String projectName = project.getProjectName();
        String subprojectName = subproject.getSubprojectName();

        // For each task, retrieve the list of employees and their skills
        Map<Integer, List<EmpSkillDTO>> employeesForTasks = new HashMap<>();
        for (Task task : listOfTasks) {
            int taskID = task.getTaskID();
            List<EmpSkillDTO> employees = taskService.getEmployeesForTask(taskID);
            employeesForTasks.put(taskID, employees);
        }

        // Add attributes to the model
        model.addAttribute("listOfTasks", listOfTasks);
        model.addAttribute("subprojectid", subprojectid);
        model.addAttribute("projectName", projectName);
        model.addAttribute("projectID", projectID);
        model.addAttribute("subprojectName", subprojectName);
        model.addAttribute("employeesForTasks", employeesForTasks);

        // Return the view
        return "tasks";
    }

    @GetMapping("/{subprojectid}/addtask")
    public String addTask(@PathVariable int subprojectid, Model model) {
        Task newTask = new Task();
        model.addAttribute("task", newTask);
        model.addAttribute("subprojectID", subprojectid);
        List<EmpSkillDTO> employees = taskService.getAllEmployeesForTask();
        model.addAttribute("employees", employees);
        return "addTask";
    }


    /*@PostMapping("/{subprojectid}/savetask")
    public String saveTask(@ModelAttribute Task newTask, @PathVariable int subprojectid) {
        newTask.setSubprojectID(subprojectid);
        newTask.setSelectedEmpIDs();
        taskService.createTask(newTask);
        return "redirect:/" + subprojectid + "/tasks";
    }*/

    @PostMapping("/{subprojectid}/savetask")
    public String saveTask(@ModelAttribute Task newTask, @PathVariable int subprojectid,
                           @RequestParam(value = "selectedEmployees", required = false) List<Integer> selectedEmployeeIds) {
        newTask.setSubprojectID(subprojectid);
        newTask.setSelectedEmpIDs(selectedEmployeeIds); // Set the selected employees
        taskService.createTask(newTask);
        return "redirect:/" + subprojectid + "/tasks";
    }

 @GetMapping("/{taskid}/edittask")
 public String editTask(@PathVariable int taskid, Model model) {
     Task editTask = taskService.getTaskFromTaskID(taskid);
     int subprojectid = editTask.getSubprojectID();
     // Fetch all employees and their skills
     List<EmpSkillDTO> allEmployees = taskService.getAllEmployeesForTask();
     // Fetch assigned employees for the task
     List<EmpSkillDTO> assignedEmployeesWithSkills = taskService.getEmployeesForTask(taskid);
     editTask.setAssignedEmployeesWithSkills(assignedEmployeesWithSkills);
     model.addAttribute("task", editTask);
     model.addAttribute("subprojectid", subprojectid);
     model.addAttribute("employees", allEmployees);
     return "editTask";
 }



    @PostMapping("/{subprojectid}/updatetask")
    public String updateTask(@ModelAttribute Task task, @PathVariable int subprojectid,
                             @RequestParam(value = "selectedEmployees", required = false) List<Integer> selectedEmployeeIds) {
        task.setSubprojectID(subprojectid);

        // Get the existing assigned employee IDs from the task
        List<Integer> existingEmployeeIds = task.getSelectedEmpIDs();

        // Initialize the existingEmployeeIds list if it's null
        if(existingEmployeeIds == null) {
            existingEmployeeIds = new ArrayList<>();
        }

        // Merge the existing and selected employee IDs
        if (selectedEmployeeIds != null) {
            existingEmployeeIds.addAll(selectedEmployeeIds);
        }

        // Set the merged list of employee IDs back to the task
        task.setSelectedEmpIDs(existingEmployeeIds);

        // Update the task
        taskService.updateTask(task);

        return "redirect:/" + subprojectid + "/tasks";
    }





    @GetMapping("/{taskid}/deletetask")
    public String deleteTask(@PathVariable int taskid, Model model) {
        int subprojectID = taskService.getSubprojectIDFromTask(taskid);
        model.addAttribute("subprojectid", taskid);
        taskService.deleteTask(taskid);
        return "redirect:/" + subprojectID + "/tasks";
    }



}
