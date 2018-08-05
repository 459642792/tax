package com.blueteam.entity.dto;

import com.blueteam.base.lang.RStr;

/**
 * 基本返回实体：适用于简单信息返回
 */
public class BaseResult {
    //是否成功
    private boolean success;
    //状态码
    private String status;
    //消息提示
    private String message;
    //返回ID:用于消息推送
    private String returnId;

    /**
     * 返回默认成功结果
     *
     * @return
     */
    public static BaseResult success() {
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        result.setMessage("成功");
        result.setStatus("200");
        return result;
    }

    /**
     * 返回自定义状态和消息失败结果
     *
     * @param status  状态码 @NotNull提示方法使用者不能传空值
     * @param message 错误提示消息
     * @return
     */
    public static BaseResult error(String status, String message) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        result.setStatus(RStr.isNull(status) ? "500" : status);
        result.setMessage(message);
        return result;
    }

    /**
     * 返回自定义消息失败结果
     *
     * @param message 提示消息
     * @return
     */
    public static BaseResult error(String message) {
        return error("500", message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }
}


