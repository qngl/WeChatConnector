package com.vwfsag.fso.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vwfsag.fso.config.GlobalSettings;
import com.vwfsag.fso.domain.AccessToken;
import com.vwfsag.fso.domain.JsTicket;

/**
 * @author qngl
 *
 */
@Service
public class TokenServiceImpl implements TokenService {

	private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

	@Autowired
	private GlobalSettings settings;

	@Override
	public AccessToken getAccessToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsTicket getJsTicket() {
		// TODO Auto-generated method stub
		return null;
	};


}
