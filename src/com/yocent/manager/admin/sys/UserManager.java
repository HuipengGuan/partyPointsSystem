package com.yocent.manager.admin.sys;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.yocent.entity.LayJsResult;
import com.yocent.model.admin.sys.Sys_User;
import com.yocent.utils.MD5Util;

/**
 * 用户管理业务层
 * @author Guanhp
 *
 */
public class UserManager {

	public static final UserManager manager = new UserManager();

	/**
	 * 获取用户列表
	 * @param page
	 * @param limit
	 * @param search 
	 * @param con
	 * @return
	 */
	public LayJsResult getList(Integer page, Integer limit, String search, Controller con) {
		String sql = "SELECT ID id, NAME name, CODE code, '党员' role, "
				+ "LOGIN_DATE logindate, case when STATUS=1 then '正常' else '停用' end status FROM sys_user "
				+ "where DELETED = 0 "
				+ "and (NAME like '%"+search+"%' or CODE like '%"+search+"%' "
						+ "or STATUS like '%"+search+"%')";
		Long count = Db.queryLong("select count(t.id) from (" + sql + ")t");
		List<Record> data = Db.find(sql
				+ "LIMIT ?,?", limit*(page-1), limit);
		return new LayJsResult(0, "", count.intValue(), data);
	}

	/**
	 * 获取用户信息
	 * @param id 用户ID
	 * @return
	 */
	public Sys_User getFullInfo(String id) {
		return Sys_User.dao.findById(id);
	}

	/**
	 * 保存用户
	 * @param user
	 * @param roleIds
	 * @param con
	 * @return
	 */
	public LayJsResult save(Sys_User user, String[] roleIds, Controller con) {
		LayJsResult result = new LayJsResult();
		boolean flag = Db.tx(new IAtom() {
			boolean save_flag = true;
			@Override
			public boolean run() throws SQLException {
				try {
					
					if(codeIsExit(user.get("CODE", ""), user.get("ID"))){
						result.setMsg("用户名已存在，请修改后重新提交!");
						return false;
					}
					
					if(null == user.get("ID")){//新用户
						user.set("PWD", MD5Util.MD5("123456"))
							.set("STATUS", 1)
							.set("DELETED", 0)
							.save();
					}else{		//修改用户
						user.update();
					}
				} catch (Exception e) {
					result.setMsg("用户保存失败, 请重试!");
					save_flag = false;
					e.printStackTrace();
				}
				return save_flag;
			}
		});
		result.setCode(flag? 0 : 1);
		System.out.println(result);
		return result;
	}

	/**
	 * 判断用户名是否已存在
	 * @param code
	 * @param id
	 * @return
	 */
	protected boolean codeIsExit(String code, Object id) {
		Long countExit = 0L;
		if(null == id){
			countExit = Db.queryLong("select count(ID) from sys_user where CODE = ? and DELETED = 0", code);
		}else{
			countExit = Db.queryLong("select count(ID) from sys_user where CODE = ? and DELETED = 0 and ID <> ?", code, id);
		}
		return countExit > 0;
	}

	/**
	 * 逻辑删除用户
	 * @param ids
	 * @param userController
	 * @return
	 */
	public LayJsResult delete(String ids, Controller con) {
		LayJsResult result = new LayJsResult();
		boolean flag = Db.tx(new IAtom() {
			boolean del_flag = true;
			@Override
			public boolean run() throws SQLException {
				try {
					Db.update("update sys_user set DELETED = 1 where ID in (" + ids + ")");
				} catch (Exception e) {
					result.setMsg("删除失败,请稍候重试!");
					del_flag = false;
					e.printStackTrace();
				}
				return del_flag;
			}
		});
		result.setCode(flag? 0 : 1);
		return result;
	}
	
}
