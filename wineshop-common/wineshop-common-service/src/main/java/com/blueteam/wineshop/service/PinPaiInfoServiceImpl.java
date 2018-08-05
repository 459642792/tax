package com.blueteam.wineshop.service;

import com.blueteam.entity.po.PinPaiInfo;
import com.blueteam.wineshop.mapper.PinPaiInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PinPaiInfoServiceImpl implements PinPaiInfoService {

    @Autowired
    PinPaiInfoMapper pinpaiInfoMapper;

    @Override
    public List<PinPaiInfo> PinPaiInfoList(String Status) {
        return pinpaiInfoMapper.PinPaiInfoList(Status);
    }

    @Override
    public PinPaiInfo PinPaiName(int Id) {
        return pinpaiInfoMapper.PinPaiName(Id);
    }

    @Override
    public List<PinPaiInfo> listPinPaiInfo(String Status) {
        // TODO Auto-generated method stub
        String[] str = Status.split(",");
        List<Integer> lists = new ArrayList<>();
        if (str != null) {
            for (int i = 0; i < str.length; i++) {
                lists.add(Integer.parseInt(str[i]));
            }
        }
        return pinpaiInfoMapper.listPinPaiInfo(lists);
    }

    /**
     * 对后台品牌库数据进行维护处理
     */
    public List<PinPaiInfo> adminPinpaiList(Integer pageSize, Integer pageIndex, String Name) {
        return pinpaiInfoMapper.adminPinpaiList(pageSize, pageIndex, Name);
    }

    /**
     * 对后台品牌管理的数据进行统计（此统计用于分页）
     */
    public List<PinPaiInfo> adminPinpaiCount(String Name) {
        return pinpaiInfoMapper.adminPinpaiCount(Name);
    }

    /**
     * 对后台品牌管理的数据进行新增操作
     *
     * @param
     * @return
     */
    @Override
    public int insertpinpai(PinPaiInfo objInfo) {
        return pinpaiInfoMapper.insertpinpai(objInfo);
    }

    /**
     * 对后台品牌管理的数据进行修改操作
     */
    @Override
    public int updatepinpai(PinPaiInfo objInfo) {
        return pinpaiInfoMapper.updatepinpai(objInfo);
    }
}
