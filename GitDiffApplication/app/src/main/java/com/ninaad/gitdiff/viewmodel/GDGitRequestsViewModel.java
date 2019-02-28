package com.ninaad.gitdiff.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.ninaad.gitdiff.models.GDGitPRList;
import com.ninaad.gitdiff.repositories.GDRepository;

public class GDGitRequestsViewModel extends ViewModel {
    private static final String TAG = GDGitRequestsViewModel.class.getName();
    private GDRepository gdRepository;
    private LiveData<String> gitQuote = null;
    private LiveData<GDGitPRList> gitPRListLiveData;
    private LiveData<String> gitDiff;

    public GDGitRequestsViewModel(){}

    public void init(){
        if (null != gitQuote){
            return;
        }
        gdRepository = GDRepository.getInstance();

    }

    public LiveData<String> getGitQuote() {
        if (null == gitQuote)
            gitQuote = gdRepository.getQuote();
        return gitQuote;
    }

    public LiveData<GDGitPRList> getGitPRListLiveData() {
        return gitPRListLiveData;
    }

    public LiveData<String> getGitDiff() {
        return gitDiff;
    }
}
