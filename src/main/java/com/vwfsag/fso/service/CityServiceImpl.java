package com.vwfsag.fso.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vwfsag.fso.domain.City;
import com.vwfsag.fso.persistence.CityMapper;

/**
 * @author liqiang
 *
 */
@Service
public class CityServiceImpl implements CityService {

	private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

	@Autowired
	private CityMapper cityMapper;

	@Autowired
	private CacheService cacheService;

	@Override
	public List<City> getProvinces() {
		String cacheKey = "getProvinces()";
		List<City> cities = cacheService.getList(City.class, cacheKey);
		if(cities == null) {
			cities = cityMapper.getProvinces();
			cacheService.putList(City.class, cacheKey, cities);
		}
		return cities;
	}

	@Override
	public List<City> getCities(Integer provinceId) {
		String cacheKey = String.format("getCities(%d)", provinceId);
		List<City> cities = cacheService.getList(City.class, cacheKey);
		if(cities == null) {
			cities = cityMapper.getCities(provinceId);
			cacheService.putList(City.class,  cacheKey, cities);
		}
		return cities;
	}

	@Override
	public List<City> getAll() {
		return cityMapper.getAll();
	}

	@Override
	public City getLocation(Integer id) {
		String cacheKey = String.format("getLocation(%d)", id);
		City city = cacheService.get(City.class, cacheKey);
		if(city == null) {
			city = cityMapper.selectByPrimaryKey(id);
			if(city != null) {
				cacheService.put(City.class, cacheKey, city);
			} else {
				cacheService.put(City.class, cacheKey, new City());
				log.error("Invalid cityId found: {}", id);
			}
		}
		return city;
	}

}
