package com.blueteam.entity.dto;

/**
 * Created by libra on 2017/4/11.
 * 分页基础搜索
 */
public class BasePageSearch {
    private int pageIndex = 1;
    private int pageSize = 10;
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
