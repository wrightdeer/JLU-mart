package com.jlumart.controller.user;

import com.jlumart.result.Result;
import com.jlumart.service.ShoppingCartService;
import com.jlumart.vo.ShoppingCartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userShoppingCartController")
@RequestMapping("/user/shoppingCart")
@Api(value = "用户购物车相关接口")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/{id}")
    @ApiOperation(value = "添加商品到购物车")
    public Result add(@PathVariable Long id, @RequestParam Long quantity) {
        log.info("添加商品到购物车,商品id:{},数量:{}", id, quantity);
        shoppingCartService.add(id, quantity);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation(value = "查询购物车列表")
    public Result<List<ShoppingCartVO>> list() {
        log.info("查询购物车列表");
        List<ShoppingCartVO> shoppingCartVO = shoppingCartService.list();
        return Result.success(shoppingCartVO);
    }
    @DeleteMapping
    @ApiOperation(value = "删除购物车")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除购物车：{}", ids);
        shoppingCartService.delete(ids);
        return Result.success();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改全部购物车")
    public Result update(@PathVariable Long id, @RequestParam Long quantity) {
        log.info("修改购物车数量：{}", quantity);
        shoppingCartService.updateQuantity(id, quantity);
        return Result.success();
    }
    @GetMapping
    @ApiOperation(value = "查询指定id购物车")
    public Result<List<ShoppingCartVO>> get(@RequestParam List<Long> ids) {
        log.info("查询购物车：{}", ids);
        List<ShoppingCartVO> shoppingCartVOList = shoppingCartService.getByIds(ids);
        return Result.success(shoppingCartVOList);
    }
}
