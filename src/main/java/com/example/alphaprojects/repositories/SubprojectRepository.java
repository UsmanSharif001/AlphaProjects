package com.example.alphaprojects.repositories;

import com.example.alphaprojects.interfaces.SubprojectInterface;
import com.example.alphaprojects.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SubprojectRepository implements SubprojectInterface {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;


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
