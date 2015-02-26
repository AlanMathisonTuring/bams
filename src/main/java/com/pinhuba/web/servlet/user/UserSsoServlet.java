package com.pinhuba.web.servlet.user;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.pinhuba.common.module.SessionUser;
import com.pinhuba.common.util.ConstWords;
import com.pinhuba.common.util.LoginContext;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.file.FileTool;
import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.core.iservice.ISysProcessService;
import com.pinhuba.core.iservice.IUserLoginService;
import com.pinhuba.core.pojo.SysCompanyInfo;
import com.pinhuba.core.pojo.SysLog;
import com.pinhuba.core.pojo.SysMethodInfo;
import com.pinhuba.core.pojo.SysUserInfo;
import com.pinhuba.web.controller.dwr.DwrOADesktopService;
import com.pinhuba.web.listener.OnlineUserBindingListener;
import com.pinhuba.web.servlet.ServletServiceController;

public class UserSsoServlet extends ServletServiceController {
	private Logger log =Logger.getLogger(this.getClass());
		
	private static final long serialVersionUID = 2560424066821933328L;
	public UserSsoServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path ="login.jsp";
		response.setContentType("text/html");
		IUserLoginService userLoginservice =this.getUserLoginService();
		ISysProcessService sysProcessService =this.getSysProcessService();
		String companyCode = SystemConfig.getParam("erp.sys.DefaultCode").toUpperCase();
		String mid = SystemConfig.getParam("erp.sys.ProjectCode");//请求项目默认编码
		String userName = request.getParameter("u");
		SysMethodInfo forwardMethodInfo = userLoginservice.getMethodInfoByPk(mid);
		SysMethodInfo defaultMethodInfo = userLoginservice.getMethodInfoByPk(ConstWords.getProjectCode());//平台页面
		
		//对公司码开始有效期验证
		SysCompanyInfo companyInfo = userLoginservice.vaildityCompany(companyCode);
		if (companyInfo==null) {
			request.setAttribute(ConstWords.TempStringMsg, "公司码不存在或者已过期！");
			request.getRequestDispatcher(path).forward(request, response);
		}else {
			SysUserInfo userInfo = userLoginservice.vaildityUserInfo(companyInfo, userName);
			if (userInfo == null) {
				request.setAttribute(ConstWords.TempStringMsg, "用户名或密码输入错误！");
				request.getRequestDispatcher(path).forward(request, response);
			}else{
				//创建平台session
				SessionUser sUser = userLoginservice.packageUserInfo(companyCode, userName, ConstWords.getProjectCode());
				//用户是否包含权限
				if (sUser.getUserMethodsSet().size()==0) {
					request.setAttribute(ConstWords.TempStringMsg, "用户没有权限，请联系管理员！");
					request.getRequestDispatcher(path).forward(request, response);
					return;
				}
				LoginContext.SetSessionValueByLogin(request, sUser);
				
				//验证用户是否有权限跳转到所选功能
				boolean tmp =false;
				Set<String> methodSet = sUser.getUserMethodsSet();
				Iterator<String> it = methodSet.iterator();
				while (it.hasNext()) {
					String str = (String) it.next();
					if (mid.equals(str)) {
						tmp =true;
						break;
					}
				}
				//登录加载
				this.loginInit(sysProcessService,sUser,request);
				//进行转向
				String name ="";
				if (tmp) {
					name = forwardMethodInfo.getMethodInfoName();
					response.sendRedirect(defaultMethodInfo.getDefaultPage()+"?mid="+forwardMethodInfo.getPrimaryKey());
				}else{
					name=defaultMethodInfo.getDefaultPage();
					request.setAttribute(ConstWords.TempStringMsg, "你没有操作 ["+forwardMethodInfo.getMethodInfoName()+"] 模块的权限!系统转向平台默认功能!");
					request.getRequestDispatcher(defaultMethodInfo.getDefaultPage()+"?mid="+ConstWords.getProjectCode()).forward(request, response);
				}
				//写入在线人员
				HttpSession session  =request.getSession(true);
				session.setAttribute(ConstWords.OnLineUser_Sign, new OnlineUserBindingListener(sUser.getEmployeeInfo().getPrimaryKey() ,(int)sUser.getCompanyId(),request.getSession().getId()));
				//写入日志
				SysLog sysLog =new SysLog();
				long uid =sUser.getUserInfo().getPrimaryKey();
				sysLog.setUserId(Integer.parseInt(uid+""));
				sysLog.setCompanyId(Integer.parseInt(sUser.getCompanyId()+""));
				sysLog.setLogDate(UtilWork.getNowTime());
				sysLog.setLogDetail("登录系统:"+name);
				sysLog.setRequestAddr(request.getRemoteAddr());
				sysProcessService.saveSysLog(sysLog);
			}
		}
	}

	public void init() throws ServletException {
		super.init();
		super.setContext(this.getServletContext());
	}

	/**
	 * 登录加载内容
	 * @param sUser
	 * @param request
	 * @throws IOException
	 */
	private void loginInit(ISysProcessService sysProcessService,SessionUser sUser,HttpServletRequest request) throws IOException{
		//公司编码
		Integer companyId = (int)sUser.getCompanyId();
		
		//创建个人磁盘文件夹
		String personalPath = SystemConfig.getParam("erp.netdisk.path") + ConstWords.septor + companyId + ConstWords.septor + sUser.getUserInfo().getHrmEmployeeId();
		FileTool.checkDirAndCreate(personalPath);
		log.info("创建个人磁盘:"+personalPath);
		
		//创建个人桌面
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		DwrOADesktopService desktopService = (DwrOADesktopService) context.getBean("dwrOADesktopService");
		desktopService.createOaDesktop(this.getServletContext(), request);
		log.info("加载个人桌面");
	}
	
}
