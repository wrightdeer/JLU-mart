package com.jlumart.service.impl;

import com.jlumart.context.BaseContext;
import com.jlumart.entity.ShoppingCart;
import com.jlumart.mapper.ProductMapper;
import com.jlumart.mapper.ShoppingCartMapper;
import com.jlumart.service.ShoppingCartService;
import com.jlumart.vo.ProductVO;
import com.jlumart.vo.ShoppingCartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ProductMapper productMapper;
    private static final String PRODUCT_UPDATE_TIME_KEY = "PRODUCT_UPDATE_TIME:";

    @Transactional
    public void add(Long id, Long quantity) {
    Long userId = BaseContext.getCurrentId();

    // 查询当前用户的购物车中是否已存在该商品
    ShoppingCart existingCart = shoppingCartMapper.getShoppingCartByUserIdAndProductId(userId, id);

    if (existingCart == null) {
        // 如果不存在，则构建新的购物车条目，需要从商品服务获取商品相关信息
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .productsId(id)
                .quantity(quantity)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        // 调用插入方法添加到数据库
        shoppingCartMapper.insert(shoppingCart);
    } else {
        // 如果已存在，更新数量
        existingCart.setQuantity(existingCart.getQuantity() + quantity);
        shoppingCartMapper.update(existingCart);
    }
}

    public List<ShoppingCartVO> list() {
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCartVO> shoppingCartVOList = shoppingCartMapper.list(userId);
        for (ShoppingCartVO shoppingCartVO : shoppingCartVOList) {
            LocalDateTime  productUpdateTime = (LocalDateTime) redisTemplate.opsForValue().get(PRODUCT_UPDATE_TIME_KEY + shoppingCartVO.getProductsId());
            shoppingCartVO.setIsValid(1);
            if (productUpdateTime == null) {
                // 无更新时间，商品数据无效
                shoppingCartVO.setIsValid(0);
            } else if (productUpdateTime.isAfter(shoppingCartVO.getUpdateTime())) {
                // 商品数据有更新
                ProductVO productVO = productMapper.getById(shoppingCartVO.getProductsId());
                shoppingCartVO.setProductsImage(productVO.getImage());
                shoppingCartVO.setProductsName(productVO.getName());
                shoppingCartVO.setProductsPrice(productVO.getPrice());
                ShoppingCart shoppingCart = new ShoppingCart();
                BeanUtils.copyProperties(shoppingCartVO, shoppingCart);
                shoppingCartMapper.update(shoppingCart);
            }

        }
        return shoppingCartVOList;
    }

    public void delete(List<Long> ids) {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.delete(ids, userId);
    }

    public void updateQuantity(Long id, Long quantity) {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = shoppingCartMapper.getShoppingCartByUserIdAndId(userId, id);
        if (shoppingCart != null) {
            if (quantity == 0) {
                shoppingCartMapper.delete(Collections.singletonList(id), userId);
            } else {
                shoppingCart.setQuantity(quantity);
                shoppingCartMapper.update(shoppingCart);
            }
        }
    }

    public List<ShoppingCartVO> getByIds(List<Long> ids) {
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCartVO> shoppingCartVOList = shoppingCartMapper.getByIds(userId, ids);
        for (ShoppingCartVO shoppingCartVO : shoppingCartVOList) {
            LocalDateTime  productUpdateTime = (LocalDateTime) redisTemplate.opsForValue().get(PRODUCT_UPDATE_TIME_KEY + shoppingCartVO.getProductsId());
            if (productUpdateTime == null) {
                // 无更新时间，删除此项数据
                shoppingCartMapper.delete(Collections.singletonList(shoppingCartVO.getId()), userId);
            } else if (productUpdateTime.isAfter(shoppingCartVO.getUpdateTime())) {
                // 商品数据有更新
                ProductVO productVO = productMapper.getById(shoppingCartVO.getProductsId());
                shoppingCartVO.setProductsImage(productVO.getImage());
                shoppingCartVO.setProductsName(productVO.getName());
                shoppingCartVO.setProductsPrice(productVO.getPrice());
                ShoppingCart shoppingCart = new ShoppingCart();
                BeanUtils.copyProperties(shoppingCartVO, shoppingCart);
                shoppingCartMapper.update(shoppingCart);
            }

        }
        return shoppingCartVOList;
    }

}
