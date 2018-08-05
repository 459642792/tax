package com.blueteam.wineshop.service;


import com.blueteam.entity.po.Continuation;

import java.util.List;

/**
 * 拓展
 *
 * @author Marx
 * <p>
 * CouponInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface ContinuationService {

    /**
     * 新增拓展信息
     *
     * @param
     * @return
     */
    int insertContinuation(Continuation continuation);

    /**
     * 查询拓展信息列表
     *
     * @param ForeignKey
     * @param Type
     * @return
     */
    List<Continuation> ContinuationList(int ForeignKey, String Type);

    /**
     * 删除拓展的相关信息
     *
     * @param
     * @return
     */
    int deleteByPrimaryKey(int ForeignKey, String Type);
}
