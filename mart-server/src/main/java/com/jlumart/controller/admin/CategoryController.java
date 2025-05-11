package com.jlumart.controller.admin;

import com.jlumart.dto.CategoryDTO;
import com.jlumart.dto.CategoryPageDTO;
import com.jlumart.entity.Category;
import com.jlumart.result.PageResult;
import com.jlumart.result.Result;
import com.jlumart.service.CategoryService;
import com.jlumart.vo.CategoryByParentVO;
import com.jlumart.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询分类")
    public Result<PageResult> page(CategoryPageDTO categoryPageDTO) {
        log.info("分页查询分类，分页参数：{}", categoryPageDTO);
        PageResult pageResult = categoryService.page(categoryPageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/parent/{parentId}")
    @ApiOperation(value = "通过父级分类获取分类")
    public Result<List<CategoryByParentVO>> getByParentId(@PathVariable Long parentId) {
        log.info("通过父级分类获取分类，父级分类ID：{}", parentId);
        List<CategoryByParentVO> pageResult = categoryService.getByParentId(parentId);
        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation(value = "添加分类")
    public Result add(@RequestBody CategoryDTO categoryDTO) {
        log.info("添加分类，分类信息：{}", categoryDTO);
        categoryService.add(categoryDTO);
        return Result.success();
    }

    @PutMapping("/status/{id}")
    @ApiOperation(value = "修改分类状态")
    public Result updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        log.info("修改分类状态，分类ID：{}，状态：{}", id, status);
        categoryService.updateStatus(id, status);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID获取分类")
    public Result<CategoryVO> getById(@PathVariable Long id) {
        log.info("通过ID获取分类，分类ID：{}", id);
        CategoryVO category = categoryService.getById(id);
        return Result.success(category);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改分类")
    public Result update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类，分类ID：{}，分类信息：{}", id, categoryDTO);
        categoryDTO.setId(id);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除分类")
    public Result delete(@PathVariable Long id) {
        log.info("删除分类，分类ID：{}", id);
        categoryService.delete(id);
        return Result.success();
    }

}
