package com.jlumart.mapper;

import com.github.pagehelper.Page;
import com.jlumart.dto.CategoryPageDTO;
import com.jlumart.entity.Category;
import com.jlumart.vo.CategoryByParentVO;
import com.jlumart.vo.CategoryPageVO;
import com.jlumart.vo.CategoryVO;
import com.jlumart.vo.CategoryViewVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    Page<CategoryPageVO> pageQuery(CategoryPageDTO categoryPageDTO);

    @Insert("INSERT INTO CATEGORIES(name, parent_id, status, sort_weight, create_user, update_user, create_time, update_time) " +
    "values (#{name}, #{parentId}, #{status}, #{sortWeight}, #{createUser}, #{updateUser}, #{createTime}, #{updateTime})")
    void insert(Category category);


    @Select("SELECT id, name,  status FROM CATEGORIES WHERE parent_id = #{parentId}")
    List<CategoryByParentVO> getByParentId(Long parentId);

    void update(Category category);

    @Select("SELECT * FROM CATEGORIES WHERE id = #{id}")
    CategoryVO getById(Long id);

    @Delete("DELETE FROM CATEGORIES WHERE id = #{id}")
    void delete(Long id);

    @Select("SELECT id, name FROM CATEGORIES WHERE parent_id = #{parentId} AND status = 1 ORDER BY sort_weight ")
    List<CategoryViewVO> getViewByParentId(Long parentId);
}
