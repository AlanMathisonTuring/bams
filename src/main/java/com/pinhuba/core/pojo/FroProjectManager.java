package com.pinhuba.core.pojo;

import com.pinhuba.common.util.EnumUtil;

/**
 * 数据库表名：fro_member
 */
public class FroProjectManager implements java.io.Serializable {

	private String managerName;
    private Integer managerSex;
    private Integer managerAge;
    private SysLibraryInfo managerDegree;
    

    //默认构造方法
    public FroProjectManager(){
        super();
    }

	public FroProjectManager(String managerName, Integer managerSex, Integer managerAge, SysLibraryInfo managerDegree) {
		this.managerName = managerName;
		this.managerSex = managerSex;
		this.managerAge = managerAge;
		this.managerDegree = managerDegree;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Integer getManagerAge() {
		return managerAge;
	}

	public void setManagerAge(Integer managerAge) {
		this.managerAge = managerAge;
	}

	public Integer getManagerSex() {
		return managerSex;
	}

	public void setManagerSex(Integer managerSex) {
		this.managerSex = managerSex;
	}

	public SysLibraryInfo getManagerDegree() {
		return managerDegree;
	}

	public void setManagerDegree(SysLibraryInfo managerDegree) {
		this.managerDegree = managerDegree;
	}

	public String getManagerSexName() {
		return EnumUtil.HRM_EMPLOYEE_SEX.valueOf(getManagerSex());
	}


}