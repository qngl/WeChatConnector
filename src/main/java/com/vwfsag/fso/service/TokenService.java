package com.vwfsag.fso.service;

import com.vwfsag.fso.domain.AccessToken;
import com.vwfsag.fso.domain.JsTicket;

/**
 * @author qngl
 *
 */
public interface TokenService {

	public AccessToken getAccessToken();

	public JsTicket getJsTicket();

}
