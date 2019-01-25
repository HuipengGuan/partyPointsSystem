package com.yocent.action.pub;

import java.io.IOException;

import com.jfinal.core.Controller;
import com.yocent.action.manager.base.InformManager;
import com.yocent.manager.pub.PubManager;


/**
 * 公共类控制层
 * @author Guanhp
 *
 */
public class PubController extends Controller {

	/**
	 * 根据图片CODE获取图片文件
	 * @throws IOException
	 */
	public void img() throws IOException{
		String code = getPara("code", "");
		renderFile(PubManager.manager.getFileByCode(code));
	}
	
	/**
	 * 查看公告
	 */
	public void inform(){
		setAttr("info", InformManager.manager.getFullInfo(getPara("id", "")));
		render("inform.html");
	}
	
}
