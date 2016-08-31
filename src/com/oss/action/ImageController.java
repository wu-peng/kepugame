package com.oss.action;

import com.jfinal.core.Controller;

public class ImageController extends Controller {
	
	public void toImageManage() throws Exception {

		render("/WEB-INF/imageManage/index.jsp");
	}
}
