package com.vwfsag.fso.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vwfsag.fso.domain.Staff;
import com.vwfsag.fso.domain.StaffRole;
import com.vwfsag.fso.service.EncryptService;
import com.vwfsag.fso.service.StaffService;

/**
 * @author liqiang
 * 
 */
@Controller
@RequestMapping(value = "/staff")
public class StaffController {

	@Autowired
	private StaffService staffService;

	@Autowired
	private EncryptService encryptService;

	@RequestMapping(method = RequestMethod.GET)
	public String staffs(Model model) {
		return "staffs";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String staffEdit(@PathVariable("id") long id, Model model) {
		model.addAttribute("id", id);
		Staff staff = staffService.getById(id);
		if (staff == null) {
			staff = new Staff();
			staff.setId(0L);
		}
		model.addAttribute("staff", staff);
		return "staff-edit";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Staff load(@PathVariable("id") long id, Model model) {
		model.addAttribute("id", id);
		Staff staff = staffService.getById(id);
		if (staff == null) {
			staff = new Staff();
			staff.setId(0L);
			staff.setRole(StaffRole.Sales);
		}
		return staff;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<Staff> staffList() {
		List<Staff> staffs = staffService.getAll();
		return staffs;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String save(@PathVariable("id") Long id, @RequestParam("userName") String userName,
			@RequestParam("password") String password, @RequestParam("fullName") String fullName,
			@RequestParam(value = "shopId", required = false) Integer shopId,
			@RequestParam("role") String role, Model model) {
		Staff staff = null;
		if (id > 0) {
			staff = staffService.getById(id);
		} else {
			Staff old = staffService.getByUserName(userName);
			staff = new Staff();
			if(old != null) {
				model.addAttribute("staff", staff);
				model.addAttribute("duplicated", old);
				return "staff-edit";
			}
		}
		staff.setRole(StaffRole.valueOf(role));
		staff.setUserName(userName);
		staff.setFullName(fullName);
		staff.setShopId(shopId);
		staff.setPassword(encryptService.sha1(password));
		staff.setCreateTime(System.currentTimeMillis());
		staffService.save(staff);
		return "redirect:.";
	}
}
