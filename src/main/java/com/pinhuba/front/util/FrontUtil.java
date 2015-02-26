package com.pinhuba.front.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.pinhuba.core.pojo.FroMember;

public class FrontUtil {
	private static final String MEMBER_SESSION ="member_session";
	
	public static FroMember getMemberSession(HttpServletRequest request) {
		if (request != null) {
			FroMember member = (FroMember) WebUtils.getSessionAttribute(request, MEMBER_SESSION);
			return member;
		}
		return null;
	}
	
	public static void setMemberSession(HttpServletRequest request, FroMember member) {
		if (request != null) {
			WebUtils.setSessionAttribute(request,MEMBER_SESSION,member);
		}
	}
	
	public static void removeMemberSession(HttpServletRequest request){
		if (request != null) {
			request.getSession(true).removeAttribute(MEMBER_SESSION);
		}
	}

}
