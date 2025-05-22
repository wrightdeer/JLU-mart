package com.jlumart.service;

import com.jlumart.dto.PaymentDTO;
import com.jlumart.dto.OrderPageDTO;
import com.jlumart.result.PageResult;
import com.jlumart.vo.PaymentVO;

public interface PaymentService {
    PaymentVO recharge(Double amount);

    void confirmRecharge(PaymentDTO paymentDTO);

    PageResult page(OrderPageDTO orderPageDTO);
}
