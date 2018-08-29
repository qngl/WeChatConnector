package com.vwfsag.fso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vwfsag.fso.domain.IndexLayout;
import com.vwfsag.fso.domain.IndexLayoutType;
import com.vwfsag.fso.domain.Status;
import com.vwfsag.fso.persistence.IndexLayoutMapper;

/**
 * @author li
 * 
 */
@Service
public class IndexLayoutServiceImpl implements IndexLayoutService {

	@Autowired
	private IndexLayoutMapper indexLayoutMapper;

	@Override
	public List<IndexLayout> getLayouts(IndexLayoutType type) {
		IndexLayout layout = new IndexLayout();
		layout.setLayoutType(type.getCode());
		return indexLayoutMapper.getByType(layout);
	}

	@Override
	public List<IndexLayout> getActiveLayouts(IndexLayoutType type) {
		IndexLayout layout = new IndexLayout();
		layout.setLayoutType(type.getCode());
		layout.setStatus(Status.ACTIVE.getCode());
		return indexLayoutMapper.getByType(layout);
	}

	@Override
	public IndexLayout getLayout(Long id) {
		return indexLayoutMapper.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(IndexLayout layout) {
		if(layout.getId() == null) {
			indexLayoutMapper.insert(layout);
		} else {
			indexLayoutMapper.update(layout);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		IndexLayout layout = indexLayoutMapper.getById(id);
		if (layout != null) {
			indexLayoutMapper.delete(id);
		}
	}

}
