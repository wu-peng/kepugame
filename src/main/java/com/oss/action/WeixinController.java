package com.oss.action;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eova.common.utils.time.DateUtil;
import com.eova.config.EovaConfig;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.oss.model.GameImage;
import com.oss.model.UserScore;
import com.oss.model.UserWeixin;

import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

public class WeixinController extends Controller {
	public void game() throws IOException {
		//1、获取code
		String code = getPara("code");
		String appId = EovaConfig.props.get("appId");
		String appSecret = EovaConfig.props.get("appSecret");
		
		//2、获取code后，请求以下链接获取access_token和openID
		SnsToken at = SnsAPI.oauth2AccessToken(appId, appSecret, code);

		if (at==null||StringUtils.isBlank(at.getAccess_token())) {
			System.out.print("没有获取到响应参数");
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
			url+="appid="+appId;
			url+="&redirect_uri="+"http://wupeng.ngrok.cc/weixin/game";
			url+="&response_type=code";
			url+="&scope=snsapi_userinfo";
			url+="&state=null";
			url+="&connect_redirect=1";
			url+="#wechat_redirect";
			this.getResponse().sendRedirect(url);
			renderNull();
			return;
			/*UserWeixin user = UserWeixin.dao.findUserByCode(code);
			if (user==null) {
				String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
				url+="appid="+appId;
				url+="&redirect_uri="+"http://wupeng.tunnel.qydev.com/weixin/game";
				url+="&response_type=code";
				url+="&scope=snsapi_userinfo";
				url+="&state=null";
				url+="&connect_redirect=1";
				url+="#wechat_redirect";
				this.getResponse().sendRedirect(url);
				renderNull();
				return;
			}
			at = SnsAPI.oauth2RefreshToken(appId, user.getStr("refresh_token"));
			accessToken = at.getAccess_token();
			openID = at.getOpenid();
			refreshToken = at.getRefresh_token();*/
		}
		
		String accessToken = at.getAccess_token();
		String openID = at.getOpenid();
		String refreshToken = at.getRefresh_token();
		
		
		//3、拉取用户信息
		User umap = SnsAPI.userinfo(accessToken, openID, "zh_CN");
		
		UserWeixin user = UserWeixin.dao.findUserByOpenid(openID);
		
		if (user==null) {
			user = new UserWeixin();
			user.set("openid", umap.getOpenid());
			user.set("nickname", umap.getNickname()+"💃");
			user.set("sex", umap.getSex());
			user.set("province", umap.getProvince());
			user.set("city", umap.getCity());
			user.set("country", umap.getCountry());
			user.set("headimgurl", umap.getHeadimgurl());
			user.set("code", code);
			user.set("refresh_token", refreshToken);
			user.save();
		}else{
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
		}
		
		
		/*System.out.println(umap.getNickname());
		System.out.println(umap.getHeadimgurl());
		System.out.println(umap.getSex());
		System.out.println(umap.getOpenid());*/
		//System.out.println(umap.getUnionid());
		
		setAttr("openid", umap.getOpenid());
		
		String imgpath = EovaConfig.props.get("domain_img");
		List<GameImage> images = GameImage.dao.find();
		List<String> urlList = new ArrayList<String>();
		for (GameImage image : images) {
			String url = imgpath+"/"+image.get("image");
			urlList.add(url);
		}
		setAttr("urlList", JsonKit.toJson(urlList));
		
		render("/game/index.html");
	}
	
	public void findRank(){
		String s1 = "2016-11-01 00:00:00";
		String e1 = "2016-11-10 23:59:59";
		
		String s2 = "2016-09-23 00:00:00";
		String e2 = "2016-09-30 23:59:59";
		
		String startDate = "";
		String endDate = "";
		if (compareDate(DateUtil.getCurrDateTimeStr(),s1)==1&&compareDate(DateUtil.getCurrDateTimeStr(),e1)==-1) {
			startDate = s1;
			endDate = e1;
		}else if (compareDate(DateUtil.getCurrDateTimeStr(),s2)==1&&compareDate(DateUtil.getCurrDateTimeStr(),e2)==-1) {
			startDate = s2;
			endDate = e2;
		}else{
			startDate = s1;
			endDate = e1;
		}
		List<UserScore> rankList = new UserScore().find(startDate,endDate);
		renderJson(rankList);
	}
	
	public int compareDate(String DATE1, String DATE2) {
        
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() >= dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() <= dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
  }
	
	public void startGame(){
		Map<String, Object> map = new HashMap<String,Object>();
		String openid = getPara("openid");
		
		String currentDate = DateUtil.getCurrDateStr();
		String startDate = currentDate+" 00:00:00";
		String endDate = currentDate+" 23:59:59";
		UserScore count = UserScore.dao.findFirst("select count(*) as count from user_score where openid=? and create_time>str_to_date(?,'%Y-%m-%d %H:%i:%s') and create_time<str_to_date(?,'%Y-%m-%d %H:%i:%s')", openid,startDate,endDate);
		if (count.getLong("count")>=9999) {
			map.put("status", "0");
			map.put("msg", "每天只能玩5次哦");
			renderJson(map);
			return;
		}
		
		UserScore rank = new UserScore();
		rank.set("openid", openid);
		rank.set("score", 0);
		rank.set("is_over", 0);
		rank.set("create_time", new Date());
		rank.save();
		
		Integer id = rank.get("id");
		map.put("status", "1");
		map.put("scoreid", id);
		renderJson(map);
	}
	
	public void uploadScore(){
		Map<String, Object> map = new HashMap<String,Object>();
		String currentDate = DateUtil.getCurrDateStr();
		String startDate = currentDate+" 00:00:00";
		String endDate = currentDate+" 23:59:59";
		
		Integer score = getParaToInt("score");
		String openid = getPara("openid");
		String scoreId = getPara("scoreid");
		
		UserScore count = UserScore.dao.findFirst("select count(*) as count from user_score where openid=? and create_time>str_to_date(?,'%Y-%m-%d %H:%i:%s') and create_time<str_to_date(?,'%Y-%m-%d %H:%i:%s')", openid,startDate,endDate);
		if (count.getLong("count")>=5) {
			map.put("status", "0");
			map.put("msg", "每天只能玩5次哦");
			renderJson(map);
			return;
		}
		
		UserScore maxscore = UserScore.dao.findFirst("select max(score) as maxscore from user_score");
		if (score<=maxscore.getInt("maxscore")) {
			map.put("status", "0");
			map.put("msg", "没有破纪录");
		}else{
			map.put("status", "1");
			map.put("msg", "破纪录啦");
		}
		
		UserScore userScore = UserScore.dao.findById(scoreId);
		userScore.set("score", score);
		userScore.set("is_over", 1);
		userScore.set("over_time", new Date());
		userScore.update();
		
		renderJson(map);
	}
}
