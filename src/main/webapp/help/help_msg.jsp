<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.pinhuba.web.controller.dwr.DwrSysProcessService"%>
<%@page import="com.pinhuba.core.pojo.SysMsg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统公告 - 帮助中心</title>
<%
WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
DwrSysProcessService service = (DwrSysProcessService)context.getBean("dwrSysProcessService");
List<SysMsg> msglist = service.listAllSysMsg(this.getServletContext(),request,50);
%>
</head>
<body>
<jsp:include page="help_top.jsp" flush="true">
	<jsp:param value="4" name="choose"/>
</jsp:include>
<br/>
<center>
<table width="950" cellpadding="0" cellspacing="0" border="0">
<!-- 
<tr>
<td>
<img src="<%=request.getContextPath() %>/images/helpimg/weihu.png" border="0">
<font style="font-size: 13px;color: red;padding-left: 10px;padding-bottom: 15px">页面升级维护中...</font>
</td>
</tr>
 -->
<%
if(msglist.size()==0){
%>
<tr>
	<td height="30">
		 暂无系统公告
	</td>
</tr>
<%}else{ %>
<%for(int i=0;i<msglist.size();i++){
	SysMsg tm = msglist.get(i);
%>
<tr>
	<td height="30" style="width:100px;">
		<%=tm.getMsgDate()%>
	</td>
	<td align="left">
		<a target="_blank" href="help_tmp.jsp?choose=4&id=<%=tm.getPrimaryKey()%>" style="color:#336699"><%= i+1%>、<%=tm.getMsgTitle()%></a>
	</td>
</tr>
<%}} %>
 
</table>
</center>
<%@ include file="../Copyright.jsp" %>
</body>
</html>