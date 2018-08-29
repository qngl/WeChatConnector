package com.vwfsag.fso.persistence;

import java.util.List;

import com.vwfsag.fso.domain.ActivityPage;

/**
 * @author liqiang
 *
 */
public interface ActivityPageMapper {

	int deleteById(Integer id);

	int insert(ActivityPage record);

	ActivityPage getById(Integer id);

	List<ActivityPage> getByType(Integer id);

	int updateByIdSelective(ActivityPage record);

	int updateById(ActivityPage record);
}