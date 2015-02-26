package com.pinhuba.core.iservice;

import com.pinhuba.core.pojo.FroMember;
import com.pinhuba.core.pojo.FroNews;
import com.pinhuba.core.pojo.FroProject;

import java.util.List;
import com.pinhuba.common.pages.Pager;

public interface IFrontService{
	public int listFroMemberCount(FroMember froMember);
    public List<FroMember> listFroMember(FroMember froMember, Pager pager);
    public List<FroMember> listFroMember(FroMember froMember);
    public FroMember saveFroMember(FroMember froMember);
    public FroMember getFroMemberByPk(String pk);
    public void deleteFroMemberByPks(String[] pks);
    public FroMember checkMemberInfo(String username, String password);
	public boolean usernameExist(String username);
	
    public int listFroNewsCount(FroNews froNews);
    public List<FroNews> listFroNews(FroNews froNews, Pager pager);
    public List<FroNews> listFroNews(FroNews froNews);
    public FroNews saveFroNews(FroNews froNews);
    public FroNews getFroNewsByPk(String pk);
    public void deleteFroNewsByPks(String[] pks);
	public List<FroNews> listFroNews(int type, int pageSize);
	public Pager listFroNewsPager(FroNews froNews, int pageNo, int pageSize);
	
	public FroProject saveFroProject(FroProject project);
	public Pager listFroProjectPager(FroProject project, int pageNo, int pageSize);
	public void deleteFroProjectByPk(String pk);
	public FroProject getFroProjectByPk(String pk);
}