package com.pinhuba.common.pack;

import com.pinhuba.core.pojo.FroMember;
import com.pinhuba.core.pojo.FroNews;
import com.pinhuba.core.pojo.FroProject;
import com.pinhuba.common.util.UtilWork;

public class FrontPack{

	public static String packFroMemberQuery(FroMember froMember){
        StringBuffer result = new StringBuffer();
        //result.append(" order by model.recordDate desc");
        return result.toString();
    }
	
    public static String packFroNewsQuery(FroNews froNews){
        StringBuffer result = new StringBuffer();
        if(froNews.getNewsType() != null){
        	HqlPack.getNumEqualPack(froNews.getNewsType().getPrimaryKey(), "newsType.primaryKey", result);
        }
        result.append(" order by model.createdate desc");
        return result.toString();
    }

    
    public static String packFroProjectQuery(FroProject froProject){
        StringBuffer result = new StringBuffer();
        HqlPack.getStringLikerPack(froProject.getProjectName(), "projectName", result);
        return result.toString();
    }
    
}