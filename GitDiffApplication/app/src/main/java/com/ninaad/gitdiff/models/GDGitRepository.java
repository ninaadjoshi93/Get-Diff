package com.ninaad.gitdiff.models;

import java.io.Serializable;

/**
 * A class for keeping details about a git repository
 * Created by ninaad on 2/26/19.
 */
public class GDGitRepository implements Serializable {
    private String mGitRepositoryOwner;
    private String mGitRepositoryName;

    public GDGitRepository(String mGitRepositoryOwner, String mGitRepositoryName) {
        this.mGitRepositoryOwner = mGitRepositoryOwner;
        this.mGitRepositoryName = mGitRepositoryName;
    }

    public String getGitRepositoryOwner() {
        return mGitRepositoryOwner;
    }

    public void setGitRepositoryOwner(String gitRepositoryOwner) {
        this.mGitRepositoryOwner = gitRepositoryOwner;
    }

    public String getGitRepositoryName() {
        return mGitRepositoryName;
    }

    public void setGitRepositoryName(String mGitRepositoryName) {
        this.mGitRepositoryName = mGitRepositoryName;
    }
}
