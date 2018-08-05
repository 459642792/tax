package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.VendorBankPO;

public interface VendorBankMapper {

    int insert(VendorBankPO record);

    VendorBankPO selectByVendorId(Integer vendorId);


    int updateByPrimaryKey(VendorBankPO record);

    int updateById(VendorBankPO record);

    int delById(VendorBankPO record);
}