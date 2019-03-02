package com.ninaad.gitdiff.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ninaad.gitdiff.R;
import com.ninaad.gitdiff.databinding.ActivityPullDifferenceBinding;
import com.ninaad.gitdiff.models.GDGitPR;
import com.ninaad.gitdiff.models.GDGitRepository;
import com.ninaad.gitdiff.viewmodel.GDGitRequestsViewModel;

public class GDPullDifferenceActivity extends AppCompatActivity {
    private ActivityPullDifferenceBinding activityPullDifferenceBinding;
    private GDGitRequestsViewModel gitRequestsViewModel;
    private GDGitPR gdGitPR;
    private GDGitRepository gitRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPullDifferenceBinding = DataBindingUtil.setContentView(this, R.layout
                .activity_pull_difference);

        gitRequestsViewModel = ViewModelProviders.of(this).get
                (GDGitRequestsViewModel.class);

        Intent intent = getIntent();

        if (intent.hasExtra("repositoryDetails") && intent.hasExtra("git_pull_object"))
        {
            gdGitPR = (GDGitPR) intent.getSerializableExtra("git_pull_object");
            gitRepository = (GDGitRepository) intent.getSerializableExtra("repositoryDetails");
        } else {
            gitRepository = new GDGitRepository(getString(R.string.repo_owner), getString(R
                    .string.repo_name));
        }

        gitRequestsViewModel.init();

        activityPullDifferenceBinding.setRepositoryName(gitRepository);

        activityPullDifferenceBinding.loadingDiffPb.setVisibility(View.VISIBLE);
        activityPullDifferenceBinding.gitDiffRl.setVisibility(View.GONE);

        gitRequestsViewModel.getGitDiff(gdGitPR.getPullDiffUrl()).observe(this, mGitDifference ->{
            if (null != mGitDifference && mGitDifference.length() > 0) {
                activityPullDifferenceBinding.setPrevious(mGitDifference);
                activityPullDifferenceBinding.setNext(mGitDifference);
                activityPullDifferenceBinding.loadingDiffPb.setVisibility(View.GONE);
                activityPullDifferenceBinding.gitDiffRl.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Sorry. Something went wrong. I couldn't find a difference",
                        Toast.LENGTH_SHORT).show();
                this.finish();
            }
        });

        activityPullDifferenceBinding.previousSv.setOnScrollChangeListener((view, newX, newY, oldX, oldY) -> {
            activityPullDifferenceBinding.nextSv.scrollTo(newX, newY);
        });

        activityPullDifferenceBinding.nextSv.setOnScrollChangeListener((view, newX, newY, oldX, oldY) -> {
            activityPullDifferenceBinding.previousSv.scrollTo(newX, newY);
        });

    }
}
