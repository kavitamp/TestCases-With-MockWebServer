package com.renatus.testingwithmockwebsever;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.test.rule.ActivityTestRule;

import com.renatus.testingwithmockwebsever.models.services.contract.ApiUrls;
import com.renatus.testingwithmockwebsever.view.MainActivity;
import com.renatus.testingwithmockwebsever.viewModel.CommentsViewModel;
import com.renatus.testingwithmockwebsever.viewModel.MainViewModel;

import org.junit.After;
import org.junit.Assert;
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
    final Dispatcher dispatcher = new Dispatcher() {

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            if (request.getPath().equals("/posts?_order=desc&userId=1&_sort=id")) {
                String fileName = "post_response.json";
                try {
                    return new MockResponse().setResponseCode(200).setBody(RestServiceTestHelper.getStringFromFile(getContext(), fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (request.getPath().equals("/posts/3/comments")) {
                String fileName = "comments_response.json";
                try {
                    return new MockResponse().setResponseCode(200).setBody(RestServiceTestHelper.getStringFromFile(getContext(), fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new MockResponse().setResponseCode(404);
        }
    };
    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(
            MainActivity.class, true, false);
    MockWebServer mockWebServer;

    @Test
    public void APITestMethods() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(dispatcher);
        mockWebServer.start();
        ApiUrls.BASE_URL = mockWebServer.url("/").toString();
        mainActivityRule.launchActivity(null);

        final MainViewModel mainViewModel = ViewModelProviders.of(mainActivityRule.getActivity()).get(MainViewModel.class);
        final CommentsViewModel commentsViewModel = ViewModelProviders.of(mainActivityRule.getActivity()).get(CommentsViewModel.class);
        await().atMost(5, SECONDS).until(() -> mainViewModel.getPosts().getValue() != null && commentsViewModel.getComment().getValue() != null);
        AppCompatTextView appCompatTextView = mainActivityRule.getActivity().findViewById(R.id.tv_first_post);
        AppCompatTextView TVFirstComment = mainActivityRule.getActivity().findViewById(R.id.tv_first_comment);

        String text = (String) appCompatTextView.getText();
        String firstComment = (String) TVFirstComment.getText();
        Assert.assertEquals("Kavita.....", text);
        Assert.assertEquals("kavitap@gmail.com", firstComment);

    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}
