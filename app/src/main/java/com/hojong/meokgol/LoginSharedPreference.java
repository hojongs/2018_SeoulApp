package com.hojong.meokgol;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hojong.meokgol.data_model.LoginInfo;

public class LoginSharedPreference {

    private static final String USER_ID_KEY = "userId";

    private static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void saveLoginInfo(Context ctx, LoginInfo loginInfo) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_ID_KEY, loginInfo.userId);
        editor.apply();
    }

    public static LoginInfo getUserInfo(Context ctx) {
        String userId = getSharedPreferences(ctx).getString(USER_ID_KEY, "");

        return new LoginInfo(userId);
    }

    public static void putLoginInfo(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(USER_ID_KEY);
        editor.apply();
    }
}