package com.ampme.challenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacebookPaging {

    @Expose
    @SerializedName("next")
    private String nextUrl;

    public FacebookPaging() {
    }

    public String getNextUrl() {
        return nextUrl;
    }
}
