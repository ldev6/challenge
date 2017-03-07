package com.ampme.challenge.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class YoutubeSnippet {

    @Expose
    @SerializedName("publishedAt")
    private String publishedAt;

    @Expose
    @SerializedName("channelId")
    private String channelId;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("thumbnails")
    private HashMap<String, YoutubeThumbnail> youtubeThumbnails;

    @Expose
    @SerializedName("channelTitle")
    private String channelTitle;

    @Expose
    @SerializedName("liveBroadcastContent")
    private String liveBroadcastContent;

    public YoutubeSnippet() {
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, YoutubeThumbnail> getYoutubeThumbnails() {
        return youtubeThumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getLiveBroadcastcontent() {
        return liveBroadcastContent;
    }
}
