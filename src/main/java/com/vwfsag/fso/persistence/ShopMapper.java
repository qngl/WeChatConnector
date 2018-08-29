package com.vwfsag.fso.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vwfsag.fso.domain.Shop;

/**
 * @author liqiang
 *
 */
public interface ShopMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer id);

    Shop selectByName(String name);

    Shop selectByCode(String code);
    
    int countByProvinceId(@Param("provinceId")Integer provinceId);
    
    List<Shop> selectByProvinceId(@Param("provinceId")Integer provinceId, @Param("start")int start, @Param("limit")int limit);
    
    int countByCityId(@Param("cityId")Integer cityId);
    
    List<Shop> selectByCityId(@Param("cityId")Integer cityId, @Param("start")int start, @Param("limit")int limit);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
}