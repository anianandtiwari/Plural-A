package com.animesh.plurals.Utility;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class CustomRequest {
    private static CustomRequest rInstance;
    private RequestQueue requestQueue;
    private static Context context;

    public CustomRequest(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized CustomRequest getInstance(Context context){
        if(rInstance==null){
            rInstance = new CustomRequest(context);

        }
        return rInstance;
    }
    public <T> void addToRequestQue(Request<T> request){
        requestQueue.add(request);
    }
}
