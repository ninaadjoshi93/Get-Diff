package com.ninaad.gitdiff.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.ninaad.gitdiff.BuildConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ninaad on 2/26/19.
 */
public class GDUtilities {
    private static final String TAG = GDUtilities.class.getName();
    private static SharedPreferences mPrefs;

    /**
     *
     * @param urlLink
     * @return A boolean indicating whether the url is valid
     */
    public static boolean isValidRepositoryUrl(String urlLink){
        Log.d(TAG, "url to validate : " + urlLink);
        Pattern pattern = Pattern.compile(BuildConfig.BASE_URL +
                "[a-zA-Z0-9\\-_]+/[a-zA-Z0-9\\-_]+/");
        Matcher matcher = pattern.matcher(urlLink);
        return matcher.matches();
    }

    public static void setSharedPreferences(Context mContext, String
            mJsonDataString, String mPreferenceKey) {

        mPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(mPreferenceKey, mJsonDataString);
        prefsEditor.apply();
    }

    public static String getSharedPreferences(Context mContext, String
            mPreferenceKey) {
        mPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        return mPrefs.getString(mPreferenceKey, "");
    }

    /**
     * @param mContext {@link android.app.Activity} {@link android.content,Context}
     * @return A boolean value whether a network is available or not(wifi or cellular)
     * This function checks whether a network is available or not.
     */
    public static boolean isNetworkAvailable(Context mContext) {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo == null || !mNetworkInfo.isConnected())
            Toast.makeText(mContext, "Network Not Available", Toast
                    .LENGTH_SHORT).show();
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }
}
