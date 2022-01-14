package com.example.testiasf.controller;

import com.example.testiasf.entity.Employee;
import com.example.testiasf.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/employees")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;
    //create
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Employee employee){
        employeeRepository.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
    //findall
    @RequestMapping(method = RequestMethod.GET)
    public  ResponseEntity<Object> getList(@RequestParam(defaultValue = "1") int page
            ,@RequestParam(defaultValue = "10")int limit){
        HashMap<String,Object> response = new HashMap<>();
        response.put("page", page);
        response.put("limit", limit);
        response.put("data", employeeRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    //find  nhân viên theo tên
    @RequestMapping(method = RequestMethod.GET,path = "{name}")
    public  ResponseEntity<Object> GetUserByName(@RequestParam(defaultValue = "1") int page
            ,@RequestParam(defaultValue = "10")int limit,@PathVariable String name){
        HashMap<String,Object> response = new HashMap<>();
        response.put("page", page);
        response.put("limit", limit);
        response.put("data", employeeRepository.findAllByNameLike(name));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    //update
    @RequestMapping(method = RequestMethod.PUT,path = "{id}")
    public ResponseEntity<Object> update(@PathVariable int id,@RequestBody Employee updateemployee){
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            employee.setName(updateemployee.getName());
            employee.setAge(updateemployee.getAge());
            employee.setSalary(updateemployee.getSalary());
            employeeRepository.save(employee);
            return new ResponseEntity<>(employee,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    //delete
    @RequestMapping(method = RequestMethod.DELETE,path = "{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
           employeeRepository.delete(optionalEmployee.get());
            return new ResponseEntity<>(true,HttpStatus.OK);

        }else {
            return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
        }
    }

}
