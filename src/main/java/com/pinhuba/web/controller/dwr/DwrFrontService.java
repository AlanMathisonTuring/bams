package com.pinhuba.web.controller.dwr;

import com.pinhuba.core.pojo.FroMember;
import com.pinhuba.core.pojo.FroNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.pinhuba.common.module.ResultBean;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.UtilTool;
import com.pinhuba.common.util.UtilPrimaryKey;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.common.util.WebUtilWork;
import com.pinhuba.common.util.security.Base64;
import com.pinhuba.core.iservice.IFrontService;

/**********************************************
 * Class name:
 * Description:
 * Others:
 * History:
 **********************************************/
@Controller
public class DwrFrontService {

    private final static Logger logger = LoggerFactory.getLogger(DwrFrontService.class);

    @Resource
    private IFrontService frontService;
    
    /**
     * 查询 FroMember 分页列表
     * @param context
     * @param request
     * @param froMember
     * @param pager
     */
    public ResultBean listFroMember(ServletContext context, HttpServletRequest request, FroMember froMember, Pager pager){
        List<FroMember> list = null;
        pager = PagerHelper.getPager(pager,frontService.listFroMemberCount(froMember));
        list = frontService.listFroMember(froMember, pager);
        for (FroMember member : list) {
        	member.setPassword(Base64.getStringFromBase64(member.getPassword()));
		}
        logger.info("查询 FroMember 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }

    /**
     * 查询所有 FroMember 列表
     * @param context
     * @param request
     * @param froMember
     * @param pager
     */
    public ResultBean listFroMemberAll(ServletContext context, HttpServletRequest request){
        FroMember froMember = new FroMember();
        List<FroMember> list = frontService.listFroMember(froMember);
        logger.info("查询所有 FroMember 列表...");
        return WebUtilWork.WebResultPack(list);
    }

    /**
     * 保存 FroMember
     * @param context
     * @param request
     * @param froMember
     */
    public ResultBean saveFroMember(ServletContext context, HttpServletRequest request, FroMember froMember){
        froMember.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        froMember.setPassword(Base64.getBase64FromString(froMember.getPassword()));
        frontService.saveFroMember(froMember);
        logger.info("保存 FroMember...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 FroMember
     * @param context
     * @param request
     * @param froMember
     */
    public ResultBean updateFroMember(ServletContext context, HttpServletRequest request, FroMember froMember){
        froMember.setPassword(Base64.getBase64FromString(froMember.getPassword()));
        frontService.saveFroMember(froMember);
        logger.info("更新 FroMember...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 FroMember
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getFroMemberByPk(ServletContext context, HttpServletRequest request, String pk){
        FroMember froMember = frontService.getFroMemberByPk(pk);
        froMember.setPassword(Base64.getStringFromBase64(froMember.getPassword()));
        logger.info("根据ID获得 FroMember...{}", froMember.getPrimaryKey());
        return WebUtilWork.WebObjectPack(froMember);
    }

    /**
     * 删除 FroMember
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteFroMemberByPks(ServletContext context, HttpServletRequest request, String[] pks){
        frontService.deleteFroMemberByPks(pks);
        for (String pk : pks) {
            logger.info("删除 FroMember...{}", pk);
        }
        return WebUtilWork.WebResultPack(null);
    }
    

    /**
     * 查询 FroNews 分页列表
     * @param context
     * @param request
     * @param froNews
     * @param pager
     */
    public ResultBean listFroNews(ServletContext context, HttpServletRequest request, FroNews froNews, Pager pager){
        List<FroNews> list = null;
        pager = PagerHelper.getPager(pager,frontService.listFroNewsCount(froNews));
        list = frontService.listFroNews(froNews, pager);
        logger.info("查询 FroNews 分页列表...");
        return WebUtilWork.WebResultPack(list, pager);
    }

    /**
     * 查询所有 FroNews 列表
     * @param context
     * @param request
     * @param froNews
     * @param pager
     */
    public ResultBean listFroNewsAll(ServletContext context, HttpServletRequest request){
        FroNews froNews = new FroNews();
        List<FroNews> list = frontService.listFroNews(froNews);
        logger.info("查询所有 FroNews 列表...");
        return WebUtilWork.WebResultPack(list);
    }

    /**
     * 保存 FroNews
     * @param context
     * @param request
     * @param froNews
     */
    public ResultBean saveFroNews(ServletContext context, HttpServletRequest request, FroNews froNews ,String attach){
        froNews.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
        froNews.setCreatedate(UtilWork.getNowTime());
        
        String ids = UtilTool.saveAttachments(context, request, attach);
        froNews.setAttachment(ids);
        
        frontService.saveFroNews(froNews);
        logger.info("保存 FroNews...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 更新 FroNews
     * @param context
     * @param request
     * @param froNews
     */
    public ResultBean updateFroNews(ServletContext context, HttpServletRequest request, FroNews froNews ,String attach){
    	FroNews tmp = frontService.getFroNewsByPk(froNews.getPrimaryKey());
        UtilTool.deleteAttachmentsNoFile(context, request, tmp.getAttachment());
		String ids = UtilTool.saveAttachments(context, request, attach);
		froNews.setAttachment(ids);
		froNews.setCreatedate(UtilWork.getNowTime());
        frontService.saveFroNews(froNews);
        logger.info("更新 FroNews...");
        return WebUtilWork.WebResultPack(null);
    }

    /**
     * 根据ID获得 FroNews
     * @param context
     * @param request
     * @param pk
     */
    public ResultBean getFroNewsByPk(ServletContext context, HttpServletRequest request, String pk){
        FroNews froNews = frontService.getFroNewsByPk(pk);
        logger.info("根据ID获得 FroNews...{}", froNews.getPrimaryKey());
        return WebUtilWork.WebObjectPack(froNews);
    }

    /**
     * 删除 FroNews
     * @param context
     * @param request
     * @param pks
     */
    public ResultBean deleteFroNewsByPks(ServletContext context, HttpServletRequest request, String[] pks){
        frontService.deleteFroNewsByPks(pks);
        for (String pk : pks) {
            logger.info("删除 FroNews...{}", pk);
        }
        return WebUtilWork.WebResultPack(null);
    }

/**********************************************
 * 以上代码由BAMS代码生成工具自动生成，一般情况下无需修改。
 * 开发人员在此注释以下编写业务逻辑代码，并将自己写的代码框起来，便于后期代码合并，例如：
 **********************************************/

/**********************JC-begin**********************/
    public void method(){
        System.out.println("JC's code here");
    }
/**********************JC-end**********************/

/**********************Jacy-begin**********************/
    public void method2(){
        System.out.println("Jacy's code here");
    }
/**********************Jacy-end**********************/

}