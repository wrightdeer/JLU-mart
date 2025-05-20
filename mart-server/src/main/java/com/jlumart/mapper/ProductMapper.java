package com.jlumart.mapper;

import com.github.pagehelper.Page;
import com.jlumart.dto.ProductPageDTO;
import com.jlumart.entity.Product;
import com.jlumart.vo.ProductPageVO;
import com.jlumart.vo.ProductVO;
import com.jlumart.vo.ProductViewVO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductMapper {
    Page<ProductPageVO> page(ProductPageDTO productPageDTO);

    @Insert("INSERT INTO PRODUCTS(name, description, image, category_id, price, stock, create_user, update_user, create_time, update_time) " +
    "VALUES (#{name}, #{description}, #{image}, #{categoryId}, #{price}, #{stock}, #{createUser}, #{updateUser}, #{createTime}, #{updateTime})")
    @SelectKey(
            statement = "SELECT JLUMARTADMIN.PRODUCTS_SEQ.CURRVAL FROM DUAL",
            keyProperty = "id",
            resultType = Long.class,
            before = false
    )
    Long insert(Product product);

    void update(Product product);

    ProductVO getById(Long id);

    @Delete("DELETE FROM PRODUCTS WHERE ID = #{id}")
    void delete(Long id);

    @Select("SELECT COUNT(*) FROM PRODUCTS WHERE CATEGORY_ID = #{id}")
    Long getCountByCategoryId(Long id);

    Page<ProductPageVO> viewPage(ProductPageDTO productPageDTO);

    ProductViewVO getViewById(Long id, Long userId);
}
