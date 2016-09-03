package com.oss.model;

import java.util.List;

import com.eova.common.base.BaseModel;

public class GameRank extends BaseModel<GameRank>{

	private static final long serialVersionUID = 1L;
	
	public static final GameRank dao = new GameRank();

	/**
	 * 查询游戏分数
	 * @return
	 */
	public List<GameRank> find(){
		String sql = "select a.nickname,b.score from user_weixin a,user_score b where a.id = b.weixin_id";
		return this.find(sql);
	}
}
