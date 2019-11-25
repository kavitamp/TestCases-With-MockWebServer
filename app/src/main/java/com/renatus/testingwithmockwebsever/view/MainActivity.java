package com.renatus.testingwithmockwebsever.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.renatus.testingwithmockwebsever.R;
import com.renatus.testingwithmockwebsever.databinding.ActivityMainBinding;
import com.renatus.testingwithmockwebsever.viewModel.CommentsViewModel;
import com.renatus.testingwithmockwebsever.viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    MainViewModel mainViewModel;
    CommentsViewModel commentsViewModel;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        mainViewModel.init();
        mainViewModel.getPosts().observe(this, posts -> {

            if (posts != null) {
                activityMainBinding.tvFirstPost.setText(posts.get(0).getTitle());
            }
        });

        commentsViewModel.init();
        commentsViewModel.getComment().observe(this, comments -> {
            if (comments != null) {
                activityMainBinding.tvFirstComment.setText(comments.get(0).getEmail());
            }
        });
    }
}
