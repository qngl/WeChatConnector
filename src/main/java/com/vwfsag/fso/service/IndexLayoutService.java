package com.vwfsag.fso.service;

import java.util.List;

import com.vwfsag.fso.domain.IndexLayout;
import com.vwfsag.fso.domain.IndexLayoutType;

/**
 * @author li
 * 
 */
public interface IndexLayoutService {

	public List<IndexLayout> getLayouts(IndexLayoutType type);

	public List<IndexLayout> getActiveLayouts(IndexLayoutType type);

	public IndexLayout getLayout(Long id);

	public void save(IndexLayout layout);

	public void delete(Long id);

}
