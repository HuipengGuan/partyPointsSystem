package com.yocent.action.admin.base;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.yocent.action.manager.base.InformManager;
import com.yocent.action.model.base.Base_Inform;

/**
 * 通知公告控制层
 * @author Guanhp
 *
 */
public class InformController extends Controller {

	public void index(){
		render("index.html");
	}
	
	/**
	 * ajax获取公告列表
	 */
	public void list(){
		Integer page = getParaToInt("page", 1);
		Integer limit = getParaToInt("limit", 10);
		String search = getPara("search", "");
		renderJson(InformManager.manager.getList(page, limit, search, this));
	}
	
	/**
	 * 编辑公告
	 */
	public void edit(){
		setAttr("info", InformManager.manager.getFullInfo(getPara("id", "")));
		render("edit.html");
	}
	
	/**
	 * 保存公告
	 */
	public void save(){
		Base_Inform info = getModel(Base_Inform.class, "info");
		renderJson(InformManager.manager.save(info, this));
	}
	
	/**
	 * 上传插图
	 */
	public void img(){
		List<UploadFile> files = getFiles();
		renderJson(InformManager.manager.saveImg(files.get(0).getFile()));
	}
	
	/**
	 * 删除公告
	 */
	public void delete(){
		String ids = getPara("ids", "");
		renderJson(InformManager.manager.delete(ids, this));
	}
	
}
