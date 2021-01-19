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

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import com.example.AudientesAPP.R;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class HearingtestMain extends Fragment implements View.OnClickListener {

    private ImageView testButton;
    private TextView names;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.hearingtest_main, container,
                false);

        testButton = (ImageView) rod.findViewById(R.id.hearingtestbutton);
        testButton.setOnClickListener(this);
        names = (TextView) rod.findViewById(R.id.namesTV);



        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this.getActivity(), callback);
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
            startActivity(intent);
        }

    }
}

