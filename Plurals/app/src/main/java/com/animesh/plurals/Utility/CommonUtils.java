package com.animesh.plurals.Utility;

import android.content.Context;
import android.content.Intent;

import com.animesh.plurals.Activity.WebviewActivity;

public  class CommonUtils {

   public static void startWebViewActivity(Context context,String url){
       Intent intent= new Intent(context, WebviewActivity.class);
       intent.putExtra("WEBVIEW_URL",url);
       context.startActivity(intent);
    }
}
