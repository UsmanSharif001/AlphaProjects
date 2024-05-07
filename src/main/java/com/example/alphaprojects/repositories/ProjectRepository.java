package com.example.alphaprojects.repositories;

import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.model.Status;
import com.example.alphaprojects.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;

    public List<Project> getListOfProjects() {
        List<Project> projectList = new ArrayList<>();
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT * FROM project";
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ResultSet projectsResultSet = ps.executeQuery();
            while (projectsResultSet.next()) {
                int projectID = projectsResultSet.getInt("project_id");
                int managerID = projectsResultSet.getInt("project_manager_id");
                String name = projectsResultSet.getString("project_name");
                String description = projectsResultSet.getString("project_description");
                int timeEstimate = projectsResultSet.getInt("project_time_estimate");
                int dedicatedHours = projectsResultSet.getInt("project_dedicated_hours");
                LocalDate deadline = projectsResultSet.getDate("project_deadline").toLocalDate();
                String statusString = projectsResultSet.getString("project_status");
                Status status = Status.valueOf(statusString.toUpperCase());
                Project project = new Project(projectID, managerID, name, description, timeEstimate, dedicatedHours, deadline, status);
                projectList.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectList;
    }

    public String getProjectManagerName(int empID) {
        String projectManager = "";
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT SELECT emp_name FROM AlphaSolution_db.emp WHERE emp_id = ?";
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
}
