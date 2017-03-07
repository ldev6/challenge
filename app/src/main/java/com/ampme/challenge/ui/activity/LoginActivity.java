package com.ampme.challenge.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ampme.challenge.R;
import com.ampme.challenge.ui.fragment.LoginFragment;
import com.ampme.challenge.model.UserManager;
import com.ampme.challenge.utils.Utils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

public class LoginActivity extends FragmentActivity {

    private AccessTokenTracker mAccessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.login_activity);

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        UserManager.getInstance().setLogin(AccessToken.getCurrentAccessToken());
        if (!UserManager.getInstance().isLoggedIn()) {
            Utils.changeFragment(this, R.id.container, LoginFragment.newInstance());
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAccessTokenTracker.stopTracking();
    }
}
