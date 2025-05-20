package com.jlumart.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.hash.Hashing;
import com.jlumart.constant.StatusConstant;
import com.jlumart.context.BaseContext;
import com.jlumart.dto.ProductDTO;
import com.jlumart.dto.ProductPageDTO;
import com.jlumart.entity.Product;
import com.jlumart.exception.IllegalOperationException;
import com.jlumart.mapper.CategoryMapper;
import com.jlumart.mapper.ProductMapper;
import com.jlumart.result.PageResult;
import com.jlumart.service.ProductService;
import com.jlumart.vo.CategoryVO;
import com.jlumart.vo.ProductPageVO;
import com.jlumart.vo.ProductVO;
import com.jlumart.vo.ProductViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private ProductRedisUtil productRedisUtil;
    @PostConstruct  // 容器初始化后会调用此方法
    private void initRedisUtil() {
        this.productRedisUtil = new ProductRedisUtil();
    }
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
            return productMapper.getById(id);
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

        productRedisUtil.redisCacheProductUpdateTime(product.getId(), product.getUpdateTime());
        productRedisUtil.redisInvalidateProductView(product.getCategoryId());
    }

    public void delete(Long id) {
        productMapper.delete(id);

        // 删除商品更新时间,删除所有商品缓存
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

    public PageResult viewPage(ProductPageDTO productPageDTO) {
        // 1. 生成缓存key
        String cacheKey = productRedisUtil.generateCacheKey(productPageDTO);

        // 2. 尝试从缓存获取
        PageResult pageResult  = productRedisUtil.redisGetProductPage(cacheKey);
        if (pageResult != null) {
            log.info("从缓存中获取商品分页数据，缓存key：{}", cacheKey);
            return pageResult;
        }

        // 3. 缓存不存在则查询数据库
        log.info("从数据库中获取商品分页数据，缓存key：{}", cacheKey);
        PageHelper.startPage(productPageDTO.getPage(), productPageDTO.getPageSize());
        Page<ProductPageVO> page = productMapper.viewPage(productPageDTO);
        pageResult = new PageResult(page.getTotal(), page.getResult());

        // 4. 将结果存入缓存
        productRedisUtil.redisCacheProductPage(cacheKey, pageResult);

        return pageResult;
    }

    public ProductViewVO getViewById(Long id) {
        Long currentUserId = BaseContext.getCurrentId();
        if (id != null) {
            return productMapper.getViewById(id, currentUserId);
        }
        return null;
    }


    private class ProductRedisUtil {
        private static final String CATEGORY_KEY = "CATEGORY:";
        private static final String PRODUCT_UPDATE_TIME_KEY = "PRODUCT_UPDATE_TIME:";
        private static final long CACHE_EXPIRE_SECONDS = 300; // 缓存过期时间1小时

        /**
         * 生成缓存key
         * @param dto 查询条件
         * @return 缓存key
         */
        public String generateCacheKey(ProductPageDTO dto) {
            // 1. 拼接关键字段（name为空时忽略）
            String input = String.format("%d|%d|%s",
                    dto.getPage(),
                    dto.getPageSize(),
                    dto.getName() != null ? dto.getName() : ""  // 处理空name
            );
            // 2. 计算 MurmurHash 并取前12位（16进制）
            return CATEGORY_KEY + dto.getCategoryId() + ":" +
                    Hashing.murmur3_128()
                            .hashString(input, StandardCharsets.UTF_8)
                            .toString()
                            .substring(0, 12);
        }
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
            Set<String> keys = redisTemplate.keys(CATEGORY_KEY + categoryId + ":*");
            if (!keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
        }
        /**
         * 删除所有商品缓存
         */
        public void redisAllProductView() {
            Set<String> keys = redisTemplate.keys(CATEGORY_KEY +"*");
            if (!keys.isEmpty()) {
                redisTemplate.delete(keys);
            }

        }

        /**
         * 从Redis获取分页缓存
         *
         * @param cacheKey 缓存key
         * @return 分页数据，不存在返回null
         */
        @SuppressWarnings("unchecked")
        public PageResult redisGetProductPage(String cacheKey) {
            try {
                return (PageResult) redisTemplate.opsForValue().get(cacheKey);
            } catch (Exception e) {
                // 反序列化失败等异常情况
                return null;
            }
        }

        /**
         * 缓存分页数据
         * @param cacheKey 缓存key
         * @param pageResult 分页数据
         */
        public void redisCacheProductPage(String cacheKey, PageResult pageResult) {
            try {
                redisTemplate.opsForValue().set(
                        cacheKey,
                        pageResult,
                        CACHE_EXPIRE_SECONDS,
                        TimeUnit.SECONDS
                );
            } catch (Exception e) {
                log.error("缓存商品分页数据失败", e);
            }
        }

    }
}
