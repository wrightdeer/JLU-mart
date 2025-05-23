package com.jlumart.controller.courier;

import com.jlumart.dto.OrderPageDTO;
import com.jlumart.result.PageResult;
import com.jlumart.result.Result;
import com.jlumart.service.OrderService;
import com.jlumart.vo.OrderAddressVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("courierOrderController")
@Api(tags = "配送端订单相关接口")
@RequestMapping("/courier/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询订单")
    public Result<PageResult> page(OrderPageDTO orderPageDTO) {
        log.info("分页查询订单，分页参数：{}", orderPageDTO);
        PageResult pageResult = orderService.pageCourier(orderPageDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/accept/{id}")
    @ApiOperation(value = "接单")
    public Result accept(@PathVariable Long id) {
        log.info("接单，订单id：{}", id);
        orderService.accept(id);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    @ApiOperation(value = "完成订单")
    public Result complete(@PathVariable Long id) {
        log.info("完成订单，订单id：{}", id);
        orderService.complete(id);
        return Result.success();
    }

    @GetMapping("/address/{id}")
    @ApiOperation(value = "查询订单配送地址")
    public Result<OrderAddressVO> getAddress(@PathVariable Long id) {
        log.info("查询订单配送地址，订单id：{}", id);
        OrderAddressVO orderAddressVO = orderService.getAddress(id);
        return Result.success(orderAddressVO);
    }
}
