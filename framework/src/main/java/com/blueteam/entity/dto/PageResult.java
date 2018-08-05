package com.blueteam.entity.dto;

import java.io.Serializable;

/**
 * 分页结果 实体
 *
 * @author libra
 */
public class PageResult<E> implements Serializable {
    /**
     * 总数量
     */
    private int count;
    /**
     * 数据结果
     */
    private E list;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public E getList() {
        return list;
    }

    public void setList(E list) {
        this.list = list;
    }

    public static <T> PageResult<T> empty() {
        PageResult<T> result = new PageResult<T>();
        result.setList(null);
        result.setCount(0);
        return result;
    }
}
