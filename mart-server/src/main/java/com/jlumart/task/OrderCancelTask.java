package com.jlumart.task;

import com.jlumart.entity.PaymentOrder;
import com.jlumart.mapper.PaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class OrderCancelTask {
    @Autowired
    private PaymentMapper paymentMapper;

    @Scheduled(cron = "0 0/2 * * * ? ") // 每2分钟执行一次
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
    }
}
