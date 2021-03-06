package com.ashleymariecramer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;

@RepositoryRestResource
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //List<Employee> findEmployeesByUsername(@Param("name") String name); //This one is used on SalvoApplication security
    Employee findByUsername(@Param("username") String username); //This works to pull up entire Employee data based on username
    Employee findById(@Param("employeeId") Long employeeId);
    List<Employee> findAll();
    List<Employee> findByAccommodationName(@Param("accomName") String accomName);
    List<Employee> findEmployeesByUsername(String name);
}
