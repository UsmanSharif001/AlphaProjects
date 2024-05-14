package com.example.alphaprojects.repositories;

import com.example.alphaprojects.Exceptions.ProjectAddException;
import com.example.alphaprojects.Exceptions.ProjectEditException;
import com.example.alphaprojects.model.Project;
import com.example.alphaprojects.interfaces.ProjectInterface;
import com.example.alphaprojects.model.ProjectManagerDTO;
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
                String status = projectsResultSet.getString("project_status");
                Project project = new Project(projectID, managerID, managerName, name, description, timeEstimate, dedicatedHours, deadline, status);
                projectList.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectList;
    }

    @Override
    public void addNewProject(Project newProject) throws ProjectAddException {
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "INSERT INTO project " +
                "(project_manager_id, project_name, project_description, " +
                "project_time_estimate, project_dedicated_hours, project_deadline, " +
                "project_status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, newProject.getProjectManagerID());
            ps.setString(2, newProject.getProjectName());
            ps.setString(3, newProject.getProjectDescription());
            ps.setInt(4, newProject.getProjectTimeEstimate());
            ps.setInt(5, 0); // dedidacted hours på nyt projekt starter på 0
            ps.setDate(6, Date.valueOf(newProject.getProjectDeadline()));
            ps.setString(7, newProject.getProjectStatus());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new ProjectAddException("Husk at udfylde alle felterne");
        }
    }

    @Override
    public void editProject(Project project) throws ProjectEditException {
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "UPDATE project SET project_manager_id = ?, project_name = ?, project_description = ?, project_time_estimate = ?, project_deadline = ?, project_status = ? WHERE project_id = ?;";

        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setInt(1, project.getProjectManagerID());
            ps.setString(2, project.getProjectName());
            ps.setString(3, project.getProjectDescription());
            ps.setInt(4, project.getProjectTimeEstimate());
            ps.setDate(5, Date.valueOf(project.getProjectDeadline()));
            ps.setString(6, project.getProjectStatus());
            ps.setInt(7, project.getProjectID());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new ProjectEditException("Husk at udfylde alle felterne.");
        }
    }

    @Override
    public List<ProjectManagerDTO> getProjectManagers() {
        List<ProjectManagerDTO> projectManagerList = new ArrayList<>();
        ProjectManagerDTO projectManagerDTO = null;
        String projectManagerName = "";
        int projectManagerID = 0;
        int skillIDforProjectManager = 2;
        int skillIDforAdmin = 1;

        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT emp_id FROM AlphaSolution_db.emp_skills WHERE skill_id IN " +
                "(" + skillIDforAdmin + ", " + skillIDforProjectManager + ")";

        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                projectManagerID = rs.getInt("emp_id");
                projectManagerName = getProjectManagerName(rs.getInt("emp_id"));
                projectManagerDTO = new ProjectManagerDTO(projectManagerID, projectManagerName);
                projectManagerList.add(projectManagerDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectManagerList;
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
    public Project getProjectFromProjectID(int projectID) {
        Project project;
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String sql = "SELECT * FROM project WHERE project_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int managerID = rs.getInt("project_manager_id");
                String managerName = getProjectManagerName(managerID);
                String name = rs.getString("project_name");
                String description = rs.getString("project_description");
                int timeEstimate = rs.getInt("project_time_estimate");
                int dedicatedHours = calculateProjectDedicatedHours(projectID);
                LocalDate deadline = LocalDate.parse(rs.getString("project_deadline"));
                String status = rs.getString("project_status").toUpperCase();
                project = new Project(projectID, managerID, managerName, name, description, timeEstimate, dedicatedHours, deadline, status);
                return project;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
