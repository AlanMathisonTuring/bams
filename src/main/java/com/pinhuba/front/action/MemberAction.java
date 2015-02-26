package com.pinhuba.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.RequestUtils;
import com.pinhuba.common.util.ResponseUtils;
import com.pinhuba.common.util.UtilPrimaryKey;
import com.pinhuba.common.util.security.Base64;
import com.pinhuba.core.iservice.IFrontService;
import com.pinhuba.core.pojo.FroMember;
import com.pinhuba.front.util.Constants;
import com.pinhuba.front.util.FrontUtil;

@Controller
public class MemberAction {
	
	private final static Logger logger = LoggerFactory.getLogger(MemberAction.class);

	@Autowired
	private IFrontService frontService;
	
	@RequestMapping(value = "login.jspx", method = RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap model) {
		return "login.html";
	}

	@RequestMapping(value = "login.jspx", method = RequestMethod.POST)
	public String login(String username,String password,String captcha,HttpServletRequest request, ModelMap model) {
		if (request.getSession().getAttribute(ConstWords.ValidCodeTempSession)==null) {
			model.addAttribute(Constants.MSG, "验证码失效，请重新登录！");
			return "login.html";
		}
		
		String sessioncode = (String) request.getSession().getAttribute(ConstWords.ValidCodeTempSession);
		if (!sessioncode.equalsIgnoreCase(captcha)) {
			model.addAttribute(Constants.MSG, "验证码输入错误！");
			return "login.html";
		}
		
		FroMember member = frontService.checkMemberInfo(username, password);
		if (member == null) {
			model.addAttribute(Constants.MSG, "用户名或密码输入错误！");
			return "login.html";
		}else{
			FrontUtil.setMemberSession(request, member);
			return "redirect:main/index.jspx";
		}
	}
	
	@RequestMapping(value = "main/logout.jspx")
	public String logout(HttpServletRequest request, ModelMap model) {
		FrontUtil.removeMemberSession(request);
		return "redirect:index.jspx";
	}
	
	@RequestMapping(value = "register.jspx", method = RequestMethod.GET)
	public String register(HttpServletRequest request, ModelMap model) {
		return "register.html";
	}
	
	
	@RequestMapping(value = "register.jspx", method = RequestMethod.POST)
	public String register(String username,String password,String captcha,HttpServletRequest request, ModelMap model) {
		
		if (request.getSession().getAttribute(ConstWords.ValidCodeTempSession)==null) {
			model.addAttribute(Constants.MSG, "验证码失效，请重新登录！");
			return "register.html";
		}
		
		String sessioncode = (String) request.getSession().getAttribute(ConstWords.ValidCodeTempSession);
		if (!sessioncode.equalsIgnoreCase(captcha)) {
			model.addAttribute(Constants.MSG, "验证码输入错误！");
			return "register.html";
		}
		
		if (frontService.usernameExist(username)) {
			model.addAttribute(Constants.MSG, "用户名已存在！");
			return "register.html";
		}else{
			FroMember member = new FroMember();
			member.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
			member.setUsername(username);
			member.setPassword(Base64.getBase64FromString(password));
			frontService.saveFroMember(member);
			return "register_result.html";
		}
	}
	
	
	@RequestMapping(value = "username_unique.jspx")
	public void usernameUnique(HttpServletRequest request,
			HttpServletResponse response) {
		String username = RequestUtils.getQueryParam(request, "username");
		// 用户名为空，返回false。
		if (StringUtils.isBlank(username)) {
			ResponseUtils.renderJson(response, "false");
			return;
		}
		// 用户名存在，返回false。
		if (frontService.usernameExist(username)) {
			ResponseUtils.renderJson(response, "false");
			return;
		}
		ResponseUtils.renderJson(response, "true");
	}
	
	
	@RequestMapping(value = "main/password_check.jspx")
	public void passwordCheck(HttpServletRequest request,
			HttpServletResponse response) {
		String oldPassword = RequestUtils.getQueryParam(request, "oldPassword");

		if (StringUtils.isBlank(oldPassword)) {
			ResponseUtils.renderJson(response, "false");
			return;
		}
		
		if (!passwordok(request, oldPassword)) {
			ResponseUtils.renderJson(response, "false");
			return;
		}
		ResponseUtils.renderJson(response, "true");
	}

	private boolean passwordok(HttpServletRequest request, String oldPassword) {
		FroMember member = FrontUtil.getMemberSession(request);
		return member.getPassword().equals(Base64.getBase64FromString(oldPassword));
	}
	
	@RequestMapping(value = "main/password.jspx", method = RequestMethod.GET)
	public String password(HttpServletRequest request, ModelMap model) {
		return "main/password.html";
	}
	
	@RequestMapping(value = "main/password.jspx", method = RequestMethod.POST)
	public String password(String oldPassword,String newPassword,HttpServletRequest request, ModelMap model) {
		if (!passwordok(request, oldPassword)) {
			model.addAttribute(Constants.MSG, "原密码不正确！");
			return "main/password.html";
		}
		
		FroMember member = FrontUtil.getMemberSession(request);
		member.setPassword(Base64.getBase64FromString(newPassword));
		frontService.saveFroMember(member);
		model.addAttribute(Constants.MSG, "密码修改成功！");
		return "main/password.html";
	}
}
