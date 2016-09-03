package com.oss.action;

import com.jfinal.core.Controller;

public class ImageController extends Controller {
	
	public void toImageManage() throws Exception {

		
		setAttr("id", "1472480300989");
		setAttr("name","1472480300989");
		setAttr("value","1472480300989.jpg");
		render("/WEB-INF/imageManage/index.html");
	}
}
