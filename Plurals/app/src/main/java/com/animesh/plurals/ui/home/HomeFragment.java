package com.animesh.plurals.ui.home;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.animesh.plurals.R;

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
        fb_card = root.findViewById(R.id.fb_card);
        fb_card.setOnClickListener(this);
        twi_card = root.findViewById(R.id.twitter_card);
        twi_card.setOnClickListener(this);
        utube_card = root.findViewById(R.id.youtube_card);
        utube_card.setOnClickListener(this);
        utube_icon = root.findViewById(R.id.youtube_icon);
        utube_icon.setOnClickListener(this);
        fb_icon = root.findViewById(R.id.fb_icon);
        fb_icon.setOnClickListener(this);
        twi_icon = root.findViewById(R.id.twitter_icon);
        twi_icon.setOnClickListener(this);
        fb_text = root.findViewById(R.id.fb_text);
        fb_text.setOnClickListener(this);
        twitter_text = root.findViewById(R.id.twitter_text);
        twitter_text.setOnClickListener(this);
        utube_text = root.findViewById(R.id.youtube_text);
        utube_text.setOnClickListener(this);

//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onClick(View v) {
        Log.d("click","Onclick = "+v.getId());
        Intent fb,tw,ut;

        switch (v.getId())
        {

            case R.id.fb_text:
                fb = getOpenFacebookIntent(context);
                startActivity(fb);
                break;
            case R.id.fb_icon:
                fb = getOpenFacebookIntent(context);
                startActivity(fb);
                break;
            case R.id.fb_card:
                fb = getOpenFacebookIntent(context);
                startActivity(fb);
                break;
            case R.id.twitter_card:
                tw = getOpenTwitterIntent(context);
                startActivity(tw);
                break;
            case R.id.twitter_icon:
                tw = getOpenTwitterIntent(context);
                startActivity(tw);
                break;
            case R.id.twitter_text:
                tw = getOpenTwitterIntent(context);
                startActivity(tw);
                break;
            case R.id.youtube_card:
                break;
            case R.id.youtube_icon:
                break;
            case R.id.youtube_text:
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
}