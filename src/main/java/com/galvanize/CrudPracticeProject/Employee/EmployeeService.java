package com.galvanize.CrudPracticeProject.Employee;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Employee> patchOneEmployeeFromDatabaseUsingId(Long id, Map<String, Object> map) {
        Employee foundEmployee = this.repository.findById(id).get();
        map.forEach((key,value)->{
            if(key.equals("startDate"))
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                df.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
                try {
                    Date date = df.parse(value.toString());
                    foundEmployee.setStartDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else {
                Field field = ReflectionUtils.findField(Employee.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,foundEmployee,value);
            }
        });
        return new ResponseEntity<>(this.repository.save(foundEmployee), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Employee> deleteById(Long aLong) {
        Employee deletedEmployee = repository.findById(aLong).get();
        repository.deleteById(aLong);
        return new ResponseEntity<>(deletedEmployee,HttpStatus.ACCEPTED);

    }
}
