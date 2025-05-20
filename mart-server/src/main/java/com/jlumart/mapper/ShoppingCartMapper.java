package com.jlumart.mapper;

import com.jlumart.entity.ShoppingCart;
import com.jlumart.vo.ShoppingCartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    @Select("SELECT * FROM SHOPPING_CARTS WHERE USER_ID = #{userId} AND PRODUCTS_ID = #{id}")
    ShoppingCart getShoppingCartByUserIdAndProductId(Long userId, Long id);

    void insert(ShoppingCart shoppingCart);

    void update(ShoppingCart existingCart);

    @Select("SELECT ID, PRODUCTS_ID, QUANTITY, PRODUCTS_NAME, PRODUCTS_IMAGE, PRODUCTS_PRICE, UPDATE_TIME FROM SHOPPING_CARTS WHERE USER_ID = #{userId}")
    List<ShoppingCartVO> list(Long userId);

    void delete(List<Long> ids, Long userId);

    @Select("SELECT ID, QUANTITY FROM SHOPPING_CARTS WHERE USER_ID = #{userId} AND ID = #{id}")
    ShoppingCart getShoppingCartByUserIdAndId(Long userId, Long id);

    List<ShoppingCartVO> getByIds(Long userId, List<Long> ids);
}
