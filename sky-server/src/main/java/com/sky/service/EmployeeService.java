package com.sky.service;

import com.sky.dto.EmployeeLoginDto;
import com.sky.entity.Employee;

public interface EmployeeService {
    Employee login(EmployeeLoginDto employeeLoginDto);
}
