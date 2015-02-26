package com.pinhuba.common.code.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pinhuba.common.code.CreateDwrAndService;
import com.pinhuba.common.code.CreatePage;
import com.pinhuba.common.code.CreatePojoAndDao;
import com.pinhuba.common.code.bean.CodeConfig;
import com.pinhuba.common.code.exception.ColumnRemarkException;
import com.pinhuba.common.util.ConstWords;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

@Controller
public class CodeAction {

	private static final String CREATE_PAGE_ONE = "/erp/code_create/code_create_one.jsp";
	private static final String CREATE_PAGE_TWO = "/erp/code_create/code_create_two.jsp";
	private static final String CREATE_PAGE_THREE = "/erp/code_create/code_create_three.jsp";
	private static final String DB_CONFIG = "/proxool.properties";
	private static final String CODE_CONFIG = "/code.properties";

	/**
	 * PojoAndDao
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/createPojoAndDao.do", method = RequestMethod.GET)
	public String createPojoAndDaoGet(HttpServletRequest request, ModelMap model) {
		Properties p = new Properties();
		try {
			p.load(CodeAction.class.getResourceAsStream(DB_CONFIG));
		} catch (Exception e) {
			request.setAttribute(ConstWords.TempStringMsg, "数据库配置文件 proxool.properties 未找到！");
		}
		request.setAttribute("url", p.getProperty("sql.driver-url"));
		request.setAttribute("user", p.getProperty("sql.user"));
		request.setAttribute("password", p.getProperty("sql.password"));
		return CREATE_PAGE_ONE;
	}

	@RequestMapping(value = "/createPojoAndDao.do", method = RequestMethod.POST)
	public String createPojoAndDaoPost(HttpServletRequest request, ModelMap model) {
		String url = request.getParameter("url");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String tables = request.getParameter("tables");
		CreatePojoAndDao cp = null;
		try {
			cp = new CreatePojoAndDao(url, user, password, tables, getCodeConfig(request));
		} catch (NullPointerException e) {
			request.setAttribute(ConstWords.TempStringMsg, "配置文件 code.properties 未找到！");
		} catch (ColumnRemarkException e) {
			request.setAttribute(ConstWords.TempStringMsg, e.getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (cp != null) {
			try {
				cp.getTablePojo();// 创建pojo
				cp.getTableDao();// 创建dao
				cp.getTableDaoImpl();// 创建daoImpl
				cp.getConfig();// 生成配置临时文件
				cp.closeConn();// 关闭连接
				request.setAttribute(ConstWords.TempStringMsg, "代码生成成功，请查看！");
			} catch (MySQLSyntaxErrorException e) {
				request.setAttribute(ConstWords.TempStringMsg, "代码生成失败，数据表不存在！");
			} catch (Exception e) {
				request.setAttribute(ConstWords.TempStringMsg, "代码生成失败，请注意查看相关数据项！");
				e.printStackTrace();
			}
		}
		request.setAttribute("url", url);
		request.setAttribute("user", user);
		request.setAttribute("password", password);
		request.setAttribute("tables", tables);
		return CREATE_PAGE_ONE;
	}

	/**
	 * DwrAndService
	 */
	@RequestMapping(value = "/createDwrAndService.do", method = RequestMethod.POST)
	public String createDwrAndServicePost(HttpServletRequest request, ModelMap model) {
		String pojoClass = request.getParameter("pojoClass");
		String moduleName = request.getParameter("moduleName");
		Boolean istree = Boolean.valueOf(request.getParameter("istree"));
		try {
			CreateDwrAndService cp = new CreateDwrAndService(moduleName, pojoClass, istree, getCodeConfig(request));
			cp.getDwr();// 创建Dwr
			cp.getService();// 创建Service
			cp.getPack();
			cp.getConfig();// 生成配置临时文件
			request.setAttribute(ConstWords.TempStringMsg, "代码生成成功，请查看！");
		} catch (NullPointerException e) {
			request.setAttribute(ConstWords.TempStringMsg, "配置文件 code.properties 未找到！");
		} catch (Exception e) {
			request.setAttribute(ConstWords.TempStringMsg, "代码生成失败，请注意查看相关数据项！");
			e.printStackTrace();
		}
		request.setAttribute("pojoClass", pojoClass);
		request.setAttribute("moduleName", moduleName);
		return CREATE_PAGE_TWO;
	}

	/**
	 * Page
	 */
	@RequestMapping(value = "/createPage.do", method = RequestMethod.POST)
	public String createPagePost(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pojoClass = request.getParameter("pojoClass");
		String pojoShortName = request.getParameter("pojoShortName");
		String pojoName = request.getParameter("pojoName");
		String dwrName = request.getParameter("dwrName");
		String columnCount = request.getParameter("columnCount");
		String folderName = request.getParameter("folderName");
		Boolean istree = Boolean.valueOf(request.getParameter("istree"));
		try {
			CreatePage cp = new CreatePage(pojoClass, pojoShortName, pojoName, dwrName, columnCount, folderName, getCodeConfig(request));
			cp.getAddPage();// 创建新增/编辑页
			cp.getManagePage();// 创建列表页
			cp.getDetailPage();// 创建详情页
			if (istree)
				cp.getTreePage();// 创建树形结构相关页面
			request.setAttribute(ConstWords.TempStringMsg, "页面生成成功，请查看！");
		} catch (NullPointerException e) {
			request.setAttribute(ConstWords.TempStringMsg, "配置文件 code.properties 未找到！");
		} catch (Exception e) {
			request.setAttribute(ConstWords.TempStringMsg, "页面生成失败，请注意查看相关数据项！");
			e.printStackTrace();
		}

		request.setAttribute("pojoClass", pojoClass);
		request.setAttribute("pojoShortName", pojoShortName);
		request.setAttribute("pojoName", pojoName);
		request.setAttribute("dwrName", dwrName);
		request.setAttribute("columnCount", columnCount);
		request.setAttribute("folderName", folderName);
		return CREATE_PAGE_THREE;
	}

	private CodeConfig getCodeConfig(HttpServletRequest request) throws Exception {
		Properties p = new Properties();
		p.load(CodeAction.class.getResourceAsStream(CODE_CONFIG));
		CodeConfig config = new CodeConfig();
		config.setPojoPack(p.getProperty("pojoPack"));
		config.setDwrPack(p.getProperty("dwrPack"));
		config.setDaoImplPack(p.getProperty("daoImplPack"));
		config.setDaoPack(p.getProperty("daoPack"));
		config.setServicePack(p.getProperty("servicePack"));
		config.setServiceImplPack(p.getProperty("serviceImplPack"));
		config.setSqlPack(p.getProperty("sqlPack"));
		config.setProjectPath(p.getProperty("projectPath"));
		config.setSrcPath(p.getProperty("srcPath"));
		config.setPagePath(p.getProperty("pagePath"));
		return config;
	}
}
