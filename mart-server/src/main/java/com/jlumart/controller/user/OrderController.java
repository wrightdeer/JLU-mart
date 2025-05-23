package com.jlumart.controller.user;

import com.jlumart.context.BaseContext;
import com.jlumart.dto.OrderCancelDTO;
import com.jlumart.dto.OrderDTO;
import com.jlumart.dto.OrderPageDTO;
import com.jlumart.dto.OrderPayDTO;
import com.jlumart.result.PageResult;
import com.jlumart.result.Result;
import com.jlumart.service.OrderService;
import com.jlumart.vo.OrderPayVO;
import com.jlumart.vo.OrderStatisticVO;
import com.jlumart.vo.OrderViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@Api(value = "用户订单相关接口")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    @ApiOperation(value = "创建订单")
    public Result<OrderPayVO> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("创建订单，参数为：{}", orderDTO);
        OrderPayVO orderPayVO = orderService.createOrder(orderDTO);
        return Result.success(orderPayVO);
    }

    @PostMapping("/confirmPayment")
    @ApiOperation(value = "确认支付")
    public Result confirmPayment(@RequestBody OrderPayDTO orderPayDTO) {
        log.info("确认支付，参数为：{}", orderPayDTO);
        orderService.confirmPayment(orderPayDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询订单")
    public Result<PageResult> page(OrderPageDTO orderPageDTO) {
        log.info("分页查询订单，分页参数：{}", orderPageDTO);
        PageResult pageResult = orderService.pageView(orderPageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/statistics")
    @ApiOperation(value = "订单数量统计")
    public Result<OrderStatisticVO> statistics() {
        log.info("订单数量统计");
        Long  userId = BaseContext.getCurrentId();
        OrderStatisticVO orderStatisticVO = orderService.statistics(userId);
        return Result.success(orderStatisticVO);
    }

    @GetMapping("/{orderId}")
    @ApiOperation(value = "查询订单详情")
    public Result<OrderViewVO> get(@PathVariable String orderId) {
        log.info("查询订单详情：{}", orderId);
        OrderViewVO orderViewVO = orderService.getViewByOrderId(orderId);
        return Result.success(orderViewVO);
    }

    @PutMapping("/cancel")
    @ApiOperation(value = "取消订单")
    public Result cancel(@RequestBody OrderCancelDTO orderCancelDTO) {
        log.info("取消订单，参数为：{}", orderCancelDTO);
        orderCancelDTO.setUserId(BaseContext.getCurrentId());
        orderService.cancel(orderCancelDTO);
        return Result.success();
    }

}
