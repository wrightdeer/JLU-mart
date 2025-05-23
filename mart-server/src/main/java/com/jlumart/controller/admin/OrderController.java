package com.jlumart.controller.admin;

import com.jlumart.context.BaseContext;
import com.jlumart.dto.OrderCancelDTO;
import com.jlumart.dto.OrderPageDTO;
import com.jlumart.result.PageResult;
import com.jlumart.result.Result;
import com.jlumart.service.OrderService;
import com.jlumart.vo.OrderPageVO;
import com.jlumart.vo.OrderStatisticVO;
import com.jlumart.vo.OrderVO;
import com.jlumart.vo.OrderViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@Slf4j
@RequestMapping("/admin/order")
@Api(tags = "订单管理")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/page")
    public Result<PageResult> page(OrderPageDTO orderPageDTO) {
        log.info("分页查询订单，分页参数：{}", orderPageDTO);
        PageResult orderPageVO = orderService.page(orderPageDTO);
        return Result.success(orderPageVO);
    }
    @GetMapping("/statistics")
    public Result<OrderStatisticVO> statistics() {
        log.info("订单数量统计");
        OrderStatisticVO orderStatisticVO = orderService.statistics(null);
        return Result.success(orderStatisticVO);
    }

    @GetMapping("/{orderId}")
    @ApiOperation(value = "查询订单详情")
    public Result<OrderVO> get(@PathVariable String orderId) {
        log.info("查询订单详情：{}", orderId);
        OrderVO orderVO = orderService.getByOrderId(orderId);
        return Result.success(orderVO);
    }

    @PutMapping("/cancel")
    @ApiOperation(value = "取消订单")
    public Result cancel(@RequestBody OrderCancelDTO orderCancelDTO) {
        log.info("取消订单，参数为：{}", orderCancelDTO);
        orderCancelDTO.setUserId(0L);
        orderService.cancel(orderCancelDTO);
        return Result.success();
    }
}
