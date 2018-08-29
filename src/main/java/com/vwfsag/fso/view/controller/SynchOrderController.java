package com.vwfsag.fso.view.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vwfsag.fso.config.GlobalSettings;
import com.vwfsag.fso.domain.City;
import com.vwfsag.fso.domain.Order;
import com.vwfsag.fso.domain.OrderStatus;
import com.vwfsag.fso.domain.ProductItem;
import com.vwfsag.fso.domain.Shop;
import com.vwfsag.fso.service.CityService;
import com.vwfsag.fso.service.EncryptService;
import com.vwfsag.fso.service.OrderService;
import com.vwfsag.fso.service.ProductService;
import com.vwfsag.fso.service.ShopService;
import com.vwfsag.fso.view.vo.SynchOrder;

/**
 * @author liqiang
 *
 */
@Controller
@RequestMapping(value = "/synch")
public class SynchOrderController {

	private static final String ENCRYPT_API_AES = "encrypt.api.aes";

	private static final Logger log = LoggerFactory.getLogger(SynchOrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private CityService cityService;
	
	@Autowired
	private EncryptService encryptService;

	@Autowired
	private GlobalSettings settings;

	@RequestMapping(value = "/encrypt", method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String encrypt(@RequestParam(value = "data", required = false) String input) {
		String key = settings.get(ENCRYPT_API_AES);
		if(input == null) {
			input = "{\"lastSynchronizedOrderId\" : 1}";
//			input = "{\"couponCode\" : \"160423231025112\", \"status\" : 4}";
		}
		return encryptService.aesEncrypt(input, key);
	}

	@RequestMapping(value = "/decrypt", method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String decrypt(@RequestParam(value = "data", required = false) String input) {
		String key = settings.get(ENCRYPT_API_AES);
		return encryptService.aesDecrypt(input, key);
	}

	@RequestMapping(value = "/fetch", method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String fetchGet(@RequestParam(value = "data", required = false) String data) {
		return fetch(data);
	}

	@RequestMapping(value = "/fetch", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String fetchPost(@RequestParam(value = "data", required = false) String data) {
		return fetch(data);
	}

	private String fetch(String data) {
		SynchOrder synch = null;
		if (StringUtils.isNotBlank(data)) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				String key = settings.get(ENCRYPT_API_AES);
				data = encryptService.aesDecrypt(data, key);
				JsonNode actualObj = mapper.readTree(data);
				JsonNode codeNode = actualObj.get("couponCode");
				JsonNode statusNode = actualObj.get("status");
				if (codeNode == null || statusNode == null) {
					synch = new SynchOrder(-4, "输入数据不完整");
				} else {
					String couponCode = codeNode.asText();
					int statusCode = statusNode.asInt();
					OrderStatus status = OrderStatus.fromCode(statusCode);
					if (StringUtils.isBlank(couponCode)) {
						synch = new SynchOrder(-4, "输入数据不完整");
					} else {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("couponCode", couponCode);
						map.put("status", OrderStatus.PAID.getCode());
						List<Order> orders = orderService.search(map, 0, 1);
						if (!orders.isEmpty()) {
							Order order = orders.get(0);
							if(order.getOrderStatus().intValue() == OrderStatus.PAID.getCode()) {
								if (status == OrderStatus.VERIFIED) {
									orderService.updateOrderStatus(order.getId(), OrderStatus.VERIFIED);
									synch = new SynchOrder(0, "订单已支付");
								} else if(status == OrderStatus.REFUNDED) {
									orderService.updateOrderStatus(order.getId(), OrderStatus.REFUNDED);
									synch = new SynchOrder(0, "订单已退款");
								} else {
									synch = new SynchOrder(0, "订单已付款");
								}
								getSynchOrder(order, synch);
							} else {
								synch = new SynchOrder(-3, "订单状态不正确");
							}
						} else {
							synch = new SynchOrder(-1, "不存在的核销码");
						}
					}
				}
			} catch (Exception e) {
				log.error("Invalid data.", e);;
				synch = new SynchOrder(-2, "数据格式不合法");
			}
		} else {
			synch = new SynchOrder(-2, "数据格式不合法");
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String key = settings.get(ENCRYPT_API_AES);
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(out, synch);
			out.close();
		} catch (Exception e) {
			log.error("Writing order JSON string failed.", e);
		}

		String orderData = out.toString();
		return encryptService.aesEncrypt(orderData, key);
	}


	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String listGet(@RequestParam(value = "data", required = false) String data) {
		return list(data);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String listPost(@RequestParam(value = "data", required = false) String data) {
		return list(data);
	}
	
	public String list(String data) {
		log.info("-------------------- Received data: {}", data);
		ObjectMapper mapper = new ObjectMapper();
		String key = settings.get(ENCRYPT_API_AES);
		List<SynchOrder> synchs = new ArrayList<SynchOrder>();
		try {
			if (StringUtils.isNotBlank(data)) {
				data = encryptService.aesDecrypt(data, key);
				JsonNode actualObj = mapper.readTree(data);
				long lastSynchronizedOrderId = actualObj.get("lastSynchronizedOrderId").asLong();
				if(lastSynchronizedOrderId >= 0L) {
					List<Order> orders = orderService.selectByOrderIdRange(lastSynchronizedOrderId);
					for(Order order : orders) {
						OrderStatus status = OrderStatus.fromCode(order.getOrderStatus().intValue());
						if(status != null) {
							SynchOrder synch = new SynchOrder(0, status.getTitle());
							getSynchOrder(order, synch);
							synchs.add(synch);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Writing order JSON string failed.", e);
		}
	
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			mapper.writeValue(out, synchs);
			out.close();
		} catch (Exception e) {
			log.error("Writing order JSON string failed.", e);
		}
		String orderData = out.toString();
		return encryptService.aesEncrypt(orderData, key);
	}

	private void getSynchOrder(Order order, SynchOrder synch) {
		ProductItem item = productService.getItem(order.getItemSkuId());
		Shop shop = shopService.getById(order.getShopId());
		synch.setOrder(order);
		synch.setItem(item);
		synch.setShop(shop);
		City city = cityService.getLocation(order.getCityId());
		if(city != null) {
			City province = cityService.getLocation(city.getParentId());
			if(province != null) {
				synch.setCityName(String.format("%s %s", province.getName(), city.getName()));
			}
		} else {
			log.error("Missng city_id for order {}", order.getId());
		}
	}
	
}
