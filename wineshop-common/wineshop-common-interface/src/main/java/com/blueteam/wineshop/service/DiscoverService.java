package com.blueteam.wineshop.service;


import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.Discover;

import java.util.List;

/**
 * @author Marx
 * <p>
 * CouponInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface DiscoverService {

    /**
     * @param Title
     * @param Type
     * @param IsUser
     * @param Status
     * @return
     */
    List<Discover> DiscoverList(String Title, String Type, String IsUser, Integer Status, Integer pageSize, Integer pageIndex);

    List<Discover> DiscoverCarrList(String VendorType, String Title, String Type, String IsUser, Integer Status, Integer pageSize, Integer pageIndex);

    /**
     * 发现管理园统计
     *
     * @param Title
     * @param Type
     * @param IsUser
     * @param Status
     * @return
     */
    int DiscoverCount(String Title, String Type, String IsUser, Integer Status);

    int DiscoverCarrCount(String VendorType, String Title, String Type, String IsUser, Integer Status);

    /**
     * C端查询商圈头条信息
     *
     * @param
     * @return
     */
    List<Discover> HeandLineList(String Label, String Type);

    /**
     * 传入城市code查询该区域的发现信息(C端)
     *
     * @param Label
     * @return
     */
    List<Discover> DisCoverHeandList(String Label, String Type, Integer pageSize, Integer pageIndex);

    /**
     * 传入城市code查询该区域的发现信息(C端总条数)
     *
     * @param Label
     * @param Type
     * @return
     */
    int DisCoverHeandCount(String Label, String Type);

    /**
     * 运营商
     *
     * @param Label
     * @param Type
     * @param pageSize
     * @param pageIndex
     * @return
     */
    List<Discover> DisCoverHeandList2(String IsUser, String Label, String Type, Integer pageSize, Integer pageIndex);

    /**
     * 运营商
     *
     * @param Label
     * @param Type
     * @return
     */
    int DisCoverHeandCount2(String IsUser, String Label, String Type);

    /**
     * @param discover
     * @return
     */
    int insertDiscover(Discover discover);

    /**
     * 查询发现详情信息
     *
     * @param Id
     * @return
     */
    Discover GetDiscover(int Id);

    /**
     * 查询发现详情
     */
    Discover getDiscoverByInteger(Integer Id);

    /**
     * 修改发现信息
     *
     * @param discover
     * @return
     */
    int updateDiscover(Discover discover);

    /**
     * 修改显示
     *
     * @param Id
     * @param IsShow
     * @return
     */
    int updateDiscoverShow(int Id, String IsShow);

    /**
     * 修改状态
     *
     * @param Id
     * @param
     * @return
     */
    int updateDiscoverStatus(int Id, int Status, String Reason);

    /**
     * 修改浏览数
     *
     * @param Id
     * @param
     * @return
     */
    int updateDiscoverVisits(int Id, int Visits);

    /**
     * 查询推荐数量
     *
     * @param
     * @return
     */
    int groomCount();

    /**
     * 修改推荐信息
     *
     * @param Id
     * @return
     */
    int updateGroom(int Id);

    /**
     * 修改全部推荐信息
     *
     * @return
     */
    int updateAllGroom();

    /**
     * 推荐信息列表
     *
     * @return
     */
    List<Discover> lstGroom();


    /**
     * 根据发现ID获取发现和运营商信息
     *
     * @param id
     * @return
     */
    Discover selectDiscoverAndCarriersByDiscoverId(Integer id);

    /**
     * 根据发现ID获取消息接收人信息
     */
    MessageRecipient getMessageRecipient(Integer discoverId);
}
