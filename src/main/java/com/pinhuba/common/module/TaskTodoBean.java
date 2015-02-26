package com.pinhuba.common.module;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.pinhuba.core.pojo.HrmEmployee;
import com.pinhuba.core.pojo.SysProcessConfig;

public class TaskTodoBean {
	private Task task;
	private ProcessDefinition processDefinition;
	private ProcessInstance processInstance;
	private Integer status;
	private String createTime;
	private SysProcessConfig config;
	private HrmEmployee employee;
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}
	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public SysProcessConfig getConfig() {
		return config;
	}
	public void setConfig(SysProcessConfig config) {
		this.config = config;
	}
	public ProcessInstance getProcessInstance() {
		return processInstance;
	}
	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}
	public HrmEmployee getEmployee() {
		return employee;
	}
	public void setEmployee(HrmEmployee employee) {
		this.employee = employee;
	}
}
