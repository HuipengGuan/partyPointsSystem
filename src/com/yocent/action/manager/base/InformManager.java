package com.yocent.action.manager.base;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.yocent.action.model.base.Base_Inform;
import com.yocent.entity.LayJsResult;
import com.yocent.model.pub.Pub_Img;
import com.yocent.utils.DateUtil;

/**
 * 通知公告业务层
 * @author Guanhp
 *
 */
public class InformManager {

	public static final InformManager manager = new InformManager();

	/**
	 * 获取公告数据
	 * @param page 页码
	 * @param limit 记录数
	 * @param search 过滤条件
	 * @param con 类对象
	 * @return
	 */
	public LayJsResult getList(Integer page, Integer limit, String search, Controller con) {
		String sql = "select id, title, content, modifi_date from base_inform where DELETED = 0 "
				+ " and (title like '%"+search+"%' or content like '%"+search+"%') ";
		Long count = Db.queryLong("select count(t.id) from ( " + sql + " )t");
		List<Record> data = Db.find(sql
				+ " order by modifi_date desc "
				+ " LIMIT ?,? ", limit*(page-1), limit);
		return new LayJsResult(0, "", count.intValue(), data);
	}

	/**
	 * 保存公告
	 * @param info 公告对象
	 * @param con 类对象
	 * @return
	 */
	public LayJsResult save(Base_Inform info, Controller con) {
		LayJsResult result = new LayJsResult();
		result.setFlag(Db.tx(new IAtom() {
			boolean save_flag = true;
			@Override
			public boolean run() throws SQLException {
				try {
					if(null == info.get("ID")){	//新增
						info.set("DELETED", 0)
							.set("MODIFI_DATE", DateUtil.getSqlDate(new Date()))
							.save();
					}else{						//修改
						info.update();
					}
				} catch (Exception e) {
					result.setMsg("保存公告失败,请稍后再试!");
					save_flag = false;
					e.printStackTrace();
				}
				return save_flag;
			}
		}));
		return result;
	}

	/**
	 * 获取公告对象
	 * @param id
	 * @return
	 */
	public Base_Inform getFullInfo(String id) {
		return Base_Inform.dao.findById(id);
	}

	public LayJsResult saveImg(File img) {
		LayJsResult result = new LayJsResult();
		result.setFlag(Db.tx(new IAtom() {
			boolean save_flag = true;
			@Override
			public boolean run() throws SQLException {
				try {
					Pub_Img pubImg = new Pub_Img();
					Long code = new Date().getTime();
					pubImg.set("NAME", img.getName())
						  .set("CODE", code)
						  .set("IMG", FileUtils.readFileToByteArray(img))
						  .save();
					Map<String, String> data = new HashMap<>();
					data.put("src", "/pub/img?code=" + code);
					data.put("title", img.getName());
					result.setData(data);
				} catch (Exception e) {
					result.setMsg("图片上传失败,请重试!");
					save_flag = false;
					e.printStackTrace();
				}
				return save_flag;
			}
		}));
		return result;
	}

	/**
	 * 删除公告
	 * @param ids
	 * @param informController
	 * @return
	 */
	public String[] delete(String ids, Controller con) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
