package com.blueteam.wineshop.service;

import com.blueteam.base.util.AES;
import com.blueteam.base.util.MD5Util;
import com.blueteam.base.util.json.EncryptUtil;
import com.blueteam.wineshop.mapper.ThirdPartyUserInfoMapper;
import com.blueteam.wineshop.mapper.UserInfoMapper;
import com.blueteam.wineshop.mapper.VendorInfoMapper;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.StringUtil;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.base.util.json.PasswordUtil;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.po.ThirdPartyUserInfo;
import com.blueteam.entity.po.UserInfo;
import com.blueteam.entity.po.VendorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserInfoMapper userDao;

    @Autowired
    VendorInfoMapper vendorDao;

    @Autowired
    ThirdPartyUserInfoMapper thirdPartyUserInfoMapper;
    @Autowired
    UserService userService;

//

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    @Override
    public UserInfo selectByPrimaryKey(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     * 根据UserId获取用户信息，包括城市中文名称
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    public UserInfo getCityUserInfo(Integer id) {
        return userDao.getCityUserInfo(id);
    }

    /**
     * 根据wxopenid获取用户信息，包括城市中文名称
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    public UserInfo getUserInfo(String wxopenid) {
        return userDao.getUserInfo(wxopenid);
    }

    /**
     * 编辑用户信息
     * This method corresponds to the database table UserInfo
     *
     * @mbg.generated Mon Feb 20 11:11:38 CST 2017
     */
    @Override
    public int updateByPrimaryKey(UserInfo record) {
        record.setUpdatedate(new Date());
        return userDao.updateByPrimaryKey(record);
    }

    @Override
    public int updateUser(UserInfo record) {
        record.setUpdatedate(new Date());
        return userDao.updateUser(record);
    }


    /**
     * 新增用户
     */
    @Override
    public int insert(UserInfo record) {
        return userDao.insert(record);
    }

    /**
     * 根据账号获取用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    @Override
    public UserInfo getUser(String account) {
        //查询数据库是否存在,不存在则添加，存在则登录成功
        UserInfo where = new UserInfo();
        where.setUsername(account);

        List<UserInfo> users = userDao.selectByWhere(where);
        if (users.size() > 0)
            return users.get(0);
        return null;
    }

    @Override
    public int addUser(String phone, int userType, String cityCode, String createBy) {
        UserInfo user = new UserInfo();
        user.setUsername(phone);
        user.setCitycode(cityCode);
        user.setUsertypes(userType);
        user.setTelephone(phone);
        user.setNickname(phone);
        user.setIslock(Enums.EnumUserIsLock.No);
        user.setCreateby(createBy);
        user.setUpdateby(createBy);
        Date now = new Date();
        user.setCreatedate(now);
        user.setUpdatedate(now);
        userDao.insert(user);
        return user.getId();
    }

    @Override
    public int addUser(String phone, String nikeName, int userType, String cityCode, String createBy) {
        UserInfo user = new UserInfo();
        user.setUsername(phone);
        user.setCitycode(cityCode);
        user.setUsertypes(userType);
        user.setTelephone(phone);
        user.setNickname(nikeName);
        user.setIslock(Enums.EnumUserIsLock.No);
        user.setCreateby(createBy);
        user.setUpdateby(createBy);
        Date now = new Date();
        user.setCreatedate(now);
        user.setUpdatedate(now);
        userDao.insert(user);
        return user.getId();
    }


    /**
     * 运营商登录
     *
     * @param account 账号 必须是电话号码
     * @return
     */
    @Override
    public BaseResult loginByPhone(String account, String loginIp, String dataSource) {
        //查询数据库是否存在,不存在则添加，存在则登录成功
        UserInfo where = new UserInfo();
        where.setTelephone(account);
        where.setIslock(Enums.EnumUserIsLock.No);
        //where.setUsertypes(Enums.UserType.Carriers);
        List<UserInfo> users = userDao.selectByWhere(where);
        if (users.size() > 1)
            return ApiResult.error("错误的账号");
        if (users.size() == 0)
            return ApiResult.error("账户不存在");
        UserInfo us = users.get(0);
        Date now = new Date();
        us.setLogintime(now);
        userDao.updateByPrimaryKey(us);
        return ApiResult.success(us);

//    	if(users.size()>1)
//    		return ApiResult.error(null,"账号不唯一，数据错乱");
//
//    	Date now = new Date();
//        if(users.size() < 1)
//        {
//        	UserInfo user = new UserInfo();
//
//        	user.setUpdateby(account);
//        	user.setCreateby(account);
//        	user.setUsername(account);
//        	user.setTelephone(account);
//        	user.setCreatedate(now);
//        	user.setDatasource(dataSource);
//        	user.setIslock(EnumUserIsLock.No);
//        	user.setLogintime(now);
//        	user.setUpdatedate(now);
//        	user.setUsertypes(UserType.Carriers);
//        	user.setRegisterip(loginIp);
//        	int kv = userDao.insert(user);
//        	return ApiResult.success(user);
//        }else{
//        	UserInfo us = users.get(0);
//            us.setLogintime(now);
//            userDao.updateByPrimaryKey(us);
//            return ApiResult.success(us);
//        }
    }

    @Override
    public BaseResult login4Vendors(String phone, String code) {
        phone = phone.trim();
        code = code.trim();

        if (phone.isEmpty() || code.isEmpty())
            return ApiResult.error("参数错误");

//        if (!VerificationUtil.VerificationCode(session, phone, code)) {
//            return BaseResult.error("错误的验证码");
//        }

        //查询用户
        UserInfo exampleUser = new UserInfo();
        exampleUser.setTelephone(phone);
        List<UserInfo> users = userDao.selectByWhere(exampleUser);
        if (users.size() > 1) {
            return BaseResult.error("电话号码有重复");
        }
        UserInfo user = users.size() == 0 ? null : users.get(0);
        if (user != null) {
            if (!Enums.FlagEnumHelper.HasFlag(user.getUsertypes(), Enums.UserType.Vendor)) {
                int newType = user.getUsertypes() + Enums.UserType.Vendor;
                user.setUsertypes(newType);
                userDao.updateByPrimaryKey(user);
            }
        }

        //若用户不存在，则自动创建一个。
        boolean hasVendorInfo = false;
        VendorInfo vendor = null;
        LoginResult result = new LoginResult();
        if (user == null) {
            int newId = addUser(phone, Enums.UserType.Vendor, null, phone);
            user = userDao.selectByPrimaryKey(newId);
            result.setVendorInfoId(null);
        } else {
            vendor = vendorDao.findByUserId(user.getId());
            result.setVendorInfoId(null != vendor ? vendor.getId() : 0);
			if(null != vendor){
				result.setVendorInfoId(vendor.getId());
				if (vendor.getIsOpen()==1) {
                    hasVendorInfo =true;
                }
			}

        }
        if (vendor!=null){
            result.setAuthenticationStatus(vendor.getAuthenticationStatus().getValue());
        }else {
            result.setAuthenticationStatus(0);
        }
        result.setAccount(user.getUsername());
        result.setUserId(user.getId());
        result.setUser(user);
        result.setToken(VerificationUtil.getToken(user, (vendor!=null) ? vendor.getId() : 0, Enums.UserType.Vendor));
        result.setHasVendorInfo(hasVendorInfo);
        return ApiResult.success(result);
    }


    /**
     * 根据条件查询用户
     *
     * @param search 查询条件
     * @return
     */
    public List<UserInfo> selectByWhere(UserInfo search) {
        return userDao.selectByWhere(search);
    }

    /**
     * 搜索用户信息，包含统计信息,交易次数，和交易总额
     *
     * @param search 搜索信息
     * @return
     */
    @Override
    public PageResult<List<UserInfo>> selectUserStaticsByWhere(UserInfoSearch search) {
        PageResult<List<UserInfo>> result = userDao.selectUserStaticsByWhere(search);
        if (result == null)
            return PageResult.empty();
        List<UserInfo> list = result.getList();
        List<UserInfo> lists = new ArrayList<>();
        if (null != list && list.size() != 0) {
            for (UserInfo userInfo : list) {
                List<ThirdPartyUserInfo> listT = thirdPartyUserInfoMapper.listThirdPartyUserInfo(userInfo.getId(), Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
                if (null != listT && listT.size() != 0) {
                    userInfo.setWxopenid(listT.get(0).getThirdPartyId());
                    userInfo.setNickname(listT.get(0).getThirdPartyNickName());
                    userInfo.setHeadimage(listT.get(0).getThirdPartyHeadImage());
                } else {
                    userInfo.setWxopenid("");
                    userInfo.setNickname("");
                    userInfo.setHeadimage("");
                }
                lists.add(userInfo);
            }
            result.setList(lists);
        } else {
            return PageResult.empty();
        }
        return result;
    }

    /**
     * 用户密码登录
     *
     * @param userName 用户账号
     * @param password 用户密码
     * @return
     */
    @Override
    public BaseResult pwdLogin(String userName, String password) {
        if (StringUtil.IsNullOrEmpty(userName) || StringUtil.IsNullOrEmpty(password))
            return BaseResult.error("请输入正确的用户名和密码");

        //UserInfo search = new UserInfo();
        //search.setUsername(userName);
        UserInfo user = userDao.getUserInfoByUserName(userName);
        if (user == null)
            return BaseResult.error("用户名或密码错误");
        String pwd = PasswordUtil.encryptPassword(password, user.getPasswordSalt());
        if (StringUtil.IsNullOrEmpty(pwd) || StringUtil.IsNullOrEmpty(user.getUserPwd()))
            return BaseResult.error("用户名或密码错误");
        if (pwd.equals(user.getUserPwd()))
            return ApiResult.success(user);

        return BaseResult.error("用户名或密码错误");
    }

    /**
     * 获取管理平台接受者
     *
     * @return
     */
    @Override
    public MessageRecipient getAdminRecipient() {
        MessageRecipient mr = new MessageRecipient();
        mr.setUserType(Enums.UserType.Admin);
        return mr;
    }


    /**
     * 根据商家微信公众号openid 获取 用户
     *
     * @param vendorWxOpenId 微信 openid
     * @return
     */
    @Override
    public UserInfo getUserInfoByVendorOpenid(String vendorWxOpenId) {
        return userDao.getUserInfoByVendorOpenid(vendorWxOpenId);
    }

    /**
     * 方法的功能描述:TODO  根据电话号码获取userinfo
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/7/15 16:20
     * @since 1.3.0
     */
    @Override
    public synchronized UserInfo getUserInfoByTelePhone(String telephone, Integer status, Integer thirdStatus, Integer bindStatus) {
        return userDao.getUserInfoByTelePhone(telephone, status, thirdStatus, bindStatus);
    }

    @Override
    /**
     * 方法的功能描述:TODO 根据第三方标示 和 状态获取唯一的userinfo数据
     *@methodName listThirdPartyUserInfo
     * @param: thirdPartyId
     * @param: status 来源
     * @param: thirdStatus 标示类型
     *@return base.model.UserInfo
     *@since 1.4.0
     *@author xiaojiang 2017/8/29 9:38
     *@modifier
     */
    public UserInfo getThirdPartyUserInfo(String thirdPartyId, Integer status, Integer thirdStatus, Integer bindStatus) {
        return userDao.getThirdPartyUserInfo(thirdPartyId, status, thirdStatus, bindStatus);
    }


    /**
     * 商家端，密码登录
     * @param telephone
     * @param password
     * @param code
     * @return
     */
    @Override
    public BaseResult loginByPwd(String telephone, String password, String code) {
        telephone = telephone.trim();

        if (telephone.isEmpty() || password.isEmpty())
            return ApiResult.error("参数错误");

        //查询用户
        UserInfo exampleUser = new UserInfo();
        exampleUser.setTelephone(telephone);
        List<UserInfo> users = userDao.selectByWhere(exampleUser);
        if (users==null||users.size()==0){
            return BaseResult.error("账号不存在");
        }
        if (users.size() > 1) {
            return BaseResult.error("电话号码有重复");
        }
        UserInfo user = users.get(0);
        logger.info("user=====>{}",user);

        String encryptPwd=null;

        if (code!=null){//找回密码的情况
            String salt= EncryptUtil.getInstance().md5(System.currentTimeMillis()+"");
            encryptPwd=PasswordUtil.encryptPassword(password, salt);
            user.setUserPwd(encryptPwd);
            user.setPasswordSalt(salt);
            userDao.updatePwd(encryptPwd,salt,user.getId());
        }

        if (encryptPwd==null){
            encryptPwd=PasswordUtil.encryptPassword(password, user.getPasswordSalt());
        }
        logger.info("encryptPwd=====>{}",encryptPwd);

        if (!encryptPwd.equals(user.getUserPwd())){
            return ApiResult.error("密码错误!");
        }

        boolean hasVendorInfo=false;

        LoginResult result = new LoginResult();
        /**
         * 店铺信息
         */
        VendorInfo vendor = vendorDao.findByUserId(user.getId());
        if (null!=vendor) {
            result.setVendorInfoId(vendor.getId());
            if (vendor.getIsOpen()==1){
                hasVendorInfo=true;
            }
            result.setAuthenticationStatus(vendor.getAuthenticationStatus().getValue());
        }else {
            result.setAuthenticationStatus(0);
        }
        result.setAccount(user.getUsername());
        result.setUserId(user.getId());
        user.setPasswordSalt(null);
        result.setUser(user);
        result.setToken(VerificationUtil.getToken(user, (vendor!=null) ? vendor.getId() : 0, Enums.UserType.Vendor));
        result.setHasVendorInfo(hasVendorInfo);
        return ApiResult.success(result);
    }

    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @Override
    public BaseResult updatePwd(String oldPwd, String newPwd,int userId) {
        UserInfo userInfo=userDao.getUserById(userId);
        if (userInfo!=null){
            String pwd=PasswordUtil.encryptPassword(oldPwd, userInfo.getPasswordSalt());
            if (pwd.equals(userInfo.getUserPwd())){
                pwd=PasswordUtil.encryptPassword(newPwd, userInfo.getPasswordSalt());
                int res=userDao.updatePwd(pwd,userInfo.getPasswordSalt(),userId);
                if (res>0){
                    return BaseResult.success();
                }
            }else {
                return BaseResult.error("修改失败，原密码错误");
            }
        }
        return BaseResult.error("修改密码失败");
    }


    @Override
    public BaseResult updateAccount(String telephone, int id) {
        //查询用户
        UserInfo exampleUser = new UserInfo();
        exampleUser.setTelephone(telephone);
        List<UserInfo> users = userDao.selectByWhere(exampleUser);
        if (users!=null&&users.size()>0){
            return BaseResult.error("绑定失败，该号码已被注册！");
        }
        int res=userDao.updateAccount(telephone,id);
        if (res>0) {
            return BaseResult.success();
        }
        return BaseResult.error("绑定失败");
    }
}
