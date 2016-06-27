package com.example.prateek.spotmate;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nilesh on 27/6/16.
 */
public class StoreSharePreferances {
        static final String PREF_USER_NAME= "username";
        static final String PREF_PASSWORD= "password";

        static SharedPreferences getSharedPreferences(Context ctx) {
            return PreferenceManager.getDefaultSharedPreferences(ctx);
        }

        public static void setUserName(Context ctx, String userName)
        {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            editor.putString(PREF_USER_NAME, userName);
            editor.commit();
        }

        public static void setPassword(Context ctx, String password)
        {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            editor.putString(PREF_PASSWORD, password);
            editor.commit();
        }

        public static String getUserName(Context ctx)
        {
            return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
        }

        public static String getPassword(Context ctx)
        {
            return getSharedPreferences(ctx).getString(PREF_PASSWORD, "");
        }
    }

