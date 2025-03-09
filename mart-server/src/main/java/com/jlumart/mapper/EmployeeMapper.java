package com.jlumart.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("select id from emp")
    public List<String> getEmpId();
    @Insert("insert into emp(id) values(#{id})")
    public void insertEmp(String id);
}
