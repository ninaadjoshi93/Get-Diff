package com.ninaad.gitdiff.ds;

/**
 * Created by ninaad on 2/26/19.
 * A class to contain the required fields of the git pull requests
 */
public class GBGitPullRequest {
    private int mPullId;
    private String mPullUrl;
    private int mPullNumber;
    private String mPullDiffUrl;
    private String mUserLogin;
    private String mUserId;
    private String mDateCreated;
    private String mDateUpdated;


    public GBGitPullRequest(int mPullId, String mPullUrl, int mPullNumber,
                            String mPullDiffUrl, String mUserLogin,
                            String mUserId, String mDateCreated,
                            String mDateUpdated) {
        this.mPullId = mPullId;
        this.mPullUrl = mPullUrl;
        this.mPullNumber = mPullNumber;
        this.mPullDiffUrl = mPullDiffUrl;
        this.mUserLogin = mUserLogin;
        this.mUserId = mUserId;
        this.mDateCreated = mDateCreated;
        this.mDateUpdated = mDateUpdated;
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
}
