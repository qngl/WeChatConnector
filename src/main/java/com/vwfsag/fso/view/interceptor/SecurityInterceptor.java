package com.vwfsag.fso.view.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vwfsag.fso.domain.Staff;
import com.vwfsag.fso.domain.StaffRole;

/**
 * @author liqiang
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getSession(false) != null && request.getSession(false).getAttribute("STAFF") != null) {
			// user already login
			if (request.getRequestURI().startsWith("/staff", request.getContextPath().length())) {
				Staff staff = (Staff)request.getSession(false).getAttribute("STAFF");
				if(staff.getRole() != StaffRole.Administrator) {
					// Operator trying to access /staff
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return false;
				}
				return true;
			}
			return true;
		} else if (request.getRequestURI().startsWith("/login", request.getContextPath().length())
				|| request.getRequestURI().startsWith("/synch", request.getContextPath().length())) {
			// goto the /login page or the /synch API
			return true;
		}

		response.sendRedirect(String.format("%s%s", request.getContextPath(), "/login"));
		return false;
	}

}