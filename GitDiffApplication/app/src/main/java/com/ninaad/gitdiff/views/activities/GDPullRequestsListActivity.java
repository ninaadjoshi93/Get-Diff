package com.ninaad.gitdiff.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ninaad.gitdiff.R;
import com.ninaad.gitdiff.databinding.ActivityPullRequestsListBinding;
import com.ninaad.gitdiff.models.GDGitRepository;
import com.ninaad.gitdiff.utils.GDUtilities;
import com.ninaad.gitdiff.viewmodel.GDGitRequestsViewModel;
import com.ninaad.gitdiff.views.adapters.GDPullRequestsAdapter;

public class GDPullRequestsListActivity extends AppCompatActivity {
    private static final String TAG = GDPullDifferenceActivity.class.getName();
    private ActivityPullRequestsListBinding activityPullRequestsListBinding;
    private GDGitRequestsViewModel gitRequestsViewModel;
    private GDPullRequestsAdapter pullRequestsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPullRequestsListBinding = DataBindingUtil.setContentView(this, R
                .layout.activity_pull_requests_list);
        gitRequestsViewModel = ViewModelProviders.of(this).get(GDGitRequestsViewModel.class);
        pullRequestsAdapter = new GDPullRequestsAdapter(this);

        activityPullRequestsListBinding.loadingListPb.setVisibility(View.VISIBLE);
        activityPullRequestsListBinding.pullRequestsScreenRl.setVisibility(View.GONE);

        gitRequestsViewModel.init();

        activityPullRequestsListBinding.pullRequestsListRv
                .setLayoutManager(new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        String mRepoOwner;
        String mRepoName;
        if (intent.hasExtra("name") && intent.hasExtra("owner"))
        {
            mRepoOwner = intent.getStringExtra("owner");
            mRepoName = intent.getStringExtra("name");
        } else {
            mRepoName = getString(R.string.repo_name);
            mRepoOwner = getString(R.string.repo_owner);
        }

        activityPullRequestsListBinding.setRepositoryName(new GDGitRepository(mRepoOwner, mRepoName));

        if (GDUtilities.isNetworkAvailable(this)) {

            gitRequestsViewModel.getGitPRListLiveData(mRepoOwner, mRepoName).observe(this, gitPRList -> {
                activityPullRequestsListBinding.loadingListPb.setVisibility(View.GONE);
                activityPullRequestsListBinding.pullRequestsScreenRl.setVisibility(View.VISIBLE);
                pullRequestsAdapter.setPullRequestsList(gitPRList);

            });
        } else {
            this.finish();
        }
        activityPullRequestsListBinding.pullRequestsListRv.setAdapter(pullRequestsAdapter);

        pullRequestsAdapter.setClickListener((view, gitPRObject) -> {
            if (GDUtilities.isNetworkAvailable(this)) {

                Intent mPullDiffIntent = new Intent(GDPullRequestsListActivity.this,
                        GDPullDifferenceActivity.class);
                mPullDiffIntent.putExtra("git_pull_object", gitPRObject);
                startActivity(mPullDiffIntent);
            }
        });
    }
}
