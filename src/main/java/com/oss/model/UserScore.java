package com.oss.model;

import java.util.List;

import com.eova.common.base.BaseModel;

public class UserScore extends BaseModel<UserScore>{

	private static final long serialVersionUID = 1L;
	
	public static final UserScore dao = new UserScore();

	/**
	 * 排行榜查询
	 * @return
	 */
	public List<UserScore> find(){
		String sql =	"SELECT "+
				   "	@rownum:=@rownum+1 rownum,a.nickname,a.maxscore,a.mintime "+
				   "FROM "+
				   "	( "+
				   "		SELECT "+
				   "			@rownum:=0, "+
				   "			b.nickname as nickname, "+
				   "			max(a.score) AS maxscore, "+
				   "			min(a.create_time) AS mintime "+
				   "		FROM "+
				   "			user_score a, "+
				   "			user_weixin b "+
				   "		WHERE "+
				   "			a.openid = b.openid "+
				   "		GROUP BY "+
				   "			a.openid "+
				   "	) a "+
				   "ORDER BY "+
				   "	a.maxscore DESC, "+
				   "	a.mintime ";
		return this.find(sql);
	}
}
