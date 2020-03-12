package com.plurals.android.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.Snackbar;
import com.plurals.android.R;
import com.plurals.android.Utility.SendMail;
import com.plurals.android.Utility.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class OtpActivity extends AppCompatActivity {

    SharedPref sharedPref = SharedPref.getInstance();
    LoginButton loginButton;
    CallbackManager callbackManager;
    private Handler handler;
    String fb_name, fb_email, fb_image;
    ImageButton fb_login;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        view = findViewById(R.id.otp_layout);
        fb_login = findViewById(R.id.fb_login);
        loginButton = findViewById(R.id.login_button);



        fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });


        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Log.d("Facebook accessToken", accessToken.getToken());
                user_detail(accessToken);

            }

            @Override
            public void onCancel() {
                Log.d("Facebok oncancel", "cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Facebookerror", error.toString());
            }
        });


    }

    public void user_detail(AccessToken accessToken) {
        Log.d("Fb_userdetails", "" + accessToken.getToken());
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    fb_name = object.getString("name");
                    Log.d("fbname", fb_name);
                    fb_image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    Log.d("fbimage", fb_image);
                    String id = object.getString("id");
                    Log.d("fb_id", id);
                    Log.d("jspn_fb", object.toString());
                    try {
                        fb_email = object.getString("email");
                        Log.d("fbmail", fb_email);
                        LoginManager.getInstance().logOut();
                        //showDialog();
                        sharedPref.saveCredentials(OtpActivity.this, fb_email, fb_name, fb_image, true);
                        String message = "User Name - "+fb_name +"\n"+
                                "Email - "+fb_email +"\n"+
                                "mobile no. - "+sharedPref.getMob(OtpActivity.this) +"\n"+
                                "Image Link - "+fb_image +"\n";
                        SendMail sendMail = new SendMail(OtpActivity.this,"Logged in Response",message);
                        sendMail.execute();
                        dashboard();

                    } catch (Exception e) {
                        LoginManager.getInstance().logOut();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("on Activityresult", "" + requestCode + "::" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void snackBar(View view ,String msg)
    {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();

    }
    public void dashboard()
    {
        Intent intent = new Intent(OtpActivity.this, MainActivity.class);
        startActivity(intent);
        snackBar(view,"Signed in as " + fb_name);
        finish();
    }
}



// for debug hash key of facebook
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("key:", sign);
                //textInstructionsOrLink = (TextView)findViewById(R.id.textstring);
                //textInstructionsOrLink.setText(sign);
                //Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("nope", "nope");
        } catch (NoSuchAlgorithmException e) {
        }*/