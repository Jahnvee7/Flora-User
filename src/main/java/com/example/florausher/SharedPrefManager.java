package com.example.florausher;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context ctx;
    private static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String KEY_USERNAME="UserName";
    private static final String KEY_USER_EMAIL="Email";
    private static final String KEY_USER_ID="UserId";
    private static final String KEY_Mobile_Number="MobileNumber";

    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }
    public boolean userLogin(int UserId, String UserName, String Email)
    {
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY_USER_ID,UserId);
        editor.putString(KEY_USERNAME,UserName);
        editor.putString(KEY_USER_EMAIL,Email);
        //editor.putString(KEY_Mobile_Number,MobileNumber);
        editor.apply();
        return true;
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_EMAIL,null)!=null)
        {
            return true;
        }
        return false;
    }

    public boolean logout()
    {
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
