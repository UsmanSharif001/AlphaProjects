package com.example.alphaprojects.services;

import com.example.alphaprojects.repositories.SubprojectRepository;
import org.springframework.stereotype.Service;

@Service
public class SubprojectService {
    private SubprojectRepository subprojectRepository;

    public SubprojectService(SubprojectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
    }
}
