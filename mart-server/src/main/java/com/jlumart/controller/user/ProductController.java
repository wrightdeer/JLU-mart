package com.jlumart.controller.user;

import com.jlumart.dto.ProductPageDTO;
import com.jlumart.result.PageResult;
import com.jlumart.result.Result;
import com.jlumart.service.ProductService;
import com.jlumart.vo.ProductViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("useProductController")
@RequestMapping("/user/products")
@Api(value = "用户商品相关接口")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/page")
    public Result<PageResult> page(ProductPageDTO productPageDTO) {
        log.info("分页查询商品，分页参数：{}", productPageDTO);
        PageResult pageResult = productService.viewPage(productPageDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "查询商品详情")
    public Result<ProductViewVO> getById(@PathVariable Long id) {
        log.info("查询商品详情，参数：{}", id);
        ProductViewVO productViewVO = productService.getViewById(id);
        return Result.success(productViewVO);
    }
}
