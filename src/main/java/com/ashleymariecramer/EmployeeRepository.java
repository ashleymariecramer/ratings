package com.ashleymariecramer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findEmployeesByUsername(@Param("name") String name); //This one is used on SalvoApplication security
    Employee findByUsername(@Param("name") String name); //This one is used on SalvoApplication security
}
