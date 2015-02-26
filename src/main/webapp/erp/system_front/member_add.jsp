<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
    String tab = request.getParameter("tab");
    String pk = request.getParameter("pk");
    String isedit = "false";
    String saveOrEdit = "新增";
    String helpTitle = "您可以在此处添加您想新增的用户！";
    if(pk != null){
        isedit = "true";
        saveOrEdit = "编辑";
        helpTitle = "您可以在此处编辑用户信息！";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=saveOrEdit%>用户</title>
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
        dwrFrontService.getFroMemberByPk(pk,setMember);
    }
}

function setMember(data){
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
function save(){
    var warnArr = new Array();
    //清空所有信息提示
    warnInit(warnArr);
    var bl = validvalue('helpTitle');
    if(bl){
        //此处可编写js代码进一步验证数据项

        //Btn.close();
        if(<%=isedit%>){
            dwrFrontService.updateFroMember(getMember(),updateCallback);
        }else{
            dwrFrontService.saveFroMember(getMember(),saveCallback);
        }
    }
}
function getMember(){
    var member = new Object();
    if(<%=isedit%>){
        member.primaryKey = '<%=pk%>';
    }
    member.username = DWRUtil.getValue("username");
    member.password = DWRUtil.getValue("password");
    return member;
}
function saveCallback(data){
    //Btn.open();
    if(data.success){
        confirmmsgAndTitle("添加用户成功！是否想继续添加用户？","reset();","继续添加","closePage();","关闭页面");
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
        <div class="formTitle"><%=saveOrEdit%>用户</div>
        <table class="inputtable">
            <tr>
                <th><em>*</em>&nbsp;&nbsp;用户名</th>
                <td>
                    <input type="text" id="username" must="用户名不能为空!" formust="usernameMust"></input><label id="usernameMust"></label>
                </td>
            </tr>
            <tr>
                <th><em>*</em>&nbsp;&nbsp;密码</th>
                <td>
                    <input type="text" id="password" must="密码不能为空!" formust="passwordMust"></input><label id="passwordMust"></label>
                </td>
            </tr>
        </table>
    </div>
    <table align="center">
        <tr>
            <td><btn:btn onclick="save();" value="保 存 " imgsrc="../../images/png-1718.png" title="保存用户信息" /></td>
            <td style="width:20px;"></td>
            <td><btn:btn onclick="closePage();" value="关 闭 " imgsrc="../../images/winclose.png" title="关闭当前页面"/></td>
        </tr>
    </table>
</body>
</html>
