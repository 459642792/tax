package com.blueteam.entity.dto;

import com.blueteam.entity.po.Message;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by clam on 2017/5/18.
 */
public class SendMessage extends Message implements Serializable {
    private HashMap paramters;

    /**
     * controller
     */
    private String controller;

    /**
     * action
     */
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public HashMap getParamters() {
        return paramters;
    }

    public void setParamters(HashMap paramters) {
        this.paramters = paramters;
    }

    public Message toBase() {

        Message msg = new Message();
        msg.setReceivingUserTypes(this.getReceivingUserTypes());
        msg.setSoftApp(this.getSoftApp());
        msg.setTypeCode(this.getTypeCode());
        msg.setCarriersID(this.getCarriersID());
        msg.setPushServerVersion(this.getPushServerVersion());
        msg.setContent(this.getContent());
        msg.setDataKey(this.getDataKey());
        msg.setDataSource(this.getDataSource());
        msg.setDevice_tokens(this.getDevice_tokens());
        msg.setVendorID(this.getVendorID());
        msg.setPlatform(this.getPlatform());
        msg.setSendingTime(this.getSendingTime());
        msg.setRecipients(this.getRecipients());
        msg.setTitle(this.getTitle());
        msg.setReceivingUserTypes(this.getReceivingUserTypes());
        return msg;
    }
}
