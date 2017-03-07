package com.ampme.challenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YoutubeThumbnail {

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("width")
    private int width;

    @Expose
    @SerializedName("height")
    private int height;

    public YoutubeThumbnail() {
    }

    public String getUrl() {
        return url;
    }

    public int getWith() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
