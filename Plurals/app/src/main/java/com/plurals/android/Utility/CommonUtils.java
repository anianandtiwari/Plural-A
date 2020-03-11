package com.plurals.android.Utility;

import android.content.Context;
import android.content.Intent;

import com.plurals.android.Activity.WebviewActivity;

public  class CommonUtils {

   public static void startWebViewActivity(Context context,String url){
       Intent intent= new Intent(context, WebviewActivity.class);
       intent.putExtra("WEBVIEW_URL",url);
       context.startActivity(intent);
    }
}
