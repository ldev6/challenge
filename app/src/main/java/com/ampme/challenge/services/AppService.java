package com.ampme.challenge.services;

import com.ampme.challenge.model.FacebookResponse;
import com.ampme.challenge.model.YoutubeResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface AppService {

    @GET("search")
    Observable<YoutubeResponse> playlistForArtiste(
            @Query("part") String part,
            @Query("q") String artiste,
            @Query("type") String type);

    @GET("search")
    Observable<YoutubeResponse> nextPlayListForArtiste(
            @Query("part") String part,
            @Query("q") String artiste,
            @Query("type") String type,
            @Query("pageToken")
                    String pageToken
    );

    @GET("https://graph.facebook.com/v2.8/{userId}/music")
    Observable<FacebookResponse> facebookMusicLike(
            @Path("userId") String userId,
            @Query("access_token") String token
    );

    @GET
    Observable<FacebookResponse> nextFacebookMusicLike(
            @Url String url
    );
}
