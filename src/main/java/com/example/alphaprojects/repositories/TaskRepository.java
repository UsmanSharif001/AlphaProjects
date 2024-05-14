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
        String SQL = "INSERT INTO task (task_id, subproject_id, task_name, task_description, task_time_estimate, task_deadline, task_status) VALUES(?,?,?,?,?,?,?);";

        try (PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            int taskID;
            if (generatedKeys.next()) {
                taskID = generatedKeys.getInt(0);
                ps.setInt(1, taskID);
            }
            ps.setInt(2, newTask.getSubprojectID());
            ps.setString(3, newTask.getTaskName());
            ps.setString(4, newTask.getTaskDescription());
            ps.setInt(5, newTask.getTaskEstimate());
            ps.setDate(6, Date.valueOf(newTask.getTaskDeadline()));
            ps.setString(7, newTask.getTaskStatus());

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
            ps.setDate(4, Date.valueOf(task.getTaskDeadline()));
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
    public void deleteTask(int taskID) {
        Connection con = ConnectionManager.getConnection(db_url,username,pwd);
        String SQL = "DELETE FROM task WHERE taskid =?;";


        try(PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, taskID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
