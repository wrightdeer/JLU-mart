package com.jlumart.service.impl;

import com.jlumart.context.BaseContext;
import com.jlumart.entity.Favorite;
import com.jlumart.mapper.FavoriteMapper;
import com.jlumart.mapper.ProductMapper;
import com.jlumart.service.FavoriteService;
import com.jlumart.service.ShoppingCartService;
import com.jlumart.vo.FavoriteVO;
import com.jlumart.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private RedisTemplate  redisTemplate;
    @Autowired
    private ProductMapper  productMapper;
    @Autowired
    private ShoppingCartService  shoppingCartService;
    private static final String PRODUCT_UPDATE_TIME_KEY = "PRODUCT_UPDATE_TIME:";

    public List<FavoriteVO> list() {
        Long  userId = BaseContext.getCurrentId();
        List<FavoriteVO> favoriteVOList = favoriteMapper.list(userId);
        for (FavoriteVO favoriteVO : favoriteVOList) {

            // 获取商品更新时间
            LocalDateTime productUpdateTime = (LocalDateTime) redisTemplate.opsForValue().get(PRODUCT_UPDATE_TIME_KEY + favoriteVO.getProductsId());

            favoriteVO.setIsValid(1);
            if (productUpdateTime == null) {
                // 无更新时间，商品数据无效
                favoriteVO.setIsChange(0);
                favoriteVO.setIsValid(0);
            }else if (productUpdateTime.isAfter(favoriteVO.getUpdateTime())) {
                // 商品数据有更新
                favoriteVO.setIsChange(1);
                ProductVO productVO = productMapper.getById(favoriteVO.getProductsId());
                favoriteVO.setProductsImage(productVO.getImage());
                favoriteVO.setProductsName(productVO.getName());
                favoriteVO.setProductsPrice(productVO.getPrice());
                favoriteVO.setUpdateTime(LocalDateTime.now());
                favoriteMapper.update(favoriteVO);
            } else {
                // 商品数据无更新
                favoriteVO.setIsChange(0);
            }
        }
        return favoriteVOList;
    }

    public void add(Long id) {
        Long  userId = BaseContext.getCurrentId();
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductsId(id);
        favorite.setCreateTime(LocalDateTime.now());
        favorite.setUpdateTime(LocalDateTime.now());
        favoriteMapper.insert(favorite);
    }

    public void delete(Long id) {
        Long  userId = BaseContext.getCurrentId();
        favoriteMapper.delete(id, userId);
    }

    public void deleteAll() {
        Long  userId = BaseContext.getCurrentId();
        favoriteMapper.deleteAll(userId);
    }

    public void addCart() {
        Long  userId = BaseContext.getCurrentId();
        favoriteMapper.list(userId).forEach(favoriteVO -> shoppingCartService.add(favoriteVO.getProductsId(), 1L));
    }
}
