<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String tab = request.getParameter("tab");
    String pk = request.getParameter("pk");
    String isedit = "false";
    String saveOrEdit = "新增";
    String helpTitle = "您可以在此处添加您想新增的新闻！";
    if(pk != null){
        isedit = "true";
        saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑新闻信息！";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=saveOrEdit%>新闻</title>
<script type="text/javascript" src="<%=contextPath%>/dwr/interface/dwrFrontService.js"></script>
<script type="text/javascript">
window.onload = function(){
    useLoadingMassage();
    initInput("helpTitle","<%=helpTitle%>");
    saveOrEdit();
}
function saveOrEdit(){
    if(<%=isedit%>){
        var pk = '<%=pk%>';
        dwrFrontService.getFroNewsByPk(pk,setNews);
    }
}

var fckvalue = "";
var fck;
function FCKeditor_OnComplete(editorInstance) {
    fck = editorInstance;
    editorInstance.SetHTML(fckvalue);
    window.status = editorInstance.Description;
}

function setNews(data){
    if(data.success == true){
        if(data.resultList.length > 0){
            var news = data.resultList[0];
            DWRUtil.setValue("title",news.title);
            setSelectValue("newsType",news.newsType.primaryKey);
            fckvalue = news.content;
            
			if(isNotBlank(news.attachment)){
				dwrCommonService.getAttachmentInfoListToString(news.attachment,function(data){Sys.setFilevalue("attachment",data)});
			}
            
        }else{
            alert(data.message);
        }
    }else{
        alert(data.message);
    }
}
function save(){
    var warnArr = new Array();
    //清空所有信息提示
    warnInit(warnArr);
    var bl = validvalue('helpTitle');
    if(bl){
        //此处可编写js代码进一步验证数据项
		if(isBlank(fck.GetXHTML())){
			setMustWarn("contentMust","请填写内容！");
			return false;
		}
        
		var attach = DWRUtil.getValue("attachment");//附件
        //Btn.close();
        if(<%=isedit%>){
            dwrFrontService.updateFroNews(getNews(),attach,updateCallback);
        }else{
            dwrFrontService.saveFroNews(getNews(),attach,saveCallback);
        }
    }
}
function getNews(){
    var news = new Object();
    if(<%=isedit%>){
        news.primaryKey = '<%=pk%>';
    }
    news.title = DWRUtil.getValue("title");
    news.newsType = {"primaryKey":DWRUtil.getValue("newsType")};
    news.content = fck.GetXHTML();
    return news;
}
function saveCallback(data){
    //Btn.open();
    if(data.success){
        confirmmsgAndTitle("添加新闻成功！是否想继续添加新闻？","reset();","继续添加","closePage();","关闭页面");
    }else{
        alertmsg(data);
    }
}
function updateCallback(data){
    //Btn.open();
    if(data.success){
        alertmsg(data,"closePage();");
    }else{
        alertmsg(data);
    }
}
function reset(){
    Sys.reload();
}
function closePage(){
    closeMDITab(<%=tab%>);
}
</script>
</head>
<body class="inputcls">
    <div class="formDetail">
        <div class="requdiv"><label id="helpTitle"></label></div>
        <div class="formTitle"><%=saveOrEdit%>新闻</div>
        <table class="inputtable">
            <tr>
                <th><em>*</em>&nbsp;&nbsp;题标</th>
                <td>
                    <input type="text" id="title" style="width:300px;" must="题标不能为空!" formust="titleMust"></input><label id="titleMust"></label>
                	类别 <select id="newsType"><%=UtilTool.getSelectOptions(this.getServletContext(),request,null,"33") %></select>
                </td>
            </tr>
            <tr>
                <th><em>*</em>&nbsp;&nbsp;内容</th>
                <td>
                    <label id="contentMust"></label><FCK:editor instanceName="content" width="90%" height="350"></FCK:editor>
                </td>
            </tr>
            <tr>
				<th>附件</th>
				<td>
				<file:multifileupload width="90%" acceptTextId="attachment" height="100" saveType="file" edit="<%=isedit%>"></file:multifileupload>
				</td>
			</tr>
        </table>
    </div>
    <table align="center">
        <tr>
            <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存新闻信息" /></td>
            <td style="width:20px;"></td>
            <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
        </tr>
    </table>
</body>
</html>
