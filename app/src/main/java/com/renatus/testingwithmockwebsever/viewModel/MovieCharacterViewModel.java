package com.renatus.testingwithmockwebsever.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.renatus.testingwithmockwebsever.models.repositories.MovieCharacterRepository;
import com.renatus.testingwithmockwebsever.models.responses.MovieCharacterResponse;

public class MovieCharacterViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<MovieCharacterResponse> mutableLiveData;

    public MovieCharacterViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        MovieCharacterRepository commentsRepository = MovieCharacterRepository.getInstance();
        mutableLiveData = commentsRepository.getMovieCharacterDetails();

    }

    public MutableLiveData<MovieCharacterResponse> getMovieCharacterDetails() {
        return mutableLiveData;
    }
}
