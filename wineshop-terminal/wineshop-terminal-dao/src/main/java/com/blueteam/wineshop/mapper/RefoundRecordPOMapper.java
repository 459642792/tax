package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.RefoundRecordPO;

public interface RefoundRecordPOMapper {
    int deleteByPrimaryKey(Integer refoundRecordId);

    int insert(RefoundRecordPO record);

    int insertSelective(RefoundRecordPO record);

    RefoundRecordPO selectByPrimaryKey(Integer refoundRecordId);

    int updateByPrimaryKeySelective(RefoundRecordPO record);

    int updateByPrimaryKey(RefoundRecordPO record);
}