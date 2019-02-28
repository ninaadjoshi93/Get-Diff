package com.ninaad.gitdiff.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ninaad.gitdiff.R;
import com.ninaad.gitdiff.databinding.ActivityPullRequestsListBinding;

public class GDPullRequestsListActivity extends AppCompatActivity {
    private ActivityPullRequestsListBinding activityPullRequestsListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPullRequestsListBinding = DataBindingUtil.setContentView(this, R
                .layout.activity_pull_requests_list);
    }
}
