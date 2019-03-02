package com.ninaad.gitdiff.models;

/**
 * Created by ninaad on 3/2/19.
 */
public class GDGitDiffLine {
    private String mGitLine;
    private int mGitColor;

    public GDGitDiffLine(String mGitLine, int mGitColor) {
        this.mGitLine = mGitLine;
        this.mGitColor = mGitColor;
    }

    public String getGitLine() {
        return mGitLine;
    }

    public void setGitLine(String mGitLine) {
        this.mGitLine = mGitLine;
    }

    public int getGitColor() {
        return mGitColor;
    }

    public void setGitColor(int mGitColor) {
        this.mGitColor = mGitColor;
    }
}
