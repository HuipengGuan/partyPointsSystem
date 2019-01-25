package com.yocent.manager.pub;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.yocent.model.pub.Pub_Img;

/**
 * 公共类业务层
 * @author Guanhp
 *
 */
public class PubManager {

	public static final PubManager manager = new PubManager();

	public File getFileByCode(String code) throws IOException {
		Pub_Img img = Pub_Img.dao.findByCode(code);
		File temp = new File("D:/" + img.get("NAME", ""));
		byte[] bys = img.getBytes("IMG");
		FileUtils.writeByteArrayToFile(temp, bys);
		return temp;
	}
	
}
