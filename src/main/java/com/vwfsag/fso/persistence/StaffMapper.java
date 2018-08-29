package com.vwfsag.fso.persistence;

import java.util.List;

import com.vwfsag.fso.domain.Staff;
import com.vwfsag.fso.domain.StaffRole;

/**
 * @author liqiang
 *
 */
public interface StaffMapper {

	public Staff getById(Long id);

	public Staff getByUserName(String userName);

	public List<Staff> getAll();

	public List<Staff> getByRole(StaffRole role);

	public void insert(Staff user);

	public void update(Staff user);
}
