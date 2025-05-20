package com.jlumart.service;

import com.jlumart.vo.FavoriteVO;

import java.util.List;

public interface FavoriteService {
    List<FavoriteVO> list();

    void add(Long id);

    void delete(Long id);

    void deleteAll();

    void addCart();
}
