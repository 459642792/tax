package com.blueteam.wineshop.service;


import com.blueteam.entity.dto.SendMessage;

/**
 * Created by clam on 2017/5/18.
 */
public interface MqService {
    void sendMessage(SendMessage message);
}
