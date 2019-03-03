package com.ninaad.gitdiff.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ninaad.gitdiff.models.GDGitPR;
import com.ninaad.gitdiff.repositories.GDRepository;

import java.util.List;

/**
 * Created by ninaad
 */
public class GDGitRequestsViewModel extends ViewModel {
    private static final String TAG = GDGitRequestsViewModel.class.getName();
    private GDRepository gdRepository = null;
    private LiveData<String> gitQuote = null;
    private LiveData<List<GDGitPR>> gitPRListLiveData = null;
    private LiveData<String> gitDiff = null;

    public GDGitRequestsViewModel(){}

    /**
     * A method to initialize the viewmodel
     */
    public void init(){
        if (null != gdRepository){
            return;
        }
        Log.d(TAG, "repo initialized");
        gdRepository = GDRepository.getInstance();

    }

    /**
     * A public method to get the quote from Git
     * @return The Git quote
     */
    public LiveData<String> getGitQuote() {
        if (null == gitQuote)
            gitQuote = gdRepository.getQuote();
        return gitQuote;
    }

    /**
     * A public method to get the list of pull requests
     * @param owner The owner of the Git repository
     * @param repository The name of the Git repository
     * @return A list of pull requests
     */
    public LiveData<List<GDGitPR>> getGitPRListLiveData(String owner, String repository) {
        if (null == gitPRListLiveData)
            gitPRListLiveData = gdRepository.getPullRequests(owner, repository);
        return gitPRListLiveData;
    }

    /**
     * A public method to get the difference data in the pull request
     * @param mGitDiffUrl The url link of the difference in the pull request
     * @return A string containing the difference data
     */
    public LiveData<String> getGitDiff(String mGitDiffUrl) {
        if (null == gitDiff)
            gitDiff = gdRepository.getGitDiff(mGitDiffUrl);
        return gitDiff;
    }
}
