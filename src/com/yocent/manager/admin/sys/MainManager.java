package com.yocent.manager.admin.sys;


import java.util.List;

import com.jfinal.core.Controller;
import com.yocent.model.admin.sys.Sys_Menu;

/**
 * 主页业务层
 * @author Guanhp
 *
 */
public class MainManager {

	public static final MainManager manager = new MainManager();

	public List<Sys_Menu> getMenus(Controller con) {
		List<Sys_Menu> allMenu = Sys_Menu.dao.findAll();
		return allMenu;
	}
	
}