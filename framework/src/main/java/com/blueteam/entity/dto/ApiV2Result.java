package com.blueteam.entity.dto;

/**
 * 调用原来C#接口的返回实体
 *
 * @author libra
 */
public class ApiV2Result<E> {
    /**
     * 请求是否成功
     */
    private boolean IsSucceed;

    /**
     * 总数量
     */
    private int TotalCount;

    /**
     * 消息
     */
    private String Msg;

    /**
     * 返回的结果的数据部分
     */
    private E Data;

    /**
     * 状态
     */
    private Integer Status;


    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public boolean isIsSucceed() {
        return IsSucceed;
    }

    public void setIsSucceed(boolean isSucceed) {
        IsSucceed = isSucceed;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public E getData() {
        return Data;
    }

    public void setData(E data) {
        Data = data;
    }
}
