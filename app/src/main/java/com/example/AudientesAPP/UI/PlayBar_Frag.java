package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.AudientesAPP.LydSpiller;
import com.example.AudientesAPP.MainActivity;
import com.example.AudientesAPP.R;


/**
 * test fragment til en playbar (dvs en menu hvor man kan pause og play en lyd)
 */
public class PlayBar_Frag extends Fragment implements View.OnClickListener {

    ImageView play, pause;
    LydSpiller lydSpiller;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.playbar, container, false);

        play = rod.findViewById(R.id.playbar_playbutton);
        pause = rod.findViewById(R.id.playbar_pausebutton);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);


        MainActivity main = (MainActivity) getActivity();
        lydSpiller = main.getLydSpiller();

        return rod;
    }


    @Override
    public void onClick(View v) {
        if (v == play) {
            lydSpiller.playSound();
        } else if (v == pause) {
            lydSpiller.pause();
        }
    }
}
