package com.matchbook.sdk.rest.workers;

import com.matchbook.sdk.core.StreamObserver;
import com.matchbook.sdk.core.exceptions.MatchbookSDKException;
import com.matchbook.sdk.rest.UserClient;
import com.matchbook.sdk.rest.dtos.user.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionKeepAliveScheduler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(SessionKeepAliveScheduler.class);

    private final UserClient userClient;

    public SessionKeepAliveScheduler(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public void run() {
        userClient.login(new StreamObserver<Login>() {
            @Override
            public void onNext(Login login) {
                //do nothing
            }

            @Override
            public void onCompleted() {
                LOG.info("Session token successfully updated");
            }

            @Override
            public <E extends MatchbookSDKException> void onError(E exception) {
                LOG.warn("Fail to update session token", exception);
            }
        });
    }
}