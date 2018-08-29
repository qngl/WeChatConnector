package com.vwfsag.fso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vwfsag.fso.domain.Product;
import com.vwfsag.fso.domain.ProductItem;
import com.vwfsag.fso.persistence.ProductItemMapper;
import com.vwfsag.fso.persistence.ProductMapper;

/**
 * @author liqiang
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductItemMapper itemMapper;

	@Autowired
	private CacheService cacheService;
	
	@Override
	public List<Product> getProducts() {
		String cacheKey = "getProducts";
		List<Product> prods = cacheService.getList(Product.class, cacheKey);
		if(prods == null) {
			prods = productMapper.selectAll();
			cacheService.putList(Product.class, cacheKey, prods);
		}
		return prods;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveProduct(Product product) {
		if(product.getId() != null) {
			productMapper.updateByPrimaryKeySelective(product);
		} else {
			productMapper.insert(product);
		}
		cacheService.clear(Product.class);
	}

	@Override
	public Product getProduct(Integer productId) {
		String cacheKey = String.format("getProduct(%d)", productId);
		Product prod = cacheService.get(Product.class, cacheKey);
		if(prod == null) {
			prod = productMapper.selectByPrimaryKey(productId);
			if (prod != null) {
				cacheService.put(Product.class, cacheKey, prod);
			}
		}
		return prod;
	}

	@Override
	public List<ProductItem> getItems(Integer productId) {
		String cacheKey = String.format("getItems(%d)", productId);
		List<ProductItem> items = cacheService.getList(ProductItem.class, cacheKey);
		if(items == null) {
			items = itemMapper.selectByProductId(productId);
			cacheService.putList(ProductItem.class, cacheKey, items);
		}
		return items;
	}

	@Override
	public ProductItem getItem(Integer itemId) {
		String cacheKey = String.format("getItem(%d)", itemId);
		ProductItem prod = cacheService.get(ProductItem.class, cacheKey);
		if(prod == null) {
			prod = itemMapper.selectByPrimaryKey(itemId);
			cacheService.put(ProductItem.class, cacheKey, prod);
		}
		return prod;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveItem(ProductItem item) {
		if(item.getId() != null) {
			itemMapper.updateByPrimaryKeySelective(item);
		} else {
			itemMapper.insert(item);
		}
		cacheService.clear(ProductItem.class);
	}

	@Override
	public ProductItem getItem(Integer productId, Integer typeId, Integer colorId) {
		String cacheKey = String.format("getItem(%d， %d, %d)", productId, typeId, colorId);
		ProductItem prod = cacheService.get(ProductItem.class, cacheKey);
		if(prod == null) {
			ProductItem item = new ProductItem();
			item.setProductId(productId);
			item.setColorId(colorId);
			item.setTypeId(typeId);
			prod = itemMapper.selectByProductIdTypeIdColorId(item);
			cacheService.put(ProductItem.class, cacheKey, prod);
		}
		return prod;
	}

}
