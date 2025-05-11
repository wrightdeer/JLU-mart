package com.jlumart.service;
import com.jlumart.dto.EmployeeDTO;
import com.jlumart.dto.EmployeePageDTO;
import com.jlumart.dto.LoginDTO;
import com.jlumart.dto.PasswordDTO;
import com.jlumart.mapper.EmployeeMapper;
import com.jlumart.result.PageResult;
import com.jlumart.vo.EmployeeInfoVO;
import com.jlumart.vo.EmployeeLoginVO;
import com.jlumart.vo.EmployeeVO;
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

    public EmployeeLoginVO login(LoginDTO emp);

    public void addEmployee(EmployeeDTO employee);

    EmployeeInfoVO getInfo();

    void editPassword(PasswordDTO passwordDTO);

    PageResult page(EmployeePageDTO employeePageDTO);

    EmployeeVO getById(Long id);

    void editEmployee(EmployeeDTO employeeDTO);

    void delete(Long id);

    void editStatus(Long id, Integer status);
}

