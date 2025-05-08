package com.jlumart.service;
import com.jlumart.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import com.jlumart.entity.Employee;
import java.time.LocalDate;
import java.util.List;

import java.time.LocalDate;

/**
 * 员工管理
 */
public interface EmployeeService {

    public Employee login(Employee emp);

    public void addEmployee(Employee employee, Long currentUserId);
}

