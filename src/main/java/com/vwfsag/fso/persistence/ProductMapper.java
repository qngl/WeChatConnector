package com.vwfsag.fso.persistence;

import java.util.List;

import com.vwfsag.fso.domain.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    List<Product> selectAll();

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}