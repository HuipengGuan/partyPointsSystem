package com.yocent.utils;

import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import com.jfinal.weixin.sdk.api.TemplateMsgApi;

public class WeChartUtil {
	
	private static final String APPID = PropKit.use("../config/config.properties").get("appId");
	private static final String SECRET = PropKit.use("../config/config.properties").get("appSecret");
	private static final String TEMPLATE_APPLY_RESULT = PropKit.use("../config/config.properties").get("tempApplyResult");

	public static String getOpenID(String code) {
		String openId = "";
		try {
			openId = SnsAccessTokenApi.getSnsAccessToken(APPID, SECRET, code).getOpenid();
		} catch (Exception e) {
			System.out.println("获取不到OPENID");
			e.printStackTrace();
		} 
		return openId; 
	}

}
