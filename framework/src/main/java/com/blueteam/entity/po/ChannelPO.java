
package com.blueteam.entity.po;

/**
 * 渠道信息表(TF_M_CHANNEL)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class ChannelPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 8493360393238645938L;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道详情
     */
    private String channelDetail;

    /**
     * 渠道地址
     */
    private String channelAddress;

    /**
     * 获取渠道id
     *
     * @return 渠道id
     */
    public Integer getChannelId() {
        return this.channelId;
    }

    /**
     * 设置渠道id
     *
     * @param channelId 渠道id
     */
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取渠道名称
     *
     * @return 渠道名称
     */
    public String getChannelName() {
        return this.channelName;
    }

    /**
     * 设置渠道名称
     *
     * @param channelName 渠道名称
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 获取渠道详情
     *
     * @return 渠道详情
     */
    public String getChannelDetail() {
        return this.channelDetail;
    }

    /**
     * 设置渠道详情
     *
     * @param channelDetail 渠道详情
     */
    public void setChannelDetail(String channelDetail) {
        this.channelDetail = channelDetail;
    }

    /**
     * 获取渠道地址
     *
     * @return 渠道地址
     */
    public String getChannelAddress() {
        return this.channelAddress;
    }

    /**
     * 设置渠道地址
     *
     * @param channelAddress 渠道地址
     */
    public void setChannelAddress(String channelAddress) {
        this.channelAddress = channelAddress;
    }
}