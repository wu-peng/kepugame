package com.oss.action;

import java.util.List;

import com.jfinal.core.Controller;
import com.oss.model.UserScore;

public class GameController extends Controller {
	
	public void queryRank() throws Exception {
		String startDate = this.getPara("startDate");
		String endDate = this.getPara("endDate");
		List<UserScore> rankList = new UserScore().find(startDate,endDate);
		setAttr("ranks", rankList);
		//renderJson(rankList);
		render("/WEB-INF/rank/index.html");
	}
}
