package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.DiscoverMapper;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.Discover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscoverServiceImpl implements DiscoverService {

    @Autowired
    DiscoverMapper discoverMapper;

    /**
     * 发现列表信息
     */
    @Override
    public List<Discover> DiscoverList(String Title, String Type, String IsUser, Integer Status, Integer pageSize, Integer pageIndex) {
        return discoverMapper.DiscoverList(Title, Type, IsUser, Status, pageSize, pageIndex);
    }

    @Override
    public List<Discover> DiscoverCarrList(String VendorType, String Title, String Type, String IsUser, Integer Status, Integer pageSize, Integer pageIndex) {
        return discoverMapper.DiscoverCarrList(VendorType, Title, Type, IsUser, Status, pageSize, pageIndex);
    }

    /**
     * 发现列表总数量条数
     */
    @Override
    public int DiscoverCount(String Title, String Type, String IsUser, Integer Status) {
        return discoverMapper.DiscoverCount(Title, Type, IsUser, Status);
    }

    @Override
    public int DiscoverCarrCount(String VendorType, String Title, String Type, String IsUser, Integer Status) {
        return discoverMapper.DiscoverCarrCount(VendorType, Title, Type, IsUser, Status);
    }

    /**
     * 查询商圈头条信息(C端)
     */
    @Override
    public List<Discover> HeandLineList(String Label, String Type) {
        return discoverMapper.HeandLineList(Label, Type);
    }

    /**
     * 传入城市code查询该区域的发现信息(C端)
     */
    @Override
    public List<Discover> DisCoverHeandList(String Label, String Type, Integer pageSize, Integer pageIndex) {
        return discoverMapper.DisCoverHeandList(Label, Type, pageSize, pageIndex);
    }

    /**
     * 传入城市code查询该区域的发现信息(C端总条数)
     */
    @Override
    public int DisCoverHeandCount(String Label, String Type) {
        return discoverMapper.DisCoverHeandCount(Label, Type);
    }

    /**
     * 运营商
     */
    @Override
    public List<Discover> DisCoverHeandList2(String IsUser, String Label, String Type, Integer pageSize, Integer pageIndex) {
        return discoverMapper.DisCoverHeandList2(IsUser, Label, Type, pageSize, pageIndex);
    }

    /**
     * 运营商
     */
    @Override
    public int DisCoverHeandCount2(String IsUser, String Label, String Type) {
        return discoverMapper.DisCoverHeandCount2(IsUser, Label, Type);
    }

    /**
     * 新增发现资料信息
     */
    @Override
    public int insertDiscover(Discover discover) {
        discoverMapper.insertDiscover(discover);
        return (int) discover.getId();
    }

    /**
     * 查询发现详情
     */
    @Override
    public Discover GetDiscover(int Id) {
        return discoverMapper.GetDiscover(Id);
    }


    /**
     * 查询发现详情
     */
    @Override
    public Discover getDiscoverByInteger(Integer Id) {
        return discoverMapper.GetDiscover(Id);
    }


    /**
     * 修改发现信息
     */
    @Override
    public int updateDiscover(Discover discover) {
        return discoverMapper.updateDiscover(discover);
    }

    /**
     * 修改显示
     */
    @Override
    public int updateDiscoverShow(int Id, String IsShow) {
        return discoverMapper.updateDiscoverShow(Id, IsShow);
    }

    /**
     * 修改状态
     */
    @Override
    public int updateDiscoverStatus(int Id, int Status, String Reason) {
        return discoverMapper.updateDiscoverStatus(Id, Status, Reason);
    }

    /**
     * 修改浏览数
     */
    @Override
    public int updateDiscoverVisits(int Id, int Visits) {
        return discoverMapper.updateDiscoverVisits(Id, Visits);
    }

    /**
     * 修改全部的推荐信息
     */
    @Override
    public int updateAllGroom() {
        return discoverMapper.updateAllGroom();
    }

    /**
     * 修改推荐信息
     */
    @Override
    public int updateGroom(int Id) {
        return discoverMapper.updateGroom(Id);
    }

    /**
     * 查询推荐信息数量
     *
     * @return
     */
    @Override
    public int groomCount() {
        return discoverMapper.groomCount();
    }

    /**
     * 推荐信息列表
     *
     * @return
     */
    @Override
    public List<Discover> lstGroom() {
        return discoverMapper.lstGroom();
    }

    /**
     * 根据发现ID获取发现和运营商信息
     *
     * @param id
     * @return
     */
    public Discover selectDiscoverAndCarriersByDiscoverId(Integer id) {
        return discoverMapper.selectDiscoverAndCarriersByDiscoverId(id);
    }

    /**
     * 根据发现ID获取消息接收人信息
     */
    @Override
    public MessageRecipient getMessageRecipient(Integer discoverId) {
        MessageRecipient objRe = new MessageRecipient();
        if (null == discoverId)
            return objRe;

        Discover discover = selectDiscoverAndCarriersByDiscoverId(discoverId);
        if (discover == null)
            return objRe;

        objRe.setUserId(discover.getUserId());
        objRe.setVendorId(0);
        objRe.setCarriersId(discover.getCarriersId());
        return objRe;
    }
}

