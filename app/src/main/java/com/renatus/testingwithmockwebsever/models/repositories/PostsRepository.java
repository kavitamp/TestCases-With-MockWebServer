package com.renatus.testingwithmockwebsever.models.repositories;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.renatus.testingwithmockwebsever.configs.RetrofitService;
import com.renatus.testingwithmockwebsever.models.responses.Post;
import com.renatus.testingwithmockwebsever.models.services.contract.APIService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsRepository {

    private static PostsRepository newsRepository;
    private static Application application;
    private APIService apiService;

    public PostsRepository() {
        apiService = RetrofitService.createService(APIService.class);
    }

    public static PostsRepository getInstance(Application application) {
        PostsRepository.application = application;
        if (newsRepository == null) {
            newsRepository = new PostsRepository();
        }
        return newsRepository;
    }

    public MutableLiveData<List<Post>> getPosts() {
        final MutableLiveData<List<Post>> listMutableLiveData = new MutableLiveData<>();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        apiService.getPosts(parameters).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable throwable) {
                Post post = new Post();
                post.setErrorCode(404);
                List<Post> postList = new ArrayList<>();
                postList.add(post);
                listMutableLiveData.setValue(postList);
            }
        });
        return listMutableLiveData;
    }
}
