package com.ninaad.gitdiff.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
    private GDGitRepository gitRepository;

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
        if (intent.hasExtra("repositoryDetails"))
        {
            gitRepository = (GDGitRepository) intent.getSerializableExtra("repositoryDetails");
        } else {
            gitRepository = new GDGitRepository(getString(R.string.repo_owner), getString(R.string
                    .repo_name));
        }

        activityPullRequestsListBinding.setRepositoryName(gitRepository);

        if (GDUtilities.isNetworkAvailable(this)) {

            gitRequestsViewModel.getGitPRListLiveData(gitRepository.getGitRepositoryOwner(),
                    gitRepository.getGitRepositoryName())
                    .observe(this,
                    gitPRList -> {
                if (null != gitPRList && gitPRList.size() > 0) {
                    activityPullRequestsListBinding.loadingListPb.setVisibility(View.GONE);
                    activityPullRequestsListBinding.pullRequestsScreenRl.setVisibility(View.VISIBLE);
                    pullRequestsAdapter.setPullRequestsList(gitPRList);
                } else {
                    Toast.makeText(this, "Sorry! No pull requests in this repository. :(", Toast
                            .LENGTH_SHORT).show();
                    this.finish();
                }
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
                mPullDiffIntent.putExtra("repositoryDetails", gitRepository);
                Toast.makeText(this, "For comparing the difference line by line, " +
                        "I would suggest the landscape orientation", Toast.LENGTH_LONG).show();
                startActivity(mPullDiffIntent);
            }
        });

        activityPullRequestsListBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Visit the web page of the repository", Snackbar.LENGTH_LONG)
                        .setAction("VISIT", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent visitSiteIntent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("https://github.com/"
                                                + gitRepository.getGitRepositoryOwner() + "/"
                                                + gitRepository.getGitRepositoryName()));
                                startActivity(visitSiteIntent);
                            }
                        }).setActionTextColor(Color.YELLOW).show();
            }
        });
    }
}
