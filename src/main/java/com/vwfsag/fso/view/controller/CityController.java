package com.vwfsag.fso.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vwfsag.fso.domain.City;
import com.vwfsag.fso.service.CityService;

/**
 * @author liqiang
 * 
 */
@Controller
@RequestMapping(value = "/city")
public class CityController {

	@Autowired
	private CityService cityService;


	@RequestMapping(value = "/province", method = RequestMethod.GET)
	public @ResponseBody List<City> provinces() {
		List<City> cities = cityService.getProvinces();
		return cities;
	}
	
	@RequestMapping(value = "/province/{provinceId}", method = RequestMethod.GET)
	public @ResponseBody List<City> citiesOfProvince(@PathVariable("provinceId") int provinceId) {
		List<City> cities = cityService.getCities(provinceId);
		return cities;
	}
}
