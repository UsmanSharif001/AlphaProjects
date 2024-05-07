package com.example.alphaprojects.repositories;

import com.example.alphaprojects.interfaces.SubprojectInterface;
import com.example.alphaprojects.model.Subproject;
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
public class SubprojectRepository implements SubprojectInterface {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;

    //List of subprojects of specific projectID
    public List<Subproject> getSubprojects(int projectID){
        List<Subproject> subprojectList = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT * FROM subproject WHERE project_id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(SQL)){
            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                subprojectList.add(new Subproject(rs.getInt("subproject_id"),
                        projectID,
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("time_estimate"),
                        rs.getInt("dedicated_hours"),
                        LocalDate.parse(rs.getString("deadline")),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subprojectList;
    }

    //Create subproject

    //Edit subproject

    //Delete subproject
}
