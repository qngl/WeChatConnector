package com.vwfsag.fso.service;

import java.util.List;

import com.vwfsag.fso.domain.Shop;

/**
 * @author liqiang
 *
 */
public interface ShopService {

	public Shop getById(int id);

	public void saveShop(Shop... shops);

	public List<Shop> getByProvince(int provinceId, int start, int limit);

	public int countByProvince(int provinceId);

	public List<Shop> getByCity(int cityId, int start, int limit);

	public int countByCity(int cityId);

	public void delete(Integer shopId);

}
