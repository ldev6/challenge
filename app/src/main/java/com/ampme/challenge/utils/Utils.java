package com.ampme.challenge.utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class Utils {
    /**
     * To change fragment
     *
     * @param activity
     * @param idView
     * @param fragment
     */
    public static void changeFragment(FragmentActivity activity, int idView, Fragment fragment) {
        if (!fragment.isAdded()) {
            activity.getSupportFragmentManager().beginTransaction().replace(idView, fragment).commit();
        }
    }

    public static void changeFragment(Activity activity, int idView, android.app.Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction().replace(idView, fragment).commit();
        }
    }
}
