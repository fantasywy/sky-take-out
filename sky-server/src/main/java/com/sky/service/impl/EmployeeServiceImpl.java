package com.sky.service.impl;

import com.sky.dto.EmployeeLoginDto;
import com.sky.entity.Employee;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(EmployeeLoginDto employeeLoginDto) {
        String username = employeeLoginDto.getUsername();
        String password = employeeLoginDto.getPassword();

        Employee employee = employeeMapper.getByUsername(username);

        return employee;
    }
}
