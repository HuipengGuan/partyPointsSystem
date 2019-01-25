package com.yocent.manager.admin.sys;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.yocent.entity.LayJsResult;
import com.yocent.model.admin.sys.Sys_Menu;
import com.yocent.model.admin.sys.Sys_Role;

/**
 * 角色管理业务层
 * @author Guanhp
 *
 */
public class RoleManager {

	public static final RoleManager manager = new RoleManager();

	/**
	 * 获取角色的权限
	 * @param roleId
	 * @param con
	 * @return
	 */
	public String getPermissions(String roleId, Controller con) {
		return JsonKit.toJson(Sys_Menu.dao.ztree(roleId)).replaceAll("pid", "pId");
	}

	/**
	 * 查找所有角色
	 * @return
	 */
	public static List<Sys_Role> findAll() {
		return Sys_Role.dao.findAll();
	}

	/**
	 * 删除角色
	 * @param ids
	 * @param roleController
	 * @return
	 */
	public LayJsResult delete(String ids, Controller con) {
		LayJsResult result = new LayJsResult();
		boolean flag = Db.tx(new IAtom() {
			boolean del_flag = true;
			@Override
			public boolean run() throws SQLException {
				try {
					Db.update("update sys_role set DELETED = 1 where ID in (" + ids + ")");
				} catch (Exception e) {
					result.setMsg("系统删除失败,请稍候再试!");
					del_flag = false;
					e.printStackTrace();
				}
				return del_flag;
			}
		});
		result.setCode(flag ? 0 : 1);
		return result;
	}

	/**
	 * 保存角色
	 * @param role 角色对象
	 * @param rights 角色权限IDS
	 * @param con 类对象
	 * @return
	 */
	public LayJsResult save(Sys_Role role, String rights, Controller con) {
		LayJsResult result = new LayJsResult();
		boolean flag = Db.tx(new IAtom() {
			boolean save_flag = true;
			@Override
			public boolean run() throws SQLException {
				try {
					if(null == role.get("ID")){	//新增角色
						role.set("DELETED", 0)
							.save();
					}else{						//修改角色
						role.update();
						deleteAllRightsByRole(role.get("ID").toString());
					}
					saveRightsByRole(role.get("ID").toString(), rights);
				} catch (Exception e) {
					result.setMsg("角色保存失败, 请稍候重试!");
					save_flag = false;
					e.printStackTrace();
				}
				return save_flag;
			}
		});
		result.setCode(flag ? 0 : 1);
		return result;
	}

	/**
	 * 添加角色权限
	 * @param string
	 * @param rights
	 */
	protected void saveRightsByRole(String roleId, String rights) throws Exception {
		if(StrKit.isBlank(rights)) return;
		String[] idArr = rights.split(",");
		for (String rightId : idArr) {
			Db.update("insert into SYS_ROLE_MENU (ROLE_ID, MENU_ID) VALUES (?, ?)", roleId, rightId);
		}
	}

	/**
	 * 删除角色权限
	 * @param roleId
	 */
	protected void deleteAllRightsByRole(String roleId) throws Exception {
		Db.update("delete from sys_role_menu where ROLE_ID = ?", roleId);
	}

}
