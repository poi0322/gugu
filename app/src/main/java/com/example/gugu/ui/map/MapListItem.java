package com.example.gugu.ui.map;

import android.graphics.drawable.Drawable;

public class MapListItem {
    private String key;
    private Drawable listImage;
    private String listTitle;
    private String listProfile;
    private int list_active_sup;
    private int list_bath_sup;
    private int list_toilet_sup;
    private int list_clean_sup;

    public Drawable getListImage() {
        return listImage;
    }

    public void setListImage(Drawable listImage) { this.listImage = listImage; }

    public String getListTitle() { return listTitle; }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getListProfile() {
        return listProfile;
    }

    public void setListProfile(String listProfile) {
        this.listProfile = listProfile;
    }

    public int getList_active_sup() {
        return list_active_sup;
    }

    public void setList_active_sup(int list_active_sup) {
        this.list_active_sup = list_active_sup;
    }

    public int getList_bath_sup() {
        return list_bath_sup;
    }

    public void setList_bath_sup(int list_bath_sup) {
        this.list_bath_sup = list_bath_sup;
    }

    public int getList_toilet_sup() {
        return list_toilet_sup;
    }

    public void setList_toilet_sup(int list_toilet_sup) {
        this.list_toilet_sup = list_toilet_sup;
    }

    public int getList_clean_sup() {
        return list_clean_sup;
    }

    public void setList_clean_sup(int list_clean_sup) {
        this.list_clean_sup = list_clean_sup;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
