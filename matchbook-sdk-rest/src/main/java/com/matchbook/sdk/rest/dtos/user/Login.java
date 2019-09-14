package com.matchbook.sdk.rest.dtos.user;

import com.matchbook.sdk.rest.dtos.RestResponse;

import java.util.Collections;
import java.util.Set;

public class Login implements RestResponse<Login> {

    private String sessionToken;
    private Long userId;
    private Account account;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Set<Login> getContent() {
        return Collections.singleton(this);
    }

    @Override
    public String toString() {
        return Login.class.getSimpleName() + " {" +
                "sessionToken=" + sessionToken +
                ", userId=" + userId +
                ", account=" + account +
                "}";
    }

}
