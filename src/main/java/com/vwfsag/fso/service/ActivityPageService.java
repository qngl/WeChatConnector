package com.vwfsag.fso.service;

import java.util.List;

import com.vwfsag.fso.domain.ActivityPage;
import com.vwfsag.fso.domain.ActivityPageType;

/**
 * @author li
 * 
 */
public interface ActivityPageService {

	public List<ActivityPage> getPages(ActivityPageType type);

	public ActivityPage getPage(Integer id);

	public void save(ActivityPage layout);

	public void delete(Integer id);

}
