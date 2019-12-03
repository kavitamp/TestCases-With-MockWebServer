package com.renatus.testingwithmockwebsever.models.repositories;

import androidx.lifecycle.MutableLiveData;

import com.renatus.testingwithmockwebsever.configs.RetrofitService;
import com.renatus.testingwithmockwebsever.models.responses.MovieDetailResponse;
import com.renatus.testingwithmockwebsever.models.services.contract.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailRepository {

    private static MovieDetailRepository newsRepository;
    private APIService apiService;

    private MovieDetailRepository() {
        apiService = RetrofitService.createService(APIService.class);
    }

    public static MovieDetailRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new MovieDetailRepository();
        }
        return newsRepository;
    }

    public MutableLiveData<MovieDetailResponse> getMovieCharacterDetails() {
        final MutableLiveData<MovieDetailResponse> movieDetailResponseMutableLiveData = new MutableLiveData<>();
        apiService.getMovieDetails(1).enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                MovieDetailResponse movieDetailResponse = response.body();
                movieDetailResponseMutableLiveData.setValue(movieDetailResponse);
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable throwable) {
                MovieDetailResponse MovieDetailResponse = new MovieDetailResponse();
                MovieDetailResponse.setErrorCode(404);
                movieDetailResponseMutableLiveData.setValue(MovieDetailResponse);
            }
        });

        return movieDetailResponseMutableLiveData;
    }
}
