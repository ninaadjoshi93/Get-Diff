package com.ninaad.gitdiff.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ninaad.gitdiff.R;
import com.ninaad.gitdiff.databinding.ActivityLandingBinding;
import com.ninaad.gitdiff.models.GDGitRepository;
import com.ninaad.gitdiff.utils.GDUtilities;
import com.ninaad.gitdiff.viewmodel.GDGitRequestsViewModel;

public class GDLandingActivity extends AppCompatActivity {

    private ActivityLandingBinding activityLandingBinding;
    private GDGitRequestsViewModel gitRequestsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLandingBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_landing);
        gitRequestsViewModel = ViewModelProviders.of(this).get
                (GDGitRequestsViewModel.class);

        activityLandingBinding.loadingQuotePb.setVisibility(View.VISIBLE);
        activityLandingBinding.landingScreenRl.setVisibility(View.GONE);

        gitRequestsViewModel.init();

        if (GDUtilities.isNetworkAvailable(this)) {
            gitRequestsViewModel.getGitQuote().observe(this, mQuote -> {
                if (null != mQuote && mQuote.length() > 0){
                    activityLandingBinding.loadingQuotePb.setVisibility(View.GONE);
                    activityLandingBinding.landingScreenRl.setVisibility(View.VISIBLE);
                    activityLandingBinding.setGitQuote(mQuote);
                }
            });
        } else {
            activityLandingBinding.loadingQuotePb.setVisibility(View.GONE);
            activityLandingBinding.landingScreenRl.setVisibility(View.VISIBLE);
            activityLandingBinding.setGitQuote(getString(R.string.no_internet));
        }
    }


    public void goToYourRepository(View view){

        if (!GDUtilities.isNetworkAvailable(this)){
            return;
        }
        activityLandingBinding.goToPullRequestsListBtn.setEnabled(false);
        activityLandingBinding.selectMyRepositoryBtn.setEnabled(false);
        String[] mOwnerNameAndRepo = activityLandingBinding.repositoryUrlEt.getText()
                .toString().split(",+ *");
        String message;
        String mRepoOwner = getResources().getString(R.string.repo_owner);
        String mRepoName = getResources().getString(R.string.repo_name);

        // if you provide valid owner and repository names
        if (mOwnerNameAndRepo.length == 2){
            // if you provide me pull requests to my favorite repository
            if (mOwnerNameAndRepo[0].equals(mRepoOwner) && mOwnerNameAndRepo[1].equals(mRepoName)) {
                message = "Woah! Your repository is the same as my favorite " +
                        "repository. Let's check it out. :)";
            } else {
                message = "Cool. Let's check your repository. :)";
            }
            activityLandingBinding.setGitRepository(new GDGitRepository(mOwnerNameAndRepo[0],
                    mOwnerNameAndRepo[1]));
        }
        // if you don't provide valid owner and repository names
        else {
            activityLandingBinding.setGitRepository(new GDGitRepository(mRepoOwner, mRepoName));
            message = "Oops! Your repository is not valid. I will " +
                    "show you my favorite repository instead. :)";

        }
        showSnackBar(view, message, activityLandingBinding.getGitRepository().getGitRepositoryOwner(),
                activityLandingBinding.getGitRepository().getmGitRepositoryName());
    }

    public void goToMyRepository(View view){
        if (!GDUtilities.isNetworkAvailable(this)){
            return;
        }
        activityLandingBinding.goToPullRequestsListBtn.setEnabled(false);
        activityLandingBinding.selectMyRepositoryBtn.setEnabled(false);
        String message = "Hooray! Check out my favorite " +
                "repository. :)";
        String mRepoOwner = getResources().getString(R.string.repo_owner);
        String mRepoName = getResources().getString(R.string.repo_name);
        showSnackBar(view, message, mRepoOwner, mRepoName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityLandingBinding.goToPullRequestsListBtn.setEnabled(true);
        activityLandingBinding.selectMyRepositoryBtn.setEnabled(true);
    }

    private void showSnackBar(View view, String message, final String mRepoOwner, final String
            mRepoName){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .addCallback(new Snackbar.Callback(){

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                            // Snackbar closed on its own
                            // then go to the next activity
                            Intent goToGitPullRequestsIntent = new Intent
                                    (GDLandingActivity.this, GDPullRequestsListActivity
                                            .class);
                            goToGitPullRequestsIntent.putExtra("owner", mRepoOwner);
                            goToGitPullRequestsIntent.putExtra("name", mRepoName);
//                            startActivity(goToGitPullRequestsIntent);
                        }
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                    }
                }).show();
    }
}
