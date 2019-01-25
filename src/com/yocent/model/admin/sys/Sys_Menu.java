package com.yocent.model.admin.sys;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**
 * 系统菜单数据层
 * 
 * @author Guanhp
 *
 */
public class Sys_Menu extends Model<Sys_Menu> {

	private static final long serialVersionUID = 366269438877039520L;
	public static final Sys_Menu dao = new Sys_Menu();

	public List<Sys_Menu> findAll() {
		return dao.find("select * from sys_menu where type = 1");
	}

	public List<Record> ztree(String roleId) {
		List<Record> list = null;
		if (!("".equals(roleId)))
			list = Db.find(
					"select distinct m.id, m.code, m.pid, m.name, case when rm.id is null then 'false' else 'true' end checked from sys_menu m left join sys_role_menu rm on m.id = rm.MENU_ID and rm.ROLE_ID = ? where m.DELETED = 0 order by code",
					new Object[] { roleId });
		else {
			list = Db.find("select id, pid, name from sys_menu m where m.DELETED = 0 order by code");
		}

		return list;
	}

	public List<Sys_Menu> getPermissions() {
		return dao.find("select *, case when TYPE=1 then '菜单' else '按钮' end as ptype from sys_menu where DELETED = 0");
	}

	public List<Sys_Menu> getMenuTree() {
		return dao.find("select * from sys_menu where DELETED = 0 order by pid, id");
	}

	public List<Sys_Menu> getMenus() {
		return dao.find("select * from sys_menu where DELETED = 0 order by CODE ");
	}

}
