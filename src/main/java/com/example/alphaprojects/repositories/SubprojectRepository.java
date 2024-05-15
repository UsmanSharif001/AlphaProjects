package com.example.alphaprojects.repositories;

import com.example.alphaprojects.interfaces.SubprojectRepositoryInterface;
import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import com.example.alphaprojects.interfaces.SubprojectRepositoryInterface;
import com.example.alphaprojects.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SubprojectRepository implements SubprojectRepositoryInterface {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;

    //List of subprojects of specific projectID
    public List<Subproject> getSubprojects(int projectID) {
        List<Subproject> subprojectList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT * FROM subproject WHERE project_id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                subprojectList.add(new Subproject(rs.getInt("subproject_id"),
                        projectID,
                        rs.getString("subproject_name"),
                        rs.getString("subproject_description"),
                        rs.getInt("subproject_time_estimate"),
                        rs.getInt("subproject_dedicated_hours"),
                        LocalDate.parse(rs.getString("subproject_deadline")),
                        rs.getString("subproject_status")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subprojectList;
    }

    //Create subproject
    public void createSubproject(Subproject subproject) {
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "INSERT INTO subproject (project_id, subproject_name, subproject_description, subproject_time_estimate, subproject_dedicated_hours, subproject_deadline, subproject_status) VALUES (?,?,?,?,?,?,?);";

        try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, subproject.getProjectID());
            ps.setString(2, subproject.getSubprojectName());
            ps.setString(3, subproject.getSubprojectDescription());
            ps.setInt(4, subproject.getSubprojectTimeEstimate());
            ps.setInt(5, subproject.getSubprojectDedicatedHours());
            ps.setString(6, subproject.getSubprojectDeadline().toString());
            ps.setString(7, subproject.getSubprojectStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Edit subproject
    public void editSubproject(Subproject subproject) {
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "UPDATE subproject SET subproject_name = ?, subproject_description = ?, subproject_time_estimate = ?, subproject_dedicated_hours = ?, subproject_deadline = ?, subproject_status = ? WHERE subproject_id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, subproject.getSubprojectName());
            ps.setString(2, subproject.getSubprojectDescription());
            ps.setInt(3, subproject.getSubprojectTimeEstimate());
            ps.setInt(4, subproject.getSubprojectDedicatedHours());
            ps.setString(5, subproject.getSubprojectDeadline().toString());
            ps.setString(6, subproject.getSubprojectStatus());
            ps.setInt(7, subproject.getSubprojectID());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Delete subproject
    public void deleteSubproject(int subprojectID) {
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "DELETE FROM subproject WHERE subproject_id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, subprojectID);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Hjælpemetode til at finde et subproject ud fra subprojectID
    public Subproject getSubprojectFromSubprojectID(int subprojectID) {
        Subproject foundSubproject;
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);

        String SQL = "SELECT project_id, subproject_name, subproject_description, subproject_time_estimate, subproject_dedicated_hours, subproject_deadline, subproject_status FROM subproject WHERE subproject_id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, subprojectID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int projectID = rs.getInt("project_id");
                String name = rs.getString("subproject_name");
                String description = rs.getString("subproject_description");
                int timeEstimate = rs.getInt("subproject_time_estimate");
                int dedicatedHours = rs.getInt("subproject_dedicated_hours");
                LocalDate deadline = LocalDate.parse(rs.getString("subproject_deadline"));
                String status = rs.getString("subproject_status");

                foundSubproject = new Subproject(subprojectID, projectID, name, description, timeEstimate, dedicatedHours, deadline, status);

                return foundSubproject;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


        //Hjælpemetode
        //TODO: Skal refaktoreres så den opdaterer subproject_dedicated_hours i databasen:
        private int calculateSubprojectDedicatedHours(int subProjectID) {
            int dedicatedHours = 0;
            Connection con = ConnectionManager.getConnection(db_url, username, pwd);
            String SQL = "SELECT COALESCE(SUM(t.task_time_estimate), 0) AS total_task_dedicated_hours\n" +
                    "FROM task t\n" +
                    "JOIN subproject sp ON t.subproject_id = sp.subproject_id\n" +
                    "WHERE sp.subproject_id = ?";
            try (PreparedStatement ps = con.prepareStatement(SQL)) {
                ps.setInt(1, subProjectID);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    dedicatedHours = rs.getInt("total_task_dedicated_hours");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return dedicatedHours;
        }
    }
