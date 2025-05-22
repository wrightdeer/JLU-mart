package com.jlumart.mapper;

import com.github.pagehelper.Page;
import com.jlumart.dto.OrderPageDTO;
import com.jlumart.entity.ShoppingOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface OrderMapper {
    void insert(ShoppingOrder shoppingOrder);

    @Select("SELECT * FROM SHOPPING_ORDERS WHERE ID = #{id}")
    ShoppingOrder getById(Long id);

    void update(ShoppingOrder shoppingOrder);

    Page<ShoppingOrder> page(OrderPageDTO orderPageDTO);

    Long countByStatus(Integer status, Long userId);

    @Select("SELECT * FROM SHOPPING_ORDERS WHERE ORDER_ID = #{orderId} AND USER_ID = #{userId}")
    ShoppingOrder getByOrderId(String orderId, Long userId);

    @Select("SELECT * FROM SHOPPING_ORDERS WHERE STATUS = 0")
    Set<ShoppingOrder> getAllToBePay();
}
