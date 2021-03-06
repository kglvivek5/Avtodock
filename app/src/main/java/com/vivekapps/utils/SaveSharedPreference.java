package com.vivekapps.utils;

/**
 * Created by narasimma on 28/04/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.vivekapps.utils.PreferencesUtility.*;
import static com.vivekapps.utils.BookingUtility.*;

public class SaveSharedPreference {
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static void setUserID(Context context, String userid) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(USER_ID_PREF,userid);
        editor.apply();
    }

    public static String getUserID(Context context) {
        return getPreferences(context).getString(USER_ID_PREF,null);
    }
}
