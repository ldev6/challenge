package com.ampme.challenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class YoutubeResponse {

    @Expose
    @SerializedName("kind")
    private String kind;

    @Expose
    @SerializedName("etag")
    private String etag;

    @Expose
    @SerializedName("items")
    private ArrayList<YoutubeItem> youtubeItems;

    @Expose
    @SerializedName("pageInfo")
    private YoutubePageInfo youtubePageInfo;

    @Expose
    @SerializedName("nextPageToken")
    private String nextPageToken;

    public YoutubeResponse() {
    }

    public ArrayList<YoutubeItem> getYoutubeItems() {
        return youtubeItems;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }
}
