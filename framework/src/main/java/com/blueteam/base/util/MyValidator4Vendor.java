package com.blueteam.base.util;


import com.blueteam.entity.po.CouponInfo;

/**
 * 用于商家的验证器
 *
 * @author Eric Lee, 2017-03-06
 */
public class MyValidator4Vendor extends MyValidator<Object> {

    /**
     * 验证发行量和生效时间必须是放大修改
     *
     * @param oldModel
     * @param newModel
     * @return
     * @throws Exception
     */
    public MyValidator4Vendor couponModify(CouponInfo oldModel, CouponInfo newModel) throws Exception {
        if (hasErrors()) return this;
        if (newModel == null) {
            updateErrorAndMessage(false, "未查询到优惠券");
            return this;
        }
        StringBuffer msg = new StringBuffer("");
        boolean isSuccessful = true;
        if (newModel.getCount() != null) {
            if (newModel.getCount() > oldModel.getCount()) {
                oldModel.setCount(newModel.getCount());
            } else {
                msg.append("发行量必须大于").append(oldModel.getCount()).append("; ");
                isSuccessful = false;
            }
        }

        if (newModel.getEndTime() != null) {
            if (DateUtil.cmpDate(newModel.getEndTime(), oldModel.getEndTime(), null, 1)) {
                oldModel.setEndTime(newModel.getEndTime());
            } else {
                msg.append("失效时间必须大于").append(oldModel.getEndTime()).append("; ");
                isSuccessful = false;
            }
        }

        updateErrorAndMessage(isSuccessful, msg.toString());
        return this;
    }

    /**
     * example
     *
     * @param args
     */
    public static void main(String[] args) {
        MyValidator<?> validator = MyValidator4Vendor.newInstance();
        validator.isNotBlankStr("   ", "cannot be blank")
                .isNotNullObj(222, "cannot be null");

        System.out.println(validator.hasErrors());
        System.out.println(validator.getErrorMessages());

    }
}
