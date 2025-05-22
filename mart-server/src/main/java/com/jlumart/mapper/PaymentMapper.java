package com.jlumart.mapper;

import com.github.pagehelper.Page;
import com.jlumart.dto.OrderPageDTO;
import com.jlumart.entity.PaymentOrder;
import com.jlumart.vo.PaymentPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface PaymentMapper {
    void insert(PaymentOrder paymentOrder);

    @Select("select * from payment_orders where order_id = #{paymentOrderId} and user_id = #{userId}")
    PaymentOrder get(String paymentOrderId, Long userId);

    void update(PaymentOrder paymentOrder);

    @Select("select * from payment_orders where STATUS = 0")
    Set<PaymentOrder> getAllToBePay();

    Page<PaymentPageVO> page(OrderPageDTO orderPageDTO);
}
