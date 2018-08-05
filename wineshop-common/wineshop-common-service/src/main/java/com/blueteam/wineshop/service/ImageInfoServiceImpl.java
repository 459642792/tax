package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.ImageInfoMapper;
import com.blueteam.base.constant.Enums;
import com.blueteam.entity.po.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageInfoServiceImpl implements ImageInfoService {

    @Autowired
    ImageInfoMapper imageInfoMapper;

    @Override
    public List<ImageInfo> ImageInfoList(String Type, int ForeignKey) {
        return imageInfoMapper.ImageInfoList(Type, ForeignKey);
    }

    @Override
    public int insertImage(ImageInfo imageInfo) {
        return imageInfoMapper.insertImage(imageInfo);
    }

    @Override
    public List<ImageInfo> getImagesByType(int vendorId, String type) {
        return imageInfoMapper.getImagesByType(vendorId, type);
    }

    @Override
    public int raiseUpHeadImage(Integer id) throws Exception {
        List<ImageInfo> list = imageInfoMapper.getImagesByIdAndType(id, Enums.ImageInfoType.Vendor_Top.getValue() + "");
        int index = -1, len = list.size(), result = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (list.get(i).getId().intValue() == id) {
                index = i;
                break;
            }
        }
        if (index > 0) {
            ImageInfo first = list.get(index);
            ImageInfo second = list.get(index - 1);
            result = imageInfoMapper.swapSortNumber(first.getId(), second.getId(), first.getSortNumber(), second.getSortNumber());
        }
        return result;
    }

    @Override
    public int removeHeadImage(Integer Id) {
        return imageInfoMapper.removeHeadImage(Id);
    }

    @Override
    public int findMaxSortNumberOfHeadImg(Integer vendorId) {
        ImageInfo info = imageInfoMapper.findMaxSortNumber(vendorId,
                Enums.ImageInfoType.Vendor_Top.getValue() + "");
        int result = info == null ? 0 : info.getSortNumber();
        return result;
    }

    /**
     * 删除推荐商家信息
     *
     * @return
     */
    @Override
    public int deleteByPrimaryKey(int ExtendId, String Type) {
        return imageInfoMapper.deleteByPrimaryKey(ExtendId, Type);
    }
}
