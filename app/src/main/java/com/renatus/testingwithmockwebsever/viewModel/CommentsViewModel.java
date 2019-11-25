package com.renatus.testingwithmockwebsever.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.renatus.testingwithmockwebsever.models.repositories.CommentsRepository;
import com.renatus.testingwithmockwebsever.models.responses.Comment;

import java.util.List;

public class CommentsViewModel extends AndroidViewModel {
    Application application;
    private MutableLiveData<List<Comment>> mutableLiveData;
    private CommentsRepository commentsRepository;

    public CommentsViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        commentsRepository = CommentsRepository.getInstance(application);
        mutableLiveData = commentsRepository.getComments();

    }

    public MutableLiveData<List<Comment>> getComment() {
        return mutableLiveData;
    }
}
