package com.jlumart.service;

import com.jlumart.dto.OrderCancelDTO;
import com.jlumart.dto.OrderDTO;
import com.jlumart.dto.OrderPageDTO;
import com.jlumart.dto.OrderPayDTO;
import com.jlumart.result.PageResult;
import com.jlumart.vo.*;

public interface OrderService {
    OrderPayVO createOrder(OrderDTO orderDTO);

    void confirmPayment(OrderPayDTO orderPayDTO);

    PageResult pageView(OrderPageDTO orderPageDTO);

    OrderStatisticVO statistics(Long userId);

    OrderViewVO getViewByOrderId(String orderId);

    void cancel(OrderCancelDTO orderCancelDTO);

    PageResult page(OrderPageDTO orderPageDTO);

    OrderVO getByOrderId(String orderId);

    PageResult pageCourier(OrderPageDTO orderPageDTO);

    void accept(Long id);

    void complete(Long id);

    OrderAddressVO getAddress(Long id);
}
