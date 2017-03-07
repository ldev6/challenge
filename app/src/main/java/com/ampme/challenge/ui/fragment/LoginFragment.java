package com.ampme.challenge.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ampme.challenge.R;
import com.ampme.challenge.model.UserManager;
import com.ampme.challenge.utils.NetworkUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginFragment extends android.support.v4.app.Fragment {

    private CallbackManager mCallBackManager;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallBackManager = new CallbackManager.Factory().create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_likes", "user_actions.music"));
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                UserManager.getInstance().setLogin(loginResult.getAccessToken());
                getActivity().finish();
            }

            @Override
            public void onCancel() {
                if (!NetworkUtils.isNetworkAvailable(getActivity()) && !NetworkUtils.isNetworkAvailableByWifi(getActivity())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.default_error_message), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(), R.string.loging_failed + " ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }
}
