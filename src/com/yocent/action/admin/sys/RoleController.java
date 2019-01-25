package com.yocent.action.admin.sys;

import com.jfinal.core.Controller;
import com.yocent.manager.admin.sys.RoleManager;
import com.yocent.model.admin.sys.Sys_Role;

/**
 * 角色管理控制层
 * @author Guanhp
 *
 */
public class RoleController extends Controller {

	public void index(){
		setAttr("roles", RoleManager.findAll());
		render("index.html");
	}
	
	/**
	 * 获取角色权限(菜单列表)
	 */
	public void getPermissions(){
		String roleId = getPara("roleId", "");
		renderJson(RoleManager.manager.getPermissions(roleId, this));
	}
	
	/**
	 * 保存角色
	 */
	public void save(){
		Sys_Role role = getModel(Sys_Role.class, "role");
		String rights = getPara("rights", "");
		renderJson(RoleManager.manager.save(role, rights, this));
	}
	
	/**
	 * 删除角色
	 */
	public void delete(){
		String ids = getPara("ids", "");
		renderJson(RoleManager.manager.delete(ids, this));
	}
	
}
