package com.jlumart.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jlumart.constant.PasswordConstant;
import com.jlumart.entity.Employee;
import com.jlumart.mapper.EmployeeMapper;
import com.jlumart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public void addEmployee(Employee employee, Long currentUserId) {
        // 检查用户名是否已存在
        if (employeeMapper.getByUsername(employee.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        employee.setPassword(PasswordConstant.DEFAULT_PASSWORD); // 加密默认密码
        employee.setStatus(0); // 状态设为禁用
        employee.setCreateUser(currentUserId);
        employee.setUpdateUser(currentUserId);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        // 插入数据库
        employeeMapper.insert(employee);
    }

    @Override
    public Employee login(Employee emp){
        return employeeMapper.GetBy(emp);
    }


}
