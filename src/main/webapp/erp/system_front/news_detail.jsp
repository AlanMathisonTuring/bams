<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String pk = request.getParameter("pk");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻明细</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrFrontService.js"></script>
<script type="text/javascript">
window.onload = function(){
    dwrFrontService.getFroNewsByPk('<%=pk%>',setPageValue);
}
function setPageValue(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var news = data.resultList[0];
            DWRUtil.setValue("title",news.title);
            DWRUtil.setValue("newsType",news.newsType.libraryInfoName);
            DWRUtil.setValue("content",news.content,{escapeHtml:false});
            if(isNotBlank(news.attachment)){
                Sys.showDownload(news.attachment,"attachment");
            }
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
    <div class="requdivdetail"><label>查看帮助:&nbsp; 显示新闻相关信息！</label></div>
    <div class="detailtitle">新闻明细</div>
    <table class="detailtable">
        <tr>
            <th>题标</th>
            <td id="title" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>类别</th>
            <td id="newsType" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>内容</th>
            <td id="content" class="detailtabletd"></td>
        </tr>
        <tr>
            <th>附件</th>
            <td id="attachment" class="detailtabletd"></td>
        </tr>
    </table>
</body>
</html>
