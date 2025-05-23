package com.jlumart.mapper;

import com.github.pagehelper.Page;
import com.jlumart.dto.CourierPageDTO;
import com.jlumart.entity.Courier;
import com.jlumart.vo.CourierPageVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourierMapper {
    @Insert("insert into couriers(username, name, password, phone, email, avatar, sex, create_time, update_time) " +
            "values(#{username}, #{name}, #{password}, #{phone}, #{email}, #{avatar}, #{sex}, #{createTime}, #{updateTime})")
    void insert(Courier courier);

    Page<CourierPageVO> page(CourierPageDTO courierPageDTO);

    @Select("select * from couriers where id = #{id}")
    Courier getById(Long id);

    void update(Courier courier);

    @Delete("delete from couriers where id = #{id}")
    void delete(Long id);

    @Select("select * from couriers where username = #{username}")
    Courier getByUsername(String username);
}
