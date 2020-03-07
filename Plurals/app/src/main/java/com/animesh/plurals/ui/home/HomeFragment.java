package com.animesh.plurals.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.content.Intent;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.animesh.plurals.Activity.WebviewActivity;
import com.animesh.plurals.R;
import com.animesh.plurals.Utility.CommonUtils;
import com.animesh.plurals.Utility.Constants;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    TextView fb_text,twitter_text,utube_text;
    ImageView fb_icon,twi_icon,utube_icon;
    CardView fb_card,twi_card,utube_card;
    Context context;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    CardView cv_youtube, cv_fb, cv_twitter, cv_about;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cv_youtube = view.findViewById(R.id.cv_youtube);
        cv_fb = view.findViewById(R.id.cv_fb);
        cv_twitter = view.findViewById(R.id.cv_twitter);
        cv_about = view.findViewById(R.id.cv_about);

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
                fb = getOpenFacebookIntent(context);
                startActivity(fb);
                break;

            case R.id.cv_twitter:
                tw = getOpenTwitterIntent(context);
                startActivity(tw);
                break;


            case R.id.cv_about:
                CommonUtils.startWebViewActivity(getActivity(),Constants.WEBSITE_URL);
                break;
            case R.id.cv_youtube:
                ut = getOpenYoutubeIntent(context);
                startActivity(ut);
                break;


        }
    }


    public static Intent getOpenFacebookIntent(Context context ) {
        Log.d("click","fb click ");
        String id = "https://www.facebook.com/pushpampc13/";
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
        String id = "https://twitter.com/pushpampc13";
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
        String id = "https://www.youtube.com/channel/UCDI8o1h8Zq_JKRPaPdmHdLQ";
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