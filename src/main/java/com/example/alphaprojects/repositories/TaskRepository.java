package com.example.alphaprojects.repositories;

import com.example.alphaprojects.interfaces.TaskInterface;
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

    @Override
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

    }


    @Override
    public void editTask(Task task) {
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = """
                UPDATE task SET task_name = ?, task_description = ?,
                task_time_estimate = ?, task_deadline = ?, task_status = ? WHERE task_id = ?""";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getTaskDescription());
            ps.setInt(3, task.getTaskEstimate());
            ps.setString(4, task.getTaskDeadline().toString());
            ps.setString(5, task.getTaskStatus());
            ps.setInt(6,task.getTaskID());

            ps.executeUpdate();

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
                taskList.add(new Task(
                        tasksResultSet.getInt("task_id"),
                        tasksResultSet.getInt("subproject_id"),
                        tasksResultSet.getString("task_name"),
                        tasksResultSet.getString("task_description"),
                        tasksResultSet.getInt("task_time_estimate"),
                        tasksResultSet.getDate("task_deadline").toLocalDate(),
                        tasksResultSet.getString("task_status")));

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return taskList;
    }

    @Override
    public void deleteTask(int taskId) {
        try (Connection con = ConnectionManager.getConnection(db_url, username, pwd)) {

            String deleteTaskEmpSQL = "DELETE FROM task_emp WHERE task_id = ?";
            try (PreparedStatement psTaskEmp = con.prepareStatement(deleteTaskEmpSQL)) {
                psTaskEmp.setInt(1, taskId);
                psTaskEmp.executeUpdate();
            }
            String deleteTaskSQL = "DELETE FROM task WHERE task_id = ?";
            try (PreparedStatement psTask = con.prepareStatement(deleteTaskSQL)) {
                psTask.setInt(1, taskId);
                psTask.executeUpdate();
            }
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
                String status = rs.getString("task_status");
                foundTask = new Task(taskid, subprojectID,name,description,timeEstimate,deadline,status);
                return foundTask;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
