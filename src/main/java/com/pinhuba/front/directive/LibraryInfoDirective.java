package com.pinhuba.front.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.pinhuba.core.iservice.ISysProcessService;
import com.pinhuba.core.pojo.SysLibraryInfo;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


public class LibraryInfoDirective implements TemplateDirectiveModel {
	public static final String CODE = "code";
	public static final String OUT_LIST = "tag_list";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String code = DirectiveUtils.getString(CODE, params);
		List<SysLibraryInfo> list = processService.getSysLibraryInfoByCode(code);
		
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	@Resource
	private ISysProcessService processService;
}
