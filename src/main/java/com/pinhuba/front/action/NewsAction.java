package com.pinhuba.front.action;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.core.iservice.IFrontService;
import com.pinhuba.core.iservice.ISysProcessService;
import com.pinhuba.core.pojo.FroNews;
import com.pinhuba.core.pojo.SysAttachmentInfo;
import com.pinhuba.core.pojo.SysLibraryInfo;

@Controller
public class NewsAction {
	
	private final static Logger logger = LoggerFactory.getLogger(NewsAction.class);

	@Resource
	private IFrontService frontService;
	@Resource
	private ISysProcessService processService;
	
	@RequestMapping(value = "channel.jspx")
	public String channel(Integer type,Integer pageNo, HttpServletRequest request, ModelMap model) {
		pageNo = PagerHelper.cpn(pageNo);
		SysLibraryInfo lib = processService.getSysLibraryInfoByPk(type);
		FroNews news = new FroNews();
		news.setNewsType(lib);
		Pager pager = frontService.listFroNewsPager(news, PagerHelper.cpn(pageNo), 5);
		model.addAttribute("type", type);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("lib", lib);
		model.addAttribute("pager", pager);
		return "channel.html";
	}

	@RequestMapping(value = "content.jspx", method = RequestMethod.GET)
	public String content(String id, HttpServletRequest request, ModelMap model) {
		FroNews news = frontService.getFroNewsByPk(id);
		List<SysAttachmentInfo> attachs = processService.getAttachmentInfoListByIds(news.getAttachment());
		model.addAttribute("news", news);
		model.addAttribute("attachs", attachs);
		return "content.html";
	}
	
}
