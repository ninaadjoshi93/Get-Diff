package com.ninaad.gitdiff.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * A class to contain the required fields of the git pull requests
 * Created by ninaad on 2/26/19.
 */
public class GDGitPR implements Serializable {
    @SerializedName("id")
    @Expose
    private int mPullId;
    @SerializedName("url")
    @Expose
    private String mPullUrl;
    @SerializedName("number")
    @Expose
    private int mPullNumber;
    @SerializedName("diff_url")
    @Expose
    private String mPullDiffUrl;
    @SerializedName("created_at")
    @Expose
    private String mDateCreated;
    @SerializedName("updated_at")
    @Expose
    private String mDateUpdated;
    @SerializedName("title")
    @Expose
    private String mPullTitle;
    @SerializedName("user")
    @Expose
    private GDGitUser mGitUser;


    public GDGitPR(int mPullId, String mPullUrl, int mPullNumber,
                   String mPullDiffUrl, String mDateCreated,
                   String mDateUpdated, String mPullTitle, GDGitUser user) {
        this.mPullId = mPullId;
        this.mPullUrl = mPullUrl;
        this.mPullNumber = mPullNumber;
        this.mPullDiffUrl = mPullDiffUrl;
        this.mDateCreated = mDateCreated;
        this.mDateUpdated = mDateUpdated;
        this.mPullTitle = mPullTitle;
        this.mGitUser = user;
    }

    public int getPullId() {
        return mPullId;
    }

    public void setPullId(int mPullId) {
        this.mPullId = mPullId;
    }

    public String getPullUrl() {
        return mPullUrl;
    }

    public void setPullUrl(String mPullUrl) {
        this.mPullUrl = mPullUrl;
    }

    public int getPullNumber() {
        return mPullNumber;
    }

    public void setPullNumber(int mPullNumber) {
        this.mPullNumber = mPullNumber;
    }

    public String getPullDiffUrl() {
        return mPullDiffUrl;
    }

    public void setPullDiffUrl(String mPullDiffUrl) {
        this.mPullDiffUrl = mPullDiffUrl;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String mDateCreated) {
        this.mDateCreated = mDateCreated;
    }

    public String getDateUpdated() {
        return mDateUpdated;
    }

    public void setDateUpdated(String mDateUpdated) {
        this.mDateUpdated = mDateUpdated;
    }

    public String getPullTitle() {
        return mPullTitle;
    }

    public void setPullTitle(String mPullTitle) {
        this.mPullTitle = mPullTitle;
    }

    public GDGitUser getGitUser() {
        return mGitUser;
    }

    public void setGitUser(GDGitUser mGitUser) {
        this.mGitUser = mGitUser;
    }

}
