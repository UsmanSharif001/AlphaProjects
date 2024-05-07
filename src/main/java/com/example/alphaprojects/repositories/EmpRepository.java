package com.example.alphaprojects.repositories;

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
public class EmpRepository {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;

    public Emp getEmp(String email, String password) {
        Emp emp = null;
        Connection con = ConnectionManager.getConnection(db_url, username, pwd);
        String sql = """
                SELECT emp.emp_id, emp.emp_name, emp.emp_email, emp.emp_password, skill.skill_id, skill.skill_name
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
                Skill skill = new Skill(rs.getInt("skill_id"), rs.getString("skill_name"));
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
}
