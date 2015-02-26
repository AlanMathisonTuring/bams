package com.pinhuba.front.action;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.pages.PagerHelper;
import com.pinhuba.common.util.UtilPrimaryKey;
import com.pinhuba.core.iservice.IFrontService;
import com.pinhuba.core.iservice.ISysProcessService;
import com.pinhuba.core.pojo.FroMember;
import com.pinhuba.core.pojo.FroProject;
import com.pinhuba.core.pojo.FroProjectManager;
import com.pinhuba.core.pojo.SysAttachmentInfo;
import com.pinhuba.core.pojo.SysLibraryInfo;
import com.pinhuba.front.util.FrontUtil;

@Controller
@RequestMapping("/main/")  
public class MainAction {
	
	private final static Logger logger = LoggerFactory.getLogger(MainAction.class);

	@Resource
	private IFrontService frontService;
	@Resource
	private ISysProcessService processService;
	
	@RequestMapping(value = "index.jspx", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {
		return "main/index.html";
	}
	
	@RequestMapping(value = "project_list.jspx")
	public String projectList(FroProject project, Integer pageNo, HttpServletRequest request, ModelMap model) {
		pageNo = PagerHelper.cpn(pageNo);
		Pager pager = frontService.listFroProjectPager(project, PagerHelper.cpn(pageNo), 5);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pager", pager);
		return "main/project_list.html";
	}
	
	@RequestMapping(value = "project_add.jspx", method = RequestMethod.GET)
	public String projectAdd(HttpServletRequest request, ModelMap model) {
		return "main/project_add.html";
	}
	
	@RequestMapping(value = "project_save.jspx", method = RequestMethod.POST)
	public String projectSave(FroProject project, String[] managerName, Integer[] managerSex,
			Integer[] managerAge, Integer[] managerDegreeId, String attachUrl,
			HttpServletRequest request, ModelMap model) {
		
		FroMember member = FrontUtil.getMemberSession(request);
		
		//附件
		List<SysAttachmentInfo> list = processService.saveAttachmentInfo(attachUrl, member.getPrimaryKey(), -1);
		String ids = "";
		for (SysAttachmentInfo sysAttachmentInfo : list) {
			ids += sysAttachmentInfo.getPrimaryKey() + ",";
		}
		if (ids.length() > 0) {
			ids = ids.substring(0, ids.length() - 1);
		}
		
		//项目负责人
		List<FroProjectManager> managerList = new ArrayList<FroProjectManager>();
		if(managerName != null && managerName.length > 0){
			for (int i = 0, len = managerName.length; i < len; i++) {
				managerList.add(new FroProjectManager(
						managerName[i],managerSex[i],managerAge[i],new SysLibraryInfo(managerDegreeId[i])));
			}
		}
		
		//主键
		if(StringUtils.isBlank(project.getPrimaryKey())){
			project.setPrimaryKey(UtilPrimaryKey.getPrimaryKey());
		}
		project.setProjectFile(ids);
		project.setMember(member);
		project.setManagers(managerList);
		project = frontService.saveFroProject(project);
		model.addAttribute("project", project);
		return "main/project_result.html";
	}
	
	@RequestMapping(value = "project_delete.jspx")
	public String projectDelete(String id, HttpServletRequest request, ModelMap model) {
		frontService.deleteFroProjectByPk(id);
		return "redirect:project_list.jspx";
	}
	
	
	@RequestMapping(value = "project_edit.jspx", method = RequestMethod.GET)
	public String projectEdit(String id, HttpServletRequest request, ModelMap model) {
		FroProject project = frontService.getFroProjectByPk(id);
		List<SysAttachmentInfo> attachList = processService.getAttachmentInfoListByIds(project.getProjectFile());
		model.addAttribute("project", project);
		model.addAttribute("attachList", attachList);
		return "main/project_edit.html";
	}
	
	@RequestMapping(value = "project_detail.jspx", method = RequestMethod.GET)
	public String projectDetail(String id, HttpServletRequest request, ModelMap model) {
		FroProject project = frontService.getFroProjectByPk(id);
		project.setProjectPlanName(processService.getSysLibraryNamesByPks(project.getProjectPlan().split(",")));	
		List<SysAttachmentInfo> attachList = processService.getAttachmentInfoListByIds(project.getProjectFile());
		model.addAttribute("project", project);
		model.addAttribute("attachList", attachList);
		return "main/project_detail.html";
	}
}
