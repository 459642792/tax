package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.Constants;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.CityInfo;
import com.blueteam.entity.dto.ExclusiveCityInfo;
import com.blueteam.wineshop.mapper.CityInfoMapper;
import com.blueteam.wineshop.service.CityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marx
 * <p>
 * CityInfoController.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
@Controller
@RequestMapping("/city")
public class CityInfoController extends BaseController {
    @Autowired
    CityInfoService cityInfoService;


    @Autowired
    CityInfoMapper cityInfoMapper;

    /**
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cityList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult cityList(HttpServletResponse response) throws Exception {
        String[] pinyin = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        List<ExclusiveCityInfo> lstCity = new ArrayList<ExclusiveCityInfo>();
        for (int i = 0; i < pinyin.length; i++) {
            ExclusiveCityInfo objCitys = new ExclusiveCityInfo();
            List<CityInfo> lstCityss = cityInfoService.CityInfoList(String.valueOf(pinyin[i].toLowerCase()));
            if (lstCityss.size() > 0) {
                objCitys.setPinyin(pinyin[i]);
                objCitys.setLstCityInfo(lstCityss);
                lstCity.add(objCitys);
            }
        }
        return ApiResult.success(lstCity);
    }

    /**
     * @param PinYin
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryCity", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult querycity(@RequestParam("PinYin") String PinYin, HttpServletResponse response) throws Exception {
        //List<ExclusiveCityInfo> lstCity=new ArrayList<ExclusiveCityInfo>();
        if (PinYin == null || PinYin.trim().isEmpty()) {
            return ApiResult.error(null, "参数错误");
        }
        List<CityInfo> lstCityss = cityInfoService.queryCity(PinYin);
        for (int i = 0; i < lstCityss.size(); i++) {
            if (stringNumbers(lstCityss.get(i).getCode()) == 2) {
                CityInfo objInfo = cityInfoService.selectCityName(lstCityss.get(i).getCode().substring(0, lstCityss.get(i).getCode().lastIndexOf("_")));
                if (null != objInfo) {
                    lstCityss.get(i).setName((lstCityss.get(i).getName() + "." + objInfo.getName()));
                }
            }
        }
        /*List<CityInfo> lstCityss=cityInfoService.queryCity(new String(PinYin.getBytes("ISO-8859-1"),"UTF-8"));
        String [] pinyin={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		for(int i=0;i<pinyin.length;i++)
		{
			ExclusiveCityInfo objCitys=new ExclusiveCityInfo();
			objCitys.setPinyin(pinyin[i]);
			List<CityInfo> newInfo=new ArrayList<CityInfo>();;
			for(int j=0;j<lstCityss.size();j++)
			{
				if(lstCityss.get(j).getPinYin().startsWith(pinyin[i].toLowerCase()))
				{
					newInfo.add(lstCityss.get(j));
				}
			}
			objCitys.setLstCityInfo(newInfo);
			lstCity.add(objCitys);
		}
		for(int f=0;f<lstCity.size();f++)
		{
			if(lstCity.get(f).getLstCityInfo().size()<1)
			{
				lstCity.remove(f);
				f--;
			}
		}*/
        return ApiResult.success(lstCityss);
    }

    /**
     * 查询市下面的区域接口
     */
    @RequestMapping(value = "/queryCityList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult queryCityList(@RequestParam("Code") String Code, HttpServletResponse response) throws Exception {
        if (Code == null || Code.trim().isEmpty()) {
            return ApiResult.error(null, "参数错误");
        }
        List<CityInfo> CityList = cityInfoMapper.listCityList(Code);
        return ApiResult.success(CityList);
    }

    /**
     * 字符串中包含指定字符的个数
     *
     * @param str
     * @return
     */
    public static int stringNumbers(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if ("_".equals(String.valueOf(str.charAt(i)))) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取所有省份
     *
     * @return
     */
    @RequestMapping("/getProvinceList")
    @ResponseBody
    public BaseResult getProvinceList() {
        CityInfo info = new CityInfo();
        info.setParentCode(Constants.CITY_ROOT_CODE);
        List<CityInfo> citys = cityInfoService.selectListForNotGYSS(info);
        return ApiResult.success(citys);
    }

    /**
     * 获取城市列表，根据parentCode
     *
     * @return
     */
    @RequestMapping("/getCityByParent")
    @ResponseBody
    public BaseResult getCityByParent(String parent) {
        CityInfo info = new CityInfo();
        info.setParentCode(parent);
        List<CityInfo> citys = cityInfoService.selectListForNotGYSS(info);
        return ApiResult.success(citys);
    }
}
