package com.jlumart.mapper;

import com.jlumart.entity.AddressBook;
import com.jlumart.vo.AddressBookVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    @Select("SELECT * FROM ADDRESS_BOOKS WHERE USER_ID = #{userId}")
    List<AddressBookVO> list(Long userId);

    void insert(AddressBook addressBook);

    void update(AddressBook addressBook);

    @Delete("DELETE FROM ADDRESS_BOOKS WHERE ID = #{id} AND USER_ID = #{userId}")
    void delete(Long id, Long userId);

    @Update("UPDATE ADDRESS_BOOKS SET IS_DEFAULT = 0 WHERE USER_ID = #{userId}")
    void unsetDefaultForUser(Long userId);

    @Update("UPDATE ADDRESS_BOOKS SET IS_DEFAULT = 1 WHERE ID = #{id} AND USER_ID = #{userId}")
    void setDefaultForAddress(Long id, Long userId);

    @Select("SELECT * FROM ADDRESS_BOOKS WHERE USER_ID = #{userId} AND IS_DEFAULT = 1")
    AddressBookVO getDefault(Long userId);

    AddressBookVO getLatestUpdatedAddress(Long userId);

    @Select("SELECT * FROM ADDRESS_BOOKS WHERE ID = #{id} AND USER_ID = #{userId}")
    AddressBook getAddressBook(Long id, Long userId);
}
