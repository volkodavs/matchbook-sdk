package com.matchbook.sdk;

import java.util.Arrays;

public class ClientConfig {

    private final char[] username;
    private final char[] password;
    private final String url;
    private final String loginUrl;

    private ClientConfig(ClientConfig.Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.url = builder.url;
        this.loginUrl = builder.loginUrl;
    }

    public String getUrl() {
        return url;
    }

    public char[] getUsername() {
        return username;
    }

    public char[] getPassword() {
        return password;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    @Override
    public String toString() {
        return ClientConfig.class.getSimpleName() + "{" +
                "username=" + Arrays.toString(username) +
                ", password='" + Arrays.toString(password) +
                ", url=" + url +
                ", loginUrl=" + loginUrl +
                "}";
    }

    public static class Builder {

        private final char[] username;
        private final char[] password;

        private String url;
        private String loginUrl;

        public Builder(char[] username, char[] password) {
            this.username = username;
            this.password = password;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder loginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
            return this;
        }

        public ClientConfig build() {
            return new ClientConfig(this);
        }
    }
}