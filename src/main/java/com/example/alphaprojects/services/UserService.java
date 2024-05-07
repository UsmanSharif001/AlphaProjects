package com.example.alphaprojects.services;

import com.example.alphaprojects.model.Emp;
import com.example.alphaprojects.repositories.EmpRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private EmpRepository empRepository;

    public UserService(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    public Emp getEmp(String username, String password) {
      return empRepository.getEmp(username,password);
    }
}
