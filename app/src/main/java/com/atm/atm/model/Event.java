package com.atm.atm.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Event {
    String id;
    String description;//user input
    LatLng latLng;//automatically sent as argument
    double lat;
    double lon;
    long created_att; //datetime 2016-09-21:T21:08:31.z001zzz
    long end_datetime;
    String host_id;//automatically sent as argument
    String host_name;//from backend, automatically sent as argument
    String host_img_url;
    String img_url;//automatically uploaded by the backend, sent as argument
    String hashtag;//user input
    int distance; //automatically calculated and rounded in MapsActivity, sent as argument
    int participants_count;
    List<String> participants;

    public Event(String desc, LatLng location, String creator_id, String creator_name, String url, String tag) {
        id = UUID.randomUUID().toString();
        description = desc;
        latLng = location;
        lat = latLng.latitude;
        lon = latLng.longitude;
        created_att = new Date().getTime();
        end_datetime = created_att+3600000;
        host_id = creator_id;
        host_name = creator_name;
        host_img_url = "https://graph.facebook.com/" + host_id + "/picture?width=128&height=128";
        img_url = url;
        hashtag = tag;
        participants_count = 1;
        participants = new ArrayList<>();
        participants.add(host_id);
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public long getCreated_att() {
        return created_att;
    }

    public long getEnd_datetime() {
        return end_datetime;
    }

    public String getHost_id() {
        return host_id;
    }

    public String getHost_name() {
        return host_name;
    }

    public String getHost_img_url() {
        return host_img_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getHashtag() {
        return hashtag;
    }

    public int getDistance() {
        return distance;
    }

    public int getParticipants_count() {
        return participants_count;
    }

    public List<String> getParticipants() {
        return participants;
    }
}