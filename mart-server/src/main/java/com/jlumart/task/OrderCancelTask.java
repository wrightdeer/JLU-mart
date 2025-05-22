package com.jlumart.task;

import com.jlumart.entity.PaymentOrder;
import com.jlumart.entity.ShoppingOrder;
import com.jlumart.mapper.PaymentMapper;
import com.jlumart.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@Slf4j
public class OrderCancelTask {
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 0/5 * * * ? ") // 每2分钟执行一次
    public void execute() {
        log.info("清理过期订单");

        LocalDateTime now = LocalDateTime.now();
        Set<PaymentOrder> paymentOrders = paymentMapper.getAllToBePay();
        for (PaymentOrder paymentOrder : paymentOrders) {
            log.info("订单：{}", paymentOrder);
            if (now.isAfter(paymentOrder.getCreateTime().plusMinutes(30))) {
                paymentOrder.setStatus(2);
                paymentMapper.update(paymentOrder);
            }
        }

        Set<ShoppingOrder> shoppingOrders = orderMapper.getAllToBePay();
        for (ShoppingOrder shoppingOrder : shoppingOrders) {
            log.info("订单：{}", shoppingOrder);
            if (now.isAfter(shoppingOrder.getCreateTime().plusMinutes(30))) {
                shoppingOrder.setStatus(4);
                shoppingOrder.setCancelReason("订单超时未支付，系统自动取消");
                orderMapper.update(shoppingOrder);
            }
        }
    }
}
