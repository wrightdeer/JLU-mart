package com.jlumart.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jlumart.constant.StatusConstant;
import com.jlumart.context.BaseContext;
import com.jlumart.dto.CategoryDTO;
import com.jlumart.dto.CategoryPageDTO;
import com.jlumart.entity.Category;
import com.jlumart.exception.ForbiddenOperationException;
import com.jlumart.exception.IllegalOperationException;
import com.jlumart.mapper.CategoryMapper;
import com.jlumart.mapper.ProductMapper;
import com.jlumart.result.PageResult;
import com.jlumart.service.CategoryService;
import com.jlumart.vo.CategoryByParentVO;
import com.jlumart.vo.CategoryPageVO;
import com.jlumart.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper  productMapper;

    public PageResult page(CategoryPageDTO categoryPageDTO) {
        PageHelper.startPage(categoryPageDTO.getPage(), categoryPageDTO.getPageSize());
        Page<CategoryPageVO> page = categoryMapper.pageQuery(categoryPageDTO);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    public void add(CategoryDTO categoryDTO) {
        Long currentUserId = BaseContext.getCurrentId();
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        category.setStatus(StatusConstant.DISABLE);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(currentUserId);
        category.setUpdateUser(currentUserId);

        categoryMapper.insert(category);
    }

    public List<CategoryByParentVO> getByParentId(Long parentId) {
        return categoryMapper.getByParentId(parentId);
    }

    public void updateStatus(Long id, Integer status) {
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    public CategoryVO getById(Long id) {
        return categoryMapper.getById(id);
    }

    public void update(CategoryDTO categoryDTO) {
        if (Objects.equals(categoryDTO.getId(), categoryDTO.getParentId())) {
            throw new IllegalOperationException("上级分类不能是自己");
        }
        Long currentUserId = BaseContext.getCurrentId();
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(currentUserId);
        categoryMapper.update(category);
    }

    @Transactional
    public void delete(Long id) {
        // 检查是否有子分类
        List<CategoryByParentVO> categoryByParentVOS = categoryMapper.getByParentId(id);
        if (!categoryByParentVOS.isEmpty()) {
            throw new IllegalOperationException("该分类下有子分类，无法删除");
        }
        // 检查是否有商品
        Long count = productMapper.getCountByCategoryId(id);
        if (count > 0) {
            throw new IllegalOperationException("该分类下有商品，无法删除");
        }
        categoryMapper.delete(id);
    }
}
