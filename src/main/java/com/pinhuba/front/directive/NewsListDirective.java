package com.pinhuba.front.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.pinhuba.core.iservice.IFrontService;
import com.pinhuba.core.pojo.FroNews;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


public class NewsListDirective implements TemplateDirectiveModel {
	public static final String COUNT = "count";
	public static final String TYPE_ID = "typeId";
	public static final String OUT_LIST = "tag_list";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer count = DirectiveUtils.getInt(COUNT, params);
		Integer typeId = DirectiveUtils.getInt(TYPE_ID, params);
		
		List<FroNews> list = frontService.listFroNews(typeId, count);

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	@Resource
	private IFrontService frontService;
}
