package com.blueteam.entity.vo;

import java.io.Serializable;
import java.util.List;

public class VendorImagesVO implements Serializable{

    private String type;

    private List<Image> images;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public class Image implements Serializable{
        private Integer id;
        private String image;
        private Integer sort;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }
    }
}
