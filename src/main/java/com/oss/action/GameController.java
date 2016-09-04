package com.oss.action;

import java.util.List;

import com.jfinal.core.Controller;
import com.oss.model.GameRank;

public class GameController extends Controller {
	
	public void queryRank() throws Exception {
		List<GameRank> rankList = new GameRank().find();
		setAttr("ranks", rankList);
		renderJson(rankList);
	}
}
