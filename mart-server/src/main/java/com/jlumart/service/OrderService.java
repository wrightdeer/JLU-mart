package com.jlumart.service;

import com.jlumart.dto.OrderCancelDTO;
import com.jlumart.dto.OrderDTO;
import com.jlumart.dto.OrderPageDTO;
import com.jlumart.dto.OrderPayDTO;
import com.jlumart.result.PageResult;
import com.jlumart.vo.OrderPayVO;
import com.jlumart.vo.OrderStatisticVO;
import com.jlumart.vo.OrderViewVO;

public interface OrderService {
    OrderPayVO createOrder(OrderDTO orderDTO);

    void confirmPayment(OrderPayDTO orderPayDTO);

    PageResult page(OrderPageDTO orderPageDTO);

    OrderStatisticVO statistics();

    OrderViewVO getViewByOrderId(String orderId);

    void cancel(OrderCancelDTO orderCancelDTO);
}
