package com.vwfsag.fso.persistence;

import java.util.List;

import com.vwfsag.fso.domain.City;

/**
 * @author liqiang
 *
 */
public interface CityMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(City record);

	List<City> getAll();

	List<City> getProvinces();

	List<City> getCities(Integer provinceId);

	City selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(City record);

	int updateByPrimaryKey(City record);
}