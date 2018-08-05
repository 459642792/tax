package com.blueteam.entity.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 * 
 * @author Administrator
 *
 */
public class PageUtil {
	private int count;//分页数据总量
	private List<?> arr;//本页数据

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<?> getArr() {
		return arr;
	}

	public void setArr(List<?> arr) {
		this.arr = arr;
	}

	public PageUtil(int count) {
		this.count = count;
	}

	public PageUtil(int count, List<?> arr) {
		this.count = count;
		this.arr = arr;
	}

	/**
	 * 空数据对象
	 * @return
	 */
	public static PageUtil emptyData() {
		return new PageUtil(0, new ArrayList<>());

	}

	/**
	 * 获得数据量为count 的pageUtil对象
	 * @param count
	 * @return
	 */
	public static PageUtil getPageByCount(int count) {
		return new PageUtil(count);
	}
}
