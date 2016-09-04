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
		String sql = "select a.nickname,b.score from user_weixin a,user_score b where a.openid = b.openid";
		return this.find(sql);
	}
	
	/**
	 * 根据openid查询游戏分数
	 * @return
	 */
	public GameRank findByOpenid(String openid){
		String sql = "select a.* from user_score a where a.openid=?";
		return this.findFirst(sql,openid);
	}
}
