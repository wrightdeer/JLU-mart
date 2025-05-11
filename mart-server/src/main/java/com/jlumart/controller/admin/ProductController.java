package com.jlumart.controller.admin;

import com.jlumart.dto.ProductDTO;
import com.jlumart.dto.ProductPageDTO;
import com.jlumart.result.PageResult;
import com.jlumart.result.Result;
import com.jlumart.service.ProductService;
import com.jlumart.vo.ProductPageVO;
import com.jlumart.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminProductController")
@RequestMapping("/admin/product")
@Api(tags = "商品管理")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/page")
    @ApiOperation("分页查询商品")
    public Result<PageResult> page(ProductPageDTO productPageDTO) {
        log.info("分页查询商品，分页参数：{}", productPageDTO);
        PageResult pageResult = productService.page(productPageDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation("新增商品")
    public Result add(@RequestBody ProductDTO productDTO) {
        log.info("新增商品，参数：{}", productDTO);
        productService.add(productDTO);
        return Result.success();
    }

    @PutMapping("/status/{id}")
    @ApiOperation("修改商品状态")
    public Result updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        log.info("修改商品状态，参数：{}, {}", id, status);
        productService.updateStatus(id, status);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("id查询商品")
    public Result<ProductVO> getById(@PathVariable Long id) {
        log.info("id查询商品，参数：{}", id);
        ProductVO productPageVO = productService.getById(id);
        return Result.success(productPageVO);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改商品")
    public Result edit(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        log.info("修改商品，参数：{}, {}", id, productDTO);
        productDTO.setId(id);
        productService.update(productDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除商品")
    public Result delete(@PathVariable Long id) {
        log.info("删除商品，参数：{}", id);
        productService.delete(id);
        return Result.success();
    }

    @PutMapping("/stock/{id}")
    @ApiOperation("修改商品库存")
    public Result updateStock(@PathVariable Long id, @RequestParam Long stock) {
        log.info("修改商品库存，参数：{}, {}", id, stock);
        productService.updateStock(id, stock);
        return Result.success();
    }
}
