package com.yocent.utils;

public class TestUtil {

	public static void main(String[] args) {
		String str = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"name\": \"助学金申请\",\n" +
                "            \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3e425ee6ad27c8db&redirect_uri=http%3a%2f%2fxsswzx.zjbti.net.cn%2ffront%2flogin&response_type=code&scope=snsapi_base&state=gsxyxssw#wechat_redirect\",\n" +
                "            \"type\": \"view\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"个人中心\",\n" +
                "            \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3e425ee6ad27c8db&redirect_uri=http%3a%2f%2fxsswzx.zjbti.net.cn%2ffront%2ftoPerson&response_type=code&scope=snsapi_base&state=gsxyxssw#wechat_redirect\",\n" +
                "            \"type\": \"view\"\n" +
                "        },\n" +
                "        {\n" +
                "\t    \"name\": \"使用帮助\",\n" +
                "\t    \"url\": \"http://m.bajie8.com/footer/cjwt\",\n" +
                "\t    \"type\": \"view\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
		System.out.println(str);
	}
	
}
