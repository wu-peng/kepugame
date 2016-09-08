package com.oss.action;

import java.util.List;

import com.jfinal.core.Controller;
import com.oss.model.UserScore;

public class GameController extends Controller {
	
	public void queryRank() throws Exception {
		List<UserScore> rankList = new UserScore().find();
		setAttr("ranks", rankList);
		renderJson(rankList);
	}
}
