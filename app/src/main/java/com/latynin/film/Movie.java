package com.latynin.film;


public class Movie {

    private String name;
    private String desk;
    private String imgUrl;
    private String ret;

    public Movie(String name, String desk, String imgUrl,String ret) {
        this.name = name;
        this.desk = desk;
        this.imgUrl = imgUrl;
        this.ret = ret;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
