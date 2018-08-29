package com.vwfsag.fso.view.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
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

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vwfsag.fso.domain.City;
import com.vwfsag.fso.domain.Order;
import com.vwfsag.fso.domain.OrderStatus;
import com.vwfsag.fso.domain.ProductItem;
import com.vwfsag.fso.domain.Shop;
import com.vwfsag.fso.domain.Staff;
import com.vwfsag.fso.domain.StaffRole;
import com.vwfsag.fso.service.CityService;
import com.vwfsag.fso.service.OrderService;
import com.vwfsag.fso.service.ProductService;
import com.vwfsag.fso.service.ShopService;
import com.vwfsag.fso.view.vo.Page;
import com.vwfsag.fso.view.vo.ResponseStatus;
import com.vwfsag.fso.view.vo.SynchOrder;

/**
 * @author liqiang
 *
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CityService cityService;

	@Autowired
	private ShopService shopService;

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String gotoListPage(@RequestParam(value = "status", required = false) Integer statusCode,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "cityId", required = false) Integer cityId,
			@RequestParam(value = "shopId", required = false) String shopId,
			@RequestParam(value = "productId", required = false) String productId,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "couponCode", required = false) String couponCode,
			@RequestParam(value = "itemId", required = false) String itemId,
			@RequestParam(value = "createDateFrom", required = false) String createDateFrom,
			@RequestParam(value = "createDateTo", required = false) String createDateTo, Model model) {
		model.addAttribute("orderStatusList", OrderStatus.values());
		statusCode = statusCode == null ? OrderStatus.PAID.getCode() : statusCode;
		model.addAttribute("selectedStatusCode", statusCode);
		model.addAttribute("phone", phone);
		model.addAttribute("couponCode", couponCode);
		model.addAttribute("createDateFrom", createDateFrom);
		model.addAttribute("createDateTo", createDateTo);
		return "orders";
	}

	@RequestMapping(value = "/view/{orderId}", method = RequestMethod.GET)
	public String edit(@PathVariable("orderId") Long orderId, Model model) {
		model.addAttribute("orderId", orderId);
		return "order-view";
	}

	@RequestMapping(value = "/statuses", method = RequestMethod.GET)
	public @ResponseBody ArrayNode listOrderStatus() {
		ArrayNode nodes = JsonNodeFactory.instance.arrayNode();
		for (OrderStatus s : OrderStatus.values()) {
			ObjectNode n = nodes.addObject();
			n.put("code", s.getCode());
			n.put("title", s.getTitle());
		}
		return nodes;
	}

	@RequestMapping(value = "/search/{statusCode}", method = RequestMethod.POST)
	public @ResponseBody List<SynchOrder> list(@PathVariable("statusCode") Integer statusCode,
			@RequestParam(value = "page", required = false) int page,
			@RequestParam(value = "cityId", required = false) Integer cityId,
			@RequestParam(value = "shopId", required = false) Integer shopId,
			@RequestParam(value = "productId", required = false) String productId,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "couponCode", required = false) String couponCode,
			@RequestParam(value = "itemId", required = false) String itemId,
			@RequestParam(value = "createDateFrom", required = false) String createDateFrom,
			@RequestParam(value = "createDateTo", required = false) String createDateTo,
			HttpSession session) {
		int pageSize = 10;
		page -= 1;
		String[] format = new String[] {"yyyy-MM-dd"};
		Long createFrom = null, createTo = null;
		try {
			if(StringUtils.isNotBlank(createDateFrom)) {
				createFrom = DateUtils.parseDate(createDateFrom, format).getTime();
			}
			if(StringUtils.isNotBlank(createDateTo)) {
				createTo = DateUtils.parseDate(createDateTo, format).getTime();
			}
		} catch (ParseException e) {
		}
		Map<String, Object> params = collectOrderParameters(statusCode, phone, cityId, createFrom, createTo, couponCode, shopId);
		Staff staff = (Staff)session.getAttribute("STAFF");
		if(staff.getRole() == StaffRole.Sales) {
			params.put("shopId", staff.getShopId());
		}
		List<Order> all = orderService.search(params, page * pageSize, pageSize);
		List<SynchOrder> results = new ArrayList<SynchOrder>();
		for(Order order : all) {
			SynchOrder synch = getSynchOrder(order);
			results.add(synch);
		}
		return results;
	}

	private SynchOrder getSynchOrder(Order order) {
		SynchOrder synch = new SynchOrder(0, "订单已支付");
		ProductItem item = productService.getItem(order.getItemSkuId());
		Shop shop = shopService.getById(order.getShopId());
		synch.setOrder(order);
		synch.setItem(item);
		synch.setShop(shop);

		City city = cityService.getLocation(order.getCityId());
		if(city != null) {
			City province = cityService.getLocation(city.getParentId());
			synch.setCityName(String.format("%s %s", province.getName(), city.getName()));
		} else {
			log.error("Missng city_id for order {}", order.getId());
		}
		return synch;
	}

	private Map<String, Object> collectOrderParameters(Integer statusCode, String phone, Integer cityId,
			Long createDateFrom, Long createDateTo, String couponCode, Integer shopId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", statusCode);
		if (StringUtils.isNotBlank(phone)) {
			map.put("phone", phone);
		}
		if (cityId!= null && cityId > 0) {
			map.put("cityId", cityId);
		}
		if (shopId!= null && shopId > 0) {
			map.put("shopId", shopId);
		}
		if (StringUtils.isNotBlank(couponCode)) {
			map.put("couponCode", couponCode);
		}
		if (createDateFrom != null) {
			map.put("createDateFrom", createDateFrom);
		}
		if (createDateTo != null) {
			map.put("createDateTo", createDateTo);
		}
		return map;
	}

	@RequestMapping(value = "/count/{statusCode}", method = RequestMethod.POST)
	public @ResponseBody Page count(@PathVariable("statusCode") Integer statusCode,
			@RequestParam(value = "cityId", required = false) int cityId,
			@RequestParam(value = "shopId", required = false) Integer shopId,
			@RequestParam(value = "productId", required = false) String productId,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "couponCode", required = false) String couponCode,
			@RequestParam(value = "itemId", required = false) String itemId,
			@RequestParam(value = "createDateFrom", required = false) String createDateFrom,
			@RequestParam(value = "createDateTo", required = false) String createDateTo,
			HttpSession session) {
		String[] format = new String[] {"yyyy-MM-dd"};
		Long createFrom = null, createTo = null;
		try {
			createFrom = DateUtils.parseDate(createDateFrom, format).getTime();
			createTo = DateUtils.parseDate(createDateTo, format).getTime();
		} catch (ParseException e) {
		}
		Map<String, Object> params = collectOrderParameters(statusCode, phone, cityId, createFrom, createTo, couponCode, shopId);
		Staff staff = (Staff)session.getAttribute("STAFF");
		if(staff.getRole() == StaffRole.Sales) {
			params.put("shopId", staff.getShopId());
		}
		Page page = new Page();
		int pageSize = 10;
		int total = orderService.count(params);

		page.setTotal(total);
		page.setPageSize(pageSize);
		return page;
	}

	@RequestMapping(value = "/status/{orderId}/{statusCode}", method = RequestMethod.GET)
	public @ResponseBody ResponseStatus invalidate(@PathVariable("orderId") Long orderId,
			@PathVariable("statusCode") Integer statusCode) {
		orderService.updateOrderStatus(orderId, OrderStatus.fromCode(statusCode));
		return ResponseStatus.success();
	}

	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	public @ResponseBody SynchOrder load(@PathVariable("orderId") Long orderId) {
		Order order = orderService.getOrder(orderId);
		SynchOrder synch = getSynchOrder(order);
		return synch;
	}

}
