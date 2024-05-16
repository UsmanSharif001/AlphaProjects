package com.example.alphaprojects;


import com.example.alphaprojects.repositories.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("h2")
public class EmpRepositoryTest {

    @Autowired
    EmpRepository empRepository;
}
