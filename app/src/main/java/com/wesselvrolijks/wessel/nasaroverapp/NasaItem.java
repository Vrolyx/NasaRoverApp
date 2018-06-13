package com.wesselvrolijks.wessel.nasaroverapp;

import java.io.Serializable;

public class NasaItem implements Serializable {
    private String imgageId;
    private String imageUrl;

    public NasaItem(String imgageId, String imageUrl) {
        this.imgageId = imgageId;
        this.imageUrl = imageUrl;
    }

    public String getImgageId() {
        return imgageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImgageId(String imgageId) {
        this.imgageId = imgageId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString(){
        return "NasaItem{imageId=" + imgageId + ", imageUrl=" + imageUrl + "}";
    }
}
