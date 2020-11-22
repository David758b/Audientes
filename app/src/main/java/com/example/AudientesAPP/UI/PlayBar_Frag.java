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

    ImageView play;

    LydSpiller lydSpiller;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.playbar, container, false);

        play = rod.findViewById(R.id.playbar_playbutton);
        play.setOnClickListener(this);

        MainActivity main = (MainActivity) getActivity();
        lydSpiller = main.getLydSpiller();

        // TODO: 22/11/2020 Der skal laves en eller anden form for listener på lydspiller 
        //Der skal laves en eller anden form for listener på lydspiller
        //så når der bliver valgt en lyd skal kanppen ændre sig



        return rod;
    }


    @Override
    public void onClick(View v) {

        if(v == play){
            if(!lydSpiller.isPlaying()){
                play.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                lydSpiller.playSound();

            }
            else{
                play.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                lydSpiller.pause();

            }
        }
    }
}
