package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.model.Project;

import java.util.List;

public interface ProjectInterface {

    public List<Project> getListOfProjects();

    public String getProjectManagerName(int empID);

}
