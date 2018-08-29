
package com.vwfsag.fso.service;

import java.util.List;

import com.vwfsag.fso.domain.Product;
import com.vwfsag.fso.domain.ProductItem;

/**
 * @author liqiang
 *
 */
public interface ProductService {

	public List<Product> getProducts();

	public void saveProduct(Product product);

	public Product getProduct(Integer productId);

	public List<ProductItem> getItems(Integer productId);

    ProductItem getItem(Integer productId, Integer typeId, Integer colorId);

	public ProductItem getItem(Integer itemId);

	public void saveItem(ProductItem item);

}
