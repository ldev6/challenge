package com.ampme.challenge.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.ampme.challenge.R;
import com.ampme.challenge.model.FacebookMusic;
import com.ampme.challenge.model.FacebookResponse;
import com.ampme.challenge.model.UserManager;
import com.ampme.challenge.model.YoutubeItem;
import com.ampme.challenge.model.YoutubeResponse;
import com.ampme.challenge.model.YoutubeSnippet;
import com.ampme.challenge.services.ServiceGenerator;
import com.ampme.challenge.services.AppService;
import com.ampme.challenge.ui.adapter.MusicAdapter;
import com.ampme.challenge.utils.NetworkUtils;
import com.ampme.challenge.utils.view.MessageView;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements MessageView.MessageViewInterface {

    private final static int MAX_PULL_COUNT = 500;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<YoutubeSnippet> mYoutubeSnippets;
    private HashMap<String, String> mYoutubeNextPageTokens;
    private MessageView mMessageView;
    private int mCount = 0;

    private AppService mAppService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!UserManager.getInstance().isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        mAppService = ServiceGenerator.createService(AppService.class);
        mYoutubeNextPageTokens = new HashMap<>();

        mMessageView = (MessageView) findViewById(R.id.messageView);
        mMessageView.setMessageViewInterface(this);
        IndexFastScrollRecyclerView recyclerView = (IndexFastScrollRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();


                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        mCount = 0;
                        if (mYoutubeNextPageTokens.size() > 0) {
                            Iterator<Map.Entry<String, String>> iterator = mYoutubeNextPageTokens.entrySet().iterator();
                            mMessageView.showProgressBar();
                            while (iterator.hasNext()) {
                                Map.Entry<String, String> entry = iterator.next();
                                getNextPlayList(entry.getKey(), entry.getValue());
                                iterator.remove();
                            }
                        }
                    }
                }
            }
        });

        mYoutubeSnippets = new ArrayList<>();
        mAdapter = new MusicAdapter(this, mYoutubeSnippets);
        recyclerView.setIndexTextSize(12);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (UserManager.getInstance().isLoggedIn() && mYoutubeSnippets.size() == 0) {
            mMessageView.showProgressBar();
            getLikeMusic();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                LoginManager.getInstance().logOut();
                startActivity(new Intent(this, LoginActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getLikeMusic() {
        mAppService.facebookMusicLike(UserManager.getInstance().getUserId(), UserManager.getInstance().getAccessToken().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FacebookSubscriber());
    }

    private void getNextPageLikeMusic(String url) {
        mAppService.nextFacebookMusicLike(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FacebookSubscriber());
    }

    private void getPlaylist(String artiste) {
        ServiceGenerator.createService(AppService.class);
        mAppService.playlistForArtiste("snippet", artiste, "playlist")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new YoutubeSubscriber(artiste));
    }

    private void getNextPlayList(String artiste, String nextPageToken) {
        mAppService.nextPlayListForArtiste("snippet", artiste, "playlist", nextPageToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new YoutubeSubscriber(artiste));
    }

    @Override
    public void retry() {
        mMessageView.hideMessageError();
        mMessageView.showProgressBar();
        getLikeMusic();
    }

    private class FacebookSubscriber extends Subscriber<FacebookResponse> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            NetworkUtils.showError(MainActivity.this, mMessageView, e, mYoutubeSnippets.size() > 0);
        }

        @Override
        public void onNext(FacebookResponse facebookResponse) {
            if (!TextUtils.isEmpty(facebookResponse.getFacebookPaging().getNextUrl())) {
                getNextPageLikeMusic(facebookResponse.getFacebookPaging().getNextUrl());
            }
            for (FacebookMusic music : facebookResponse.getMusics()) {
                getPlaylist(music.getName());
            }
        }
    }

    private class YoutubeSubscriber extends Subscriber<YoutubeResponse> {

        private String artiste;
        private String pageToken;


        public YoutubeSubscriber(String artiste) {
            this.artiste = artiste;
        }

        @Override
        public void onCompleted() {
            if (!TextUtils.isEmpty(pageToken)) {
                if (mCount <= MAX_PULL_COUNT) {
                    //Download the next page
                    getNextPlayList(artiste, pageToken);
                } else {
                    mMessageView.hideProgessBar();
                    mYoutubeNextPageTokens.put(artiste, pageToken);
                }
            } else {
                mMessageView.hideProgessBar();
            }
        }

        @Override
        public void onError(Throwable e) {
            NetworkUtils.showError(MainActivity.this, mMessageView, e, mYoutubeSnippets.size() > 0);
        }

        @Override
        public void onNext(YoutubeResponse youtubeResponse) {

            for (YoutubeItem item : youtubeResponse.getYoutubeItems()) {
                mCount++;
                mYoutubeSnippets.add(item.getYoutubeSnippet());
            }

            if (!TextUtils.isEmpty(youtubeResponse.getNextPageToken())) {
                pageToken = youtubeResponse.getNextPageToken();
            }
            Collections.sort(mYoutubeSnippets, new Comparator<YoutubeSnippet>() {
                public int compare(YoutubeSnippet v1, YoutubeSnippet v2) {
                    return v1.getTitle().toUpperCase().compareTo(v2.getTitle().toUpperCase());
                }
            });
            mAdapter.notifyDataSetChanged();
        }
    }
}
