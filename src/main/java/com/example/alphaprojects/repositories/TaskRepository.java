package com.example.alphaprojects.repositories;

import com.example.alphaprojects.interfaces.TaskInterface;
import com.example.alphaprojects.model.EmpSkillDTO;
import com.example.alphaprojects.model.Skill;
import com.example.alphaprojects.model.Task;
import com.example.alphaprojects.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Repository
public class TaskRepository implements TaskInterface {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;

    /*@Override
    public void addTask(Task newTask) {

        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "INSERT INTO task (subproject_id, task_name, task_description, task_time_estimate, task_deadline, task_status) VALUES(?,?,?,?,?,?);";

        try (PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, newTask.getSubprojectID());
            ps.setString(2, newTask.getTaskName());
            ps.setString(3, newTask.getTaskDescription());
            ps.setInt(4, newTask.getTaskEstimate());
            ps.setString(5, newTask.getTaskDeadline().toString());
            ps.setString(6, newTask.getTaskStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/

    @Override
    public void addTask(Task newTask) {
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String taskSQL = "INSERT INTO task (subproject_id, task_name, task_description, task_time_estimate, task_deadline, task_status) VALUES(?,?,?,?,?,?);";
        String empTaskSQL = "INSERT INTO task_emp (emp_id, task_id) VALUES(?,?);";

        try (PreparedStatement taskPS = con.prepareStatement(taskSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement empTaskPS = con.prepareStatement(empTaskSQL)) {

            // Insert the task
            taskPS.setInt(1, newTask.getSubprojectID());
            taskPS.setString(2, newTask.getTaskName());
            taskPS.setString(3, newTask.getTaskDescription());
            taskPS.setInt(4, newTask.getTaskEstimate());
            taskPS.setString(5, newTask.getTaskDeadline().toString());
            taskPS.setString(6, newTask.getTaskStatus());
            taskPS.executeUpdate();

            // Retrieve the auto-generated task ID
            ResultSet generatedKeys = taskPS.getGeneratedKeys();
            int taskID = -1;
            if (generatedKeys.next()) {
                taskID = generatedKeys.getInt(1);
            }

            // Insert selected employees and their associated task ID
            List<Integer> selectedEmployeeIds = newTask.getSelectedEmpIDs();
            if (selectedEmployeeIds != null) {
                for (int empId : selectedEmployeeIds) {
                    empTaskPS.setInt(1, empId);
                    empTaskPS.setInt(2, taskID);
                    empTaskPS.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public Task getTask(int taskID) {
        Task task = null;
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT * FROM task WHERE task_id = ?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, taskID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                task = new Task(
                        rs.getInt("task_id"),
                        rs.getInt("subproject_id"),
                        rs.getString("task_name"),
                        rs.getString("task_description"),
                        rs.getInt("task_time_estimate"),
                        LocalDate.parse(rs.getString("task_deadline")),
                        rs.getString("task_status")
                );
            }

            return task;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> getTasks(int subprojectID) {
        List<Task> taskList = new ArrayList<>();
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT * FROM task WHERE subproject_id = ?;";

        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, subprojectID);

            ResultSet tasksResultSet = ps.executeQuery();
            while (tasksResultSet.next()) {
                int taskId = tasksResultSet.getInt("task_id");
                Task task = new Task(
                        taskId,
                        tasksResultSet.getInt("subproject_id"),
                        tasksResultSet.getString("task_name"),
                        tasksResultSet.getString("task_description"),
                        tasksResultSet.getInt("task_time_estimate"),
                        tasksResultSet.getDate("task_deadline").toLocalDate(),
                        tasksResultSet.getString("task_status")
                );

                // Get the list of assigned employees with their skills for this task
                List<EmpSkillDTO> assignedEmployeesWithSkills = getEmployeesForTask(taskId);
                task.setAssignedEmployeesWithSkills(assignedEmployeesWithSkills);

                taskList.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return taskList;
    }


    @Override
    public void deleteTask(int taskId) {
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);

            String deleteTaskEmpSQL = "DELETE FROM task_emp WHERE task_id = ?";
            try (PreparedStatement psTaskEmp = con.prepareStatement(deleteTaskEmpSQL)) {
                psTaskEmp.setInt(1, taskId);
                psTaskEmp.executeUpdate();
                String deleteTaskSQL = "DELETE FROM task WHERE task_id = ?";
                PreparedStatement psTask = con.prepareStatement(deleteTaskSQL);
                    psTask.setInt(1, taskId);
                    psTask.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }


    @Override
    public Task getTaskFromTaskID(int taskid) {
        Task foundTask;
        Connection con = ConnectionManager.getConnection(db_url,username,pwd);

        String SQL = "SELECT subproject_id, task_name, task_description, task_time_estimate, task_deadline, task_status FROM task WHERE task_id = ?;";

        try(PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, taskid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int subprojectID = rs.getInt("subproject_id");
                String name = rs.getString("task_name");
                String description = rs.getString("task_description");
                int timeEstimate = rs.getInt("task_time_estimate");
                LocalDate deadline = LocalDate.parse(rs.getString("task_deadline"));
                String status = rs.getString("task_status").toUpperCase();
                foundTask = new Task(taskid, subprojectID,name,description,timeEstimate,deadline,status);

                // Retrieve employee skills for the task
                List<EmpSkillDTO> assignedEmployeesWithSkills = getEmployeesForTask(taskid);

                // Set assigned employees with skills for the task
                foundTask.setAssignedEmployeesWithSkills(assignedEmployeesWithSkills);
                return foundTask;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    public int getSubprojectIDFromTask(int taskID) {
        int subprojectID = 0;
        Connection con = ConnectionManager.getConnection(db_url,username,pwd);

        String SQL = "SELECT subproject_id FROM AlphaSolution_db.task WHERE task_id = ?";

        try (PreparedStatement ps = con.prepareStatement(SQL)) {
                ps.setInt(1, taskID);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    subprojectID = rs.getInt("subproject_id");
                }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subprojectID;
    }

    @Override
    public List<EmpSkillDTO> getAllEmployeesWithSkills() {
        List<EmpSkillDTO> emps = new ArrayList<>();
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = """
            SELECT emp.emp_id, emp.emp_name, skill.skill_id, skill.skill_name 
            FROM emp
            JOIN emp_skills ON emp.emp_id = emp_skills.emp_id
            JOIN skill ON emp_skills.skill_id = skill.skill_id
            """;
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int empID = rs.getInt("emp_id");
                String empName = rs.getString("emp_name");
                int skillID = rs.getInt("skill_id");
                String skillName = rs.getString("skill_name");

                EmpSkillDTO empSkillDTO = emps.stream()
                        .filter(e -> e.getEmpID() == empID)
                        .findFirst()
                        .orElseGet(() -> {
                            EmpSkillDTO newEmp = new EmpSkillDTO(empID, empName, new ArrayList<>());
                            emps.add(newEmp);
                            return newEmp;
                        });

                empSkillDTO.getSkills().add(new Skill(skillID, skillName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return emps;
}

    @Override
    public List<EmpSkillDTO> getEmployeesForTask(int taskID) {
        List<EmpSkillDTO> emps = new ArrayList<>();
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = """
            SELECT emp.emp_id, emp.emp_name, skill.skill_id, skill.skill_name 
            FROM emp
            JOIN emp_skills ON emp.emp_id = emp_skills.emp_id
            JOIN skill ON emp_skills.skill_id = skill.skill_id
            JOIN task_emp ON emp.emp_id = task_emp.emp_id
            WHERE task_emp.task_id = ?
            """;
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, taskID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int empID = rs.getInt("emp_id");
                String empName = rs.getString("emp_name");
                int skillID = rs.getInt("skill_id");
                String skillName = rs.getString("skill_name");

                // Check if the employee already exists in the list
                EmpSkillDTO empSkillDTO = emps.stream()
                        .filter(e -> e.getEmpID() == empID)
                        .findFirst()
                        .orElseGet(() -> {
                            EmpSkillDTO newEmp = new EmpSkillDTO(empID, empName, new ArrayList<>());
                            emps.add(newEmp);
                            return newEmp;
                        });

                // Add the skill to the employee's skill list
                empSkillDTO.getSkills().add(new Skill(skillID, skillName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return emps;
    }

    private void updateAssignedEmployees(int taskId, List<Integer> previousAssignedEmployeeIds, List<Integer> newAssignedEmployeeIds)  {
        List<Integer> employeesToRemove = new ArrayList<>(previousAssignedEmployeeIds);
        employeesToRemove.removeAll(newAssignedEmployeeIds);

        List<Integer> employeesToAdd = new ArrayList<>(newAssignedEmployeeIds);
        employeesToAdd.removeAll(previousAssignedEmployeeIds);

        removeEmployeesFromTask(taskId, employeesToRemove);
        addEmployeesToTask(taskId, employeesToAdd);
    }



    private void removeEmployeesFromTask(int taskId, List<Integer> employeeIdsToRemove) {
        Connection con = ConnectionManager.getConnection(db_url,username,pwd);
        String sql = "DELETE FROM task_emp WHERE task_id = ? AND emp_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int empId : employeeIdsToRemove) {
                ps.setInt(1, taskId);
                ps.setInt(2, empId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addEmployeesToTask(int taskId, List<Integer> employeeIdsToAdd) {
        Connection con = ConnectionManager.getConnection(db_url,username,pwd);
        String sql = "INSERT INTO task_emp (task_id, emp_id) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int empId : employeeIdsToAdd) {
                ps.setInt(1, taskId);
                ps.setInt(2, empId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTaskInformation(Task task) {
        Connection con = ConnectionManager.getConnection(db_url,username,pwd);

        String updateTaskSQL = "UPDATE task SET task_name = ?, task_description = ?, task_time_estimate = ?, task_deadline = ?, task_status = ? WHERE task_id = ?";
        try (PreparedStatement ps = con.prepareStatement(updateTaskSQL)) {
            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getTaskDescription());
            ps.setInt(3, task.getTaskEstimate());
            ps.setString(4, task.getTaskDeadline().toString());
            ps.setString(5, task.getTaskStatus());
            ps.setInt(6, task.getTaskID());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editTask(Task task) {
        updateTaskInformation(task);

        List<Integer> previousAssignedEmployeeIds = getAssignedEmployeeIdsForTask(task.getTaskID());
        List<Integer> newAssignedEmployeeIds = task.getSelectedEmpIDs();
        updateAssignedEmployees(task.getTaskID(), previousAssignedEmployeeIds, newAssignedEmployeeIds);
    }

    private List<Integer> getAssignedEmployeeIdsForTask(int taskId) {
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        List<Integer> employeeIds = new ArrayList<>();
        String sql = "SELECT emp_id FROM task_emp WHERE task_id = ?";

        ResultSet rs;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            rs = ps.executeQuery();
            while (rs.next()) {
                employeeIds.add(rs.getInt("emp_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return employeeIds;
    }
    }
