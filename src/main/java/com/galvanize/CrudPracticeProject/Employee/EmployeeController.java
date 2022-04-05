package com.galvanize.CrudPracticeProject.Employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.dsig.SignatureMethod;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeRepository repository;
    EmployeeService service;

    public EmployeeController(EmployeeRepository repository, EmployeeService service) {
        this.repository = repository;
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<Employee> addOneEmployeeToDatabase(@RequestBody Employee employee){
        return new ResponseEntity<Employee>(repository.save(employee),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getOneEmployeeFromDatabaseUsingId(@PathVariable Long id){
        return new ResponseEntity<Employee>(repository.findById(id).get(),HttpStatus.FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> patchOneEmployeeFromDatabaseUsingId(@PathVariable Long id, @RequestBody Map<String, Object> map){
        return service.patchOneEmployeeFromDatabaseUsingId(id,map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @GetMapping("")
    public List<Employee> findAll() {
        return repository.findAll();
    }
}
