package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.model.Subproject;

import java.util.List;

public interface SubprojectRepositoryInterface {
    Subproject getSubprojectFromSubprojectID(int subprojectID);

    void deleteSubproject(int subprojectID);

    void editSubproject(Subproject subproject);

    void createSubproject(Subproject subproject);

    List<Subproject> getSubprojects(int projectID);
}
