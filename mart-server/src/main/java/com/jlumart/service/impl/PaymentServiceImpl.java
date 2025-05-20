package com.jlumart.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jlumart.context.BaseContext;
import com.jlumart.dto.PaymentDTO;
import com.jlumart.dto.PaymentPageDTO;
import com.jlumart.entity.PaymentOrder;
import com.jlumart.entity.User;
import com.jlumart.exception.IllegalOperationException;
import com.jlumart.mapper.PaymentMapper;
import com.jlumart.mapper.UserMapper;
import com.jlumart.result.PageResult;
import com.jlumart.service.PaymentService;
import com.jlumart.vo.PaymentPageVO;
import com.jlumart.vo.PaymentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private UserMapper userMapper;

    public PaymentVO recharge(Double amount) {
        Long userId = BaseContext.getCurrentId();
        String paymentOrderId = UUID.randomUUID().toString();
        PaymentOrder  paymentOrder = PaymentOrder.builder()
                .orderId(paymentOrderId)
                .userId(userId)
                .amount(amount)
                .createTime(LocalDateTime.now())
                .build();
        paymentMapper.insert(paymentOrder);
        return new PaymentVO(paymentOrderId);
    }

    @Transactional
    public void confirmRecharge(PaymentDTO paymentDTO) {
        Long userId = BaseContext.getCurrentId();
        PaymentOrder paymentOrder = paymentMapper.get(paymentDTO.getPaymentOrderId(), userId);
        if (paymentOrder == null) {
            throw new IllegalOperationException("订单不存在");
        }
        if (paymentOrder.getStatus() == 1) {
            throw new IllegalOperationException("订单已支付");
        }
        if (paymentOrder.getStatus() == 2) {
            throw new IllegalOperationException("订单已取消");
        }
        paymentOrder.setStatus(1);
        User user = User.builder()
                .id(userId)
                .balance(paymentOrder.getAmount())
                .build();
        userMapper.update(user);
        paymentMapper.update(paymentOrder);
    }

    public PageResult page(PaymentPageDTO paymentPageDTO) {
        Long userId = BaseContext.getCurrentId();
        paymentPageDTO.setUserId(userId);
        PageHelper.startPage(paymentPageDTO.getPage(), paymentPageDTO.getPageSize());
        Page<PaymentPageVO> page = paymentMapper.page(paymentPageDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
