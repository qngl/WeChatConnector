package com.vwfsag.fso.service;

import java.util.List;
import java.util.Map;

import com.vwfsag.fso.domain.Order;
import com.vwfsag.fso.domain.OrderStatus;

/**
 * @author liqiang
 *
 */
public interface OrderService {

	public List<Order> search(Map<String, Object> params, Integer start, Integer limit);

	public int count(Map<String, Object> params);

	public Order getOrder(Long id);

	public void updateOrderStatus(Long id, OrderStatus status);
	
	List<Order> selectByOrderIdRange(Long id);

}
