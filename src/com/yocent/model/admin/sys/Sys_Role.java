package com.yocent.model.admin.sys;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Sys_Role extends Model<Sys_Role> {
	
	private static final long serialVersionUID = -8952989364089344262L;
	public static final Sys_Role dao = new Sys_Role();
	
	/**
	 * 返回所有角色信息
	 * @return
	 */
	public List<Sys_Role> findAll() {
		return dao.find("select * from sys_role where DELETED = 0");
	}

}
