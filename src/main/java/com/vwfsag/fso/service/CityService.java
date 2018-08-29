package com.vwfsag.fso.service;

import java.util.List;

import com.vwfsag.fso.domain.City;

/**
 * @author liqiang
 *
 */
public interface CityService {

	public List<City> getAll();

	public List<City> getProvinces();

	public List<City> getCities(Integer provinceId);

	public City getLocation(Integer id);

}
