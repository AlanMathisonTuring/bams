package com.pinhuba.front.action;

import java.io.File;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pinhuba.common.util.ResponseUtils;
import com.pinhuba.common.util.file.FileTool;
import com.pinhuba.common.util.file.properties.SystemConfig;
import com.pinhuba.common.util.imgProcess.MyFileRenamePolicy;
import com.pinhuba.common.util.security.Base64;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * 批量上传附件
 */
@Controller
public class SwfUploadAct{
	
	/**
	 * 错误信息参数
	 */
	@RequestMapping(value = "/swfAttachsUpload.jspx", method = RequestMethod.POST)
	public void swfAttachsUpload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject data = new JSONObject();
		
		String saveDirectory = SystemConfig.getParam("erp.upload.memberPath");
		FileTool.checkDirAndCreate(saveDirectory);
		FileRenamePolicy rename = new MyFileRenamePolicy();
		MultipartRequest multi = new MultipartRequest(request, saveDirectory, 1024*1024*1024, "utf-8", rename);
		
		// 输出反馈信息
		Enumeration files = multi.getFileNames();
		while (files.hasMoreElements()){
			String name = (String) files.nextElement();
			File f = multi.getFile(name);
			if (f != null) {
				String fileName = multi.getFilesystemName(name).replaceAll(",", "");
				String oldFileName = multi.getOriginalFileName(name).replaceAll(",", "");
				String lastFileName = Base64.getBase64FromString(saveDirectory + fileName);
				data.put("file",oldFileName+"|"+lastFileName);
			}
		}
		ResponseUtils.renderJson(response, data.toString());
	}
}
