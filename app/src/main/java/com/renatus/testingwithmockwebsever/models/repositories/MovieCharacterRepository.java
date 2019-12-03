package com.renatus.testingwithmockwebsever.models.repositories;

import androidx.lifecycle.MutableLiveData;

import com.renatus.testingwithmockwebsever.configs.RetrofitService;
import com.renatus.testingwithmockwebsever.models.responses.MovieCharacterResponse;
import com.renatus.testingwithmockwebsever.models.services.contract.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieCharacterRepository {

    private static MovieCharacterRepository newsRepository;
    private APIService apiService;

    private MovieCharacterRepository() {
        apiService = RetrofitService.createService(APIService.class);
    }

    public static MovieCharacterRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new MovieCharacterRepository();
        }
        return newsRepository;
    }

    public MutableLiveData<MovieCharacterResponse> getMovieCharacterDetails() {
        final MutableLiveData<MovieCharacterResponse> movieCharacterResponseMutableLiveData = new MutableLiveData<>();
        apiService.getMovieCharacterData(1).enqueue(new Callback<MovieCharacterResponse>() {
            @Override
            public void onResponse(Call<MovieCharacterResponse> call, Response<MovieCharacterResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                MovieCharacterResponse movieCharacterResponses = response.body();
                movieCharacterResponseMutableLiveData.setValue(movieCharacterResponses);
            }

            @Override
            public void onFailure(Call<MovieCharacterResponse> call, Throwable throwable) {
                MovieCharacterResponse movieCharacterResponse = new MovieCharacterResponse();
                movieCharacterResponse.setErrorCode(404);
                movieCharacterResponseMutableLiveData.setValue(movieCharacterResponse);
            }
        });

        return movieCharacterResponseMutableLiveData;
    }
}
