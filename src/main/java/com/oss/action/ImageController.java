package com.oss.action;

import java.util.ArrayList;
import java.util.List;

import com.eova.config.EovaConfig;
import com.jfinal.core.Controller;
import com.oss.model.GameImage;

public class ImageController extends Controller {
	
	public void toImageManage() throws Exception {
		
		String imgpath = EovaConfig.props.get("domain_img");
		List<GameImage> images = GameImage.dao.find();
		List<String> urlList = new ArrayList<String>();
		for (GameImage image : images) {
			String url = imgpath+image.get("image");
			urlList.add(url);
		}
		
		renderJson(urlList);
	}
}
