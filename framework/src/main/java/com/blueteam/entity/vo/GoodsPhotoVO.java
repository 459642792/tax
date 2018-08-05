package com.blueteam.entity.vo;

import java.io.Serializable;

/**
 * 商品关联图片列表
 *
 * @author xiaojiang
 * @create 2017-10-24  14:54
 */
public class GoodsPhotoVO implements Serializable {
    /**
     * 商品图片地址
     */
    private String imageUrl;
    /**
     * 商品图片背景色调
     */
    private String imageTone;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageTone() {
        return imageTone;
    }

    public void setImageTone(String imageTone) {
        this.imageTone = imageTone;
    }
}
