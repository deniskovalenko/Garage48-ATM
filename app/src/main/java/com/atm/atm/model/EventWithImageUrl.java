package com.atm.atm.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by denis on 11.09.16.
 */
public class EventWithImageUrl {
    String description;//user input
    double lat;
    double lon;
    int time_limit;
    String host_id;//automatically sent as argument
    String host_name;//from backend, automatically sent as argument
    String hashtag;//user input
    String img_url;//automatically uploaded by the backend, sent as argument

    public EventWithImageUrl(String description, double lat, double lon, int time_limit, String host_id, String host_name, String hashtag, String img_url) {
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.time_limit = time_limit;
        this.host_id = host_id;
        this.host_name = host_name;
        this.hashtag = hashtag;
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
    }

    public String getHost_id() {
        return host_id;
    }

    public void setHost_id(String host_id) {
        this.host_id = host_id;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
