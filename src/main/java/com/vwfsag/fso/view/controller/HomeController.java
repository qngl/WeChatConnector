package com.vwfsag.fso.view.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vwfsag.fso.domain.Staff;
import com.vwfsag.fso.service.EncryptService;
import com.vwfsag.fso.service.StaffService;
import com.vwfsag.fso.view.vo.ResponseStatus;

/**
 * @author liqiang
 *
 */
@Controller
public class HomeController {
	
	@Autowired
	private StaffService staffService;

	@Autowired
	private EncryptService encryptService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "redirect:/welcome";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(Model model) {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/psw", method = RequestMethod.GET)
	public String changePsw(Model model) {
		return "psw";
	}

	@RequestMapping(value = "/psw", method = RequestMethod.POST)
	public @ResponseBody ResponseStatus savePsw(@RequestParam(value = "current", required = false) String password, 
			@RequestParam(value = "new1", required = false, defaultValue="") String newPassword1, 
			@RequestParam(value = "new2", required = false) String newPassword2, HttpSession session) {
		Staff staff = (Staff)session.getAttribute("STAFF");
		String psw = encryptService.sha1(password);
		if(!staff.getPassword().equals(psw) || !newPassword1.equals(newPassword2)) {
			return ResponseStatus.failure();
		}
		String newPsw = encryptService.sha1(newPassword1);
		staff.setPassword(newPsw);
		staffService.resetPassword(staff);
		session.setAttribute("STAFF", staff);
		return ResponseStatus.success();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseStatus login(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password, HttpSession session, Model model) {
		boolean success = true;
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			success = false;
		} else {
			String psw = encryptService.sha1(password);
			Staff staff = staffService.getByUserName(username);
			if (staff != null && psw.equals(staff.getPassword())) {
				session.setAttribute("STAFF", staff);
			} else{
				success = false;
			}
		}
		if (success) {
			return ResponseStatus.success();
		} else {
			return ResponseStatus.failure();
		}
	}

}
