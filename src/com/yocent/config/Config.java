package com.yocent.config;

import java.sql.Connection;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.yocent.action.admin.base.InformController;
import com.yocent.action.admin.base.PointTypeController;
import com.yocent.action.admin.sys.MainController;
import com.yocent.action.admin.sys.PermissionController;
import com.yocent.action.admin.sys.RoleController;
import com.yocent.action.admin.sys.UserController;
import com.yocent.action.model.base.Base_Inform;
import com.yocent.action.pub.PubController;
import com.yocent.handler.SessionHandler;
import com.yocent.model.admin.sys.Sys_Log;
import com.yocent.model.admin.sys.Sys_Menu;
import com.yocent.model.admin.sys.Sys_Role;
import com.yocent.model.admin.sys.Sys_User;
import com.yocent.model.pub.Pub_Img;
import com.yocent.weixin.controller.WeixinApiController;
import com.yocent.weixin.controller.WeixinMsgController;


public class Config extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setBaseViewPath("/WEB-INF/page");
		me.setDevMode(true); 
		me.setViewType(ViewType.FREE_MARKER);
		PropKit.use("../config/druid.properties");
		
	}

	@Override
	public void configRoute(Routes me) {
		//公告配置
		me.add("/pub", PubController.class);
		
		//后台控制路径
		me.add("/main", MainController.class);
		me.add("/user", UserController.class);
		me.add("/role", RoleController.class);
		me.add("/permission", PermissionController.class);
		
		//基础配置
		me.add("/inform", InformController.class);
		me.add("/pointType", PointTypeController.class);
		
		//微信API
		me.add("/api", WeixinApiController.class);
		me.add("/msg", WeixinMsgController.class);
	}
	
	/**
	 * 数据库配置
	 * @return
	 */
	public static C3p0Plugin createC3p0Plugin() {
		return new C3p0Plugin(PropKit.get("url"), PropKit.get("username"), PropKit.get("password").trim(), PropKit.get("driverClass").trim(),
				PropKit.getInt("maxPoolSize"), PropKit.getInt("minPoolSize"), PropKit.getInt("initialPoolSize"), PropKit.getInt("maxIdleTime"), 
				PropKit.getInt("acquireIncrement"));
	}

	@Override
	public void configPlugin(Plugins me) {
		C3p0Plugin C3p0Plugin = createC3p0Plugin();
		me.add(C3p0Plugin);
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(C3p0Plugin, Connection.TRANSACTION_READ_COMMITTED);
		//arp.addMapping("t_reservations", "ID", Task_Reservations.class);
		arp.addMapping("sys_menu", "ID", Sys_Menu.class);
		arp.addMapping("sys_role", "ID", Sys_Role.class);
		arp.addMapping("sys_user", "ID", Sys_User.class);
		arp.addMapping("sys_log", "ID", Sys_Log.class);
		
		arp.addMapping("base_inform", "ID", Base_Inform.class);
		
		arp.addMapping("pub_img", "ID", Pub_Img.class);
		
		arp.setShowSql(PropKit.getBoolean("devMode", true));
		me.add(arp);
		//me.add(new QuartzPlugin("../config/job.properties")); //这是定时器，如需使用取消注释即可
		arp.setDialect(new MysqlDialect());
		
	}
	
	/**
	 * jfinal框架启动后执行
	 */
	public void afterJFinalStart(){
		
		/*System.out.println("启动LDAP连接");
		LdapUtil.getCtx();*/
		
		// 1.5 之后支持redis存储access_token、js_ticket，需要先启动RedisPlugin
//      ApiConfigKit.setAccessTokenCache(new RedisAccessTokenCache());
      // 1.6新增的2种初始化
//      ApiConfigKit.setAccessTokenCache(new RedisAccessTokenCache(Redis.use("weixin")));
//      ApiConfigKit.setAccessTokenCache(new RedisAccessTokenCache("weixin"));

      ApiConfig ac = new ApiConfig();
      Prop p = PropKit.use("../config/config.properties");
      // 配置微信 API 相关参数
      ac.setToken(p.get("token"));
      ac.setAppId(p.get("appId"));
      ac.setAppSecret(p.get("appSecret"));

      /**
       *  是否对消息进行加密，对应于微信平台的消息加解密方式：
       *  1：true进行加密且必须配置 encodingAesKey
       *  2：false采用明文模式，同时也支持混合模式
       */
      ac.setEncryptMessage(p.getBoolean("encryptMessage", false));
      ac.setEncodingAesKey(p.get("encodingAesKey", "setting it in config file"));

      /**
       * 多个公众号时，重复调用ApiConfigKit.putApiConfig(ac)依次添加即可，第一个添加的是默认。
       */
      ApiConfigKit.putApiConfig(ac);
      
      /**
       * 1.9 新增LocalTestTokenCache用于本地和线上同时使用一套appId时避免本地将线上AccessToken冲掉
       * 
       * 设计初衷：https://www.oschina.net/question/2702126_2237352
       * 
       * 注意：
       * 1. 上线时应保证此处isLocalDev为false，或者注释掉该部分代码！
       * 
       * 2. 为了安全起见，此处可以自己添加密钥之类的参数，例如：
       * http://localhost/weixin/api/getToken?secret=xxxx
       * 然后在WeixinApiController#getToken()方法中判断secret
       * 
       * @see WeixinApiController#getToken()
       */
//    if (isLocalDev) {
//        String onLineTokenUrl = "http://localhost/weixin/api/getToken";
//        ApiConfigKit.setAccessTokenCache(new LocalTestTokenCache(onLineTokenUrl));
//    }
      //微信小程序
      /*WxaConfig wc = new WxaConfig();
      wc.setAppId("wx4f53594f9a6b3dcb");
      wc.setAppSecret("eec6482ba3804df05bd10895bace0579");
      WxaConfigKit.setWxaConfig(wc);*/
	}

	@Override
	public void configInterceptor(Interceptors me) {
		
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new SessionHandler());
		me.add(new ContextPathHandler("base"));
	}

}
