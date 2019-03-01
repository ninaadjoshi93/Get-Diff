package com.ninaad.gitdiff.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * A class used to keep user details, used in the GDGitPR class
 * Created by ninaad on 2/27/19.
 */
public class GDGitUser implements Serializable {
    @SerializedName("login")
    @Expose
    private String mUserLogin;
    @SerializedName("id")
    @Expose
    private String mUserId;

    public GDGitUser(String mUserLogin, String mUserId) {
        this.mUserLogin = mUserLogin;
        this.mUserId = mUserId;
    }

    public String getUserLogin() {
        return mUserLogin;
    }

    public void setUserLogin(String mUserLogin) {
        this.mUserLogin = mUserLogin;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }
}

