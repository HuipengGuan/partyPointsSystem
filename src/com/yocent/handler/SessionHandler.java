package com.yocent.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class SessionHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

		System.out.println("访问地址:");
		System.out.println(target);
		
		next.handle(target, request, response, isHandled);
		
	}

}

