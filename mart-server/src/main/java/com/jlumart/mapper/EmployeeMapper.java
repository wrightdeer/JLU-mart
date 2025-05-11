package com.jlumart.mapper;
import com.github.pagehelper.Page;
import com.jlumart.dto.EmployeePageDTO;
import com.jlumart.dto.LoginDTO;
import com.jlumart.vo.EmployeePageVO;
import org.apache.ibatis.annotations.*;
import com.jlumart.entity.Employee;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Insert("INSERT INTO EMPLOYEES(username, name, password, phone, email, avatar, sex, status, " +
            "create_user, update_user, create_time, update_time) " +
            "VALUES(#{username}, #{name}, #{password}, #{phone}, #{email}, #{avatar}, #{sex}, " +
            "#{status}, #{createUser}, #{updateUser}, #{createTime}, #{updateTime})")
    void insert(Employee employee);

    @Select("SELECT * FROM EMPLOYEES WHERE username = #{username}")
    Employee getByUsername(String username);

    @Select("SELECT * FROM EMPLOYEES WHERE id = #{id}")
    Employee getById(Long id);

    void update(Employee employee);

    Page<EmployeePageVO> page(EmployeePageDTO employeePageDTO);

    @Delete("DELETE FROM EMPLOYEES WHERE id = #{id}")
    void delete(Long id);
}