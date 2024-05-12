package com.example.alphaprojects.repositories;

import com.example.alphaprojects.interfaces.SubprojectInterface;
import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
    public List<Subproject> getSubprojects(int projectID) {
        List<Subproject> subprojectList = new ArrayList<>();
        subprojectList.add(new Subproject(1,1,"subproject1","beskrivelse af..", 100, 25, LocalDate.of(2024,10,10), "In progress"));
        /*Connection connection = DriverManager.getConnection(db_url, username, pwd);
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
        }*/
        return subprojectList;
    }

    //Create subproject
   public void createSubproject(Subproject subproject){
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "INSERT INTO subproject (project_id, subproject_id, name, description, time_estimate, dedicated_hours, deadline, status) VALUES (?,?,?,?,?,?);";

        try (PreparedStatement ps = connection.prepareStatement(SQL)){
            ps.setInt(1, subproject.getProjectID());
            ps.setInt(2, subproject.getSubprojectID());
            ps.setString(3, subproject.getName());
            ps.setString(4, subproject.getDescription());
            ps.setInt(5, subproject.getTimeEstimate());
            ps.setInt(6, subproject.getDedicatedHours());
            ps.setString(7, subproject.getDeadline().toString());
            ps.setString(8, subproject.getStatus());

            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Edit subproject
    public void editSubproject(Subproject subproject){
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "UPDATE subproject SET name = ?, description = ?, time_estimate = ?, dedicated_time = ?, deadline = ?, status = ?;";

        try (PreparedStatement ps = connection.prepareStatement(SQL)){
            ps.setString(1, subproject.getName());
            ps.setString(2, subproject.getDescription());
            ps.setInt(3, subproject.getTimeEstimate());
            ps.setInt(4, subproject.getDedicatedHours());
            ps.setString(5, subproject.getDeadline().toString());
            ps.setString(6, subproject.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Delete subproject
    public void deleteSubproject(int subprojectID){
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "DELETE FROM subproject WHERE subproject_id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(SQL)){
            ps.setInt(1, subprojectID);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Subproject getSubprojectFromSubprojectID(int subprojectID){
        Subproject foundSubproject;
        Connection connection = ConnectionManager.getConnection(db_url, username,pwd);

        String SQL = "SELECT project_id, name, description, time_estimate, dedicated_hours, deadline, status FROM subproject WHERE subproject_id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(SQL)){
            ps.setInt(1, subprojectID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                int projectID = rs.getInt("project_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int timeEstimate = rs.getInt("time_estimate");
                int dedicatedHours = rs.getInt("dedicated_hours");
                LocalDate deadline = LocalDate.parse(rs.getString("deadline"));
                String status = rs.getString("status");

                foundSubproject = new Subproject(subprojectID, projectID, name, description, timeEstimate, dedicatedHours, deadline, status);

                return foundSubproject;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
