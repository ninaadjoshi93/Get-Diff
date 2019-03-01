package com.ninaad.gitdiff.models;

/**
 * A class for keeping details about a git repository
 * Created by ninaad on 2/26/19.
 */
public class GDGitRepository {
    public String mGitRepositoryOwner;
    public String mGitRepositoryName;

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
