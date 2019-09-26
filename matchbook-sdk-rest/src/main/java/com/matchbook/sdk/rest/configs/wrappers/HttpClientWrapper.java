package com.matchbook.sdk.rest.configs.wrappers;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.matchbook.sdk.core.exceptions.ErrorType;
import com.matchbook.sdk.core.exceptions.MatchbookSDKHttpException;
import com.matchbook.sdk.rest.HttpConfig;
import com.matchbook.sdk.rest.configs.HttpCallback;
import com.matchbook.sdk.rest.configs.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpClientWrapper implements HttpClient {

    private static final String HTTP_HEADER_ACCEPT = "Accept";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String MEDIA_TYPE_JSON = "application/json";

    private final OkHttpClient httpClient;
    private final MediaType jsonMediaType;

    public HttpClientWrapper(HttpConfig httpConfig) {
        httpClient = initHttpClient(httpConfig);

        this.jsonMediaType = MediaType.parse(MEDIA_TYPE_JSON);
    }

    private OkHttpClient initHttpClient(HttpConfig httpConfig) {
        return new OkHttpClient.Builder()
                .connectTimeout(httpConfig.getConnectionTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(httpConfig.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(httpConfig.getReadTimeout(), TimeUnit.MILLISECONDS)
                .followRedirects(false)
                .cookieJar(new SDKCookieJar())
                .build();
    }

    @Override
    public void get(String url, HttpCallback httpCallback) throws MatchbookSDKHttpException {
        Request request = buildRequest(url)
                .get()
                .build();
        sendHttpRequest(request, new RequestCallback(httpCallback));
    }

    @Override
    public void post(String url, String body, HttpCallback httpCallback) throws MatchbookSDKHttpException {
        Request request = buildRequest(url)
                .post(RequestBody.create(body, jsonMediaType))
                .build();
        sendHttpRequest(request, new RequestCallback(httpCallback));
    }

    @Override
    public void put(String url, String body, HttpCallback httpCallback) throws MatchbookSDKHttpException {
        Request request = buildRequest(url)
                .put(RequestBody.create(body, jsonMediaType))
                .build();
        sendHttpRequest(request, new RequestCallback(httpCallback));
    }

    @Override
    public void delete(String url, HttpCallback httpCallback) throws MatchbookSDKHttpException {
        Request request = buildRequest(url)
                .delete()
                .build();
        sendHttpRequest(request, new RequestCallback(httpCallback));
    }

    private Request.Builder buildRequest(String url) {
        return new Request.Builder()
                .addHeader(HTTP_HEADER_CONTENT_TYPE, jsonMediaType.toString())
                .addHeader(HTTP_HEADER_ACCEPT, jsonMediaType.toString())
                .url(url);
    }

    private void sendHttpRequest(Request request, Callback callback) {
        httpClient.newCall(request).enqueue(callback);
    }

    private static class RequestCallback implements Callback {

        private final HttpCallback httpCallback;

        RequestCallback(HttpCallback httpCallback) {
            this.httpCallback = httpCallback;
        }

        @Override
        public void onResponse(Call call, Response response) {
            try (ResponseBody responseBody = response.body()) {
                if (response.isSuccessful() && Objects.nonNull(responseBody)) {
                    httpCallback.onResponse(responseBody.byteStream());
                } else {
                    MatchbookSDKHttpException matchbookException = getExceptionForResponse(response);
                    httpCallback.onFailure(matchbookException);
                }
            }
        }

        @Override
        public void onFailure(Call call, IOException exception) {
            MatchbookSDKHttpException matchbookException = new MatchbookSDKHttpException(exception.getMessage(), exception);
            httpCallback.onFailure(matchbookException);
        }

        private MatchbookSDKHttpException getExceptionForResponse(Response response) {
            try {
                ResponseBody responseBody = response.body();
                byte[] responseBytes = Objects.nonNull(responseBody) ? responseBody.bytes() : null;
                if (Objects.nonNull(responseBytes) && responseBytes.length > 0
                        && isAuthenticationErrorPresent(responseBytes)) {
                    return unauthenticatedException();
                }
            } catch (IOException e) {
                // do nothing
            }
            return httpException(response.code());
        }

        private boolean isAuthenticationErrorPresent(byte[] responseBytes) {
            return new String(responseBytes).toLowerCase().contains("cannot login");
        }

        private MatchbookSDKHttpException unauthenticatedException() {
            return new MatchbookSDKHttpException("Unable to authenticate user: invalid credentials", ErrorType.UNAUTHENTICATED);
        }

        private MatchbookSDKHttpException httpException(int responseCode) {
            return new MatchbookSDKHttpException("Unexpected HTTP code " + responseCode, ErrorType.HTTP);
        }

    }

}
