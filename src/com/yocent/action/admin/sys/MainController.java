package com.yocent.action.admin.sys;

import com.jfinal.core.Controller;
import com.yocent.manager.admin.sys.MainManager;

/**
 * 主页控制层
 * @author Guanhp
 *
 */
public class MainController extends Controller{

	public void index(){
		setAttr("menus", MainManager.manager.getMenus(this));
		render("index.html");
	}
	
	public void right(){
		render("right.html");
	}
	
}
