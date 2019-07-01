package com.matchbook.sdk.core.configs;

import java.io.InputStream;

import com.matchbook.sdk.core.exceptions.MatchbookSDKHttpException;

public interface HttpCallback {

    void onResponse(InputStream inputStream);

    void onFailure(MatchbookSDKHttpException exception);

}
