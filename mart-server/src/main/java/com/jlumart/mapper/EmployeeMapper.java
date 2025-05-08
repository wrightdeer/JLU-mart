package com.jlumart.mapper;
import org.apache.ibatis.annotations.*;
import com.jlumart.entity.Employee;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Insert("INSERT INTO employee(username, name, password, phone, email, avatar, sex, status, " +
            "create_user, update_user, create_time, update_time) " +
            "VALUES(#{username}, #{name}, #{password}, #{phone}, #{email}, #{avatar}, #{sex}, " +
            "#{status}, #{createUser}, #{updateUser}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Employee employee);

    @Select("SELECT * FROM employee WHERE username = #{username}")
    Employee getByUsername(String username);

    @Select("select * from EMPLOYEES where username = #{username} and password = #{password}")
    Employee GetBy(Employee emp);
}