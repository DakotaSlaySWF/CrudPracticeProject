package com.galvanize.CrudPracticeProject.Exception;


import com.galvanize.CrudPracticeProject.Employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerAdviceTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    EmployeeRepository repository;

    @Test
    void testNoSuchElementException() throws Exception {
        this.mvc.perform(get("/employees/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No Such Element Found"));
    }
}
