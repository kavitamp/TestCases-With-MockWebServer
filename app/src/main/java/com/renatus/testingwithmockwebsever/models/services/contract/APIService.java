package com.renatus.testingwithmockwebsever.models.services.contract;

import com.renatus.testingwithmockwebsever.models.responses.MovieCharacterResponse;
import com.renatus.testingwithmockwebsever.models.responses.MovieDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("people/{number}/")
    Call<MovieCharacterResponse> getMovieCharacterData(@Path("number") int postId);

    @GET("films/{number}/")
    Call<MovieDetailResponse> getMovieDetails(@Path("number") int postId);
}
