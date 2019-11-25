package com.renatus.testingwithmockwebsever.models.services.contract;

import com.renatus.testingwithmockwebsever.models.responses.Comment;
import com.renatus.testingwithmockwebsever.models.responses.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface APIService {

    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);
}
