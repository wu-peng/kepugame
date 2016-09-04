/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss;

import com.eova.config.EovaConfig;
import com.eova.interceptor.LoginInterceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.oss.action.GameController;
import com.oss.action.ImageController;
import com.oss.action.WeixinController;
import com.oss.model.GameImage;
import com.oss.model.GameRank;
import com.oss.model.UserWeixin;

public class OSSConfig extends EovaConfig {
	
	/**
	 * 配置常量
	 */
	@Override
	public void configConstant(Constants me) {
		super.configConstant(me);
		/*me.setDevMode(true);
		me.setViewType(ViewType.FREE_MARKER);*/
	}

	/**
	 * 自定义路由
	 * 
	 * @param me
	 */
	@Override
	protected void route(Routes me) {
		// 自定义的路由配置往这里加。。。
		me.add("/imageManage", ImageController.class);
		me.add("/game", GameController.class);
		me.add("/weixin", WeixinController.class);
		
		// 不需要登录拦截的URL
		LoginInterceptor.excludes.add("/init");
		LoginInterceptor.excludes.add("/imageManage/toImageManage");
		LoginInterceptor.excludes.add("/weixin/game");
		LoginInterceptor.excludes.add("/weixin/uploadScore");
		LoginInterceptor.excludes.add("/game/queryRank");
		//LoginInterceptor.excludes.add("/game/index.html");
	}

	/**
	 * 自定义Main数据源Model映射
	 * 
	 * @param arp
	 */
	@Override
	protected void mapping(ActiveRecordPlugin arp) {
		// 自定义的Model映射往这里加。。。
		arp.addMapping("user_score", GameRank.class);
		arp.addMapping("game_image", GameImage.class);
		arp.addMapping("user_weixin", UserWeixin.class);
	}

	/**
	 * 自定义插件
	 */
	@Override
	protected void plugin(Plugins plugins) {
		// 添加数据源
		// 数据源Key
       /*String datasource = "kepugame";
         
        // 添加数据源
        String gsUrl, gsUser, gsPwd;
        gsUrl = props.get("kepu_url");
        gsUser = props.get("kepu_user");
        gsPwd = props.get("kepu_pwd");
         
        WallFilter wall = new WallFilter();
        wall.setDbType(JdbcUtils.MYSQL);
         
        DruidPlugin dp = new DruidPlugin(gsUrl, gsUser, gsPwd);
        dp.addFilter(new StatFilter());
        dp.addFilter(wall);
         
        ActiveRecordPlugin arp = new ActiveRecordPlugin(datasource, dp);
        // 方言
        arp.setDialect(new MysqlDialect());
        // 事务级别
        arp.setTransactionLevel(Connection.TRANSACTION_REPEATABLE_READ);
        // 统一全部默认小写
        arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
        // 是否显示SQL
        arp.setShowSql(true);
        System.out.println("load data source:" + gsUrl + "/" + gsUser);
 
        // arp.addMapping("xxx", Xxx.class);
        plugins.add(dp).add(arp);
         
        // 注册数据源
        dataSources.add(datasource);*/
		// 添加自动扫描插件
		
		// ...
	}

	/**
	 * Run Server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 0);
	}

}