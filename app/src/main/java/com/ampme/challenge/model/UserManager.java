package com.ampme.challenge.model;

import com.facebook.AccessToken;

public class UserManager {

    private static UserManager mInstance;
    private AccessToken mAccessToken;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (mInstance == null) {
            mInstance = new UserManager();
        }
        return mInstance;
    }

    public void setLogin(AccessToken accessToken) {
        mAccessToken = accessToken;
    }

    public boolean isLoggedIn() {
        return mAccessToken != null;
    }

    public AccessToken getAccessToken() {
        return mAccessToken;
    }

    public String getUserId() {
        return mAccessToken.getUserId();
    }
}
