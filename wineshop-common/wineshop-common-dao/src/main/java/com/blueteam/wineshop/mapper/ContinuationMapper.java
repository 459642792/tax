package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.Continuation;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 拓展
 *
 * @author Marx
 */
public interface ContinuationMapper {

    /**
     * 新增拓展信息
     *
     * @param
     * @return
     */

    int insertContinuation(Continuation continuation);

    /**
     * 查询拓展信息list信息
     *
     * @param ForeignKey
     * @param Type
     * @return
     */

    List<Continuation> ContinuationList(@Param("ForeignKey") int ForeignKey, @Param("Type") String Type);


    /**
     * 删除相关信息
     *
     * @param ForeignKey
     * @param Type
     * @return
     */
    int deleteByPrimaryKey(@Param("ForeignKey") int ForeignKey, @Param("Type") String Type);
}
