package com.jlumart.service;

import com.jlumart.dto.CategoryDTO;
import com.jlumart.dto.CategoryPageDTO;
import com.jlumart.entity.Category;
import com.jlumart.result.PageResult;
import com.jlumart.vo.CategoryByParentVO;
import com.jlumart.vo.CategoryVO;

import java.util.List;


public interface CategoryService {
    PageResult page(CategoryPageDTO categoryPageDTO);

    void add(CategoryDTO categoryDTO);

    List<CategoryByParentVO> getByParentId(Long parentId);

    void updateStatus(Long id, Integer status);

    CategoryVO getById(Long id);

    void update(CategoryDTO categoryDTO);

    void delete(Long id);
}
