# What is MockWebServer?
MockWebServer is a library that takes our request, sets up the mock server, hits the localhost instead of the actual URL & returns the response which we have set. It also tests that your code survives in awkward-to-reproduce situations like 500 errors or slow-loading responses.

## Below are the steps to perform API mocking with MockWebServer:
1. Add dependency:
Update build.gradle with
```
androidTestImplementation 'com.squareup.okhttp3:mockwebserver:$latest_version'
```

2. Setup the mock server:
- Create a MockWebServer object
- Start the server
- Update the BASE_URL with localhost
- Add the expected response body in enqueue()
- Launch the activity
```
MockWebServer mockWebServer = new MockWebServer();
mockWebServer.start();
ApiUrls.BASE_URL = mockWebServer.url("/").toString();
mockWebServer.enqueue(new MockResponse().setBody(response_data_string));
activityRule.launchActivity(intent);
```

## In case of multiple API calls:
Let's say we have multple API calls, first to get the movie details & the second one to get character details.
For example:
```// First API call to get movie details
movieDetailsViewModel.initMovieDetailsAPI();
movieDetailsViewModel.getMovieDetails().observe(this,
movieDetailResponse -> {
	if (movieDetailResponse != null) {
		activityMainBinding.tvMovieDetails
		.setText(movieDetailResponse.getTitle());
        }});


// Second API call to get a movie character details
movieCharacterViewModel.initMovieCharacterDetailsAPI();
movieCharacterViewModel.getMovieCharacterDetails().observe(this, movieCharacterResponses -> {
     if (movieCharacterResponses != null) {
		activityMainBinding.tvCharacterDetails
		.setText(movieCharacterResponses.getName());
        }});
```
enqueue() returns a response without considering the order of requests. But because of asynchronous API calls, we can't predict its sequence. Hence the enqueue method is not the solution to this.

### Solution:
Use a Dispatcher to handle requests based on request paths. Here we can add the condition which checks the API path and accordingly dispatches the response.
```final Dispatcher dispatcher = new Dispatcher() {
@Override
public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        if (request.getPath().equals("url_path")) {
            //Return the data with response code
        } else if (request.getPath().equals("another_url_path")) {
            //Return the data with response code
        }
        return new MockResponse().setResponseCode(404);
    }
};
```
```
mockWebServer.setDispatcher(dispatcher);
```

## How to handle asynchronous calls:
Because of asynchronous calls, assert statements are getting executed before we receive a mocked API response.

### Solution:
To stall the execution of assert statements before receiving mocked API response, we need to add some time lag. We can achieve that by using `System.sleep(`) method, but below are a few better solutions:
1. With the help of Espresso Idling Resources.
2. With the help of Awaitility library.
We are using the Awaitility library to handle this asynchronous operation. One of its features is, we can add the waiting time.
For example:

```
await().atMost(10, SECONDS).until(() -> write_your_waiting_condition);
```

In the above code, we have specified a maximum waiting time of 10 seconds and once the expected response arrives within the specified time, the remaining code gets executed.

Check my blog [How To Test API Response With MockWebServer Library The Right Way](https://blog.kiprosh.com/how-to-test-api-response-with-mockwebserver-library/) for more details.
