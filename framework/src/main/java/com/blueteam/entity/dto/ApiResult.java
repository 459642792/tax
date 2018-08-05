package com.blueteam.entity.dto;

/**
 * 复杂数据返回实体
 *
 * @param <E>
 */
public class ApiResult<E> extends BaseResult {
    //数据
    private E data;
    //数量
    private Integer count;
    //拓展信息
    private String extendMessage;
    //拓展状态
    private String statusMessage;

    /**
     * 返回带数据的成功结果
     *
     * @param data 数据
     * @param <E>
     * @return
     */
    public static <E> ApiResult<E> success(E data) {
        ApiResult<E> result = new ApiResult<>();
        result.setSuccess(true);
        result.setMessage("成功");
        result.setStatus("200");
        result.setData(data);
        return result;
    }

    /**
     * 返回带数据和数量的成功结果
     *
     * @param data  数据
     * @param count 数量
     * @param <E>
     * @return
     */
    public static <E> ApiResult<E> success(E data, int count) {
        ApiResult<E> result = new ApiResult<>();
        result.setSuccess(true);
        result.setMessage("成功");
        result.setStatus("200");
        result.setData(data);
        result.setCount(count);
        return result;
    }

    /**
     * 返回带消息、数量、拓展信息的成功结果
     *
     * @return
     */

    /**
     * 返回带数据、数量、拓展信息的成功结果
     *
     * @param data          数据
     * @param count         数量
     * @param extendMessage 拓展信息
     * @param <E>
     * @return
     */
    public static <E> ApiResult<E> success(E data, int count, String extendMessage) {
        ApiResult<E> result = new ApiResult<>();
        result.setSuccess(true);
        result.setMessage("成功");
        result.setStatus("200");
        result.setData(data);
        result.setExtendMessage(extendMessage);
        result.setCount(count);
        return result;
    }

    /**
     * 返回带数据、数量、拓展信息、拓展状态的成功结果
     *
     * @param data
     * @param count
     * @param extendMessage
     * @param statusMessage
     * @param <E>
     * @return
     */
    public static <E> ApiResult<E> success(E data, int count, String extendMessage, String statusMessage) {
        ApiResult<E> result = new ApiResult<>();
        result.setSuccess(true);
        result.setMessage("成功");
        result.setStatus("200");
        result.setData(data);
        result.setExtendMessage(extendMessage);
        result.setStatusMessage(statusMessage);
        result.setCount(count);
        return result;
    }

    /**
     * 返回带数据、消息的失败结果
     *
     * @param data    数据
     * @param message 消息
     * @param <E>
     * @return
     */
    public static <E> ApiResult<E> error(E data, String message) {
        if (data == null) {
            return (ApiResult<E>) error(message);
        }
        return error(data, "500", message);
    }

    /**
     * 返回带数据、状态、消息的失败结果
     *
     * @param data    数据
     * @param status  状态
     * @param message 消息
     * @param <E>
     * @return
     */
    public static <E> ApiResult<E> error(E data, String status, String message) {
        ApiResult<E> result = new ApiResult<>();
        result.setSuccess(false);
        result.setStatus(status);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getExtendMessage() {
        return extendMessage;
    }

    public void setExtendMessage(String extendMessage) {
        this.extendMessage = extendMessage;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

}
