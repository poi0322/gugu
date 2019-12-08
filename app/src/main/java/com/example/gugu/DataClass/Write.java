package com.example.gugu.DataClass;

public class Write {
    private String type;
    private double latitude;
    private double longitude;
    private String term;
    private String title;
    private String uid;
    private String upt_date;
    private String service;
    private String body;

    public Write(){}
    public Write(String type,
                 double latitude,
                 double longitude,
                 String term,
                 String title,
                 String uid,
                 String upt_date,
                 String service,
                 String body
    ) {
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.term = term;
        this.title = title;
        this.uid = uid;
        this.upt_date = upt_date;
        this.service = service;
        this.body = body;

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUpt_date() {
        return upt_date;
    }

    public void setUpt_date(String upt_date) {
        this.upt_date = upt_date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
