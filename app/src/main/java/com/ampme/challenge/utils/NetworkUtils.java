package com.ampme.challenge.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ampme.challenge.R;
import com.ampme.challenge.utils.view.MessageView;

import java.io.IOException;

import retrofit2.HttpException;

public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    public static boolean isNetworkAvailableByWifi(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    private static String parseError(Context context, Throwable throwable) {
        if (throwable instanceof HttpException) {
            // We had non-2XX http error
            //TODO Parse the error with an object and check the reason with key instead of text.
            return context.getResources().getString(R.string.error_message_serveur_prob);
        }
        if (throwable instanceof IOException) {
            // A network or conversion error happened
            return context.getResources().getString(R.string.default_error_message);
        }
        return context.getResources().getString(R.string.error_message_serveur_prob);
    }

    public static void showError(Context context, MessageView messageView, Throwable throwable, boolean alreadyDataShown) {
        if (alreadyDataShown) {
            messageView.setMessageError(NetworkUtils.parseError(context, throwable), true);
        } else {
            messageView.setMessageError(NetworkUtils.parseError(context, throwable), false);
        }
    }
}
