package com.ninaad.gitdiff.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ninaad.gitdiff.R;
import com.ninaad.gitdiff.databinding.ActivityPullDifferenceBinding;
import com.ninaad.gitdiff.models.GDGitPR;
import com.ninaad.gitdiff.models.GDGitRepository;
import com.ninaad.gitdiff.utils.GDDiffGenerator;
import com.ninaad.gitdiff.viewmodel.GDGitRequestsViewModel;
import com.ninaad.gitdiff.views.adapters.GDGitDiffAdapter;

public class GDPullDifferenceActivity extends AppCompatActivity {
    private static final String TAG = GDPullDifferenceActivity.class.getName();
    private ActivityPullDifferenceBinding activityPullDifferenceBinding;
    private GDGitRequestsViewModel gitRequestsViewModel;
    private GDGitPR gdGitPR;
    private GDGitRepository gitRepository;
    private GDGitDiffAdapter gitPrevDiffAdapter;
    private GDGitDiffAdapter gitNextDiffAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPullDifferenceBinding = DataBindingUtil.setContentView(this, R.layout
                .activity_pull_difference);

        gitRequestsViewModel = ViewModelProviders.of(this).get
                (GDGitRequestsViewModel.class);
        gitPrevDiffAdapter = new GDGitDiffAdapter(this);
        gitNextDiffAdapter = new GDGitDiffAdapter(this);

        activityPullDifferenceBinding.prevRv
                .setLayoutManager(new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false));
        activityPullDifferenceBinding.prevRv.setHasFixedSize(true);

        activityPullDifferenceBinding.nextRv
                .setLayoutManager(new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false));
        activityPullDifferenceBinding.nextRv.setHasFixedSize(true);

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
                GDDiffGenerator generator = new GDDiffGenerator(this, mGitDifference);
                gitPrevDiffAdapter.setGitLineList(generator.getPreviousDiff());
                gitNextDiffAdapter.setGitLineList(generator.getNextDiff());
                activityPullDifferenceBinding.prevRv.setAdapter(gitPrevDiffAdapter);
                activityPullDifferenceBinding.nextRv.setAdapter(gitNextDiffAdapter);
                activityPullDifferenceBinding.loadingDiffPb.setVisibility(View.GONE);
                activityPullDifferenceBinding.gitDiffRl.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Sorry. Something went wrong. I couldn't find the difference",
                        Toast.LENGTH_SHORT).show();
                this.finish();
            }
        });


        /*
          Referred the following snippet to scroll the recycler views simultaneously from
          https://stackoverflow.com/questions/30702726/sync-scrolling-of-multiple-recyclerviews/31359767
         */

        final RecyclerView.OnScrollListener[] scrollListeners = new RecyclerView.OnScrollListener[2];
        scrollListeners[0] = new RecyclerView.OnScrollListener( )
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                activityPullDifferenceBinding.nextRv.removeOnScrollListener(scrollListeners[1]);
                activityPullDifferenceBinding.nextRv.scrollBy(dx, dy);
                activityPullDifferenceBinding.nextRv.addOnScrollListener(scrollListeners[1]);
            }
        };
        scrollListeners[1] = new RecyclerView.OnScrollListener( )
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                activityPullDifferenceBinding.prevRv.removeOnScrollListener(scrollListeners[0]);
                activityPullDifferenceBinding.prevRv.scrollBy(dx, dy);
                activityPullDifferenceBinding.prevRv.addOnScrollListener(scrollListeners[0]);
            }
        };
        activityPullDifferenceBinding.prevRv.addOnScrollListener(scrollListeners[0]);
        activityPullDifferenceBinding.nextRv.addOnScrollListener(scrollListeners[1]);

        activityPullDifferenceBinding.prevHsv.setOnScrollChangeListener((view, xNew, yNew, xOld, yOld) -> {
            activityPullDifferenceBinding.nextHsv.scrollTo(xNew, yNew);
        });

        activityPullDifferenceBinding.nextHsv.setOnScrollChangeListener((view, xNew, yNew, xOld, yOld) -> {
            activityPullDifferenceBinding.prevHsv.scrollTo(xNew, yNew);
        });


    }
}
