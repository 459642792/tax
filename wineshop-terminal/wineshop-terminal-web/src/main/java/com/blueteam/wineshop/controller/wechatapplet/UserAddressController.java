package com.blueteam.wineshop.controller.wechatapplet;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.wechatapplet.UserAddressDTO;
import com.blueteam.wineshop.controller.BaseController;
import com.blueteam.wineshop.service.wechatapplet.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户收获地址
 *
 * @author xiaojiang
 * @create 2018-01-05  11:21 H
 */
@Controller
@RequestMapping("/wechat/userAddress")
public class UserAddressController extends BaseController {
    @Autowired
    UserAddressService userAddressService;

    /**
     * 方法的功能描述: 保存修改用户地址
     *
     * @return
     * @methodName
     * @ * @param: null
     * @author xiaojiang 2018/1/5 15:55
     * @modifier
     * @since 1.4.0
     */
    @RequestMapping(value = "/saveOrModifyUserAddress", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult saveOrModifyUserAddress( UserAddressDTO useraddressDTO) {
        useraddressDTO.setUserId(this.getCurrentUserID());
//        useraddressDTO.setUserId(3);
        return userAddressService.saveOrModifyUserAddress(useraddressDTO);
    }

}
