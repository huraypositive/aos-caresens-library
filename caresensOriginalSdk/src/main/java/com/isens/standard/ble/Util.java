package com.isens.standard.ble;

import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 2017-12-18.
 */

public class Util {
    public static MainActivity mainActivity;

    public static int getPreference(String key) {
        SharedPreferences pref = mainActivity.getSharedPreferences("pref_ble_example", MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public static void setPreference(String key, int value) {
        SharedPreferences pref = mainActivity.getSharedPreferences("pref_ble_example", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getPreferenceBool(String key) {
        SharedPreferences pref = mainActivity.getSharedPreferences("pref_ble_example", MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public static void setPreference(String key, boolean value) {
        SharedPreferences pref = mainActivity.getSharedPreferences("pref_ble_example", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void showToast(String text) {
        Toast.makeText(mainActivity, text, Toast.LENGTH_SHORT).show();
    }

    public static boolean runningOnKitkatOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

}
