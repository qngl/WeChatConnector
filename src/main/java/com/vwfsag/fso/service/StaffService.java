package com.vwfsag.fso.service;

import java.util.List;

import com.vwfsag.fso.domain.Staff;
import com.vwfsag.fso.domain.StaffRole;

/**
 * @author liqiang
 */
public interface StaffService {

	public Staff getById(Long id);

	public Staff getByUserName(String userName);

	public List<Staff> getAll();

	public List<Staff> getByRole(StaffRole role);

	public void save(Staff staff);

	public void resetPassword(Staff staff);

}
