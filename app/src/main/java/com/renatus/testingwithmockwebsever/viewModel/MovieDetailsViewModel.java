package com.renatus.testingwithmockwebsever.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.renatus.testingwithmockwebsever.models.repositories.MovieDetailRepository;
import com.renatus.testingwithmockwebsever.models.responses.MovieDetailResponse;

public class MovieDetailsViewModel extends AndroidViewModel {
    Application application;
    private MutableLiveData<MovieDetailResponse> mutableLiveData;
    private MovieDetailRepository movieDetailRepository;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        movieDetailRepository = MovieDetailRepository.getInstance(application);
        mutableLiveData = movieDetailRepository.getMovieCharacterDetails();

    }

    public MutableLiveData<MovieDetailResponse> getMovieDetails() {
        return mutableLiveData;
    }
}
