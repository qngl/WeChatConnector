package com.vwfsag.fso.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author qngl
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getParameter("AK") != null) {
			//TODO: Check AK
		}

		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		return false;
	}

}