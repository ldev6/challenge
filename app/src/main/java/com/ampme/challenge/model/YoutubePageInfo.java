package com.ampme.challenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YoutubePageInfo {

    @Expose
    @SerializedName("totalResults")
    private int total;

    @Expose
    @SerializedName("resultsPerPage")
    private int resultPerPage;

    public YoutubePageInfo() {
    }

    public int getTotal() {
        return total;
    }

    public int getResultPerPage() {
        return resultPerPage;
    }
}
