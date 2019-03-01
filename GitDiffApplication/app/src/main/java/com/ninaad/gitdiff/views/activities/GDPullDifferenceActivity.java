package com.ninaad.gitdiff.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ninaad.gitdiff.R;
import com.ninaad.gitdiff.databinding.ActivityPullDifferenceBinding;
import com.ninaad.gitdiff.models.GDGitPR;
import com.ninaad.gitdiff.viewmodel.GDGitRequestsViewModel;

public class GDPullDifferenceActivity extends AppCompatActivity {
    private ActivityPullDifferenceBinding activityPullDifferenceBinding;
    private GDGitRequestsViewModel gitRequestsViewModel;
    private GDGitPR gdGitPR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPullDifferenceBinding = DataBindingUtil.setContentView(this, R.layout
                .activity_pull_difference);

        gitRequestsViewModel = ViewModelProviders.of(this).get
                (GDGitRequestsViewModel.class);

        Intent intent = getIntent();

        if (intent.hasExtra("git_pull_object")){
            gdGitPR = (GDGitPR) intent.getSerializableExtra("git_pull_object");
        } else {
            finish();
        }

        gitRequestsViewModel.init();

        gitRequestsViewModel.getGitDiff().observe(this, mGitDifference ->{
            activityPullDifferenceBinding.setPrevious(mGitDifference);
            activityPullDifferenceBinding.setNext(mGitDifference);
        });

    }
}
