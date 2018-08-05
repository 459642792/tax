package com.blueteam.wineshop.service;

import com.blueteam.entity.po.VendorBankPO;

public interface VendorBankService {

    int add(VendorBankPO po);

    VendorBankPO getInfoByVendorId(int vendorId);

    int update(VendorBankPO po);

    int del(VendorBankPO po);
}
