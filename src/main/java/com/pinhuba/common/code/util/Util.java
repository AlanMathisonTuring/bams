package com.pinhuba.common.code.util;

import java.io.InputStream;

public class Util {

	// 第一个字母小写
	public static String lowerCaseFirstLetter(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	
	// 第一个字母大写
	public static String upperCaseFirstLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	
	public static String packToPath(String pack) {
		String path = pack.replace(".", "/") + "/";
		return path;
	}
	
	
	/**
	 * 打开目录
	 * @param dir
	 */
	public static void openExplorer(String dir){
		Runtime run = Runtime.getRuntime();   
	    try {   
	        // run.exec("cmd /k shutdown -s -t 3600");   
	        Process process = run.exec("cmd.exe /c start " + dir);   
	        InputStream in = process.getInputStream();     
	        while (in.read() != -1) {   
	            System.out.println(in.read());   
	        }   
	        in.close();   
	        process.waitFor();   
	    } catch (Exception e) {            
	        e.printStackTrace();   
	    }   
	}
	
}
