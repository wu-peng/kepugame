package com.oss.action;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.oss.model.GameRank;

public class GameController extends Controller {
	
	public void queryRank() throws Exception {
		List<GameRank> rankList = new GameRank().find();
		List<String> list = new ArrayList<String>();
		list.add("顶顶顶顶");
		setAttr("ranks", rankList);
		setAttr("id", "哈哈哈");
		//render("/WEB-INF/game/index.html");
		renderJson(rankList);
	}
}
