package com.yocent.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtil {
	
	/**
	 * post提交访问
	 * @param strUrl 提交地址
	 * @param params 提交参数
	 * @param codingType 参数编码
	 * @return
	 */
	public static String httpPost(String strUrl, Map<String, String> params, String codingType){
		try{
			HttpClient httpclient = new HttpClient();
			PostMethod post = new PostMethod(strUrl);//
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,codingType);
			System.out.println(params);
			for(String key: params.keySet()){
				post.addParameter(key, params.get(key));
			}
			httpclient.executeMethod(post);
			return new String(post.getResponseBody(),codingType);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	/**
	 * 解析返回值
	 * @param result
	 * @return
	 */
	public static Map<String, String> transitionResult(String result) {
		Map<String, String> results = new HashMap<>();
		if(result.indexOf("&") > -1){
			String[] resultArr = result.split("&");
			for (String resultStr : resultArr) {
				if(null==resultStr || "".equals(resultStr.trim())){
					continue;
				}
				String[] nameAndValue = resultStr.split("=");
				String name = "";
				String value = "";
				if(nameAndValue.length >= 1){
					name = nameAndValue[0];
				}
				if(nameAndValue.length >= 2){
					value = nameAndValue[1];
				}
				results.put(name, value);
			}
		}
		return results;
	}
	
	public static String httpPostWithJSON(String url, String json) throws IOException{
		String encoderJson = URLEncoder.encode(json, "UTF-8");
		encoderJson = json;
		HttpClient httpclient = new HttpClient();
		PostMethod method = new PostMethod(url);
		@SuppressWarnings("deprecation")
		RequestEntity requestEntity = new StringRequestEntity(encoderJson);
		method.setRequestEntity(requestEntity);
		method.addRequestHeader("Content-Type", "application/json;charset=utf-8");
		int result = httpclient.executeMethod(method);
		String jsonResult = method.getResponseBodyAsString();
		method.releaseConnection();
		return jsonResult;
	}
	
}
