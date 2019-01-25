package com.yocent.action.admin.sys;

import com.jfinal.core.Controller;
import com.yocent.manager.admin.sys.UserManager;
import com.yocent.model.admin.sys.Sys_User;

/**
 * 用户管理控制层
 * @author Guanhp
 *
 */
public class UserController extends Controller {

	public void index(){
		render("index.html");
	}
	
	/**
	 * ajax获取用户数据
	 */
	public void list(){
		Integer page = getParaToInt("page", 1);
		Integer limit = getParaToInt("limit", 10);
		String search = getPara("search", "");
		renderJson(UserManager.manager.getList(page, limit, search, this));
	}
	
	/**
	 * 用户编辑页面
	 */
	public void edit(){
		String id = getPara("id", "");
		setAttr("user", UserManager.manager.getFullInfo(id));
		render("edit.html");
	}
	
	/**
	 * 用户保存
	 */
	public void save(){
		Sys_User user = getModel(Sys_User.class, "user");
		String[] roleIds = getParaValues("roleId");
		renderJson(UserManager.manager.save(user, roleIds, this));
	}
	
	/**
	 * 删除用户
	 */
	public void delete(){
		String ids = getPara("ids", "");
		renderJson(UserManager.manager.delete(ids, this));
	}
	
	
}
