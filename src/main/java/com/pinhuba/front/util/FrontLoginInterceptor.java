package com.pinhuba.front.util;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.pinhuba.common.util.StringTool;
import com.pinhuba.core.pojo.FroMember;
import com.pinhuba.front.util.FrontUtil;

public class FrontLoginInterceptor extends HandlerInterceptorAdapter {
	private String mappingUrl;
	private String excludePage;
	
	public void setMappingUrl(String mappingUrl) {
		this.mappingUrl = mappingUrl;
	}

	public void setExcludePage(String excludePage) {
		this.excludePage = excludePage;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURL().toString();
		String page = StringTool.getPageName(url);
		String[] excludePages = excludePage.split(",");
		boolean exclude = !Arrays.asList(excludePages).contains(page);
		
		if(url.matches(mappingUrl) && exclude){
			FroMember member = FrontUtil.getMemberSession(request);
			if (member == null) {
				UrlPathHelper helper = new UrlPathHelper();
				String ctxPath = helper.getOriginatingContextPath(request);
				response.sendRedirect(ctxPath + "/login.jspx"); // 返回登录页
				return false;
			}
		}
		return true;
	}
}
