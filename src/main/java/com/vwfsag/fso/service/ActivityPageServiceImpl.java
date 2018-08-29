package com.vwfsag.fso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vwfsag.fso.domain.ActivityPage;
import com.vwfsag.fso.domain.ActivityPageType;
import com.vwfsag.fso.persistence.ActivityPageMapper;

/**
 * @author liqiang
 *
 */
@Service
public class ActivityPageServiceImpl implements ActivityPageService {

	@Autowired
	private ActivityPageMapper activityPageMapper;

	@Override
	public List<ActivityPage> getPages(ActivityPageType type) {
		Integer code = type == null ? null : type.getCode();
		return activityPageMapper.getByType(code);
	}

	@Override
	public ActivityPage getPage(Integer id) {
		return activityPageMapper.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(ActivityPage layout) {
		if(layout.getId() != null) {
			activityPageMapper.updateById(layout);
		} else {
			activityPageMapper.insert(layout);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer id) {
		activityPageMapper.deleteById(id);
	}

}
