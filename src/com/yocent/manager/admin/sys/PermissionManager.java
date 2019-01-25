package com.yocent.manager.admin.sys;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.yocent.entity.LayJsResult;
import com.yocent.model.admin.sys.Sys_Menu;

public class PermissionManager {
	public static final PermissionManager manager = new PermissionManager();

	public List<Sys_Menu> getPermissions() {
		return Sys_Menu.dao.getPermissions();
	}

	public List<Map<String, Object>> arrangeTree() {
		List<Sys_Menu> list = Sys_Menu.dao.getMenuTree();
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for(Sys_Menu m : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			if("0".equals(m.get("PID").toString())) {
				map.put("ID", m.get("ID").toString());
				map.put("CODE", m.get("CODE").toString());
				map.put("NAME", m.get("NAME").toString());
				map.put("URL", m.get("URL", "").toString());
				map.put("TYPE", m.get("TYPE", "").toString());
				map.put("DESCRIPTION", m.get("DESCRIPTION", "").toString());
				if("0".equals(m.get("PID").toString())) {
					map.put("childs", arrangeChilds(list, m.get("ID").toString()));
				}
				ret.add(map);
			} else {
				break;
			}
		}
		return ret;
	}

	public List<Map<String, Object>> arrangeChilds(List<Sys_Menu> list, String id)
	  {
		List<Map<String, Object>> retChilds = new ArrayList<Map<String, Object>>();
		for(Sys_Menu m : list) {
			if(id.equals(m.get("PID").toString())) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ID", m.get("ID").toString());
		        map.put("CODE", m.get("CODE").toString());
		        map.put("NAME", m.get("NAME").toString());
		        map.put("URL", m.get("URL", "").toString());
		        map.put("TYPE", m.get("TYPE", "").toString());
		        map.put("DESCRIPTION", m.get("DESCRIPTION", "").toString());
				//二级以上菜单需移除下行注释
				map.put("childs", arrangeChilds(list, m.get("ID").toString()));
				retChilds.add(map);
			} else if(retChilds.size() > 0) {
				break;
			}
		}
		return retChilds;
	  }

	public LayJsResult delete(String ids, Controller con) {
		LayJsResult result = new LayJsResult();
		boolean flag = Db.tx(new IAtom() {
			boolean del_flag;

			public boolean run() throws SQLException {
				try {
					Db.update("update sys_menu set DELETED = 1 where ID in (" + ids + ")");
				} catch (Exception e) {
					result.setMsg("删除权限失败,请稍后再试!");
					del_flag = false;
					e.printStackTrace();
				}
				return del_flag;
			}

		});
		result.setCode(Integer.valueOf((flag) ? 0 : 1));
		return result;
	}

	public List<Sys_Menu> getMenus() {
		return Sys_Menu.dao.getMenus();
	}

	/**
	 * 获取菜单详情
	 * @param para
	 * @return
	 */
	public Sys_Menu getFullMenu(String id) {
		return Sys_Menu.dao.findById(id);
	}

	public LayJsResult save(Sys_Menu menu) {
		LayJsResult result = new LayJsResult();
		result.setFlag(Db.tx(new IAtom() {
			boolean save_flag = true;
			@Override
			public boolean run() throws SQLException {
				try {
					if(null == menu.get("ID")){	//新增菜单
						Integer pid = menu.getInt("PID");
						Integer id = Db.queryInt("select max(ID) from sys_menu where PID = ?", pid);
						menu.set("ID", null!=id? (id + 1) : (pid * 10 + 1))
							.set("DELETED", 0)
							.save();
					}else{
						menu.update();
					}
				} catch (Exception e) {
					result.setMsg("保存权限失败,请稍后再试!");
					save_flag = false;
					e.printStackTrace();
				}
				return save_flag;
			}
		}));
		return result;
	}
}
