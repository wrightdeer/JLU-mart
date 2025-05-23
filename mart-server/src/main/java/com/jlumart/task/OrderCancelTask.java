package com.jlumart.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlumart.entity.PaymentOrder;
import com.jlumart.entity.Product;
import com.jlumart.entity.ProductItem;
import com.jlumart.entity.ShoppingOrder;
import com.jlumart.exception.IllegalOperationException;
import com.jlumart.mapper.PaymentMapper;
import com.jlumart.mapper.OrderMapper;
import com.jlumart.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class OrderCancelTask {
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductService productService;

    @Transactional
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void cancelShoppingOrder() {
        log.info("清理过期购物订单");
        LocalDateTime now = LocalDateTime.now();
        Set<ShoppingOrder> shoppingOrders = orderMapper.getAllToBePay();
        for (ShoppingOrder shoppingOrder : shoppingOrders) {
            log.info("订单：{}", shoppingOrder);
            if (now.isAfter(shoppingOrder.getCreateTime().plusMinutes(30))) {
                shoppingOrder.setStatus(4);
                shoppingOrder.setCancelReason("订单超时未支付，系统自动取消");
                orderMapper.update(shoppingOrder);
                List<ProductItem> productItemList = null;
                try {
                    productItemList = objectMapper.readValue(shoppingOrder.getItems(), new TypeReference<List<ProductItem>>() {
                    });
                    for (ProductItem productItem : productItemList) {
                        Product product = Product.builder()
                                .id(productItem.getId())
                                .stock(productItem.getQuantity())
                                .build();
                        productService.updateStock(product.getId(), product.getStock());
                    }
                } catch (JsonProcessingException e) {
                    log.error("商品信息错误");
                }

            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void cancelPaymentOrder() {
        log.info("清理过期支付订单");

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
