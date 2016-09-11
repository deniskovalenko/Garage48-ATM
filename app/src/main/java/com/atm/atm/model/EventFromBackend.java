package com.atm.atm.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EventFromBackend {
    List<String> participants;
    String _id;
    String description;//user input
//    LatLng latLng;//automatically sent as argument
    double lat;
    double lon;
    String end_datetime;
    String host_id;//automatically sent as argument
    String host_name;//from backend, automatically sent as argument
    String img_url;//automatically uploaded by the backend, sent as argument
    String created_att; //datetime 2016-09-21:T21:08:31.z001zzz
    String hashtag;//user input
    double distance; //automatically calculated and rounded in MapsActivity, sent as argument
    int participants_count;


    public EventFromBackend(List<String> participants, String _id, String description, double lat, double lon, String end_datetime, String host_id, String host_name, String img_url, String created_att, String hashtag, double distance, int participants_count) {
        this.participants = participants;
        this._id = _id;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.end_datetime = end_datetime;
        this.host_id = host_id;
        this.host_name = host_name;
        this.img_url = img_url;
        this.created_att = created_att;
        this.hashtag = hashtag;
        this.distance = distance;
        this.participants_count = participants_count;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(String end_datetime) {
        this.end_datetime = end_datetime;
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getCreated_att() {
        return created_att;
    }

    public void setCreated_att(String created_att) {
        this.created_att = created_att;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getParticipants_count() {
        return participants_count;
    }

    public void setParticipants_count(int participants_count) {
        this.participants_count = participants_count;
    }
}