package com.vwfsag.fso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vwfsag.fso.domain.Staff;
import com.vwfsag.fso.domain.StaffRole;
import com.vwfsag.fso.persistence.StaffMapper;

/**
 * @author liqiang
 */
@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffMapper staffMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Staff staff) {
		if(staff.getId() != null && staff.getId() > 0) {
			staffMapper.update(staff);
		} else {
			staffMapper.insert(staff);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void resetPassword(Staff staff) {
		staffMapper.update(staff);
	}

	@Override
	public Staff getById(Long id) {
		return staffMapper.getById(id);
	}

	@Override
	public Staff getByUserName(String userName) {
		return staffMapper.getByUserName(userName);
	}

	@Override
	public List<Staff> getByRole(StaffRole role) {
		return staffMapper.getByRole(role);
	}

	@Override
	public List<Staff> getAll() {
		return staffMapper.getAll();
	}

}
