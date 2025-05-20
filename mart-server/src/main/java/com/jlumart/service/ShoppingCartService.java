package com.jlumart.service;

import com.jlumart.vo.ShoppingCartVO;

import java.util.List;

public interface ShoppingCartService {
    void add(Long id, Long quantity);

    List<ShoppingCartVO> list();

    void delete(List<Long> ids);

    void updateQuantity(Long id, Long quantity);

    List<ShoppingCartVO> getByIds(List<Long> ids);
}
