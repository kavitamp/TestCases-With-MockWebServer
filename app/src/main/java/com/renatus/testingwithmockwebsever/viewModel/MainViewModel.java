package com.renatus.testingwithmockwebsever.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.renatus.testingwithmockwebsever.models.repositories.PostsRepository;
import com.renatus.testingwithmockwebsever.models.responses.Post;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<List<Post>> mutableLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        PostsRepository postsRepository = PostsRepository.getInstance(application);
        mutableLiveData = postsRepository.getPosts();

    }

    public MutableLiveData<List<Post>> getPosts() {
        return mutableLiveData;
    }
}
