package com.renatus.testingwithmockwebsever.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.renatus.testingwithmockwebsever.R;
import com.renatus.testingwithmockwebsever.databinding.ActivityMainBinding;
import com.renatus.testingwithmockwebsever.viewModel.MovieCharacterViewModel;
import com.renatus.testingwithmockwebsever.viewModel.MovieDetailsViewModel;

public class MainActivity extends AppCompatActivity {
    MovieCharacterViewModel movieCharacterViewModel;
    MovieDetailsViewModel movieDetailsViewModel;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        movieCharacterViewModel = ViewModelProviders.of(this).get(MovieCharacterViewModel.class);
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);

        movieDetailsViewModel.init();
        movieDetailsViewModel.getMovieDetails().observe(this, movieDetailResponse -> {
            if (movieDetailResponse != null) {
                activityMainBinding.tvMovieDetails.setText(movieDetailResponse.getTitle());
            }
        });

        movieCharacterViewModel.init();
        movieCharacterViewModel.getMovieCharacterDetails().observe(this, movieCharacterResponses -> {
            if (movieCharacterResponses != null) {
                activityMainBinding.tvCharacterDetails.setText(movieCharacterResponses.getName());
            }
        });

    }
}
