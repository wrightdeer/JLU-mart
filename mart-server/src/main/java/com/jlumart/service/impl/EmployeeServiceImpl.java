package com.jlumart.service.impl;

import com.jlumart.mapper.EmployeeMapper;
import com.jlumart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
}
