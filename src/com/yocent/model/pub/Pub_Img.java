package com.yocent.model.pub;

import com.jfinal.plugin.activerecord.Model;

/**
 * 公告资源-图片
 * @author Guanhp
 *
 */
public class Pub_Img extends Model<Pub_Img> {

	private static final long serialVersionUID = 8171321650390023290L;
	
	public static final Pub_Img dao = new Pub_Img();

	public Pub_Img findByCode(String code) {
		return dao.findFirst("select * from pub_img where code = ?", code);
	}

}
