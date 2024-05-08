package com.example.alphaprojects.repositories;

import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.interfaces.ProjectInterface;
import com.example.alphaprojects.model.Status;
import com.example.alphaprojects.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository implements ProjectInterface {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;

    @Override
    public List<Project> getListOfProjects() {
        List<Project> projectList = new ArrayList<>();
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT * FROM project";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ResultSet projectsResultSet = ps.executeQuery();
            while (projectsResultSet.next()) {
                int projectID = projectsResultSet.getInt("project_id");
                int managerID = projectsResultSet.getInt("project_manager_id");
                String managerName = getProjectManagerName(managerID);
                String name = projectsResultSet.getString("project_name");
                String description = projectsResultSet.getString("project_description");
                int timeEstimate = projectsResultSet.getInt("project_time_estimate");
                int dedicatedHours = calculateProjectDedicatedHours(projectID);
                LocalDate deadline = projectsResultSet.getDate("project_deadline").toLocalDate();
                String statusString = projectsResultSet.getString("project_status");
                Status status = Status.valueOf(statusString.toUpperCase());
                Project project = new Project(projectID, managerID, managerName, name, description, timeEstimate, dedicatedHours, deadline, status);
                projectList.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectList;
    }


    // Hjælpemetoder
    @Override
    public String getProjectManagerName(int empID) {
        String projectManager = "";
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT emp_name FROM AlphaSolution_db.emp WHERE emp_id = ?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, empID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                projectManager = rs.getString("emp_name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectManager;
    }

    // TODO: Skal refaktoreres så databasen opdateres:
    @Override
    public int calculateProjectDedicatedHours(int projectID) {
        int dedicatedHours = 0;
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = """
                SELECT COALESCE(SUM(t.task_time_estimate), 0) AS total_task_dedicated_hours
                                        FROM task t
                                        JOIN subproject sp ON t.subproject_id = sp.subproject_id
                                        JOIN project p ON sp.project_id = p.project_id
                                        WHERE p.project_id = ?;""";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dedicatedHours = rs.getInt("total_task_dedicated_hours");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dedicatedHours;
    }

    @Override
    public int getManagerID(String managerName) {
        int managerID = 0;
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT emp_id FROM AlphaSolution_db.emp WHERE emp_name = ?";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, managerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                managerID = rs.getInt("emp_ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return managerID;
    }

    @Override
    public void addNewProject(Project newProject) {
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "INSERT INTO project (Project_id, project_manager_id, project_name, project_description, project_time_estimate, project_deadline, project_status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            // Set project ID
            int projectID;
            if (generatedKeys.next()) {
                projectID = generatedKeys.getInt(0);
                ps.setInt(1, projectID);
            }
            ps.setInt(2, getManagerID(newProject.getProjectManagerName()));
            ps.setString(3, newProject.getProjectName());
            ps.setString(4, newProject.getProjectDescription());
            ps.setInt(5, newProject.getProjectTimeEstimate());
            ps.setObject(6, newProject.getProjectDeadline().toString());
            ps.setObject(7, newProject.getProjectStatus().toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
