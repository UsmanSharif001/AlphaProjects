// <editor-fold desc="Task Package Declaration & Import of Libraries">
package com.example.alphaprojects.repositories;
import com.example.alphaprojects.exceptions.TaskAddException;
import com.example.alphaprojects.exceptions.TaskEditException;
import com.example.alphaprojects.interfaces.TaskRepositoryInterface;
import com.example.alphaprojects.model.EmpSkillDTO;
import com.example.alphaprojects.model.Skill;
import com.example.alphaprojects.model.Task;
import com.example.alphaprojects.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
// </editor-fold>

@Repository
public class TaskRepositoryRepository implements TaskRepositoryInterface {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;


    // <editor-fold desc="Task CRUD-Management Methods">

    @GetMapping("/{subprojectid}/tasks")
    @Override
    public void addTask(Task newTask) throws TaskAddException {
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String taskSQL = "INSERT INTO task (subproject_id, task_name, task_description, task_time_estimate, task_deadline, task_status) VALUES(?,?,?,?,?,?);";
        String empTaskSQL = "INSERT INTO task_emp (emp_id, task_id) VALUES(?,?);";

        try (PreparedStatement taskPS = con.prepareStatement(taskSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement empTaskPS = con.prepareStatement(empTaskSQL)) {

            taskPS.setInt(1, newTask.getSubprojectID());
            taskPS.setString(2, newTask.getTaskName());
            taskPS.setString(3, newTask.getTaskDescription());
            taskPS.setInt(4, newTask.getTaskEstimate());
            taskPS.setString(5, newTask.getTaskDeadline().toString());
            taskPS.setString(6, newTask.getTaskStatus());
            taskPS.executeUpdate();

            ResultSet generatedKeys = taskPS.getGeneratedKeys();
            int taskID = -1;
            if (generatedKeys.next()) {
                taskID = generatedKeys.getInt(1);
            }

            List<Integer> selectedEmployeeIds = newTask.getSelectedEmpIDs();
            if (selectedEmployeeIds != null) {
                for (int empId : selectedEmployeeIds) {
                    empTaskPS.setInt(1, empId);
                    empTaskPS.setInt(2, taskID);
                    empTaskPS.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new TaskAddException("Fejl - udfyld alle felter");
        }
    }


    @Override
    public List<Task> getTasks(int subprojectID) {
        List<Task> taskList = new ArrayList<>();
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT * FROM task WHERE subproject_id = ? ORDER BY task_deadline;";

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

                // Få listen af emps og deres skills for den specifikke task
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
    public void editTask(Task task) throws TaskEditException {
        try {

            updateTaskInformation(task);

            List<Integer> previousAssignedEmployeeIds = getAssignedEmployeeIdsForTask(task.getTaskID());
            if (previousAssignedEmployeeIds == null) {
                previousAssignedEmployeeIds = new ArrayList<>();
            }

            List<Integer> newAssignedEmployeeIds = task.getSelectedEmpIDs();
            if (newAssignedEmployeeIds == null) {
                newAssignedEmployeeIds = new ArrayList<>();
            }

            updateAssignedEmployees(task.getTaskID(), previousAssignedEmployeeIds, newAssignedEmployeeIds);
        } catch (Exception e) {
            throw new TaskEditException("Editeringen mislykkedes. Prøv igen");
        }
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

    // </editor-fold>

    // <editor-fold desc="Assisting Task Methods">
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

                // Hent emps og deres skills
                List<EmpSkillDTO> assignedEmployeesWithSkills = getEmployeesForTask(taskid);

                // Tilføj emps med deres skills til opgaven
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

                //Samler alle skills fra employee indtil et EmpSkillDTO, Forsikre at der ikke er dobbelt EmpSkillDTO - kun en empSkillDTO per medarbejder med alle samlet skills pr. emp.
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
        List<EmpSkillDTO> empSkillDTOS = new ArrayList<>();
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

                // 1 emp = 1 empSkillDTO - derved undgår vi duplikation v. tilfælde af emps med flere skills.
                // Her samler vi alle skills i en liste i ét empSkillDTO -ved at vi undgår at duplére kan vi nu samle alle de skill(s) der er tilknyttet til emp i en liste
                EmpSkillDTO empSkillDTO = empSkillDTOS.stream()
                        .filter(e -> e.getEmpID() == empID)
                        .findFirst()
                        .orElseGet(() -> {
                            EmpSkillDTO newEmp = new EmpSkillDTO(empID, empName, new ArrayList<>());
                            empSkillDTOS.add(newEmp);
                            return newEmp;
                        });

                // Tilføjer skill til empSkillDTO
                empSkillDTO.getSkills().add(new Skill(skillID, skillName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empSkillDTOS;
    }

    private void updateAssignedEmployees(int taskID, List<Integer> previousAssignedEmployeeIds, List<Integer> newAssignedEmployeeIds)  {

        List<Integer> employeesToRemove = new ArrayList<>(previousAssignedEmployeeIds);
        employeesToRemove.removeAll(newAssignedEmployeeIds);

        List<Integer> employeesToAdd = new ArrayList<>(newAssignedEmployeeIds);
        employeesToAdd.removeAll(previousAssignedEmployeeIds);

        removeEmployeesFromTask(taskID, employeesToRemove);
        addEmployeesToTask(taskID, employeesToAdd);
    }

    private void removeEmployeesFromTask(int taskID, List<Integer> employeeIdsToRemove) {
        Connection con = ConnectionManager.getConnection(db_url,username,pwd);
        String sql = "DELETE FROM task_emp WHERE task_id = ? AND emp_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int empID : employeeIdsToRemove) {
                ps.setInt(1, taskID);
                ps.setInt(2, empID);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addEmployeesToTask(int taskID, List<Integer> employeeIdsToAdd) {
        Connection con = ConnectionManager.getConnection(db_url,username,pwd);
        String sql = "INSERT INTO task_emp (task_id, emp_id) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int empID : employeeIdsToAdd) {
                ps.setInt(1, taskID);
                ps.setInt(2, empID);
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

    // </editor-fold>

    }
