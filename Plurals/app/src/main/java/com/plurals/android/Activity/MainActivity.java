package com.plurals.android.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.plurals.android.R;
import com.plurals.android.Utility.Constants;
import com.plurals.android.Utility.SharedPref;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    TextView user_name, user_email;
    ImageView user_image;
    SharedPref sharedPref = SharedPref.getInstance();
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        user_email = headerView.findViewById(R.id.user_email);
        user_name = headerView.findViewById(R.id.user_name);
        user_image = headerView.findViewById(R.id.user_image);
        showDialog();
        try {
            user_name.setText(sharedPref.getUser_username(this));
            String imageUrl = sharedPref.getUser_image(this);
            Log.d("mainactivity","image url "+ imageUrl);
            if (!(imageUrl.isEmpty() || imageUrl == null)) {
                Log.d("mainactivity","try-if");
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.plu_white)
                        .error(R.drawable.plu_white)
                        .into(user_image);
            } else {
                Log.d("mainactivity","try-else");
                user_image.setImageResource(R.drawable.plu_white);
            }
            Log.d("mainactivity","email"+sharedPref.getUser_email(this));
            user_email.setText(sharedPref.getUser_email(this));
        } catch (Exception e) {

        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_fb, R.id.nav_tw,
                R.id.nav_utube, R.id.nav_volunteer, R.id.nav_career,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
         //NavigationUI.setupWithNavController(navigationView, navController);
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_home:
                //toast_msg("home");
                break;
            case R.id.nav_fb:
               intent = getOpenFacebookIntent(MainActivity.this);
               startActivity(intent);
                break;
            case R.id.nav_tw:
               intent = getOpenTwitterIntent(this);
               startActivity(intent);
                break;
            case R.id.nav_utube:
              intent =getOpenYoutubeIntent(this);
              startActivity(intent);
                break;
            case R.id.nav_volunteer:
                Intent vol_intent = new Intent(MainActivity.this,VolunteerFormActivity.class);
                startActivity(vol_intent);
                break;
            case R.id.nav_career:
                Intent car_intent = new Intent(MainActivity.this,CareerActivity.class);
                startActivity(car_intent);
                break;
            case R.id.nav_logout:
                sharedPref.removeToken(this);
                Intent login_intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(login_intent);
                finish();
                finishAffinity();
                default:



        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void toast_msg(String msg) {
        Toast toast = Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.register_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogApply = dialog.findViewById(R.id.apply_button);
        dialogApply.setText("Apply");
        /*Button dialogCancel = dialog.findViewById(R.id.cancel);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        dialogApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent volIntent = new Intent(MainActivity.this,CareerActivity.class);
                startActivity(volIntent);
            }
        });

        dialog.show();
    }


    public static Intent getOpenFacebookIntent(Context context ) {
        Log.d("click","fb click ");
        String id = Constants.FB_URL;
        try {
            Log.d("fb_app", "inside method");
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://facewebmodal/f?href=" + id)); //Trys to make intent with FB's URI
        } catch (Exception e) {
            Log.d("fb_app", "inside catch");
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(id)); //catches and opens a url to the desired page
        }

    }

    public static Intent getOpenTwitterIntent(Context context) {
        Log.d("click","twitter click ");
        String id = Constants.TWITTER_URL;
        Intent intent = null;
        try {
            // Get Twitter app
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        } catch (Exception e) {
            // If no Twitter app found, open on browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
            return intent;
        }
    }

    public static Intent getOpenYoutubeIntent(Context context) {
        Log.d("click","Youtube click ");
        String id = Constants.YOUTUBE_URL;
        Intent intent = null;
        try {
            // Get Twitter app
            context.getPackageManager().getPackageInfo("com.google.android.youtube", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        } catch (Exception e) {
            // If no Twitter app found, open on browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
            return intent;
        }
    }


}
