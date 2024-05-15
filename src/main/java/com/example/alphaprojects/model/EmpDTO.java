package com.example.alphaprojects.model;

import java.util.List;

public class EmpDTO {
    private int empID;
    private String name;
    private String email;
    private String password;
    private int roleID;



    public EmpDTO(int empID, String name, String email, String password, int roleID) {
        this.empID = empID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roleID = roleID;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
