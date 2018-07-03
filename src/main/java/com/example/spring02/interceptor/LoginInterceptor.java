package com.example.spring02.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter  {
	//메인액션이 실행되기 전
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 세션 객체 생성
		HttpSession session = request.getSession();
		if(session.getAttribute("userid") == null) {
			response.sendRedirect(request.getContextPath()+"/member/login.do?message=nologin");
			return false; // 메인액션으로 넘어가지 않음
		}
			return true; // 메인액션으로 넘어감
	}
	
}
