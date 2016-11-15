package com.oss.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.eova.common.base.BaseModel;

public class UserScore extends BaseModel<UserScore>{

	private static final long serialVersionUID = 1L;
	
	public static final UserScore dao = new UserScore();

	/**
	 * 排行榜查询
	 * @return
	 */
	public List<UserScore> find(String startDate,String endDate){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  ");
		sb.append(" 	@rownum:=@rownum+1 rownum,a.nickname,a.maxscore,a.mintime  ");
		sb.append(" FROM  ");
		sb.append(" 	(  ");
		sb.append(" 		SELECT  ");
		sb.append(" 			@rownum:=0,  ");
		sb.append(" 			b.nickname as nickname,  ");
		sb.append(" 			max(a.score) AS maxscore,  ");
		sb.append(" 			min(a.create_time) AS mintime  ");
		sb.append(" 		FROM  ");
		sb.append(" 			user_score a,  ");
		sb.append(" 			user_weixin b  ");
		sb.append(" 		WHERE  ");
		sb.append(" 			a.openid = b.openid  ");
		if (StringUtils.isNotBlank(startDate)) {
			sb.append(" 			and a.create_time >= str_to_date(?,'%Y-%m-%d %H:%i:%s')  ");
			params.add(startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			sb.append(" 			and a.create_time <= str_to_date(?,'%Y-%m-%d %H:%i:%s')  ");
			params.add(endDate);
		}
		sb.append(" 		GROUP BY  ");
		sb.append(" 			a.openid  ");
		sb.append(" 	) a  ");
		sb.append(" ORDER BY  ");
		sb.append(" 	a.maxscore DESC,  ");
		sb.append(" 	a.mintime ");
		return this.find(sb.toString(),params.toArray());
	}
	
}
