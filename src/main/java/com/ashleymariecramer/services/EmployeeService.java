package com.ashleymariecramer.services;

import com.ashleymariecramer.Employee;
import com.ashleymariecramer.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository eRepo;

    public Map<String, Object> makeUsernameListDTO(Employee employee) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("username", employee.getUsername());
        dto.put("id", employee.getId());
        dto.put("fullName", employee.getFirstName() + " " + employee.getSurname());
//        dto.put("employeeData", eRepo.findByUsername(employee.getUsername())); //
// This returns all employee data by username
        return dto;
    }



} //End of EmployeeService