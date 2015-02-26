<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<%@include  file="../editmsgbox.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath %>/dwr/interface/dwrPersonalProcessService.js"></script>
<title>待办工作</title>
<script type="text/javascript">
//查询方法
function queryData() {
	startQuery();
	var pager = getPager();
	dwrPersonalProcessService.getTaskTodo(pager, queryCallback);
}

function queryCallback(data) {
	if (data.success == true) {
		initGridData(data.resultList, data.pager);
	} else {
		alert(data.message);
	}
	endQuery();
}

function handle(handleUrl,businessKey,taskId,taskDefinitionKey,taskName) {
	var url = "<%=contextPath%>/erp/"+handleUrl+"?pk="+businessKey+"&taskId="+taskId+"&definitionKey="+taskDefinitionKey+"&name="+encodeURI(taskName)+"&tab="+getMDITab();
	openMDITab(url);
}

function showDetail(detailUrl,businessKey, processInstanceId) {
	var url = "<%=contextPath%>/erp/"+detailUrl+"?pk="+businessKey+"&processInstanceId="+processInstanceId;
	var box = new Sys.msgbox('明细查看', url);
	box.show();
}

function createProcessMethod(rowObj) {
	var str = ""
	str += "<a href=\"javascript:void(0);\" onclick=\"showDetail('"+rowObj.config.detailPage+"','"+rowObj.processInstance.businessKey+"','"+rowObj.processInstance.id+"');\"><img src='<%=contextPath%>/images/grid_images/rowinfo.png' border='0'/></a>&nbsp;&nbsp;";
	
	if (rowObj.task.assignee == null){
		str += "<a href='javascript:void(0)' title='签收' onclick=\"claim('" + rowObj.task.id + "')\"><img src='<%=contextPath%>/images/grid_images/rowok.png' border='0'/></a>";
	} else{
		str += "<a href='javascript:void(0)' title='办理' onclick=\"handle('"+rowObj.config.handlePage+"','" + rowObj.processInstance.businessKey + "','" + rowObj.task.id + "','" + rowObj.task.taskDefinitionKey + "','" + rowObj.task.name + "')\"><img src='<%=contextPath%>/images/grid_images/rowedit.png' border='0'/></a>";
	}
	
	return str;
}

function claim(taskId){
	dwrPersonalProcessService.claimTask(taskId,claimCallback); 
}

function claimCallback(data){
	alertmsg(data, "queryData()");
}

function repleaSuspensionState(rowObj){
	var	str="";
	if(rowObj.processInstance.suspensionState == <%=EnumUtil.SUSPENSION_STATE.ACTIVE.value%>){
		str = "<font color='green'><%=EnumUtil.SUSPENSION_STATE.valueOf(EnumUtil.SUSPENSION_STATE.ACTIVE.value)%></font>";
	}else if(rowObj.processInstance.suspensionState == <%=EnumUtil.SUSPENSION_STATE.SUSPENDED.value%>){
		str = "<font color='red'><%=EnumUtil.SUSPENSION_STATE.valueOf(EnumUtil.SUSPENSION_STATE.SUSPENDED.value)%></font>";
	}
	return str;
}


function repleaTaskname(rowObj){
	var str = "<a href=\"javascript:void(0);\" title=\"点击查看流程图\" onclick=\"showProcessTrace('"+rowObj.task.processInstanceId+"');\">"+rowObj.task.name+"</a>";
	return str;
}

function showProcessTrace(instanceId){
	var box = new Sys.msgbox('流程追踪','<%=contextPath %>/processTrace.do?id='+instanceId,750,500);
	box.show();
}
	
</script>
</head>
<body>
<%
	SysGrid bg =new SysGrid(request);
	bg.setTableTitle("待办工作");
	//设置附加信息
	bg.setQueryFunction("queryData");	//查询的方法名
	bg.setDblFunction("dblCallback");	//双击列的方法名，又返回值，为列对象
	bg.setDblBundle("primaryKey");		//双击列的绑定的列值
	
	//放入按钮
	//ArrayList<SysGridBtnBean> btnList =new ArrayList<SysGridBtnBean>();
	//btnList.add(new SysGridBtnBean("请假申请","add()","add.png"));
	//btnList.add(new SysGridBtnBean("批量删除","deleteObject()","close.png"));
	//bg.setBtnList(btnList);

	ArrayList<SysColumnControl> sccList = new ArrayList<SysColumnControl>();
	sccList.add(new SysColumnControl("task.processInstanceId","流水号",1,2,2,0));
	sccList.add(new SysColumnControl("processDefinition.name","流程类型",1,2,2,0));
	sccList.add(new SysColumnControl("employee.hrmEmployeeName","发起人",1,2,2,0));
	sccList.add(new SysColumnControl("createTime","工作创建时间",1,2,2,0));
	sccList.add(new SysColumnControl("taskname","当前节点",1,2,2,0));
	//sccList.add(new SysColumnControl("suspensionState","流程状态",1,2,2,0));
	ArrayList<SysGridColumnBean> colList = UtilTool.getGridColumnList(sccList); 
	//进行高级查询显示处理
	for(int i=0;i<colList.size();i++){
		SysGridColumnBean bc =colList.get(i);
		if("suspensionState".equalsIgnoreCase(bc.getDataName())){
			bc.setColumnReplace("repleaSuspensionState");
			bc.setColumnStyle("text-align:center");
		}
		if("taskname".equalsIgnoreCase(bc.getDataName())){
			bc.setColumnReplace("repleaTaskname");
			bc.setColumnStyle("text-align:center");
		}
	}
	bg.setColumnList(colList);
	bg.setCheckboxOrNum(false);
	bg.setShowProcess(true);
	bg.setShowImg(false);
	bg.setProcessMethodName("createProcessMethod");
	//开始创建
	out.print(bg.createTable());
%>
</body>
</html>