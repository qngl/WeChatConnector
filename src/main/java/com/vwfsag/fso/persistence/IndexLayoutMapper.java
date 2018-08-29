package com.vwfsag.fso.persistence;

import java.util.List;

import com.vwfsag.fso.domain.IndexLayout;

/**
 * @author liqiang
 *
 */
public interface IndexLayoutMapper {
	
	public IndexLayout getById(Long id);

	public List<IndexLayout> getByType(IndexLayout layout);

	public void insert(IndexLayout layout);

	public void update(IndexLayout layout);

	public void delete(Long id);
}
