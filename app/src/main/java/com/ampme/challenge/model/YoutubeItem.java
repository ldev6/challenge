package com.ampme.challenge.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YoutubeItem {

    @Expose
    @SerializedName("kind")
    private String kind;

    @Expose
    @SerializedName("etag")
    private String etag;

    @Expose
    @SerializedName("snippet")
    private YoutubeSnippet youtubeSnippet;

    public YoutubeItem() {
    }

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public YoutubeSnippet getYoutubeSnippet() {
        return youtubeSnippet;
    }
}
