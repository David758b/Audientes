package com.example.AudientesAPP.UI;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.AudientesAPP.R;

public class HearingtestMain extends Fragment implements View.OnClickListener {

    private ImageView testButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.hearingtest_main, container,
                false);
        ImageView imageView = (ImageView) rod.findViewById(R.id.hearingTestImage);

        testButton = (ImageView) rod.findViewById(R.id.hearingtestbutton);
        testButton.setOnClickListener(this);


        return rod;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=dk.alen.audientes"));
        try{
            startActivity(intent);
        }
        catch(Exception e){
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=dk.alen.audientes"));
        }

    }
}

