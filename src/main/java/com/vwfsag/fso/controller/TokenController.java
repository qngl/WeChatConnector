package com.vwfsag.fso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vwfsag.fso.domain.JsTicket;
import com.vwfsag.fso.service.TokenService;

/**
 * @author qngl
 *
 */
@Controller
@RequestMapping(value = "/api/token")
public class TokenController {

	@Autowired
	private TokenService tokenService;


	@RequestMapping(value = "/jsticket", method = RequestMethod.GET)
	public @ResponseBody JsTicket getJsTicket() {
		JsTicket jsTicket = tokenService.getJsTicket();
		return jsTicket;
	}
}
