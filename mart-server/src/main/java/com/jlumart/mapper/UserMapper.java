package com.jlumart.mapper;

import com.jlumart.entity.User;
import com.jlumart.vo.UserInfoVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface UserMapper {
    @Select("select id, username, name, avatar,  balance from users where id = #{id}")
    UserInfoVO getInfo(Long id);

    @Insert("INSERT INTO USERS (username, name, password, avatar, create_time, update_time, LAST_REQUEST_TIME)" +
            "VALUES (#{username}, #{name}, #{password}, #{avatar}, #{createTime}, #{updateTime}, #{lastRequestTime})")
    @SelectKey(statement = "SELECT JLUMARTADMIN.USERS_SEQ.CURRVAL FROM DUAL", keyProperty = "id", before = false, resultType = Long.class)
    void insert(User user);

    @Select("SELECT id, username, password, name, avatar, balance FROM USERS WHERE username = #{username}")
    User getByUsername(String username);

    void update(User user);

    @Select("SELECT * FROM USERS WHERE ID = #{id}")
    User getById(Long id);
}
