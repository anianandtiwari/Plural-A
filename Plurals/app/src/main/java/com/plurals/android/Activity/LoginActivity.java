package com.plurals.android.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.plurals.android.Model.OtpLogin;
import com.plurals.android.R;
import com.plurals.android.Utility.CustomRequest;
import com.plurals.android.Utility.SharedPref;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    EditText mobile;
    Button getotp;
    EditText dialogedit;
    private ProgressDialog getotp_Pd;
    SharedPref sharedPref = SharedPref.getInstance();
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mobile = findViewById(R.id.mobile);
        getotp = findViewById(R.id.getotp);

        getotp_Pd = new ProgressDialog(LoginActivity.this);
        getotp_Pd.setMessage("Please Wait...");
        getotp_Pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        getotp_Pd.setCancelable(false);
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpnetworkCall(mobile.getText().toString().trim());

            }
        });

    }



    public void showDialog(String description) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.mobile_otp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView textView = dialog.findViewById(R.id.mobileno);
        textView.setText("Enter OTP sent on " + description);
        dialogedit = dialog.findViewById(R.id.mobile);
        Button dialogSubmit = dialog.findViewById(R.id.submit);
        Button dialogCancel = dialog.findViewById(R.id.cancel);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpVerificationCall(mobile.getText().toString().trim(), dialogedit.getText().toString().trim());
            }
        });

        dialog.show();
    }

    public void toast_msg(String msg) {
        Toast toast = Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void otpnetworkCall(final String mobile) {
        if (mobile.length() != 10) {
            toast_msg("Enter valid mobile no.");
        } else {
            getotp_Pd.show();
            JSONObject object_mob = new JSONObject();
            try {
                object_mob.put("mobile", mobile);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://gaathaonair.com/wp-json/gathaa/v1/send-otp", object_mob,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            getotp_Pd.dismiss();
                            try {
                                String status = response.getString("success");
                                Log.d("otp", status);
                                if (status.equalsIgnoreCase("true")) {
                                    showDialog(mobile);
                                } else {
                                    toast_msg("Server Error");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    getotp_Pd.dismiss();
                    toast_msg("Invalid mobile no.");
                    Log.d("Reserterror0", "error in reseting pwd ");
                    // networkCall(email);
                }


            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    12000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            CustomRequest.getInstance(this).addToRequestQue(jsonObjectRequest);

        }
    }

    public void otpVerificationCall(String mobile, String otp) {
        if (otp.length() < 4) {
            toast_msg("Entered otp is wrong");
        } else {
             dialog.dismiss();
            getotp_Pd.show();
            final JSONObject object_mob = new JSONObject();
            try {
                object_mob.put("mobile", mobile);
                object_mob.put("otp", otp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://gaathaonair.com/wp-json/gathaa/v1/signup-otp", object_mob,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            getotp_Pd.dismiss();
                            try {
                                boolean success = response.getBoolean("success");
                                if (success) {
                                    OtpLogin otpLogin = new Gson().fromJson(response.toString(), OtpLogin.class);
                                    Log.d("otpActivity", otpLogin.getData().toString());
                                    //toast_msg(otpLogin.getData().getUser_display_name());
                                    sharedPref.saveMob(LoginActivity.this, otpLogin.getData().getUser_display_name());
                                    Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //finishAffinity();
                                } else {
                                    Log.d("otpActivity-else", "" + success);
                                    toast_msg("Wrong Otp");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    getotp_Pd.dismiss();
                    toast_msg("Wrong otp");
                }


            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    12000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            CustomRequest.getInstance(LoginActivity.this).addToRequestQue(jsonObjectRequest);
        }
    }
}
