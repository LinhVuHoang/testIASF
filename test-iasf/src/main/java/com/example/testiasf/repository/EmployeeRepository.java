package com.example.testiasf.repository;

import com.example.testiasf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
        List<Employee> findAllByNameLike(String name);
}
