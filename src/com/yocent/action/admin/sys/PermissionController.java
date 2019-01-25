package com.yocent.action.admin.sys;

import com.jfinal.core.Controller;
import com.yocent.manager.admin.sys.PermissionManager;
import com.yocent.model.admin.sys.Sys_Menu;

/**
 * 权限管理控制层
 * @author Guanhp
 *
 */
public class PermissionController extends Controller {
	
	public void index(){
		setAttr("menus", PermissionManager.manager.arrangeTree());
		render("index.html");
	}
	
	public void delete(){
		String ids = getPara("ids", "");
		renderJson(PermissionManager.manager.delete(ids, this));
	}

	public void edit() {
		setAttr("menus", PermissionManager.manager.getMenus());
		setAttr("menu", PermissionManager.manager.getFullMenu(getPara("id", "")));
		render("edit.html");
	}
	
	public void save(){
		Sys_Menu menu = getModel(Sys_Menu.class, "menu");
		renderJson(PermissionManager.manager.save(menu));
	}
}
