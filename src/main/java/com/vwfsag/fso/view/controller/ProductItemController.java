package com.vwfsag.fso.view.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vwfsag.fso.domain.ProductItem;
import com.vwfsag.fso.domain.Status;
import com.vwfsag.fso.service.ProductService;
import com.vwfsag.fso.view.vo.Price;
import com.vwfsag.fso.view.vo.ProductItemForm;
import com.vwfsag.fso.view.vo.ResponseStatus;

/**
 * @author liqiang
 *
 */
@Controller
@RequestMapping(value = "/product/item")
public class ProductItemController {
	
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/page/{productId}", method = RequestMethod.GET)
	public String gotoListPage(@PathVariable("productId") Integer productId, Model model) {
		model.addAttribute("productId", productId);
		return "product-items";
	}

	@RequestMapping(value = "/edit/{productId}/{itemId}", method = RequestMethod.GET)
	public String edit(@PathVariable("productId") Integer productId, @PathVariable("itemId") Integer itemId, Model model) {
		model.addAttribute("productId", productId);
		model.addAttribute("itemId", itemId);
		return "product-item-edit";
	}
	

	@RequestMapping(value = "/list/{productId}", method = RequestMethod.GET)
	public @ResponseBody List<ProductItemForm> list(@PathVariable("productId") Integer productId) {
		List<ProductItem> all = productService.getItems(productId);
		List<ProductItemForm> results = new ArrayList<ProductItemForm>();
		for (ProductItem item : all) {
			results.add(new ProductItemForm(item));
		}
		return results;
	}

	@RequestMapping(value = "/invalidate/{itemId}", method = RequestMethod.GET)
	public @ResponseBody ResponseStatus invalidate(@PathVariable("itemId") Integer itemId) {
		ProductItem item = new ProductItem();
		item.setId(itemId);
		item.setSkuStatus(Status.INACTIVE.getCode());
		productService.saveItem(item);
		return ResponseStatus.success();
	}

	@RequestMapping(value = "/activate/{itemId}", method = RequestMethod.GET)
	public @ResponseBody ResponseStatus activate(@PathVariable("itemId") Integer itemId) {
		ProductItem item = new ProductItem();
		item.setId(itemId);
		item.setSkuStatus(Status.ACTIVE.getCode());
		productService.saveItem(item);
		return ResponseStatus.success();
	}

	@RequestMapping(value = "/{productId}/{itemId}", method = RequestMethod.GET)
	public @ResponseBody ProductItemForm load(@PathVariable("productId") Integer productId, @PathVariable("itemId") int itemId) {
		ProductItem item = null;
		if(itemId == 0) {
			item = new ProductItem();
			item.setId(itemId);
			item.setProductId(productId);
		} else {
			item = productService.getItem(itemId);
		}
		return new ProductItemForm(item);
	}

	@RequestMapping(value = "/{productId}/{itemId}", method = RequestMethod.POST)
	public @ResponseBody ResponseStatus save(@PathVariable("productId") Integer productId, @PathVariable("itemId") Integer itemId,
			@RequestParam(value = "typeValue") String typeValue,
			@RequestParam(value = "colorValue") String colorValue,
			@RequestParam(value = "typeId") Integer typeId,
			@RequestParam(value = "colorId") Integer colorId,
			@RequestParam(value = "discountPrice", required = false) String discountPrice,
			@RequestParam(value = "price", required = false) String marketPrice,
			@RequestParam(value = "guidePrice", required = false) String guidePrice,
			@RequestParam(value = "actPrice", required = false) String actPrice,
			Model model) {
		ResponseStatus res = ResponseStatus.success();
		Map<String, String> errors = new HashMap<String, String>();
		model.addAttribute("errors", errors);
		ProductItem check = productService.getItem(productId, typeId, colorId);
		if(check != null && (check.getId().intValue() != itemId || itemId.intValue() == 0)) {
			return new ResponseStatus(-1, "该款车已经存在");
		} else {
			ProductItem item = new ProductItem();
			if(itemId != null) {
				item = productService.getItem(itemId);
			} else {
				item.setProductId(productId);
				item.setTypeId(typeId);
				item.setTypeName("车款");
				item.setColorId(colorId);
				item.setColorName("颜色");
			}
			item.setTypeValue(typeValue);
			item.setColorValue(colorValue);
			Price price = new Price();
			if(NumberUtils.isNumber(discountPrice)) {
				price.setDiscountPrice(NumberUtils.toInt(discountPrice));
			} else {
				errors.put("discountPrice", "");
			}
			if(NumberUtils.isNumber(marketPrice)) {
				price.setPrice(NumberUtils.toInt(marketPrice));
			} else {
				errors.put("price", "");
			}
			if(NumberUtils.isNumber(guidePrice)) {
				price.setGuidePrice(NumberUtils.toInt(guidePrice));
			} else {
				errors.put("guidePrice", "");
			}
			if(NumberUtils.isNumber(actPrice)) {
				price.setActPrice(NumberUtils.toInt(actPrice));
			} else {
				errors.put("actPrice", "");
			}
			item.setSkuPrice(price.toJsonString());
			item.setSkuStatus(Status.ACTIVE.getCode());
			item.setCreateTime(System.currentTimeMillis());
			if(errors.isEmpty()) {
				productService.saveItem(item);
				return ResponseStatus.success();
			}
		}
		res = ResponseStatus.failure();
		res.setErrors(errors);
		return res;
	}

}
