package com.jlumart.service;

import com.jlumart.dto.ProductDTO;
import com.jlumart.dto.ProductPageDTO;
import com.jlumart.result.PageResult;
import com.jlumart.vo.ProductVO;

public interface ProductService {
    PageResult page(ProductPageDTO productPageDTO);

    void add(ProductDTO productDTO);

    void updateStatus(Long id, Integer status);

    ProductVO getById(Long id);

    void update(ProductDTO productDTO);

    void delete(Long id);

    void updateStock(Long id, Long stock);
}
