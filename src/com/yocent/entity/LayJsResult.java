package com.yocent.entity;

import com.jfinal.kit.JsonKit;

/**
 * 返回值
 * @author Guanhp
 *
 */
public class LayJsResult {
	
	/**
	 * 返回状态码
	 */
	private Integer code;
	
	/**
	 * 提示信息
	 */
	private String msg;
	
	/**
	 * 数据数量
	 */
	private Integer count;
	
	/**
	 * 数据
	 */
	private Object data;
	
	public LayJsResult(){}

	public LayJsResult(Integer code, String msg, Integer count, Object data) {
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}
	
	public void setFlag(boolean flag){
		this.code = flag ? 0 : 1;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String toJson(){
		return JsonKit.toJson(this);
	}

	@Override
	public String toString() {
		return "LayJsResult [code=" + code + ", msg=" + msg + ", count=" + count + ", data=" + data + "]";
	}
	
}
