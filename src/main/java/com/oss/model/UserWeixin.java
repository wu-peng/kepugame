package com.oss.model;

import com.eova.common.base.BaseModel;

public class UserWeixin extends BaseModel<UserWeixin>{

	private static final long serialVersionUID = 1L;
	
	public static final UserWeixin dao = new UserWeixin();

	/**
	 * 查询微信用户
	 * @return
	 */
	public UserWeixin findUserByOpenid(String openid){
		String sql = "select a.* from user_weixin a where a.openid = ?";
		return this.findFirst(sql,openid);
	}
	
	public UserWeixin findUserByCode(String code){
		String sql = "select a.* from user_weixin a where a.code = ?";
		return this.findFirst(sql,code);
	}
}
