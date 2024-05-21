package com.example.alphaprojects.services;

import com.example.alphaprojects.Exceptions.SubprojectAddException;
import com.example.alphaprojects.Exceptions.SubprojectEditException;
import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.repositories.SubprojectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService {
    private SubprojectRepository subprojectRepository;
    public SubprojectService(SubprojectRepository subprojectRepository) { //inject interface
        this.subprojectRepository = subprojectRepository;
    }

    public List<Subproject> getSubprojects(int projectID) {
        return subprojectRepository.getSubprojects(projectID);
    }

    public void createSubproject(Subproject subproject) throws SubprojectAddException {
        subprojectRepository.createSubproject(subproject);
    }

    public void editSubproject(Subproject subproject) throws SubprojectEditException {
        subprojectRepository.editSubproject(subproject);
    }

    public void deleteSubproject(int subprojectID){
        subprojectRepository.deleteSubproject(subprojectID);
    }

    public Subproject getSubprojectFromSubprojectID(int subprojectID){
        return subprojectRepository.getSubprojectFromSubprojectID(subprojectID);
    }

    public int getProjectEstimatedHours(int projectID){
        return subprojectRepository.getProjectEstimatedHours(projectID);
    }
}
