package com.example.alphaprojects.services;

import com.example.alphaprojects.repositories.EmpRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private EmpRepository empRepository;

    public UserService(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }


}
