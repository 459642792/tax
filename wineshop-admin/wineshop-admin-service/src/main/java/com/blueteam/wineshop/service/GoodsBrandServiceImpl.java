package com.blueteam.wineshop.service;

import com.blueteam.base.exception.BusinessException;
import com.blueteam.entity.dto.BrandSearchDTO;
import com.blueteam.entity.po.BrandMainInfoPO;
import com.blueteam.entity.po.BrandSubInfoPO;
import com.blueteam.entity.vo.AdminBrandVO;
import com.blueteam.wineshop.mapper.GoodsBrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by  NastyNas on 2017/10/19.
 */
@Service
public class GoodsBrandServiceImpl implements GoodsBrandService {
    @Autowired
    GoodsBrandMapper goodsBrandMapper;

    @Override
    public Integer countMainBrandByName(String brandName) {
        return goodsBrandMapper.countMainBrandByName(brandName);
    }

    @Override
    public Integer saveMainBrand(BrandMainInfoPO brandMainInfoPO) {
        return goodsBrandMapper.saveMainBrand(brandMainInfoPO);
    }

    @Override
    public Map getMainBrandInfo(Integer mainBrandId) {
        return goodsBrandMapper.getMainBrandInfo(mainBrandId);
    }

    @Override
    public Integer updateMainBrand(BrandMainInfoPO brandMainInfoPO) {
        return goodsBrandMapper.updateMainBrand(brandMainInfoPO);
    }

    @Override
    public Integer removeMainBrand(Integer mainBrandId) {
        return goodsBrandMapper.removeMainBrand(mainBrandId);
    }

    @Override
    public Integer countMainBrandGoods(Integer mainBrandId) {
        return goodsBrandMapper.countMainBrandGoods(mainBrandId);
    }

    @Override
    public Integer countSubBrand(Integer mainBrandId) {
        return goodsBrandMapper.countSubBrand(mainBrandId);
    }

    @Override
    public Integer countSubBrandByName(Integer mainBrandId, String brandName) {
        return goodsBrandMapper.countSubBrandByName(mainBrandId, brandName);
    }

    @Override
    public void saveSubBrand(BrandSubInfoPO brandSubInfoPO) throws Exception {
        //保存子品牌
        Integer saveResult = goodsBrandMapper.saveSubBrand(brandSubInfoPO);
        if (saveResult != 1) {
            throw new Exception();
        }
        try {
            //同步更新主品牌更新时间,更新失败不影响子品牌保存结果
            Integer timeUpdateResult = goodsBrandMapper.updateMainBrandUpdateTime(brandSubInfoPO.getMainBrandId(), brandSubInfoPO.getUpdateStaffId());
            if (timeUpdateResult != 1) {
                throw new BusinessException();
            }
        } catch (Exception e) {
            throw new BusinessException("保存子品牌同步更新主品牌表失败");
        }
    }

    @Override
    public Map getSubBrandInfo(Integer brandId) {
        return goodsBrandMapper.getSubBrandInfo(brandId);
    }

    @Override
    public Integer countSubBrandGoods(Integer brandId) {
        return goodsBrandMapper.countSubBrandGoods(brandId);
    }

    @Override
    public void updateSubBrand(BrandSubInfoPO brandSubInfoPO) throws Exception {
        //更新子品牌
        Integer updateResult = goodsBrandMapper.updateSubBrand(brandSubInfoPO);
        if (updateResult != 1) {
            throw new Exception();
        }
        try {
            //同步更新主品牌更新时间,更新失败不影响子品牌保存结果
            Integer timeUpdateResult = goodsBrandMapper.updateMainBrandUpdateTime(brandSubInfoPO.getMainBrandId(), brandSubInfoPO.getUpdateStaffId());
            if (timeUpdateResult != 1) {
                throw new BusinessException();
            }
        } catch (Exception e) {
            throw new BusinessException("更新子品牌同步更新主品牌表失败", e);
        }
    }

    @Override
    public Integer removeSubBrand(Integer brandId) {
        return goodsBrandMapper.removeSubBrand(brandId);
    }


    @Override
    public List listBrandInfo(BrandSearchDTO brandSearchDTO) {
        return goodsBrandMapper.listBrandInfo(brandSearchDTO);
    }

    /**
     * 下架商品并禁用品牌，非BusinessException异常会回滚整个事务操作
     *
     * @param brandSubInfoPO
     */
    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void disableBrandWithGoods(BrandSubInfoPO brandSubInfoPO) throws Exception {
        goodsBrandMapper.updateVendorGoodsState(brandSubInfoPO);
        goodsBrandMapper.updateVendorBrandState(brandSubInfoPO);
        goodsBrandMapper.updateGoodsState(brandSubInfoPO);
        Integer result = goodsBrandMapper.updateSubBrandState(brandSubInfoPO);
        if (result != 1) {
            throw new Exception();
        }
//        try {
//            //同步更新主品牌更新时间,更新失败不影响操作结果
//            Integer timeUpdateResult = goodsBrandMapper.updateMainBrandUpdateTime(brandSubInfoPO.getMainBrandId(), brandSubInfoPO.getUpdateStaffId());
//            if (timeUpdateResult != 1) {
//                throw new BusinessException();
//            }
//        } catch (Exception e) {
//            throw new BusinessException("更新子品牌同步更新主品牌表失败", e);
//        }
    }

    @Override
    public void disableBrandWithoutGoods(BrandSubInfoPO brandSubInfoPO) throws Exception {
        Integer result = goodsBrandMapper.updateSubBrandState(brandSubInfoPO);
        if (result != 1) {
            throw new Exception();
        }
//        try {
//            //同步更新主品牌更新时间,更新失败不影响操作结果
//            Integer timeUpdateResult = goodsBrandMapper.updateMainBrandUpdateTime(brandSubInfoPO.getMainBrandId(), brandSubInfoPO.getUpdateStaffId());
//            if (timeUpdateResult != 1) {
//                throw new BusinessException();
//            }
//        } catch (Exception e) {
//            throw new BusinessException("更新子品牌同步更新主品牌表失败", e);
//        }
    }

    @Override
    public List<Map> listMainBrand(String mainBrandName) {
        return goodsBrandMapper.listMainBrand(mainBrandName);
    }

    @Override
    public List<AdminBrandVO> listSubBrand(Integer mainBrandId) {
        return goodsBrandMapper.listSubBrand(mainBrandId, BrandSubInfoPO.STATE_TAG_VALID);
    }

    @Override
    public void enableBrand(BrandSubInfoPO brandSubInfoPO) throws Exception {
        Integer result = goodsBrandMapper.updateSubBrandState(brandSubInfoPO);
        if (result != 1) {
            throw new Exception();
        }
//        try {
//            //同步更新主品牌更新时间,更新失败不影响操作结果
//            Integer timeUpdateResult = goodsBrandMapper.updateMainBrandUpdateTime(brandSubInfoPO.getMainBrandId(), brandSubInfoPO.getUpdateStaffId());
//            if (timeUpdateResult != 1) {
//                throw new BusinessException();
//            }
//        } catch (Exception e) {
//            throw new BusinessException("更新子品牌同步更新主品牌表失败", e);
//        }
    }

    @Override
    public List<AdminBrandVO> listSubBrandTypeLimited(Integer mainBrandId, Integer goodsTypeId) {
        return goodsBrandMapper.listSubBrandTypeLimited(mainBrandId, goodsTypeId, BrandSubInfoPO.STATE_TAG_VALID);
    }
}
