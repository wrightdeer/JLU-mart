package com.jlumart.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jlumart.constant.StatusConstant;
import com.jlumart.context.BaseContext;
import com.jlumart.dto.ProductDTO;
import com.jlumart.dto.ProductPageDTO;
import com.jlumart.entity.Category;
import com.jlumart.entity.Product;
import com.jlumart.exception.IllegalOperationException;
import com.jlumart.mapper.CategoryMapper;
import com.jlumart.mapper.ProductMapper;
import com.jlumart.result.PageResult;
import com.jlumart.service.ProductService;
import com.jlumart.vo.CategoryVO;
import com.jlumart.vo.ProductPageVO;
import com.jlumart.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    public PageResult page(ProductPageDTO productPageDTO) {
        PageHelper.startPage(productPageDTO.getPage(), productPageDTO.getPageSize());
        Page<ProductPageVO> page = productMapper.page(productPageDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Transactional
    public void add(ProductDTO productDTO) {
        // 首先检查分类是否存在，且是否为二级分类
        CategoryVO category = categoryMapper.getById(productDTO.getCategoryId());
        if (category == null) {
            throw new IllegalOperationException("分类不存在");
        }
        if (Objects.equals(category.getParentId(), 0L)) {
            throw new IllegalOperationException("该分类不是二级分类，无法添加商品");
        }

        // 获取当前用户id
        Long currentUserId = BaseContext.getCurrentId();

        // 创建商品对象
        Product  product = new Product();

        BeanUtils.copyProperties(productDTO, product);
        product.setStatus(StatusConstant.DISABLE);
        product.setStock(0L);
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());
        product.setCreateUser(currentUserId);
        product.setUpdateUser(currentUserId);

        productMapper.insert(product);

        // 缓存商品更新时间
        ProductRedisUtil  productRedisUtil = new ProductRedisUtil();
        productRedisUtil.redisCacheProductUpdateTime(product.getId(), product.getUpdateTime());

        productRedisUtil.redisInvalidateProductView(category.getParentId());
    }

    public void updateStatus(Long id, Integer status) {
        Long currentUserId = BaseContext.getCurrentId();
        Product product = new Product();
        product.setId(id);
        product.setStatus(status);
        product.setUpdateTime(LocalDateTime.now());
        product.setUpdateUser(currentUserId);
        productMapper.update(product);

        ProductRedisUtil productRedisUtil = new ProductRedisUtil();
        productRedisUtil.redisAllProductView();

        // 如果商品状态为禁用，则删除商品更新时间, 否则缓存商品更新时间
        if (Objects.equals(status, StatusConstant.DISABLE)) {
            productRedisUtil.redisRemoveUpdateTime(id);
        } else {
            productRedisUtil.redisCacheProductUpdateTime(id, product.getUpdateTime());
        }
    }

    public ProductVO getById(Long id) {
        if (id != null) {
            ProductVO productVO = productMapper.getById(id);
            return productVO;
        }
        return null;
    }

    @Transactional
    public void update(ProductDTO productDTO) {
        // 首先检查分类是否存在，且是否为二级分类
        if (productDTO.getCategoryId() != null) {
            CategoryVO category = categoryMapper.getById(productDTO.getCategoryId());
            if (category == null) {
                throw new IllegalOperationException("分类不存在");
            }
            if (Objects.equals(category.getParentId(), 0L)) {
                throw new IllegalOperationException("该分类不是二级分类，无法添加商品");
            }
        }
        // 获取当前用户id
        Long currentUserId = BaseContext.getCurrentId();
        // 创建商品对象
        Product  product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        product.setUpdateTime(LocalDateTime.now());
        product.setUpdateUser(currentUserId);

        productMapper.update(product);

        ProductRedisUtil productRedisUtil = new ProductRedisUtil();
        productRedisUtil.redisCacheProductUpdateTime(product.getId(), product.getUpdateTime());
        productRedisUtil.redisInvalidateProductView(product.getCategoryId());
    }

    public void delete(Long id) {
        productMapper.delete(id);

        // 删除商品更新时间,删除所有商品缓存
        ProductRedisUtil productRedisUtil = new ProductRedisUtil();
        productRedisUtil.redisRemoveUpdateTime(id);
        productRedisUtil.redisAllProductView();
    }

    public void updateStock(Long id, Long stock) {
        Long currentUserId = BaseContext.getCurrentId();
        Product  product = new Product();
        product.setId(id);
        product.setStock(stock);
        product.setUpdateTime(LocalDateTime.now());
        product.setUpdateUser(currentUserId);
        productMapper.update(product);
    }


    private class ProductRedisUtil {
        private static final String CATEGORY_KEY = "CATEGORY:";
        private static final String PRODUCT_UPDATE_TIME_KEY = "PRODUCT_UPDATE_TIME:";
        /**
         * 缓存商品更新时间
         * @param productId 商品id
         * @param updateTime 更新时间
         */
        public void redisCacheProductUpdateTime(Long productId, LocalDateTime updateTime) {
            redisTemplate.opsForValue().set(PRODUCT_UPDATE_TIME_KEY + productId, updateTime);
        }
        /**
         * 删除商品更新时间
         * @param productId 商品id
         */
        public void redisRemoveUpdateTime(Long productId) {
            redisTemplate.delete(PRODUCT_UPDATE_TIME_KEY + productId);
        }
        /**
         * 删除商品分类下的所有商品缓存
         * @param categoryId 商品分类id
         */
        public void redisInvalidateProductView(Long categoryId) {
            redisTemplate.delete(CATEGORY_KEY + categoryId);
        }
        /**
         * 删除所有商品缓存
         */
        public void redisAllProductView() {
            Set<String> keys = redisTemplate.keys("CATEGORY:*");
            if (!keys.isEmpty()) {
                redisTemplate.delete(keys);
            }

        }
    }
}
