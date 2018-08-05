package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.ContinuationMapper;
import com.blueteam.entity.po.Continuation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContinuationServiceImpl implements ContinuationService {

    @Autowired
    ContinuationMapper continuationMapper;

    /**
     * 新增拓展
     */
    @Override
    public int insertContinuation(Continuation continuation) {
        return continuationMapper.insertContinuation(continuation);
    }

    /**
     * 查询拓展信息列表
     *
     * @param ForeignKey
     * @param Type
     * @return
     */
    @Override
    public List<Continuation> ContinuationList(int ForeignKey, String Type) {
        return continuationMapper.ContinuationList(ForeignKey, Type);
    }

    /**
     * 删除拓展的相关信息
     *
     * @return
     */
    @Override
    public int deleteByPrimaryKey(int ForeignKey, String Type) {
        return continuationMapper.deleteByPrimaryKey(ForeignKey, Type);
    }
}

