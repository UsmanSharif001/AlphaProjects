package com.example.alphaprojects.repositories;

import com.example.alphaprojects.interfaces.EmployeeInterface;
import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.model.Skill;
import com.example.alphaprojects.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class EmpRepository implements EmployeeInterface {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;


    @Override
    public Emp getEmp(String email, String password) {
        Emp emp = null;
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String sql = """
                SELECT emp.emp_id, emp.emp_name, emp.emp_email, emp.emp_password,skill.skill_name
                FROM emp
                JOIN emp_skills on emp.emp_id = emp_skills.emp_id
                JOIN skill on emp_skills.skill_id = skill.skill_id
                WHERE
                emp_email = ? and emp_password = ?
                """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            String currentEmpName = "";

            while (rs.next()) {
                int empId = rs.getInt("emp_id");
                String empName = rs.getString("emp_name");
                String empEmail = rs.getString("emp_email");
                String empPassword = rs.getString("emp_password");
                Skill skill = new Skill(rs.getString("skill_name"));
                if (empName.equals(currentEmpName)) {
                    emp.addSkill(skill);
                } else {
                    emp = new Emp(empId, empName, empEmail, empPassword, new ArrayList<>(List.of(skill)));
                    currentEmpName = empName;
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return emp;
    }

    @Override
    public Emp addEmp(Emp emp) {
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = """
                INSERT INTO emp (emp_name, emp_email, emp_password) VALUES (?, ?, ?)
                """;
        try (PreparedStatement ps = con.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPassword());
            ps.executeUpdate();

            //Get the generated key
            ResultSet generatedKeys = ps.getGeneratedKeys();
            int empID = -1;
            if (generatedKeys.next()) {
                empID = generatedKeys.getInt(1);
            }

            //Associate the new emp with the skills
            String skillSQL = "INSERT INTO emp_skills(skill_id,emp_id) VALUES(?,?)";
            PreparedStatement psSkill = con.prepareStatement(skillSQL);
            for (Skill skill : emp.getSkillList()) {
                int skillID = skill.getSkillID();
                psSkill.setInt(1, skillID);
                psSkill.setInt(2, empID);
                psSkill.executeUpdate();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return emp;
    }

    @Override
    public List<Skill> getSkills() {
        List<Skill> skills = new ArrayList<>();
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = """
                SELECT * FROM skill
                """;
        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                skills.add(new Skill(
                        rs.getInt("skill_id"),
                        rs.getString("skill_name"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skills;
    }


    //hjælpe metode der måske ikke skal bruges.
    private int getSkillIdFromSkillTable(String skillName) {
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);

        String SQL = "SELECT skill_id FROM skills WHERE skill_name = ?";

        try (PreparedStatement ps = con.prepareStatement(SQL)) {
            ps.setString(1, skillName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("skill_id");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


}
