package com.renatus.testingwithmockwebsever.models.repositories;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.renatus.testingwithmockwebsever.configs.RetrofitService;
import com.renatus.testingwithmockwebsever.models.responses.Comment;
import com.renatus.testingwithmockwebsever.models.services.contract.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsRepository {

    private static CommentsRepository newsRepository;
    private APIService apiService;

    private CommentsRepository() {
        apiService = RetrofitService.createService(APIService.class);
    }

    public static CommentsRepository getInstance(Application application) {
        if (newsRepository == null) {
            newsRepository = new CommentsRepository();
        }
        return newsRepository;
    }

    public MutableLiveData<List<Comment>> getComments() {
        final MutableLiveData<List<Comment>> listMutableLiveData = new MutableLiveData<>();
        apiService.getComments(3).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<Comment> comments = response.body();
                List<Comment> listOfComments = new ArrayList<>();

                if (comments != null) {
                    for (Comment comment : comments) {
                        comment.setErrorCode(response.code());
                        listOfComments.add(comment);
                    }
                }
                listMutableLiveData.setValue(listOfComments);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable throwable) {
                Comment comment = new Comment();
                comment.setErrorCode(404);
                List<Comment> commentList = new ArrayList<>();
                commentList.add(comment);
                listMutableLiveData.setValue(commentList);
            }
        });

        return listMutableLiveData;
    }
}
