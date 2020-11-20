package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.AudientesAPP.R;

public class PlayBar_Frag extends Fragment implements View.OnClickListener {

    Button play, pause;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.playbar, container, false);

        play = rod.findViewById(R.id.playbar_playbutton);
        pause = rod.findViewById(R.id.playbar_pausebutton);


        return rod;
    }


    @Override
    public void onClick(View v) {

    }
}
