package com.blueteam.entity.dto;


import com.blueteam.entity.po.Message;

import java.util.List;

/**
 * Created by libra on 2017/5/23.
 * <p>
 * 消息搜索
 */
public class MessageSearch extends Message {
    /**
     * 搜索的类型编码
     */
    private List<String> searchTypes;

    private int pageIndex;

    private int pageSize;


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

    public List<String> getSearchTypes() {
        return searchTypes;
    }

    public void setSearchTypes(List<String> searchTypes) {
        this.searchTypes = searchTypes;
    }
}
