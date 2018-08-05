package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.LogMapper;
import com.blueteam.entity.po.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper dao;

    @Override
    public int insert(Log record) {
        return dao.insert(record);
    }

}
