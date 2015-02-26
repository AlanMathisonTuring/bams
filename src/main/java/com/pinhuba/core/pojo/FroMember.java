package com.pinhuba.core.pojo;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：fro_member
 */
public class FroMember extends BaseStringBean implements java.io.Serializable {

	@Remark("用户名|2|2|1|1")
    private String username;
	@Remark("密码|2|2|1|1")
    private String password;

    //默认构造方法
    public FroMember(){
        super();
    }

    //构造方法(手工生成)


    //get和set方法
    public String getUsername(){
        return username;
    }

    public void setUsername(String aUsername){
        this.username = aUsername;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String aPassword){
        this.password = aPassword;
    }

}