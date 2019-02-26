package com.ninaad.gitdiff.ds;

/**
 * Created by ninaad on 2/26/19.
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

    public int getmPullId() {
        return mPullId;
    }

    public void setmPullId(int mPullId) {
        this.mPullId = mPullId;
    }

    public String getmPullUrl() {
        return mPullUrl;
    }

    public void setmPullUrl(String mPullUrl) {
        this.mPullUrl = mPullUrl;
    }

    public int getmPullNumber() {
        return mPullNumber;
    }

    public void setmPullNumber(int mPullNumber) {
        this.mPullNumber = mPullNumber;
    }

    public String getmPullDiffUrl() {
        return mPullDiffUrl;
    }

    public void setmPullDiffUrl(String mPullDiffUrl) {
        this.mPullDiffUrl = mPullDiffUrl;
    }

    public String getmUserLogin() {
        return mUserLogin;
    }

    public void setmUserLogin(String mUserLogin) {
        this.mUserLogin = mUserLogin;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmDateCreated() {
        return mDateCreated;
    }

    public void setmDateCreated(String mDateCreated) {
        this.mDateCreated = mDateCreated;
    }

    public String getmDateUpdated() {
        return mDateUpdated;
    }

    public void setmDateUpdated(String mDateUpdated) {
        this.mDateUpdated = mDateUpdated;
    }
}
