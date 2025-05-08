package com.jlumart.controller.admin;

import com.jlumart.entity.DTO.EmployeeRequest;
import com.jlumart.entity.Employee;
import com.jlumart.result.Result;
import com.jlumart.service.EmployeeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addEmployee(@RequestBody EmployeeRequest request,
                            @RequestAttribute("currentUserId") Long currentUserId) {
        // 转换DTO为实体
        Employee employee = new Employee();
        employee.setUsername(request.getUsername());
        employee.setName(request.getName());
        employee.setPhone(request.getPhone());
        employee.setEmail(request.getEmail());
        employee.setAvatar(request.getAvatar());
        employee.setSex(request.getSex());

        // 调用服务
        employeeService.addEmployee(employee, currentUserId);
    }


}
