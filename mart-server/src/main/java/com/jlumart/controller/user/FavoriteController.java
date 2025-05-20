package com.jlumart.controller.user;

import com.jlumart.result.Result;
import com.jlumart.service.FavoriteService;
import com.jlumart.vo.FavoriteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userFavoriteController")
@RequestMapping("/user/favorite")
@Slf4j
@Api(value = "用户收藏相关接口")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/list")
    @ApiOperation(value = "查询用户收藏列表")
    public Result<List<FavoriteVO>> list(){
        log.info("查询用户收藏列表");
        List<FavoriteVO> favoriteVO = favoriteService.list();
        return Result.success(favoriteVO);
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "收藏商品")
    public Result favorite(@PathVariable Long id){
        log.info("收藏商品,{}" , id);
        favoriteService.add(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "取消收藏")
    public Result cancelFavorite(@PathVariable Long id){
        log.info("取消收藏,{}" , id);
        favoriteService.delete(id);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation(value = "取消全部收藏")
    public Result cancelFavorites(){
        log.info("批量取消收藏,{}");
        favoriteService.deleteAll();
        return Result.success();
    }

    @PostMapping("/add-cart")
    @ApiOperation(value = "收藏商品全部加入购物车")
    public Result addCart(){
        log.info("收藏商品全部加入购物车");
        favoriteService.addCart();
        return Result.success();
    }
}
