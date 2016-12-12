package com.example.joo.mytaste;

/**
 * Created by Joo on 2016-09-24.
 */
public class location {

    private String name;
    private int value;
    private String imgpath;

    public location(String name_, int value_, String imgpath_){
        this.name = name_;
        this.value = value_;
        this.imgpath = imgpath_;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getImgpath() {
        return imgpath;
    }
}
