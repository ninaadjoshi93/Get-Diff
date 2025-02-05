package com.ninaad.gitdiff.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.ninaad.gitdiff.BuildConfig;
import com.ninaad.gitdiff.api.GDGitRequestsService;
import com.ninaad.gitdiff.models.GDGitPR;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GDRepository {
    private static final String TAG = GDRepository.class.getName();
    private static GDRepository instance = null;
    private GDGitRequestsService mGitRequestsService;
    private List<GDGitPR> mGitPRList = null;
    private String mQuote = null;
    private String mGitDiff = null;
    private MutableLiveData<String> mQuoteData = null;
    private MutableLiveData<List<GDGitPR>> mGitPRListData = null;
    private MutableLiveData<String> mGitDiffData = null;

    /**
     * Singleton Instance
     * @return An instance of GDRepository
     */
    public static GDRepository getInstance() {
        if (instance == null) {
            instance = new GDRepository();
        }
        return instance;
    }

    private GDRepository() {
        this.mGitRequestsService = provideGitPullRequestsService();
    }

    /**
     * A method to create a Retrofit instance for web apis
     * @return A retrofit instance
     */
    private GDGitRequestsService provideGitPullRequestsService() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GDGitRequestsService.class);
    }

    /**
     * A public method to fetch pull requests of a given public repository
     * @param owner The owner of the repository
     * @param repository The name of the repository
     * @return A list of open pull requests of that repository
     */
    public MutableLiveData<List<GDGitPR>> getPullRequests(String owner, String repository) {
        mGitPRListData = new MutableLiveData<>();
        getPullRequestsData(owner, repository);
        return mGitPRListData;
    }

    /**
     * An internal method to populate the pull requests from a web api
     * @param owner The owner of the repository
     * @param repository The name of the repository
     */
    private void getPullRequestsData(String owner, String repository) {
        mGitRequestsService.getRepo(owner, repository).enqueue(new Callback<List<GDGitPR>>() {
            @Override
            public void onResponse(Call<List<GDGitPR>> call, Response<List<GDGitPR>> response) {
                Log.d(TAG, "git pull request = " + call.request());
                if (response.body() != null) {
                    mGitPRList = response.body();

                    for (GDGitPR gitPR : mGitPRList){
                        String createdTime = gitPR.getDateCreated();
                        String updatedTime = gitPR.getDateUpdated();
                        SimpleDateFormat inputFormat = new SimpleDateFormat
                                ("yyyy-MM-dd'T'HH:mm:ss'Z'",
                                Locale.US);
                        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy",
                                Locale.US);
                        Date createdDate = null;
                        Date updatedDate = null;
                        try {
                            createdDate = inputFormat.parse(createdTime);
                            updatedDate = inputFormat.parse(updatedTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        gitPR.setDateCreated(outputFormat.format(createdDate));
                        gitPR.setDateUpdated(outputFormat.format(updatedDate));
                    }

                    mGitPRListData.postValue(mGitPRList);
                } else {
                    mGitPRList = null;
                    mGitPRListData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<GDGitPR>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t + " call: " + call.request().toString());
                mGitPRList = null;
                mGitPRListData.postValue(null);
            }
        });
    }


    /**
     * A public method to fetch a quote from github
     * @return A string containing the quote
     */
    public MutableLiveData<String> getQuote() {
        mQuoteData = new MutableLiveData<>();
        getQuoteData();
        return mQuoteData;
    }

    /**
     * An internal method to get the github quote from a web api
     */
    private void getQuoteData() {
        mGitRequestsService.getQuote().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "git pull request = " + call.request());
                if (response.raw() != null) {
                    try {
                        mQuote = response.body().string();
//                        Log.d("pull response", mQuote);
                        mQuoteData.postValue(mQuote);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mQuote = null;
                    mQuoteData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t + " call: " + call.request().toString());
                mQuote = null;
                mQuoteData.postValue(null);
            }
        });
    }

    /**
     * A public method to fetch a git diff string from github
     * @param mGitDiffUrl The git diff url string
     * @return A string containing the diff
     */
    public MutableLiveData<String> getGitDiff(String mGitDiffUrl){
        mGitDiffData = new MutableLiveData<>();
        getGitDiffData(mGitDiffUrl);
        return mGitDiffData;
    }

    /**
     * An internal method to get the git diff from a web api
     * @param mGitDiffUrl
     */
    private void getGitDiffData(String mGitDiffUrl) {
        mGitRequestsService.getGitDiff(mGitDiffUrl).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "git pull request = " + call.request());
                if (response.body() != null) {
                    try {
                        mGitDiff = response.body().string();
//                        Log.d("pull response", mGitDiff);

                        mGitDiffData.postValue(mGitDiff);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    mGitDiff = null;
                    mGitDiffData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t + " call: " + call.request().toString());
                mGitDiff = null;
                mGitDiffData.postValue(null);
            }
        });
    }

}
