package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.vo.VendorImagesVO;
import com.blueteam.wineshop.mapper.AdvertiseInfoMapper;
import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.EnabledOrDisabled;
import com.blueteam.base.util.DateUtil;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.ToutuSearch;
import com.blueteam.entity.po.AdvertiseInfo;
import com.blueteam.entity.po.CarriersInfo;
import com.blueteam.entity.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdvertiseInfoServiceImpl implements AdvertiseInfoService {

    /**
     *
     */
    @Autowired
    private AdvertiseInfoMapper advertiseInfoMapper;

    /**
     * 运营商服务
     */
    @Autowired
    private CarriersService carriersService;

    @Override
    public List<AdvertiseInfo> AdvertiseInfoList(String TypeCode, String CityCode) {
        return advertiseInfoMapper.AdvertiseInfoList(TypeCode, CityCode);
    }

    @Override
    public List<AdvertiseInfo> VendorTopList(int ForeignKey, String TypeCode) {
        return advertiseInfoMapper.VendorTopList(ForeignKey, TypeCode);
    }

    @Override
    public List<AdvertiseInfo> SdppList(int ForeignKey, String TypeCode) {
        return advertiseInfoMapper.SdppList(ForeignKey, TypeCode);
    }

    @Override
    public List<AdvertiseInfo> VendorPhotoList(int ForeignKey, String TypeCode) {
        return advertiseInfoMapper.VendorPhotoList(ForeignKey, TypeCode);
    }

    /**
     * 获取运营商管辖区域内的所有广告
     *
     * @param carriersId 运营商ID
     * @param typeCode   广告类型编码
     * @param cityCode   地区编码 主要为以后管理多地区准备
     * @return
     */
    @Override
    public List<AdvertiseInfo> selectAdvByYYS(int carriersId, String typeCode, String cityCode) {
        return advertiseInfoMapper.selectAdvByYYS(carriersId, typeCode, cityCode);
    }

    /**
     * 运营商广告插入
     *
     * @param adv
     * @param userId 运营商用户ID
     * @return
     */
    @Override
    public BaseResult yysInsert(int userId, AdvertiseInfo adv) {
        CarriersInfo info = carriersService.selectForUser(userId);
        if (info == null)
            return BaseResult.error("没有找到对应的运营商");
        if (StringUtil.IsNullOrEmpty(info.getManagementarea()))
            return BaseResult.error("没有查询到运营商的管理区域");

        //TODO:二期处理 这里先写死广告区域
        adv.setCityCode(info.getCitycode());

        if (info.getManagementarea().indexOf(adv.getCityCode()) == -1)
            return BaseResult.error("该运营商不能管理该广告区域");

        Date now = new Date();
        String nowTimeStr = DateUtil.format(now);
        adv.setCreateDate(nowTimeStr);
        adv.setUpdateDate(nowTimeStr);


        int count = advertiseInfoMapper.insert(adv);
        if (count > 0)
            return BaseResult.success();
        return BaseResult.error("添加广告失败");
    }

    /**
     * 运营商广告编辑
     *
     * @param adv
     * @param userId 运营商用户ID
     * @return
     */
    @Override
    public BaseResult yysUpdate(int userId, AdvertiseInfo adv) {
        CarriersInfo info = carriersService.selectForUser(userId);
        if (info == null)
            return BaseResult.error("没有找到对应的运营商");
        if (StringUtil.IsNullOrEmpty(info.getManagementarea()))
            return BaseResult.error("没有查询到运营商的管理区域");

        //TODO:二期处理 这里先写死广告区域
        adv.setCityCode(info.getCitycode());

        if (info.getManagementarea().indexOf(adv.getCityCode()) == -1)
            return BaseResult.error("该运营商不能管理该广告区域");

        AdvertiseInfo dbModel = advertiseInfoMapper.selectByPrimaryKey(adv.getId());
        if (dbModel == null)
            return BaseResult.error("没有找到对应的广告信息");

        if (!Constants.CREATE_VENDOR_LISTIMAGE.equals(dbModel.getTypeCode()))
            return BaseResult.error("没有找到对应的广告信息,TypeCode错误");

        if (info.getManagementarea().indexOf(dbModel.getCityCode()) == -1)
            return BaseResult.error("该运营商不能编辑该广告区域");

        dbModel.setCityCode(adv.getCityCode());
        dbModel.setImg(adv.getImg());
        dbModel.setForeignKey(adv.getForeignKey());

        Date now = new Date();
        String nowTimeStr = DateUtil.format(now);
        dbModel.setUpdateDate(nowTimeStr);

        int count = advertiseInfoMapper.updateByPrimaryKey(dbModel);
        if (count > 0)
            return BaseResult.success();
        return BaseResult.error("编辑广告失败");
    }

    /**
     * 交换SortNumber 升序交换
     * 指和比自己小的sortNumber进行交换，没有则不交换
     *
     * @param id         待交换的广告ID
     * @param typeCode
     * @param carriersId 运营商ID
     * @param cityCode   区域code    这里先用云运营商的区域Code,后面会使用传入的cityCode
     * @return
     */
    @Override
    public int switchSortNum(int id, String typeCode, int carriersId, String cityCode) {
        CarriersInfo carriersInfo = carriersService.getCarriersByID(carriersId);
        if (carriersInfo == null)
            return 0;
        //TODO:这里先用云运营商的区域Code,后面会使用传入的cityCode
        String localCityCode = carriersInfo.getCitycode();
        return advertiseInfoMapper.switchSortNum(id, typeCode, carriersId, localCityCode);
    }

    /**
     * 删除运营商广告
     *
     * @param userId 运营商用户ID
     * @param advId  要删除的广告ID
     * @return
     */
    @Override
    public BaseResult deleteYysAdv(int userId, int advId) {
        //获取运营商和广告，判断是否可以删除
        CarriersInfo info = carriersService.selectForUser(userId);
        if (info == null)
            return BaseResult.error("没有找到运营商");
        if (StringUtil.IsNullOrEmpty(info.getManagementarea()))
            return BaseResult.error("您没有管理区域");
        AdvertiseInfo adv = advertiseInfoMapper.selectByPrimaryKey(advId);
        if (adv == null)
            return BaseResult.error("没有找到该广告");

        if (info.getManagementarea().indexOf(adv.getCityCode()) == -1)
            return BaseResult.error("运营商无权管理该区域广告");
        //删除
        int count = advertiseInfoMapper.deleteByPrimaryKey(advId);
        if (count > 0)
            return BaseResult.success();

        return BaseResult.error("删除广告失败");
    }

    @Override
    public int save(AdvertiseInfo model) {
        return advertiseInfoMapper.insert(model);
    }

    @Override
    public int findMaxSortNumberOfHeadImg(Integer vendorId) {
        AdvertiseInfo info = advertiseInfoMapper.findMaxSortNumber(vendorId,
                Constants.CREATE_VENDOR_DETAIL_TOPIMAGE);
        int result = info == null ? 0 : info.getSortNumber();
        return result;
    }

    @Override
    public List<AdvertiseInfo> getImagesByType(int vendorId, String type) {
        return advertiseInfoMapper.getImagesByType(vendorId, type);
    }

    @Override
    public int removeHeadImage(Integer Id) {
        return advertiseInfoMapper.removeHeadImage(Id);
    }

    @Override
    public int raiseUpHeadImage(Integer id) {
        List<AdvertiseInfo> list = advertiseInfoMapper.getImagesByIdAndType(id, Constants.CREATE_VENDOR_DETAIL_TOPIMAGE);
        int index = -1, len = list.size(), result = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (list.get(i).getId().intValue() == id) {
                index = i;
                break;
            }
        }
        if (index > 0) {
            AdvertiseInfo first = list.get(index);
            AdvertiseInfo second = list.get(index - 1);
            result = advertiseInfoMapper.swapSortNumber(first.getId(), second.getId(), first.getSortNumber(), second.getSortNumber());
        }
        return result;
    }

    /**
     * 根据搜索条件分页查询广告
     *
     * @param search 搜索条件
     * @return
     */
    @Override
    public PageResult<List<AdvertiseInfo>> selectAdvByWhere(ToutuSearch search) {
        PageResult<List<AdvertiseInfo>> result = advertiseInfoMapper.selectAdvByWhere(search);
        if (result == null)
            return PageResult.empty();
        return result;
    }


    /**
     * 根据广告ID获取广告详情
     *
     * @param id
     * @return
     */
    @Override
    public AdvertiseInfo findAdvByID(int id) {
        return advertiseInfoMapper.findAdvByID(id);
    }


    /**
     * 管理员广告插入
     *
     * @param adv
     * @return
     */
    @Override
    public BaseResult adminInsert(AdvertiseInfo adv) {
        Date now = new Date();
        String nowTimeStr = DateUtil.format(now);
        adv.setCreateDate(nowTimeStr);
        adv.setUpdateDate(nowTimeStr);
        adv.setEnableFlag(EnabledOrDisabled.ENABLED);


        int count = advertiseInfoMapper.insert(adv);
        if (count > 0)
            return BaseResult.success();
        return BaseResult.error("添加广告失败");
    }

    /**
     * 运营商广告编辑
     *
     * @param adv
     * @return
     */
    @Override
    public BaseResult adminUpdate(AdvertiseInfo adv) {

        AdvertiseInfo dbModel = advertiseInfoMapper.selectByPrimaryKey(adv.getId());
        if (dbModel == null)
            return BaseResult.error("没有找到对应的广告信息");
//
//		if(!Constants.CREATE_VENDOR_LISTIMAGE.equals(dbModel.getTypeCode()))
//			return BaseResult.error("没有找到对应的广告信息,TypeCode错误");


        dbModel.setCityCode(adv.getCityCode());
        dbModel.setImg(adv.getImg());
        dbModel.setForeignKey(adv.getForeignKey());
        dbModel.setBrandId(adv.getBrandId());

        Date now = new Date();
        String nowTimeStr = DateUtil.format(now);
        dbModel.setUpdateDate(nowTimeStr);

        int count = advertiseInfoMapper.updateByPrimaryKey(dbModel);
        if (count > 0)
            return BaseResult.success();
        return BaseResult.error("编辑广告失败");
    }

    @Override
    public int update(AdvertiseInfo adv) {
        return advertiseInfoMapper.updates(adv);
    }



    @Override
    public BaseResult getVendorImagesByForeignKey(String foreignKey) {
        List<AdvertiseInfo> list=advertiseInfoMapper.getVendorImagesByForeignKey(foreignKey,
                new String[]{Constants.CREATE_VENDOR_DETAIL_FACADE,Constants.CREATE_VENDOR_DETAIL_AMBIENT,Constants.CREATE_VENDOR_GENERALVIEW});
        List<VendorImagesVO> resList=new ArrayList<>();

        if (list==null||list.size()<=0){
            return ApiResult.success(null);
        }

        VendorImagesVO vo=new VendorImagesVO();

        String type=list.get(0).getTypeCode();

        List<VendorImagesVO.Image> imageList=new ArrayList<>();

        for (int i=0;i<list.size();i++){
            AdvertiseInfo info=list.get(i);
            if (!type.equals(info.getTypeCode())){
                vo.setImages(imageList);
                if (Constants.CREATE_VENDOR_DETAIL_FACADE.equals(type)) {
                    vo.setType("1");
                }else if (Constants.CREATE_VENDOR_DETAIL_AMBIENT.equals(type)){
                    vo.setType("2");
                }else {
                    vo.setType("3");
                }
                resList.add(vo);
                vo=new VendorImagesVO();
                imageList=new ArrayList<>();
                type=info.getTypeCode();
            }
            VendorImagesVO.Image image=vo.new Image();
            image.setId(info.getId());
            image.setImage(info.getImg());
            image.setSort(info.getSortNumber());
            imageList.add(image);
            if (i==(list.size()-1)){
                vo.setImages(imageList);
                if (Constants.CREATE_VENDOR_DETAIL_FACADE.equals(type)) {
                    vo.setType("1");
                }else if (Constants.CREATE_VENDOR_DETAIL_AMBIENT.equals(type)){
                    vo.setType("2");
                }else {
                    vo.setType("3");
                }
                resList.add(vo);
            }
        }
        return ApiResult.success(resList);
    }
}
