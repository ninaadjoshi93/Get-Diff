package com.ninaad.gitdiff.api;

import com.ninaad.gitdiff.models.GDGitPR;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GDGitRequestsService {

    @GET("zen")
    Call<ResponseBody> getQuote();

    @GET("repos/{owner}/{repository}/pulls?state=open")
    Call<List<GDGitPR>> getRepo(@Path("owner") String owner, @Path("repository") String repository);

    @GET
    Call<String> getGitDiff(@Url String urlLink);
}
