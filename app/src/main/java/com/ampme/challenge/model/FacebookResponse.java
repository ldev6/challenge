package com.ampme.challenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FacebookResponse {

    @Expose
    @SerializedName("data")
    private ArrayList<FacebookMusic> musics;

    @Expose
    @SerializedName("paging")
    private FacebookPaging facebookPaging;

    public FacebookResponse() {
    }

    public ArrayList<FacebookMusic> getMusics() {
        return musics;
    }

    public FacebookPaging getFacebookPaging() {
        return facebookPaging;
    }
}
