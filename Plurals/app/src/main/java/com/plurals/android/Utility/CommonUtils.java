package com.plurals.android.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.plurals.android.Activity.WebviewActivity;
import com.plurals.android.BuildConfig;

public class CommonUtils {

    public static void startWebViewActivity(Context context, String url) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra("WEBVIEW_URL", url);
        context.startActivity(intent);
    }

    public static void showToastInDebug(Context context, String toastText) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        }
    }

    public static void hideForcefullyKeyboard(Activity activity) {
        View viewKeyboard = activity.getCurrentFocus();
        if (viewKeyboard != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewKeyboard.getWindowToken(), 0);
        }
    }

    public static void showForcefullyKey(Activity activity, View view) {
        view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0));
        view.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0));


//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }


}
