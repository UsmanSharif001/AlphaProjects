package com.example.alphaprojects.services;

import com.example.alphaprojects.model.Subproject;
import com.example.alphaprojects.repositories.SubprojectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService {
    private SubprojectRepository subprojectRepository;

    public SubprojectService(SubprojectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
    }

    public List<Subproject> getSubprojects(int projectID){
        return subprojectRepository.getSubprojects(projectID);
    }
}
