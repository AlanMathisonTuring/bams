<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrSysProcessService"%>
<%@page import="com.pinhuba.core.pojo.SysMsg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String ch = request.getParameter("choose");
String type = request.getParameter("type");
String id = request.getParameter("id");
WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrSysProcessService service = (DwrSysProcessService)context.getBean("dwrSysProcessService");
SysMsg sysMsg = (SysMsg)service.getSysMsgByPk(this.getServletContext(), request, Integer.valueOf(id)).getResultList().get(0);
%>
<title><%=sysMsg.getMsgTitle() %></title>
</head>
<body>
<jsp:include page="help_top.jsp?choose=<%=ch %>" flush="true"></jsp:include>
<br/>
<center>
<div style="width:950px;">
<div style="text-align:center;font-size:20px;font-weight:bold;padding:10px;">
<%=sysMsg.getMsgTitle() %>
</div>
<div style="text-align:right;padding:10px;">
发布时间：<%=sysMsg.getMsgDate() %>
</div>
<div style="text-align:left;padding:10px;">
<%=sysMsg.getMsgContext() %>
</div>
</div>
</center>
<%@ include file="../Copyright.jsp" %>
</body>
</html>