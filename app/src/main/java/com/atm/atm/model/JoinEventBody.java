package com.atm.atm.model;

/**
 * Created by denis on 11.09.16.
 */
public class JoinEventBody {
    public String user_id;

    public JoinEventBody(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
