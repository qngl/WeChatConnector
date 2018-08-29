package com.vwfsag.fso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vwfsag.fso.domain.Shop;
import com.vwfsag.fso.persistence.ShopMapper;

/**
 * @author liqiang
 *
 */
@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopMapper shopMapper;

	@Autowired
	private CacheService cacheService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveShop(Shop... shops) {
		if (shops != null) {
			for (Shop shop : shops) {
				if(shop.getId() != null && shop.getId() > 0) {
					shopMapper.updateByPrimaryKeySelective(shop);
				} else {
					shopMapper.insert(shop);
				}
			}
			cacheService.clear(Shop.class);
 		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer shopId) {
		shopMapper.deleteByPrimaryKey(shopId);
		cacheService.clear(Shop.class);
	}

	@Override
	public List<Shop> getByProvince(int provinceId, int start, int limit) {
		String listKey = String.format("getByProvince(%d, %d, %d)", provinceId, start, limit);
		List<Shop> shops = cacheService.getList(Shop.class, listKey);
		if(shops == null) {
			shops = shopMapper.selectByProvinceId(provinceId < 0 ? null : new Integer(provinceId), start, limit);
			cacheService.putList(Shop.class, listKey, shops);
		}
		return shops;
	}

	@Override
	public int countByProvince(int provinceId) {
		return shopMapper.countByProvinceId(provinceId < 0 ? null : new Integer(provinceId));
	}

	@Override
	public List<Shop> getByCity(int cityId, int start, int limit) {
		String cacheKey = String.format("getByCity(%d, %d, %d)", cityId, start, limit);
		List<Shop> shops = cacheService.getList(Shop.class, cacheKey);
		if(shops == null) {
			shops = shopMapper.selectByCityId(cityId < 0 ? null : new Integer(cityId), start, limit);
			cacheService.putList(Shop.class, cacheKey, shops);
		}
		return shops;
	}

	@Override
	public int countByCity(int cityId) {
		return shopMapper.countByCityId(cityId < 0 ? null : new Integer(cityId));
	}

	@Override
	public Shop getById(int id) {
		String cacheKey = String.format("getById(%d)", id);
		Shop shop = cacheService.get(Shop.class, cacheKey);
		if(shop == null) {
			shop = shopMapper.selectByPrimaryKey(new Integer(id));
			if(shop != null) {
				cacheService.put(Shop.class, cacheKey, shop);
			}
		}
		return shop;
	}

}
