package com.renatus.testingwithmockwebsever;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.test.rule.ActivityTestRule;

import com.renatus.testingwithmockwebsever.models.services.contract.ApiUrls;
import com.renatus.testingwithmockwebsever.view.MainActivity;
import com.renatus.testingwithmockwebsever.viewModel.MovieCharacterViewModel;
import com.renatus.testingwithmockwebsever.viewModel.MovieDetailsViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static androidx.test.InstrumentationRegistry.getContext;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class MainActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(
            MainActivity.class, true, false);
    private MockWebServer mockWebServer;

    private final Dispatcher dispatcher = new Dispatcher() {

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            if (request.getPath().equals("/films/1/")) {
                String fileName = "movie_details_response.json";
                try {
                    return new MockResponse().setResponseCode(200).setBody(RestServiceTestHelper.getStringFromFile(getContext(), fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (request.getPath().equals("/people/1/")) {
                String fileName = "movie_character_response.json";
                try {
                    return new MockResponse().setResponseCode(200).setBody(RestServiceTestHelper.getStringFromFile(getContext(), fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new MockResponse().setResponseCode(404);
        }
    };

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(dispatcher);
        mockWebServer.start();
        ApiUrls.BASE_URL = mockWebServer.url("/").toString();
        mainActivityRule.launchActivity(null);
    }

    @Test
    public void checkTextFieldsWithAPIResponse() {
        final MovieDetailsViewModel movieDetailsViewModel = ViewModelProviders.of(mainActivityRule.getActivity()).get(MovieDetailsViewModel.class);
        final MovieCharacterViewModel movieCharacterViewModel = ViewModelProviders.of(mainActivityRule.getActivity()).get(MovieCharacterViewModel.class);
        await().atMost(5, SECONDS).until(() -> movieDetailsViewModel.getMovieDetails().getValue() != null
                && movieCharacterViewModel.getMovieCharacterDetails().getValue() != null);
        AppCompatTextView tvMovieDetails = mainActivityRule.getActivity().findViewById(R.id.tv_movie_details);
        AppCompatTextView tvCharacterDetails = mainActivityRule.getActivity().findViewById(R.id.tv_character_details);

        String movieDetailsText = (String) tvMovieDetails.getText();
        String characterDetailsText = (String) tvCharacterDetails.getText();
        Assert.assertEquals("StarWar - A New Hope", movieDetailsText);
        Assert.assertEquals("Luke Skywalker.", characterDetailsText);

    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}
