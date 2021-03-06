<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户明细</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrFrontService.js"></script>
<script type="text/javascript">
window.onload = function(){
    dwrFrontService.getFroMemberByPk('<%=pk%>',setPageValue);
}
function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var member = data.resultList[0];
            DWRUtil.setValue("username",member.username);
            DWRUtil.setValue("password",member.password);
        }else{
            alert(data.message);
        }
    }else{
        alert(data.message);
    }
}
</script>
</head>
<body class="inputdetail">
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示用户相关信息！</label></div>
    <div class="detailtitle">用户明细</div>
    <table class="detailtable">
        <tr>
            <th>用户名</th>
            <td id="username" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>密码</th>
            <td id="password" class="detailtabletd"></td>
        </tr>
    </table>
</body>
</html>
