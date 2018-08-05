package com.blueteam.wineshop.service;

import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.EnabledOrDisabled;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.HttpRequestUtil;
import com.blueteam.base.util.MyBeanUtils;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.mapper.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VendorInfoServiceImpl implements VendorInfoService {

    @Autowired
    VendorInfoMapper vendorInfoMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    AdvertiseInfoMapper advertiseInfoMapper;

    @Autowired
    private CityInfoMapper cityInfoMapper;

    @Autowired
    UserService userService;
    @Autowired
    CarriersInfoMapper carriersInfoMapper;
    
    @Autowired
    private SettlementMapper settlementMapper;

    @Override
    public List<VendorInfo> VendorInfoList(String CityCode) {
        return vendorInfoMapper.VendorInfoList(CityCode);
    }

    @Override
    public List<VendorInfo> VendorInfoLists(String CityCode) {
        return vendorInfoMapper.VendorInfoLists(CityCode);
    }

    /**
     * 发现商家列表
     */
    @Override
    public List<VendorInfo> DiscoverVendorList(String CityCode) {
        return vendorInfoMapper.DiscoverVendorList(CityCode);
    }

    //商家详情
    @Override
    public VendorInfo vendorDetail(int Id) {
        //		Object object  = "ddd";
        //		Id = (Integer)object;
        return vendorInfoMapper.vendorDetail(Id);
    }

    @Override
    public int updateVisit(int Id, int VisitCount) {
        return vendorInfoMapper.updateVisit(Id, VisitCount);
    }

    @Override
    public List<VendorInfo> vendorInfoQuey(String Name) {
        return vendorInfoMapper.vendorInfoQuey(Name);
    }

    /**
     * 查询该区域的商家信息
     */
    @Override
    public List<VendorInfo> quyuList(String Name, String CityCode) {
        return vendorInfoMapper.quyuList(Name, CityCode);
    }

    /**
     * 查询运营商管理区域范围的所有商家
     *
     * @param userId               运营商用户ID
     * @param authenticationStatus 商家审核状态
     * @param auditStatus          是否加V
     * @return
     */
    @Override
    public PageResult<List<VendorInfo>> carriersVendors(Integer userId, Enums.VendorStatus authenticationStatus, String auditStatus, Integer pageIndex, Integer pageSize) {
        PageResult<List<VendorInfo>> list = vendorInfoMapper.carriersVendors(userId, authenticationStatus, auditStatus, pageIndex, pageSize);
        if (list == null)
            list = new PageResult<List<VendorInfo>>();
        return list;
    }

    /**
     * 获取运营商下面 提交了资料等待认证审核的商家
     *
     * @param userId    运营商用户ID
     * @param pageIndex 页码
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<List<VendorInfo>> carriersPreAuthVendors(Integer userId, Integer pageIndex, Integer pageSize) {
        PageResult<List<VendorInfo>> list = vendorInfoMapper.carriersPreAuthVendors(userId, Enums.VendorStatus.CheckAccess, pageIndex, pageSize);
        if (list == null)
            list = new PageResult<List<VendorInfo>>();
        return list;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table VendorInfo
     *
     * @mbg.generated Mon Feb 20 17:40:15 CST 2017
     */
    @Override
    public int insert(VendorInfo record) {
        updateCityInfo(record.getCityCode());
        int count = vendorInfoMapper.insert(record);
        if (count > 0)
            return record.getId();
        return 0;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table VendorInfo
     *
     * @mbg.generated Mon Feb 20 17:40:15 CST 2017
     */
    @Override
    public int updateByPrimaryKey(VendorInfo record) {
        return vendorInfoMapper.updateByPrimaryKey(record);
    }


    /**
     * 获取运营商下面的商家详情
     *
     * @param carrisId 运营商ID
     * @param vendorId 商家ID
     * @return
     */
    @Override
    public VendorInfo getCarriersVendor(int carrisId, int vendorId) {
        return vendorInfoMapper.getCarriersVendor(carrisId, vendorId);
    }

    /**
     * 获取运营商下面的商家详情 根据运营商用户ID
     *
     * @param userId   运营商用户 ID
     * @param vendorId 商家ID
     * @return
     */
    @Override
    public VendorInfo getCarriersVendorByUserID(int userId, int vendorId) {
        return vendorInfoMapper.getCarriersVendorByUserID(userId, vendorId);
    }


    /**
     * 查询运营商下面的商家
     *
     * @param carriersId  运营商ID
     * @param orderField  排序字段
     * @param orderBy     ASC OR DESC
     * @param status      状态
     * @param auditStatus 加V状态
     * @param pageIndex   页码
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<List<VendorInfo>> carriersVendorList(Integer carriersId, String orderField, String orderBy, Enums.VendorStatus status, String auditStatus, Integer pageIndex, Integer pageSize) {
        PageResult<List<VendorInfo>> list = vendorInfoMapper.carriersVendorList(carriersId, orderField, orderBy, status, auditStatus, pageIndex, pageSize);
        if (list == null) {
            list = new PageResult<List<VendorInfo>>();
        }

        if (list.getList() == null)
            list.setList(new ArrayList<VendorInfo>());

        for (int i = 0; i < list.getList().size(); i++) {
            if (list.getList().get(i) == null) {
                list.getList().remove(list.getList().get(i));
                i--;
            }
        }
        return list;
    }

    @Override
    public String updateByModel(VendorInfo model) throws Exception {

        VendorInfo tmpModel = findByUserId(model.getUserId());
        if (tmpModel == null) {
            return "商家不存在";
        }

        //修改全景图
        if (StringUtils.isNotBlank(model.getQjImage())) {
            Integer vendorId = model.getId();
            List<AdvertiseInfo> imglist = advertiseInfoMapper.getImagesByType(vendorId, Constants.CREATE_VENDOR_GENERALVIEW);
            if (imglist.isEmpty()) { //新增全景图
                AdvertiseInfo adModel = new AdvertiseInfo();
                adModel.setImg(model.getQjImage());
                adModel.setTypeCode(Constants.CREATE_VENDOR_GENERALVIEW);
                adModel.setForeignKey(vendorId + "");
                adModel.setEnableFlag(EnabledOrDisabled.ENABLED);
                adModel.setCreateBy(model.getUpdateBy());
                adModel.setCreateDate(model.getUpdateDate());
                advertiseInfoMapper.insert(adModel);
            } else { //修改全景图
                AdvertiseInfo advertiseInfo = imglist.get(0);
                advertiseInfo.setImg(model.getQjImage());
                advertiseInfo.setUpdateBy(model.getUpdateBy());
                advertiseInfo.setUpdateDate(model.getUpdateDate());
                advertiseInfoMapper.updateByPrimaryKey(advertiseInfo);
            }
        }

        //修改基本信息
        String msg = initAndCopyModels(model, tmpModel);
        if (!tmpModel.getImage().isEmpty() && tmpModel.getImage().substring(7, 15).equalsIgnoreCase("fjjh-oss")) {
            String rgb = HttpRequestUtil.httpRequest(tmpModel.getImage() + "?x-oss-process=image/average-hue");
            tmpModel.setImageTone(rgb);
        }
        updateByPrimaryKey(tmpModel);
        return msg;
    }

    private String initAndCopyModels(VendorInfo fromModel, VendorInfo toModel) throws Exception {
        StringBuilder msg = new StringBuilder("");
        final long MS = 30 * 24 * 3600 * 1000L;
        Date currDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(currDate);
        JSONObject newJson = StringUtils.isBlank(toModel.getModify()) ?
                new JSONObject() : JSONObject.fromObject(toModel.getModify());

        //判断店招图片是否可上传
        if (fromModel.getImage() != null) {
            if (newJson.containsKey("Image")) {
                if (null == toModel.getImage() || !fromModel.getImage().equals(toModel.getImage())) {
                    Date imageLastModifyDate = sdf.parse(newJson.getString("Image"));
                    if (currDate.getTime() - imageLastModifyDate.getTime() < MS) {
                        fromModel.setImage(null);
                        msg.append("店招图片一个月只能更换一次，您上次更换时间为: ").append(sdf2.format(imageLastModifyDate));
                    } else {
                        newJson.put("Image", strDate);
                    }
                } else {
                    if (null == toModel.getImage() || toModel.getImage().isEmpty()) {
                        newJson.remove("Image");
                    }
                }
            } else {
                if (null != toModel.getImage() && !toModel.getImage().isEmpty()) {
                    newJson.put("Image", strDate);
                }
            }
        }

        //判断店铺名称是否可修改
        if (fromModel.getName() != null) {
            if (newJson.containsKey("Name")) {
                if (null == toModel.getName() || !fromModel.getName().equals(toModel.getName().trim())) {
                    Date nameLastModifyDate = sdf.parse(newJson.getString("Name"));
                    if (currDate.getTime() - nameLastModifyDate.getTime() < MS) {
                        fromModel.setName(null);
                        msg.append(msg.length() == 0 ? "" : "; ");
                        msg.append("店铺名称一个月只能更换一次，您上次更换时间为: ").append(sdf2.format(nameLastModifyDate));
                    } else {
                        newJson.put("Name", strDate);
                    }
                } else {
                    if (null == toModel.getName() || toModel.getName().isEmpty()) {
                        newJson.remove("Name");
                    }
                }
            } else {
                if (null != toModel.getName() && !toModel.getName().isEmpty()) {
                    newJson.put("Name", strDate);
                }
            }
        }
        MyBeanUtils.copyPropertiesIgnoreNull(fromModel, toModel);
        toModel.setModify(newJson.toString());
        return msg.toString();
    }

    @Override
    public VendorInfo findByUserId(Integer userId) {
        return vendorInfoMapper.findByUserId(userId);
    }

    @Override
    public Map<String, Object> fetchIndexInfo(int vendorId) {
        Map<String, Object> result = new HashMap<String, Object>();
        VendorInfo vendorInfo = vendorDetail(vendorId);
        String authenticationStatus = vendorInfo.getAuthenticationStatus() == null ?
                "" : vendorInfo.getAuthenticationStatus().getValue() + "";
        result.put("name", vendorInfo.getName());
        result.put("image", vendorInfo.getImage());
        result.put("auditStatus", vendorInfo.getAuditStatus());
        result.put("authenticationStatus", authenticationStatus);
        Map<String, Object> tradeMap = orderInfoMapper.getVendorTrade(vendorId);
        //		result.put("moneyAccount", 1000); //账户余额 TODO
        if (tradeMap != null) {
            result.putAll(tradeMap);
            result.put("moneyAccount", result.get("moneyAll")); //账户余额,暂时用累计交易额  TODO
        } else {
            result.put("moneyAll", 0);
            result.put("moneyToday", 0);
            result.put("moneyAccount", 0);
        }
        return result;
    }

    @Override
    public CityInfo updateCityInfo(String code) {
        if (null != code && !code.isEmpty()) {
            CityInfo cityInfo = cityInfoMapper.getCityInfo(code);
            if (null != cityInfo) {
                if (cityInfo.getIsExistVendor().toUpperCase().equals("N")) {
                    cityInfoMapper.updateCityInfo("Y", cityInfo.getCode());
                    if (null != cityInfo.getParentCode() && !cityInfo.getParentCode().toUpperCase().equals("ROOT")) {
                        return updateCityInfo(cityInfo.getParentCode());
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * 根据搜索条件分页查询商家
     *
     * @param search
     * @return
     */
    @Override
    public PageResult<List<VendorInfo>> selectVendorListByWhere(VendorSearch search) {
        PageResult<List<VendorInfo>> result = vendorInfoMapper.selectVendorListByWhere(search);
        if (result == null)
            return PageResult.empty();
        return result;
    }


    /**
     * 管理员添加商家
     * 会先添加用户
     * 然后在添加商户
     * <p>
     * 创建用户 取 vendor.getCreateBy()
     * 用户所在区域 取 vendor.getCityCode()
     *
     * @param phone  电话号码
     * @param vendor 商家信息
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public BaseResult adminAddVendor(String phone, VendorInfo vendor) throws Exception {
//		try {
        //监测是否已经存在改手机号的账号,不存在则注册，存在则提示错误
        UserInfo user = userService.getUser(phone);
        if (user != null)
            return BaseResult.error("已经存在相同的手机账号");


        int userId = userService.addUser(phone, Enums.UserType.Vendor, vendor.getCityCode(), vendor.getCreateBy());

        if (userId <= 0)
            return BaseResult.error("添加用户失败");

        if (StringUtil.IsNullOrEmpty(vendor.getCityCode()))
            return BaseResult.error("请选择城市");

        //创建店铺信息
        vendor.setUserId(userId);
        //设置为审核
        vendor.setStatus(Enums.VendorStatus.CheckAccess.getValue());
        vendor.setTelephone(phone);
        vendor.setLatitude("0");
        vendor.setLongitude("0");
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
        int vendorId = insert(vendor);
        if (vendorId > 0)
            return ApiResult.success(vendorId);
        return BaseResult.error("创建店铺信息失败");
    }

    /**
     * 根据商家ID获取商家信息
     *
     * @param id 商家ID
     * @return
     */
    @Override
    public VendorInfo getVendorById(Integer id) {
        return vendorInfoMapper.getVendorById(id);
    }


    /**
     * 根据区域code获取商家列表
     *
     * @param vendorInfo 城市代码
     * @return
     */
    @Override
    public List<VendorInfo> selectVendorByAreas(VendorInfo vendorInfo) {
        return vendorInfoMapper.selectVendorByAreas(vendorInfo);
    }

    /**
     * @param CityCode
     * @return
     */
    @Override
    public List<VendorInfo> VendorRecommendList(String CityCode) {
        return vendorInfoMapper.VendorRecommendList(CityCode);
    }


    /**
     * 管理员认证商家
     *
     * @param userId 管理员用户ID
     * @param param  认证信息
     * @return
     */
    @Override
    public BaseResult adminAuthenticateVendor(int userId, VendorInfo param) {

        VendorInfo vendor = vendorInfoMapper.vendorDetail(param.getId());
        if (vendor == null)
            return BaseResult.error("没有找到对应的商家");
        if (vendor.getStatus() != Enums.VendorStatus.CheckAccess.getValue() || EnabledOrDisabled.ENABLED.equals(vendor.getAuditStatus()) || vendor.getAuthenticationStatus() == Enums.VendorStatus.CheckAccess)
            return BaseResult.error("该商家已经认证通过，或者状态为不可用，请检查");

        UserInfo user = userService.getCityUserInfo(userId);
        if (!Enums.FlagEnumHelper.HasFlag(user.getUsertypes(), Enums.UserType.Admin))
            return BaseResult.error("您不是管理员");


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

        int count = vendorInfoMapper.updateByPrimaryKey(vendor);
        if (count > 0) {
        	//商家结算信息初始化
        	SettlementPO settlement=new SettlementPO();
        	Date date = new Date();
        	BigDecimal amount = new BigDecimal(0);
    		settlement.setVendorId(vendor.getId());
    		settlement.setVendorName(vendor.getName());
    		settlement.setRegion(vendor.getTradingArea());
    		settlement.setEndTime(date);
    		settlement.setStartTime(date);
    		settlement.setBalancedAmounts(amount);
    		settlement.setAllBalancedAmounts(amount);
    		settlement.setCreateUserId(userId);
    		settlement.setCreateUsername(" ");
    		settlement.setCreateTime(date);
    		settlementMapper.insert(settlement);
        	//初始化商家结算信息
        	return BaseResult.success();
        }

        return BaseResult.error("提交认证资料失败");
    }

    /**
     * 认证商家，加V
     *
     * @param userId     执行认证用户ID
     * @param vendorId   待认证商家ID
     * @param authStatus 认证的目标状态
     * @param reason     认证通过/不通过 的理由
     * @return
     */
    @Override
    public BaseResult adminAuthenticate(int userId, int vendorId, Enums.VendorStatus authStatus, String reason) {
        //认证商家，获取运营商管辖的商家明细
        VendorInfo vendor = vendorInfoMapper.vendorDetail(vendorId);//  vendorInfoDao.getCarriersVendorByUserID(userId, vendorId);
        if (vendor == null)
            return BaseResult.error("没有找到对应的商家，或者您没有权限审核该商家!");


        if (EnabledOrDisabled.ENABLED.equals(vendor.getAuditStatus()))
            return BaseResult.error("该商家已经认证，请勿重复认证");

        if (vendor.getStatus() != null && vendor.getStatus() != Enums.VendorStatus.CheckAccess.getValue())
            return BaseResult.error("该商家未被平台审核通过,不允许认证");

        if (vendor.getAuthenticationStatus() != null && vendor.getAuthenticationStatus() == Enums.VendorStatus.CheckAccess)
            return BaseResult.error("该商家已经认证(认证状态)，请勿重复认证");

        if (authStatus == null || authStatus == Enums.VendorStatus.Uncheck)
            return BaseResult.error("不提供此种认证方式(值)");

        UserInfo user = userService.getCityUserInfo(userId);
        if (!Enums.FlagEnumHelper.HasFlag(user.getUsertypes(), Enums.UserType.Admin))
            return BaseResult.error("您不是管理员");

        vendor.setAuthenticationStatus(authStatus);
        vendor.setAuthenticatioDate(new Date());
        vendor.setAuthenticationAuditor(userId);
        vendor.setAuthenticationReason(reason);
        if (authStatus == Enums.VendorStatus.CheckAccess)
            vendor.setAuditStatus(EnabledOrDisabled.ENABLED);

        int result = vendorInfoMapper.updateByPrimaryKey(vendor);
        if (result > 0)
            return BaseResult.success();
        return BaseResult.error("认证商家失败");
    }

    /**
     * 方法的功能描述:TODO 获取 消息接收者实体
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 15:11
     * @since 1.3.0
     */
    @Override
    public MessageRecipient getMessageRecipient(Integer vendorInfoId) {
        VendorInfo vendorInfo = vendorInfoMapper.vendorDetail(vendorInfoId);
        MessageRecipient m = new MessageRecipient();
        m.setVendorId(vendorInfoId);
        m.setUserId(vendorInfo.getUserId());

        //商家接收不需要运营商ID
//		if (null != vendorInfo){
//				if(null != vendorInfo){
//					Integer id = carriersInfoDao.selectByManagerArea(vendorInfo.getCityCode());
//					m.setCarriersId(null != id ? id : 0);
//				}else {
//					m.setCarriersId(0);
//				}
//		}else{
//			m.setCarriersId(0);
//		}


        return m;
    }


    /**
     * 根据ID获取商家和城市信息
     *
     * @param id
     * @return
     */
    @Override
    public VendorInfo getVendorAndCityById(@Param("id") Integer id) {
        return vendorInfoMapper.getVendorAndCityById(id);
    }


    /**
     * 根据venderid 获取用户昵称和商家名称
     *
     * @param id 商家ID
     * @return
     */
    @Override
    public VendorInfo getVenderAndUserName(Integer id) {
        return vendorInfoMapper.getVenderAndUserName(id);
    }

    /**
     * 方法的功能描述:TODO 根据商家id 获取C端显示商家详情
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/19 15:16
     * @modifier
     * @since 1.4.0
     */
    @Override
    public Map<String, Object> getVendorByDetails(Integer vendorId) {
        return vendorInfoMapper.getVendorByDetails(vendorId);
    }


    /**
     * 企业资质信息保存
     * @param info
     * @return
     */
    @Override
    public int saveAuthentication(VendorInfo info) {
        VendorInfo vi=vendorInfoMapper.getByUserId(info.getUserId());
        if (vi!=null){
            info.setAuthenticationStatus(Enums.VendorStatus.Uncheck);
            info.setId(vi.getId());
            return vendorInfoMapper.updateAuthentication(info);
        }
        return vendorInfoMapper.saveAuthentication(info);
    }

    @Override
    public int updateVendorInfo(VendorInfo info) {
        if (info.getDistrictCode()!=null){
            info.setCityCode(info.getDistrictCode());
            info.setTradingArea(info.getProvinceName()+info.getCityName()+info.getDistrictName());
        }
        return vendorInfoMapper.updateVendor(info);
    }

    @Override
    public VendorInfo getNewVendorById(int id) {
        return vendorInfoMapper.getNewVendorById(id);
    }

    @Override
    @Transactional
    public int increaseOrderStatistics(Integer vendorId, Integer volume, Integer pageViews, Integer salesAmount) {
        return vendorInfoMapper.increaseOrderStatistics(vendorId, volume, pageViews, salesAmount);
    }


    @Override
    public int sendQRCode(Integer vendorId) {
        return vendorInfoMapper.sendQRCode(vendorId);
    }

    @Override
    public VendorInfo getNewVendorByUserId(Integer userId) {
        return vendorInfoMapper.getByUserId(userId);
    }

    @Override
    public void bindPayInfo(Integer vendorId) {
        try {
            vendorInfoMapper.bindPayInfo(vendorId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}