package com.blueteam.entity.vo;

import java.util.Date;

/**
 * 订单节点时间
 *
 * @author xiaojiang
 * @create 2018-01-18  9:44
 */
public class OrderNodeTime {
    private String nodeName;
    private Date nodeTime;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Date getNodeTime() {
        return nodeTime;
    }

    public void setNodeTime(Date nodeTime) {
        this.nodeTime = nodeTime;
    }

    @Override
    public String toString() {
        return "OrderNodeTime{" +
                "nodeName='" + nodeName + '\'' +
                ", nodeTime=" + nodeTime +
                '}';
    }

    public OrderNodeTime() {
    }

    public OrderNodeTime(String nodeName, Date nodeTime) {
        this.nodeName = nodeName;
        this.nodeTime = nodeTime;
    }
}
