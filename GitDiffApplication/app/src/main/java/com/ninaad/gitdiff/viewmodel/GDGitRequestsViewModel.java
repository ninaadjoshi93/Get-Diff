package com.ninaad.gitdiff.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ninaad.gitdiff.models.GDGitPR;
import com.ninaad.gitdiff.repositories.GDRepository;

import java.util.List;

public class GDGitRequestsViewModel extends ViewModel {
    private static final String TAG = GDGitRequestsViewModel.class.getName();
    private GDRepository gdRepository = null;
    private LiveData<String> gitQuote = null;
    private LiveData<List<GDGitPR>> gitPRListLiveData = null;
    private LiveData<String> gitDiff = null;

    public GDGitRequestsViewModel(){}

    public void init(){
        if (null != gdRepository){
            return;
        }
        Log.d(TAG, "repo initialized");
        gdRepository = GDRepository.getInstance();

    }

    public LiveData<String> getGitQuote() {
        if (null == gitQuote)
            gitQuote = gdRepository.getQuote();
        return gitQuote;
    }

    public LiveData<List<GDGitPR>> getGitPRListLiveData(String owner, String repository) {
        if (null == gitPRListLiveData)
            gitPRListLiveData = gdRepository.getPullRequests(owner, repository);
        return gitPRListLiveData;
    }

    public LiveData<String> getGitDiff(String mGitDiffUrl) {
        if (null == gitDiff)
            gitDiff = gdRepository.getGitDiff(mGitDiffUrl);
        return gitDiff;
    }
}
