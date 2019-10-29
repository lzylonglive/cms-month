package com.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.comon.ConstClass;
import com.pojo.User;

/**
 * @作者：lzy
 * @时间：2019年10月17日
 */
public class LoginInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object obj) throws Exception {
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		
		if(loginUser == null) {
			System.out.println("拦截:" + request.getRequestURI());
			request.getRequestDispatcher("/user/login").forward(request, response);
			return false;
		}
		
		return true;
	}
	
}
