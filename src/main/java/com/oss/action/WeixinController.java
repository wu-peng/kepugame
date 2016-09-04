package com.oss.action;

import org.apache.commons.lang.StringUtils;

import com.eova.config.EovaConfig;
import com.jfinal.core.Controller;
import com.oss.model.GameRank;
import com.oss.model.UserWeixin;

import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

public class WeixinController extends Controller {
	public void game() {
		//1、获取code
		String code = getPara("code");
		String appId = EovaConfig.props.get("appId");
		String appSecret = EovaConfig.props.get("appSecret");
		
		//2、获取code后，请求以下链接获取access_token和openID
		SnsToken at = SnsAPI.oauth2AccessToken(appId, appSecret, code);
		String accessToken = at.getAccess_token();
		String openID = at.getOpenid();
		String refreshToken = at.getRefresh_token();

		if (StringUtils.isBlank(accessToken)) {
			System.out.print("没有获取到响应参数");
			UserWeixin user = UserWeixin.dao.findUserByCode(code);
			at = SnsAPI.oauth2RefreshToken(appId, user.getStr("refresh_token"));
			accessToken = at.getAccess_token();
			openID = at.getOpenid();
			refreshToken = at.getRefresh_token();
		}
		//3、拉取用户信息
		User umap = SnsAPI.userinfo(accessToken, openID, "zh_CN");
		
		UserWeixin user = UserWeixin.dao.findUserByOpenid(openID);
		
		if (user==null) {
			user = new UserWeixin();
			user.set("openid", umap.getOpenid());
			user.set("nickname", umap.getNickname());
			user.set("sex", umap.getSex());
			user.set("province", umap.getProvince());
			user.set("city", umap.getCity());
			user.set("country", umap.getCountry());
			user.set("headimgurl", umap.getHeadimgurl());
			user.set("code", code);
			user.set("refresh_token", refreshToken);
			user.save();
		}
		user.set("openid", umap.getOpenid());
		user.set("nickname", umap.getNickname());
		user.set("sex", umap.getSex());
		user.set("province", umap.getProvince());
		user.set("city", umap.getCity());
		user.set("country", umap.getCountry());
		user.set("headimgurl", umap.getHeadimgurl());
		user.set("code", code);
		user.set("refresh_token", refreshToken);
		user.update();
		
		
		System.out.println(umap.getNickname());
		System.out.println(umap.getHeadimgurl());
		System.out.println(umap.getSex());
		System.out.println(umap.getOpenid());
		//System.out.println(umap.getUnionid());
		
		setAttr("openid", umap.getOpenid());
		render("/game/index.html");
	}
	
	public void uploadScore(){
		
		String score = getPara("score");
		String openid = getPara("openid");
		
		GameRank rank = GameRank.dao.findByOpenid(openid);
		if (rank==null) {
			rank = new GameRank();
			rank.set("openid", openid);
			rank.set("score", score);
			rank.save();
		}else{
			rank.set("score", score);
			rank.update();
		}
		renderJson("ok");
	}
}
