package com.example.joo.mytaste;

/**
 * Created by Joo on 2016-02-17.
 */
public class MyPlace {
    // if you edit this .. MySQLiteOpenHelper,MyAdapter+row.xml+addplace.xml+AddPlaceDialogActivity
    int id;
    String name;
    String menu;
    String photo;
    int rate;
    String comment;
    double lat,lng;
    // double lat,double lng;

    public MyPlace(){}
    public MyPlace(String name, String menu, String photo, int rate, String comment, double lat, double lng) {
        this.name = name;
        this.menu = menu;
        this.photo = photo;
        this.rate = rate;
        this.comment = comment;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
