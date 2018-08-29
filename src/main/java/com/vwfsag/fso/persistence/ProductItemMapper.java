package com.vwfsag.fso.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vwfsag.fso.domain.ProductItem;

public interface ProductItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductItem record);

    int insertSelective(ProductItem record);

    ProductItem selectByPrimaryKey(Integer id);

    List<ProductItem> selectByProductId(@Param("productId")Integer productId);

    ProductItem selectByProductIdTypeIdColorId(ProductItem record);

    int updateByPrimaryKeySelective(ProductItem record);

    int updateByPrimaryKey(ProductItem record);
}