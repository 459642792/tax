package com.blueteam.entity.vo;

import java.util.List;

/**
 * 用于封装返回给客户端的json数据的对象
 * 
 * @author ycl
 *
 */
public class JsonBean {
	/**
	 * 200 一般操作请求成功,无数据 201 一般获取数据操作成功,获得非数组数据对象data 202 一般数据获取请求,获得无分页数组数据data 203
	 * 一般数据获取请求,获得带分页数组数据data data下包含 count 分页数据统计总量 arr 本页数据 302 响应请求需要进行跳转 根据需求
	 * data中包含跳转的refUrl 为跳转的地址 500 请求数据(操作失败) msg 中包含提示信息. 501 用户需要重新登陆 msg 包含提示信息
	 */
	private int status;// 请求数据响应状态
	private String msg;// 请求响应消息
	private Object data;// 请求相应数据

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public JsonBean(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	/**
	 * 无返回数据的操作成功
	 * 
	 * @return
	 */
	public static JsonBean success() {
		return success("操作成功!");

	}

	/**
	 * 无返回数据的操作成功
	 * 
	 * @return
	 */
	public static JsonBean success(String msg) {
		JsonBean json = new JsonBean(200, msg);
		return json;

	}

	/**
	 * 非数组对象数据返回
	 * 
	 * @return
	 */
	public static JsonBean simpleDataSuccess(Object data) {
		JsonBean json = new JsonBean(201, "获取数据成功!");
		json.setData(data);
		return json;

	}

	/**
	 * 普通数组对象数据返回
	 * 
	 * @return
	 */
	public static JsonBean listDataSuccess(List<?> data) {
		JsonBean json = new JsonBean(202, "获取数据成功!");
		json.setData(data);
		return json;

	}

	/**
	 * 分页数据响应
	 * 
	 * @return
	 */
	public static JsonBean pageDataSuccess(PageUtil data) {
		JsonBean json = new JsonBean(203, "获得数据成功!");
		json.setData(data);
		return json;

	}

	/**
	 * 操作失败
	 * 
	 * @return
	 */
	public static JsonBean fail() {
		return fail("操作失败!");

	}

	public static JsonBean fail(String msg) {
		JsonBean json = new JsonBean(500, msg);
		return json;
	}

	/**
	 * 用户认证失败
	 * 
	 * @return
	 */
	public static JsonBean reLogin() {
		JsonBean json = new JsonBean(501, "用户认证失败,请重新登录!");
		return json;

	}

}
