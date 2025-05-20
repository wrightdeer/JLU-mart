package com.jlumart.mapper;

import com.jlumart.entity.Favorite;
import com.jlumart.vo.FavoriteVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    @Select("SELECT ID, PRODUCTS_ID, PRODUCTS_NAME, PRODUCTS_IMAGE, PRODUCTS_PRICE, UPDATE_TIME FROM FAVORITES WHERE USER_ID = #{userId}")
    List<FavoriteVO> list(Long userId);

    void update(FavoriteVO favoriteVO);

    void insert(Favorite favorite);

    @Delete("DELETE FROM FAVORITES WHERE ID = #{id} AND USER_ID = #{userId}")
    void delete(Long id, Long userId);

    @Delete("DELETE FROM FAVORITES WHERE USER_ID = #{userId}")
    void deleteAll(Long userId);
}
