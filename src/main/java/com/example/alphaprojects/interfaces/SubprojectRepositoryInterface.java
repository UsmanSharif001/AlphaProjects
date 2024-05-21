package com.example.alphaprojects.interfaces;

import com.example.alphaprojects.Exceptions.SubprojectAddException;
import com.example.alphaprojects.Exceptions.SubprojectEditException;
import com.example.alphaprojects.model.Subproject;

import java.util.List;

public interface SubprojectRepositoryInterface {
    Subproject getSubprojectFromSubprojectID(int subprojectID);

    void deleteSubproject(int subprojectID);

    void editSubproject(Subproject subproject) throws SubprojectEditException;

    void createSubproject(Subproject subproject) throws SubprojectAddException;

    List<Subproject> getSubprojects(int projectID);
}
