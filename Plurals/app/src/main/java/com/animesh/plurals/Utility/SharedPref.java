package com.animesh.plurals.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPref
{
    /*
     * @Sweta
     * date : 6 Feb,2014
     */
    SharedPreferences sharedPreferences;
    private static SharedPref instance = null;

    public SharedPref()
    {
        // TODO Auto-generated constructor stub
    }
    public static SharedPref getInstance()
    {
        if (instance == null)
        {
            synchronized (SharedPref.class)
            {
                instance = new SharedPref();
            }
        }
        return instance;
    }


    public void saveToken(Context context , String token , String user_email , String user_nicename , String user_display_name , boolean bool ) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.putString("user_email", user_email);
        editor.putString("user_nicename", user_nicename);
        editor.putString("user_display_name", user_display_name);
        editor.putBoolean("isLogin", bool);
        editor.apply();
    }
    public String getAuthToken(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("token", "");
    }
    public String getUser_email(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("user_email", "");
    }
    public String getUser_image(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("user_display_name", "");
    }
    public String getUser_username(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("user_nicename", "");
    }

    public boolean isLogin(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("isLogin",false);
    }

    public void removeToken(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.remove("user_email");
        editor.remove("user_nicename");
        editor.remove("user_display_name");
        editor.putBoolean("isLogin", false);
        editor.apply();
    }


}


