package com.pinhuba.common.code.bean;

public class CodeConfig{

    private String pojoPack;
    private String daoPack;
    private String daoImplPack;
    private String servicePack;
    private String serviceImplPack;
    private String dwrPack;
    private String sqlPack;
    private String projectPath;
    private String srcPath;
    private String pagePath;

    //默认构造方法
    public CodeConfig(){
        super();
    }

    //构造方法(手工生成)


    //get和set方法
    public String getPojoPack(){
        return pojoPack;
    }

    public void setPojoPack(String aPojoPack){
        this.pojoPack = aPojoPack;
    }

    public String getDaoPack(){
        return daoPack;
    }

    public void setDaoPack(String aDaoPack){
        this.daoPack = aDaoPack;
    }

    public String getDaoImplPack(){
        return daoImplPack;
    }

    public void setDaoImplPack(String aDaoImplPack){
        this.daoImplPack = aDaoImplPack;
    }

    public String getServicePack(){
        return servicePack;
    }

    public void setServicePack(String aServicePack){
        this.servicePack = aServicePack;
    }

    public String getServiceImplPack(){
        return serviceImplPack;
    }

    public void setServiceImplPack(String aServiceImplPack){
        this.serviceImplPack = aServiceImplPack;
    }

    public String getDwrPack(){
        return dwrPack;
    }

    public void setDwrPack(String aDwrPack){
        this.dwrPack = aDwrPack;
    }

	public String getSqlPack() {
		return sqlPack;
	}

	public void setSqlPack(String sqlPack) {
		this.sqlPack = sqlPack;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
}