package com.blueteam.entity.dto;

import java.io.Serializable;
import java.util.List;

public class VendorImageDTO implements Serializable{

    private Integer id;

    private Integer imageType;

    private String image;

    private String[] images;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageType() {
        return imageType;
    }

    public void setImageType(Integer imageType) {
        this.imageType = imageType;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
