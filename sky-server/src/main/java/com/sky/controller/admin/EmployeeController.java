package com.sky.controller.admin;

import com.sky.dto.EmployeeLoginDto;
import com.sky.entity.Employee;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDto employeeLoginDto) {
        log.info("员工登录: {}", employeeLoginDto);

        Employee employee = employeeService.login(employeeLoginDto);

        EmployeeLoginVO employeeLoginVO1 = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .build();

        return Result.success(employeeLoginVO1);
    }
}
