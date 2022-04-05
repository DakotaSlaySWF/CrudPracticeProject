package com.galvanize.CrudPracticeProject.Employee;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static java.time.LocalDateTime.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository repository;

    private ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    @Transactional
    @Rollback
    public void testPutNewEmployeeIntoDatabase() throws Exception {
        Employee employeeOne = new Employee();
        employeeOne.setName("Nik");
        Date date = Timestamp.valueOf(of(2022,1,6,3,0));
        employeeOne.setStartDate(date);

        String json = objectMapper.writeValueAsString(employeeOne);

        this.mvc.perform(post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name",is("Nik")))
                .andExpect(jsonPath("$.startDate",is("2022-01-06 09:00")));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetEmployeeFromDatabase() throws Exception {
        Employee employeeOne = new Employee();
        employeeOne.setName("Nik");
        Date date = Timestamp.valueOf(of(2022,1,6,3,0));
        employeeOne.setStartDate(date);

        this.repository.save(employeeOne);

        String getRequest = String.format("/employees/%d",employeeOne.getId());

        this.mvc.perform(get(getRequest))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name",is("Nik")))
                .andExpect(jsonPath("$.startDate",is("2022-01-06 09:00")));
    }

    @Test
    @Transactional
    @Rollback
    public void testPatchEmployeeFromDatabase() throws Exception {
        Employee employeeOne = new Employee();
        employeeOne.setName("Nik");
        Date date = Timestamp.valueOf(of(2022,1,6,3,0));
        employeeOne.setStartDate(date);

        this.repository.save(employeeOne);

        String patchRequest = String.format("/employees/%d",employeeOne.getId());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        Date dateTwo = Timestamp.valueOf(of(2022,1,6,3,0));
        String newDateTime= df.format(dateTwo);
        Map<String,Object> map = new HashMap<>();
        map.put("name","Anthony");
        map.put("startDate",newDateTime);

        String json = objectMapper.writeValueAsString(map);


        MockHttpServletRequestBuilder request = patch(patchRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name",is("Anthony")))
                .andExpect(jsonPath("$.startDate",is("2022-01-06 09:00")));
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteEmployeeFromDatabase() throws Exception {
        Employee employeeOne = new Employee();
        employeeOne.setName("Nik");
        Date date = Timestamp.valueOf(of(2022,1,6,3,0));
        employeeOne.setStartDate(date);
        Employee record = this.repository.save(employeeOne);
        String patchRequest = String.format("/employees/%d",record.getId());
        MockHttpServletRequestBuilder request = delete(patchRequest);
        this.mvc.perform(request)
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", is("Nik")))
                .andExpect(jsonPath("$.startDate", is("2022-01-06 09:00")));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetEmployeesFromDatabase() throws Exception {
        String patchRequest = String.format("/employees");
        MockHttpServletRequestBuilder request = get(patchRequest);
        this.mvc.perform(request)
                .andExpect(jsonPath("$").isEmpty());
    }

}
