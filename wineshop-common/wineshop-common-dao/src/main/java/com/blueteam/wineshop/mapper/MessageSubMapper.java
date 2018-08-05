package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.MessageSubjectPO;
import com.blueteam.entity.po.MessageTemplatePO;
import com.blueteam.entity.vo.message.MessageVo;
import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 消息处理
 *
 * @author xiaojiang
 * @create 2018-01-25  14:27
 */
@Repository(value="messageSubMapper")
public interface MessageSubMapper {
    /**
     * 方法的功能描述: 获取所有的模板
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/25 14:29
     *@modifier
     */
    List<MessageTemplatePO> listMessageTemplatePO();
    /**
     * 方法的功能描述: 保存消息主题
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/25 15:33
     *@modifier
     */
    int saveMessageSubject(MessageSubjectPO messageSubject);
    /**
     * 方法的功能描述: 获取所有的消息总数
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/26 17:23
     *@modifier
     */
    List<Map<String,Object>> listUnreadMessageCount(@Param("userId") Integer userId);
    /**
     * 方法的功能描述: 获取用户所有未读消息总数
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:02
     *@modifier
     */
    int getUnreadByCount(@Param("userId")Integer userId , @Param("userSource")Integer userSource,@Param("subjectState")Integer subjectState);
    /**
     * 方法的功能描述:获取数据列表
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:15
     *@modifier
     */
    List<MessageVo> listMessage(@Param("userId")Integer userId , @Param("userSource")Integer userSource,
                                @Param("pageIndex")Integer pageIndex,@Param("pageSize")Integer pageSize);
    /**
     * 方法的功能描述: 修改所有未读消息
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:24
     *@modifier
     */
    int updateUnread(@Param("userId")Integer userId , @Param("userSource")Integer userSource);
    /**
     * 方法的功能描述: 获取手机号码
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/1 16:01
     *@modifier
     */
    String getUserPhoneByOrderNo(@Param("orderNo")String orderNo);
    /**
     * 方法的功能描述: 获取商家电话号码和商家名称
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/2 10:58
     *@modifier
     */
    Map<String,Object> getVendorPhoneByVendorId(@Param("vendorId")Integer vendorId);
    /**
     * 方法的功能描述: 根据barcode获取所有的待审核商家列表
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/2 11:53
     *@modifier
     */
    List< Map<String,Object>> listVendorIdByBarCode(@Param("barCode")String barCode);
}
