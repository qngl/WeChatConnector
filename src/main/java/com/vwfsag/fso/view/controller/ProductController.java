package com.vwfsag.fso.view.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vwfsag.fso.domain.Product;
import com.vwfsag.fso.domain.ProductItem;
import com.vwfsag.fso.domain.Status;
import com.vwfsag.fso.service.ImageService;
import com.vwfsag.fso.service.ProductService;
import com.vwfsag.fso.view.vo.ImageDescriptor;
import com.vwfsag.fso.view.vo.JsonUtils;
import com.vwfsag.fso.view.vo.Price;
import com.vwfsag.fso.view.vo.ProductForm;
import com.vwfsag.fso.view.vo.ResponseStatus;
import com.vwfsag.fso.view.vo.ImageDescriptor.ImageFormat;

/**
 * @author liqiang
 *
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ImageService imageService;

	@RequestMapping(method = RequestMethod.GET)
	public String gotoListPage() {
		return "products";
	}

	@RequestMapping(value = "/edit/{productId}", method = RequestMethod.GET)
	public String edit(@PathVariable("productId") Integer productId, Model model) {
		model.addAttribute("productId", productId);
		return "product-edit";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<Product> list() {
		List<Product> all = productService.getProducts();
		return all;
	}

	@RequestMapping(value = "/invalidate/{productId}", method = RequestMethod.GET)
	public @ResponseBody ResponseStatus invalidate(@PathVariable("productId") Integer productId) {
		Product product = new Product();
		product.setId(productId);
		product.setProductStatus(Status.INACTIVE.getCode());
		productService.saveProduct(product);
		return ResponseStatus.success();
	}

	@RequestMapping(value = "/activate/{productId}", method = RequestMethod.GET)
	public @ResponseBody ResponseStatus activate(@PathVariable("productId") Integer productId) {
		Product product = new Product();
		product.setId(productId);
		product.setProductStatus(Status.ACTIVE.getCode());
		productService.saveProduct(product);
		return ResponseStatus.success();
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public @ResponseBody ProductForm load(@PathVariable("productId") Integer productId) {
		Product product = null;
		if(productId > 0) {
			product = productService.getProduct(productId);
		}
		return new ProductForm(product);
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.POST)
	public String save(@PathVariable("productId") Integer productId,
			@RequestParam(value = "activityTitle", required = false) String activityTitle,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "activityRules", required = false) String activityRules,
			@RequestParam(value = "discountPrice", required = false) String discountPrice,
			@RequestParam(value = "price", required = false) String marketPrice,
			@RequestParam(value = "guidePrice", required = false) String guidePrice,
			@RequestParam(value = "actPrice", required = false) String actPrice,
			@RequestParam(value = "carParameters", required = false) String carParameters,
			@RequestParam(value = "typeId", required = false) int[] typeIds,
			@RequestParam(value = "typeValue", required = false) String[] typeValues,
			@RequestParam(value = "colorId", required = false) int[] colorIds,
			@RequestParam(value = "colorValue", required = false) String[] colorValues,
			@RequestParam(value = "deposit", required = false) String deposit, @RequestParam("logoURL") String logoURL,
			@RequestParam("logo") MultipartFile logo, @RequestParam("iconURL") String iconURL,
			@RequestParam("icon") MultipartFile icon, @RequestParam("bannerURL") String[] bannerURLs,
			@RequestParam("banner") MultipartFile[] banners, @RequestParam("detailURL") String[] detailURLs,
			@RequestParam("detail") MultipartFile[] details, Model model) {
		Map<String, String> errors = new HashMap<String, String>();
		productId = productId == 0 ? null : productId;
		Product product = new Product();
		product.setId(productId);
		product.setName(name);
		product.setActivityTitle(activityTitle);
		product.setActivityRules(activityRules);
		product.setActivityDeposit((int)(NumberUtils.toDouble(deposit, 0) * 100));
		product.setCarParameters(carParameters);
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
		product.setCarPrices(price.toJsonString());
		
		if(colorValues != null) {
			ArrayNode nodes = JsonNodeFactory.instance.arrayNode();
			for(int i = 0; i < colorValues.length; i++) {
				ObjectNode node = nodes.addObject();
				node.put("value", colorValues[i]);
				node.put("id", colorIds[i]);
			}
			product.setColors(JsonUtils.toJsonString(nodes));
		}
		
		if(typeValues != null) {
			ArrayNode nodes = JsonNodeFactory.instance.arrayNode();
			for(int i = 0; i < typeValues.length; i++) {
				ObjectNode node = nodes.addObject();
				node.put("value", typeValues[i]);
				node.put("id", typeIds[i]);
			}
			product.setTypes(JsonUtils.toJsonString(nodes));
		}
		
		try {

			if (!logo.isEmpty()) {
				ImageFormat fmt = ImageFormat.fromFileName(logo.getName());
				ImageDescriptor imageDesc = new ImageDescriptor(fmt, logo.getBytes());
				logoURL = imageService.saveImage(imageDesc);
				product.setLogo(logoURL);
			}
			if (!icon.isEmpty()) {
				ImageFormat fmt = ImageFormat.fromFileName(icon.getName());
				ImageDescriptor imageDesc = new ImageDescriptor(fmt, icon.getBytes());
				iconURL = imageService.saveImage(imageDesc);
				product.setIconUrl(iconURL);
			}
			if (banners != null) {
				List<String> imageURLs = new ArrayList<String>();
				for (int i = 0; i < banners.length; i++) {
					MultipartFile image = banners[i];
					String sourceURL = bannerURLs[i];
					if (!image.isEmpty()) {
						ImageFormat fmt = ImageFormat.fromFileName(image.getName());
						ImageDescriptor imageDesc = new ImageDescriptor(fmt, image.getBytes());
						sourceURL = imageService.saveImage(imageDesc);
					}
					imageURLs.add(sourceURL);
				}
				product.setImgsBanner(StringUtils.join(imageURLs, ","));
			}
			if (details != null) {
				List<String> imageURLs = new ArrayList<String>();
				for (int i = 0; i < details.length; i++) {
					MultipartFile image = details[i];
					String sourceURL = detailURLs[i];
					if (!image.isEmpty()) {
						ImageFormat fmt = ImageFormat.fromFileName(image.getName());
						ImageDescriptor imageDesc = new ImageDescriptor(fmt, image.getBytes());
						sourceURL = imageService.saveImage(imageDesc);
					}
					imageURLs.add(sourceURL);
				}
				product.setImgsDetails(StringUtils.join(imageURLs, ","));
			}

			productService.saveProduct(product);
		} catch (IOException e) {
			log.error("Trying to read the uploaded image data failed.", e);
		}

		productService.saveProduct(product);
		return "redirect:.";
	}


	@RequestMapping(value = "/fix/type", method = RequestMethod.GET)
	public String fixType() {
		List<Product> all = productService.getProducts();
		
		for(Product p : all) {
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode typeNodes = JsonNodeFactory.instance.arrayNode();
			Set<String> types = new LinkedHashSet<String>();
			List<ProductItem> items = productService.getItems(p.getId());
			for(ProductItem item : items) {
				types.add(item.getTypeValue());
			}
			int i = 1;
			for(String type : types) {
				ObjectNode typeNode = typeNodes.addObject();
				typeNode.put("id", i++);
				typeNode.put("value", type);
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				mapper.writeValue(os, typeNodes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Product product = new Product();
			product.setId(p.getId());
			product.setTypes(os.toString());
			productService.saveProduct(product);
		}
		return "redirect:/product";
	}

	@RequestMapping(value = "/fix/color", method = RequestMethod.GET)
	public String fixColor() {
		List<Product> all = productService.getProducts();
		
		for(Product p : all) {
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode colorNodes = JsonNodeFactory.instance.arrayNode();
			Set<String> colors = new LinkedHashSet<String>();
			List<ProductItem> items = productService.getItems(p.getId());
			for(ProductItem item : items) {
				colors.add(item.getColorValue());
			}
			int i = 1;
			for(String type : colors) {
				ObjectNode typeNode = colorNodes.addObject();
				typeNode.put("id", i++);
				typeNode.put("value", type);
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				mapper.writeValue(os, colorNodes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Product product = new Product();
			product.setId(p.getId());
			product.setColors(os.toString());
			productService.saveProduct(product);
		}
		return "redirect:/product";
	}
}
