package com.jlumart.controller.user;

import com.jlumart.result.Result;
import com.jlumart.service.CategoryService;
import com.jlumart.vo.CategoryViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@Slf4j
@RequestMapping("/user/category")
@Api(tags = "用户端分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{parentId}")
    @ApiOperation(value = "获取分类")
    public Result<List<CategoryViewVO>> list(@PathVariable Long parentId) {
        log.info("获取分类，父级分类ID：{}", parentId);
        List<CategoryViewVO> pageResult = categoryService.getViewByParentId(parentId);
        return Result.success(pageResult);
    }
}
