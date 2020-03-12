package com.plurals.android.Utility;

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


    public void saveCredentials(Context context , String user_email , String user_display_name ,String image, boolean bool ) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", user_email);
        editor.putString("image", image);
        editor.putString("user_display_name", user_display_name);
        editor.putBoolean("isLogin", bool);
        editor.apply();
    }

    public void saveMob(Context context , String mob ) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mob", mob);
        editor.apply();
    }



    public String getMob(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("mob", "");
    }
    public String getUser_email(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("user_email", "");
    }
    public String getUser_image(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("image", "");
    }
    public String getUser_username(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("user_display_name", "");
    }

    public boolean isLogin(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("isLogin",false);
    }

    public void removeToken(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("mob");
        editor.remove("user_email");
        editor.remove("image");
        editor.remove("user_display_name");
        editor.putBoolean("isLogin", false);
        editor.apply();
    }


}


