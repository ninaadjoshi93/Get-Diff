package com.ninaad.gitdiff.models;

import java.util.List;

/**
 * A class containing a list of pull requests to a git repository
 * Created by ninaad on 2/27/19.
 */
public class GDGitPRList {
    private List<GDGitPR> gitPullRequests;

    public GDGitPRList(List<GDGitPR> gitPullRequests) {
        this.gitPullRequests = gitPullRequests;
    }

    public List<GDGitPR> getGitPullRequests() {
        return gitPullRequests;
    }

    public void setGitPullRequests(List<GDGitPR> gitPullRequests) {
        this.gitPullRequests = gitPullRequests;
    }
}
