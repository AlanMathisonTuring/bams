package com.pinhuba.core.pojo;

import java.util.List;

/**
 * 数据库表名：fro_member
 */
public class FroProject extends BaseStringBean implements java.io.Serializable {

    private String projectName;
    private SysLibraryInfo projectType;
    
    private Integer isHighTec;
    private String projectPlan;
    private String projectBegin;
    private String projectEnd;
    private Double budgetTotal;
    private String projectFile;
    private String introOne;
    private String introTwo;
    
    private FroMember member;
    private List<FroProjectManager> managers;

    //临时
    private String projectPlanName;
    
    //默认构造方法
    public FroProject(){
        super();
    }


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public SysLibraryInfo getProjectType() {
		return projectType;
	}


	public void setProjectType(SysLibraryInfo projectType) {
		this.projectType = projectType;
	}


	public Integer getIsHighTec() {
		return isHighTec;
	}


	public void setIsHighTec(Integer isHighTec) {
		this.isHighTec = isHighTec;
	}


	public String getProjectPlan() {
		return projectPlan;
	}


	public void setProjectPlan(String projectPlan) {
		this.projectPlan = projectPlan;
	}


	public String getProjectBegin() {
		return projectBegin;
	}


	public void setProjectBegin(String projectBegin) {
		this.projectBegin = projectBegin;
	}


	public String getProjectEnd() {
		return projectEnd;
	}


	public void setProjectEnd(String projectEnd) {
		this.projectEnd = projectEnd;
	}


	public Double getBudgetTotal() {
		return budgetTotal;
	}


	public void setBudgetTotal(Double budgetTotal) {
		this.budgetTotal = budgetTotal;
	}


	public String getProjectFile() {
		return projectFile;
	}


	public void setProjectFile(String projectFile) {
		this.projectFile = projectFile;
	}


	public FroMember getMember() {
		return member;
	}


	public void setMember(FroMember member) {
		this.member = member;
	}


	public List<FroProjectManager> getManagers() {
		return managers;
	}


	public void setManagers(List<FroProjectManager> managers) {
		this.managers = managers;
	}


	public String getIntroOne() {
		return introOne;
	}


	public void setIntroOne(String introOne) {
		this.introOne = introOne;
	}


	public String getIntroTwo() {
		return introTwo;
	}


	public void setIntroTwo(String introTwo) {
		this.introTwo = introTwo;
	}


	public String getProjectPlanName() {
		return projectPlanName;
	}


	public void setProjectPlanName(String projectPlanName) {
		this.projectPlanName = projectPlanName;
	}
}