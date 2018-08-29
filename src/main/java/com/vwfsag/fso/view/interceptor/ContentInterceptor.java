package com.vwfsag.fso.view.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author liqiang
 *
 */
public class ContentInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getRequestURI().startsWith("/resources/3rd/", request.getContextPath().length())) {
			response.setHeader("Expires", "Sun, 05 Oct 2025 05:43:02 GMT");
			response.setHeader("Cache-Control", "max-age=315360000");
			response.setHeader("Last-Modified", "Mon, 22 Nov 2010 16:29:24 GMT");
		}
		request.setAttribute("WeChatConnector", "北京现代 - 滴滴商城 - ");
		return true;
	}

}