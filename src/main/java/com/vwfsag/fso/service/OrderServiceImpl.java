package com.vwfsag.fso.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vwfsag.fso.domain.Order;
import com.vwfsag.fso.domain.OrderStatus;
import com.vwfsag.fso.persistence.OrderMapper;

/**
 * @author liqiang
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public List<Order> search(Map<String, Object> params, Integer start, Integer limit) {
		params.put("start", start);
		params.put("limit", limit);
		return orderMapper.selectBySelective(params);
	}

	@Override
	public Order getOrder(Long id) {
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateOrderStatus(Long id, OrderStatus status) {
		Order order = new Order();
		order.setId(id);
		order.setOrderStatus(status.getCode());
		orderMapper.updateByPrimaryKeySelective(order);
	}

	@Override
	public int count(Map<String, Object> params) {
		return orderMapper.countBySelective(params);
	}

	@Override
	public List<Order> selectByOrderIdRange(Long id) {
		return orderMapper.selectByOrderIdRange(id);
	}

}
