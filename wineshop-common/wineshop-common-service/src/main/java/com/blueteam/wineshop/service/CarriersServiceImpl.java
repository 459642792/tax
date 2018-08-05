package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.*;
import com.blueteam.wineshop.mapper.CarriersInfoMapper;
import com.blueteam.wineshop.mapper.CityInfoMapper;
import com.blueteam.base.constant.EnabledOrDisabled;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.po.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 运营商服务实现
 */
@Service
public class CarriersServiceImpl implements CarriersService {

    /**
     * 用户服务
     */
    @Autowired
    private UserService userService;

    /**
     * 商家服务
     */
    @Autowired
    private VendorInfoService viService;

    /**
     * 运营商数据访问层
     */
    @Autowired
    private CarriersInfoMapper dao;

    /**
     * 城市服务
     */
    @Autowired
    private CityInfoService cityService;

    /**
     * 城市数据访问
     */
    @Autowired
    private CityInfoMapper cityInfoMapper;

    /**
     * 日志编写
     */
    private static Logger logger = LogManager.getLogger(CarriersServiceImpl.class);


    /**
     * 入驻商家
     * 会先添加用户
     * 然后在添加商户
     *
     * @param phone      电话号码
     * @param vendor     商家信息
     * @param carriersId 运营商用户ID
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public BaseResult enter(String phone, VendorInfo vendor, int carriersId) throws Exception {
//		try {
        //监测是否已经存在改手机号的账号,不存在则注册，存在则提示错误
        UserInfo user = userService.getUser(phone);
        if (user != null)
            return BaseResult.error("已经存在相同的手机账号");

        CarriersInfo carriers = dao.selectByPrimaryKey(carriersId);
        if (carriers == null)
            return BaseResult.error("不存在的运营商");

        UserInfo currentUser = userService.selectByPrimaryKey(carriers.getUserid());

        int userId = userService.addUser(phone, Enums.UserType.Vendor, carriers.getCitycode(), currentUser.getUsername());

        if (userId <= 0)
            return BaseResult.error("添加用户失败");
        //	throw new Exception("添加用户失败");

        //创建店铺信息
        vendor.setUserId(userId);
        vendor.setCityCode(carriers.getCitycode());
        vendor.setCreateBy(currentUser.getUsername());
        vendor.setUpdateBy(currentUser.getUsername());
        //设置为审核
        vendor.setStatus(Enums.VendorStatus.CheckAccess.getValue());
        vendor.setTelephone(phone);
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(now);

        vendor.setAuditStatus(EnabledOrDisabled.DISABLED);
        vendor.setCreateDate(dateString);
        vendor.setUpdateDate(dateString);
        String[] strsArea = vendor.getCityCode().split("\\_", -1);
        CityInfo cityInfo = null;
        String area = "";
        for (int i = 0; i < strsArea.length; i++) {
            switch (i) {
                case 0:
                    cityInfo = cityInfoMapper.selectCityName(strsArea[i]);
                    area += cityInfo != null ? cityInfo.getName() : "";
                    break;
                case 1:
                    cityInfo = cityInfoMapper.selectCityName(strsArea[0] + "_" + strsArea[i]);
                    area += cityInfo != null ? cityInfo.getName() : "";
                    break;
                case 2:
                    cityInfo = cityInfoMapper.selectCityName(vendor.getCityCode());
                    area += cityInfo != null ? cityInfo.getName() : "";
                    break;
                default:
                    break;
            }
        }
        vendor.setTradingArea(area);
        int vendorId = viService.insert(vendor);
        if (vendorId > 0)
            return ApiResult.success(vendorId);
//		    else
//		    	throw new Exception("添加商户失败");

//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		}

        return BaseResult.error("创建店铺信息失败");
    }


    /**
     * 提交商家认证资料
     *
     * @param userId 运营商用户ID
     * @param param  认证信息
     * @return
     */
    public BaseResult authenticateVendor(int userId, VendorInfo param) {
        //校验当前认证的商家是否归属于该运营商管理,以及商家状态和认证状态是否合法
        //TODO:这里暂时不考虑性能问题
        VendorInfo vendor = viService.vendorDetail(param.getId());
        if (vendor == null)
            return BaseResult.error("没有找到对应的商家");
        if (vendor.getStatus() != Enums.VendorStatus.CheckAccess.getValue() || EnabledOrDisabled.ENABLED.equals(vendor.getAuditStatus()) || vendor.getAuthenticationStatus() == Enums.VendorStatus.CheckAccess)
            return BaseResult.error("该商家已经认证通过，或者状态为不可用，请检查");

        CarriersInfo carriers = dao.selectForUser(userId);
        if (carriers == null)
            return BaseResult.error("该用户不是运营商");

        if (carriers.getManagementarea().indexOf(vendor.getCityCode()) == -1)
            return BaseResult.error("该商家不属于该运营商管理,没有权限提交认证资料");

        //进行认证操作
        vendor.setLegalPerson(param.getLegalPerson());
        vendor.setLegalPersonIdCard(param.getLegalPersonIdCard());
        vendor.setIdInHandImage(param.getIdInHandImage());
        vendor.setBusinessLicenseImg(param.getBusinessLicenseImg());
        vendor.setLicenseFileImg(param.getLicenseFileImg());
        vendor.setAuthenticationStatus(Enums.VendorStatus.CheckAccess);
        vendor.setAuthenticationAuditor(userId);
        vendor.setAuthenticatioDate(new Date());
        vendor.setAuditStatus(EnabledOrDisabled.ENABLED);

        int count = viService.updateByPrimaryKey(vendor);
        if (count > 0)
            return BaseResult.success();

        return BaseResult.error("提交认证资料失败");
    }

    /**
     * 根据用户ID 查询运营商信息
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public CarriersInfo selectForUser(int userId) {
        return dao.selectForUser(userId);
    }

    @Override
    public CarriersStatistics getStatistics(int carriersId) {
        return dao.getStatistics(carriersId);
    }


    /**
     * 审核商家认证，加V
     *
     * @param userId     执行认证用户ID
     * @param vendorId   待认证商家ID
     * @param authStatus 认证的目标状态
     * @param reason     认证通过/不通过 的理由
     * @return
     */
    @Override
    public BaseResult authenticate(int userId, int vendorId, Enums.VendorStatus authStatus, String reason) {
        //认证商家，获取运营商管辖的商家明细
        VendorInfo vendor = viService.getCarriersVendorByUserID(userId, vendorId);
        if (vendor == null)
            return BaseResult.error("没有找到对应的商家，或者您没有权限审核该商家!");

        //	    if(StringUtil.IsNullOrEmpty(vendor.getLegalPerson()) || StringUtil.IsNullOrEmpty(vendor.getLegalPersonIdCard()
        //      || StringUtil.IsNullOrEmpty(vendor.getIdInHandImage())
        //	    	|| StringUtil.IsNullOrEmpty(vendor.getBusinessLicenseImg()))
        //	    	return BaseResult.error("该商家认证资料未提供完整");

        if (EnabledOrDisabled.ENABLED.equals(vendor.getAuditStatus()))
            return BaseResult.error("该商家已经认证，请勿重复认证");

        if (vendor.getStatus() != null && vendor.getStatus() != Enums.VendorStatus.CheckAccess.getValue())
            return BaseResult.error("该商家未被平台审核通过,不允许认证");

        if (vendor.getAuthenticationStatus() != null && vendor.getAuthenticationStatus() == Enums.VendorStatus.CheckAccess)
            return BaseResult.error("该商家已经认证(认证状态)，请勿重复认证");

        if (authStatus == null || authStatus == Enums.VendorStatus.Uncheck)
            return BaseResult.error("不提供此种认证方式(值)");

        vendor.setAuthenticationStatus(authStatus);
        vendor.setAuthenticatioDate(new Date());
        vendor.setAuthenticationAuditor(userId);
        vendor.setAuthenticationReason(reason);
        if (authStatus == Enums.VendorStatus.CheckAccess)
            vendor.setAuditStatus(EnabledOrDisabled.ENABLED);

        int result = viService.updateByPrimaryKey(vendor);
        if (result > 0)
            return BaseResult.success();
        return BaseResult.error("认证商家失败");
    }

    /**
     * 根据查询条件分页查询运营商
     *
     * @param search
     * @return
     */
    @Override
    public PageResult<List<CarriersInfo>> selectCarriersByWhere(CarriersSearch search) {
        PageResult<List<CarriersInfo>> result = dao.selectCarriersByWhere(search);
        if (result == null)
            return PageResult.empty();
        return result;
    }

    /**
     * 添加运营商
     *
     * @param carriersInfo
     * @return
     */
    @Transactional
    @Override
    public BaseResult add(CarriersInfo carriersInfo) {
        //监测是否已经存在改手机号的账号,不存在则注册，存在则提示错误
        UserInfo user = userService.getUser(carriersInfo.getPersonPhone());


        String managerArea = carriersInfo.getManagementarea();
        managerArea = managerArea == null ? "" : managerArea;

        if (StringUtil.IsNullOrEmpty(managerArea))
            return BaseResult.error("请选择管理区域");

        for (String area : managerArea.split(",")) {
            Integer id = dao.selectByManagerArea(area);
            if (id != null && id > 0)
                return BaseResult.error("当前区域已经有其他运营商管理");
        }
        int userId = 0;
        if (user != null) {
            userId = user.getId();
            if (!Enums.FlagEnumHelper.HasFlag(user.getUsertypes(), Enums.UserType.Carriers)) {
                int newType = user.getUsertypes() + Enums.UserType.Carriers;
                user.setUsertypes(newType);
                userService.updateByPrimaryKey(user);
            } else {
                return BaseResult.error("已经存在相同的手机账号");
            }
        } else {
            userId = userService.addUser(carriersInfo.getPersonPhone(), carriersInfo.getPersonName(), Enums.UserType.Carriers, carriersInfo.getCitycode(), carriersInfo.getCreateby());
            if (userId <= 0)
                return BaseResult.error("添加用户失败");
        }
        //创建店铺信息
        carriersInfo.setUserid(userId);
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(now);
        carriersInfo.setCreatedate(now);
        carriersInfo.setUpdatedate(now);
        carriersInfo.setStatus(Enums.VendorStatus.CheckAccess.getValue());
        if (carriersInfo.getAddr() == null || carriersInfo.getAddr().isEmpty()) {
            carriersInfo.setAddr(" ");
        }
        int carriersId = dao.insert(carriersInfo);
        if (carriersId > 0)
            return ApiResult.success(carriersId);
        return BaseResult.error("创建运营商失败");
    }

    /**
     * 编辑运营商
     *
     * @param carriersInfo
     * @return
     */
    @Transactional
    @Override
    public BaseResult edit(CarriersInfo carriersInfo) {

        if (carriersInfo.getId() <= 0)
            return BaseResult.error("没有找到运营商");

        CarriersInfo dbinfo = dao.selectByPrimaryKey(carriersInfo.getId());
        if (dbinfo == null)
            return BaseResult.error("没有找到运营商");

        UserInfo user = userService.selectByPrimaryKey(dbinfo.getUserid());
        if (user == null)
            return BaseResult.error("没有找到对应的运营商账号");


        String managerArea = carriersInfo.getManagementarea();
        managerArea = managerArea == null ? "" : managerArea;

        if (StringUtil.IsNullOrEmpty(managerArea))
            return BaseResult.error("请选择管理区域");

        String[] areas = managerArea.split(",");
        for (String area : areas) {
            Integer id = dao.selectByManagerArea(area);
            if (id != null && id > 0 && id != carriersInfo.getId())
                return BaseResult.error("当前区域已经有其他运营商管理");
        }

        //将管理区域设置为当前运营商所在区域 TODO:前期可以这样，后期需要修正
        if (areas != null && areas.length > 0) {
            dbinfo.setCitycode(areas[0]);
        }

        user.setTelephone(carriersInfo.getPersonPhone());
        user.setNickname(carriersInfo.getPersonName());

        if (userService.updateByPrimaryKey(user) <= 0)
            return BaseResult.error("编辑运营商失败");

        dbinfo.setAddr(carriersInfo.getAddr());
        dbinfo.setManagementarea(carriersInfo.getManagementarea());

        Date now = new Date();
        dbinfo.setUpdatedate(now);
        dbinfo.setUpdateby(carriersInfo.getUpdateby());

        int vendorId = dao.updateByPrimaryKey(dbinfo);
        if (vendorId > 0)
            return ApiResult.success(vendorId);

        return BaseResult.error("编辑运营商失败");
    }


    /**
     * 根据运营商ID获取运营商信息
     *
     * @param id 运营商ID
     * @return
     */
    @Override
    public CarriersInfo getCarriersByID(int id) {
        return dao.getCarriersByID(id);
    }

    /**
     * 根据运营商CityCode获取运营商信息
     *
     * @param cityCode 运营商CityCode
     * @return
     */
    @Override
    public CarriersInfo getCarriersByCode(String cityCode) {
        return dao.getCarriersByCode(cityCode);
    }


    /**
     * 根据运营商ID获取运营商信息
     *
     * @param id 运营商ID
     * @return
     */
    public CarriersInfo getCarriersByIDInteger(Integer id) {
        return dao.getCarriersByID(id);
    }

    @Override
    public MessageRecipient getMessageRecipient(Integer carrierId, Integer userId) {
        MessageRecipient recipient = new MessageRecipient();
        recipient.setUserId(userId);
        recipient.setCarriersId(carrierId);
        return recipient;
    }


    /**
     * 获取管理区域的商家接收者信息
     *
     * @param cityCode
     * @return
     */
    @Override
    public MessageRecipient getManagerAreatMessageRecipient(String cityCode) {
        MessageRecipient messageRecipient = new MessageRecipient();
        CarriersInfo carriersInfo = dao.selectCarriersByManagerCityCode(cityCode);
        if (carriersInfo == null)
            return messageRecipient;
        messageRecipient.setUserId(carriersInfo.getUserid());
        messageRecipient.setCarriersId(carriersInfo.getId());
        return messageRecipient;
    }
}
