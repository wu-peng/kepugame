package com.oss.model;

import java.util.List;

import com.eova.common.base.BaseModel;

public class GameImage extends BaseModel<GameImage>{

	private static final long serialVersionUID = 1L;
	
	public static final GameImage dao = new GameImage();

	/**
	 * 查询游戏中的图片
	 * @return
	 */
	public List<GameImage> find(){
		String sql = "select a.image,a.sort from game_image a order by sort";
		return this.find(sql);
	}
}
