package com.vwfsag.fso.persistence;

import java.util.List;
import java.util.Map;

import com.vwfsag.fso.domain.Order;

/**
 * @author liqiang
 *
 */
public interface OrderMapper {

	int deleteByPrimaryKey(Long id);

	int insert(Order order);

	List<Order> selectBySelective(Map<String, Object> map);

	int countBySelective(Map<String, Object> map);

	Order selectByPrimaryKey(Long id);
	
	List<Order> selectByOrderIdRange(Long id);

	int updateByPrimaryKeySelective(Order order);

	int updateByPrimaryKey(Order order);
}