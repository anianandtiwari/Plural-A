package com.plurals.android.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.plurals.android.Activity.VolunteerFormActivity;
import com.plurals.android.R;
import com.plurals.android.Utility.CommonUtils;
import com.plurals.android.Utility.Constants;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    Context context;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    CardView cv_youtube, cv_fb, cv_twitter, cv_about , cv_volunteer;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cv_youtube = view.findViewById(R.id.cv_youtube);
        cv_fb = view.findViewById(R.id.cv_fb);
        cv_twitter = view.findViewById(R.id.cv_twitter);
        cv_about = view.findViewById(R.id.cv_about);
        cv_volunteer =view.findViewById(R.id.cv_volunteer);
        cv_volunteer.setOnClickListener(this);
        cv_about.setOnClickListener(this);
        cv_twitter.setOnClickListener(this);
        cv_fb.setOnClickListener(this);
        cv_youtube.setOnClickListener(this);

    }





    @Override
    public void onClick(View v) {
        Log.d("click","Onclick = "+v.getId());
        Intent fb,tw,ut;

        switch (v.getId())
        {

            case R.id.cv_fb:
                fb = getOpenFacebookIntent(getActivity());
                startActivity(fb);
                break;

            case R.id.cv_twitter:
                tw = getOpenTwitterIntent(getActivity());
                startActivity(tw);
                break;


            case R.id.cv_about:
                CommonUtils.startWebViewActivity(getActivity(),Constants.WEBSITE_URL);
                break;
            case R.id.cv_youtube:
                ut = getOpenYoutubeIntent(context);
                startActivity(ut);
                break;

            case R.id.cv_volunteer:
                Intent vol_intent = new Intent(getActivity(), VolunteerFormActivity.class);
                startActivity(vol_intent);
                break;


        }
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