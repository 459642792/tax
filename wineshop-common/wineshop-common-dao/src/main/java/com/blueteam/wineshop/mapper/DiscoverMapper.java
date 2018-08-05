package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.Discover;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 发现
 *
 * @author Marx
 */
public interface DiscoverMapper {

    /**
     * 发现管理员列表
     *
     * @param Title
     * @param Type
     * @param IsUser
     * @param Status
     * @return
     */
    List<Discover> DiscoverList(@Param("Title") String Title, @Param("Type") String Type, @Param("IsUser") String IsUser, @Param("Status") Integer Status, @Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex);

    List<Discover> DiscoverCarrList(@Param("VendorType") String VendorType, @Param("Title") String Title, @Param("Type") String Type, @Param("IsUser") String IsUser, @Param("Status") Integer Status, @Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex);

    /**
     * 发现管理园统计
     *
     * @param Title
     * @param Type
     * @param IsUser
     * @param Status
     * @return
     */
    int DiscoverCount(@Param("Title") String Title, @Param("Type") String Type, @Param("IsUser") String IsUser, @Param("Status") Integer Status);

    int DiscoverCarrCount(@Param("VendorType") String VendorType, @Param("Title") String Title, @Param("Type") String Type, @Param("IsUser") String IsUser, @Param("Status") Integer Status);

    /**
     * @param Title
     * @param Type
     * @param IsUser
     * @param Status
     * @param pageSize
     * @param pageIndex
     * @return
     */
    List<Discover> DiscoverList2(@Param("Title") String Title, @Param("Type") String Type, @Param("IsUser") String IsUser, @Param("Status") Integer Status, @Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex);

    int DiscoverCount2(@Param("Title") String Title, @Param("Type") String Type, @Param("IsUser") String IsUser, @Param("Status") Integer Status);

    /**
     * C端查询商圈头条信息
     *
     * @param
     * @return
     */
    List<Discover> HeandLineList(@Param("Label") String Label, @Param("Type") String Type);

    /**
     * 传入城市code查询该区域的发现信息(C端)
     *
     * @param
     * @return
     */
    List<Discover> DisCoverHeandList(@Param("Label") String Label, @Param("Type") String Type, @Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex);

    /**
     * 传入城市code查询该区域的发现信息(C端总条数)
     *
     * @param Label
     * @param Type
     * @return
     */
    int DisCoverHeandCount(@Param("Label") String Label, @Param("Type") String Type);

    /**
     * 运营商
     *
     * @param Label
     * @param Type
     * @param pageSize
     * @param pageIndex
     * @return
     */
    List<Discover> DisCoverHeandList2(@Param("IsUser") String IsUser, @Param("Label") String Label, @Param("Type") String Type, @Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex);

    /**
     * 运营商
     *
     * @param Label
     * @param Type
     * @return
     */
    int DisCoverHeandCount2(@Param("IsUser") String IsUser, @Param("Label") String Label, @Param("Type") String Type);

    /**
     * @param discover
     * @return
     */
    int insertDiscover(Discover discover);

    /**
     * 查询发现详情
     *
     * @param Id
     * @return
     */
    Discover GetDiscover(@Param("Id") int Id);

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
    int updateDiscoverShow(@Param("Id") int Id, @Param("IsShow") String IsShow);

    /**
     * 修改状态
     *
     * @param Id
     * @param Status
     * @return
     */
    int updateDiscoverStatus(@Param("Id") int Id, @Param("Status") int Status, @Param("Reason") String Reason);

    /**
     * 修改浏览数
     *
     * @param Id
     * @param Visits
     * @return
     */
    int updateDiscoverVisits(@Param("Id") int Id, @Param("Visits") int Visits);

    /*
     * 推荐数量
     * @param Groom
     * @return
     */
    int groomCount();

    /**
     * 修改推荐信息
     *
     * @param Id
     * @return
     */
    int updateGroom(@Param("Id") int Id);

    /**
     * 修改全部推荐
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
    Discover selectDiscoverAndCarriersByDiscoverId(@Param("id") Integer id);
}
