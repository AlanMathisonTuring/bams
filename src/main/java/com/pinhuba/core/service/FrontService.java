package com.pinhuba.core.service;

import com.pinhuba.core.pojo.FroMember;
import com.pinhuba.core.pojo.FroNews;
import com.pinhuba.core.pojo.FroProject;
import com.pinhuba.core.dao.IFroMemberDao;
import com.pinhuba.core.dao.IFroNewsDao;
import com.pinhuba.core.dao.IFroProjectDao;
import com.pinhuba.core.iservice.IFrontService;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pack.FrontPack;
import com.pinhuba.common.util.security.Base64;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class FrontService implements IFrontService{
	@Resource
    private IFroNewsDao froNewsDao;
	@Resource
    private IFroMemberDao froMemberDao;
	@Resource
    private IFroProjectDao froProjectDao;
    
    public int listFroMemberCount(FroMember froMember){
        int count = froMemberDao.findByHqlWhereCount(FrontPack.packFroMemberQuery(froMember));
        return count;
    }

    public List<FroMember> listFroMember(FroMember froMember, Pager pager){
        List<FroMember> list = froMemberDao.findByHqlWherePage(FrontPack.packFroMemberQuery(froMember), pager);
        return list;
    }

    public List<FroMember> listFroMember(FroMember froMember){
        List<FroMember> list = froMemberDao.findByHqlWhere(FrontPack.packFroMemberQuery(froMember));
        return list;
    }

    public FroMember saveFroMember(FroMember froMember){
        FroMember temp = (FroMember)froMemberDao.save(froMember);
        return temp;
    }

    public FroMember getFroMemberByPk(String pk){
        FroMember froMember = (FroMember)froMemberDao.getByPK(pk);
        return froMember;
    }

    public void deleteFroMemberByPks(String[] pks){
        for (String pk : pks) {
            FroMember froMember = froMemberDao.getByPK(pk);
            froMemberDao.remove(froMember);
        }
    }
    
    public FroMember checkMemberInfo(String username, String password){
    	FroMember member = null;
    	FroMember tmp = null;
    	List<FroMember> list = froMemberDao.findByProperty("username", username);
    	
    	if(list.size()>0) 
    		tmp = list.get(0);
    	
		if (tmp != null) {
			String parsePwd = Base64.getBase64FromString(password);// 加密密码比对
			if (StringUtils.isNotBlank(password) && parsePwd.equals(tmp.getPassword()))
				member = tmp;
		}
		return member;
    }
    
    public boolean usernameExist(String username){
    	List<FroMember> list = froMemberDao.findByProperty("username", username);
    	if(list.size() > 0){
    		return true;
    	}
    	return false;
    }
    
    
    
    

    public int listFroNewsCount(FroNews froNews){
        int count = froNewsDao.findByHqlWhereCount(FrontPack.packFroNewsQuery(froNews));
        return count;
    }

    public List<FroNews> listFroNews(FroNews froNews, Pager pager){
        List<FroNews> list = froNewsDao.findByHqlWherePage(FrontPack.packFroNewsQuery(froNews), pager);
        return list;
    }

    public List<FroNews> listFroNews(FroNews froNews){
        List<FroNews> list = froNewsDao.findByHqlWhere(FrontPack.packFroNewsQuery(froNews));
        return list;
    }
    
    public List<FroNews> listFroNews(int type,int pageSize){
    	Pager pager = new Pager();
    	pager.setStartRow(0);
    	pager.setPageSize(pageSize);
        List<FroNews> list = froNewsDao.findByHqlWherePage(" and model.newsType.primaryKey =" + type +
        		" order by model.createdate desc", pager);
        return list;
    }
    
    public Pager listFroNewsPager(FroNews news, int pageNo, int pageSize){
    	int count = froNewsDao.findByHqlWhereCount(FrontPack.packFroNewsQuery(news));
    	Pager pager = new Pager(count, pageSize);
    	pager.setStartRow((pageNo-1) * pageSize);
    	List<FroNews> list = froNewsDao.findByHqlWherePage(FrontPack.packFroNewsQuery(news), pager);
    	pager.setResultList(list);
    	return pager;
    }
    

    public FroNews saveFroNews(FroNews froNews){
        FroNews temp = (FroNews)froNewsDao.save(froNews);
        return temp;
    }

    public FroNews getFroNewsByPk(String pk){
        FroNews froNews = (FroNews)froNewsDao.getByPK(pk);
        return froNews;
    }

    public void deleteFroNewsByPks(String[] pks){
        for (String pk : pks) {
            FroNews froNews = froNewsDao.getByPK(pk);
            froNewsDao.remove(froNews);
        }
    }
    
    public FroProject saveFroProject(FroProject project){
    	FroProject temp = (FroProject)froProjectDao.save(project);
        return temp;
    }
    
    public Pager listFroProjectPager(FroProject project, int pageNo, int pageSize){
    	int count = froProjectDao.findByHqlWhereCount(FrontPack.packFroProjectQuery(project));
    	Pager pager = new Pager(count, pageSize);
    	pager.setStartRow((pageNo-1) * pageSize);
    	List<FroProject> list = froProjectDao.findByHqlWherePage(FrontPack.packFroProjectQuery(project), pager);
    	pager.setResultList(list);
    	return pager;
    }
    
    public void deleteFroProjectByPk(String pk){
    	FroProject froProject = (FroProject)froProjectDao.getByPK(pk);
    	froProjectDao.remove(froProject);
    }
    
    public FroProject getFroProjectByPk(String pk){
    	FroProject froProject = (FroProject)froProjectDao.getByPK(pk);
        return froProject;
    }
}