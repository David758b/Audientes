package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.AudientesAPP.model.controller.ModelViewController;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.funktionalitet.Utilities;

/**
 * test fragment til en playbar (dvs en menu hvor man kan pause og play en lyd)
 *
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class PlayBar_Frag extends Fragment implements LydAfspiller.OnLydAfspillerListener, SeekBar.OnSeekBarChangeListener {

    private ImageView play;
    private SeekBar seekBar;
    private TextView soundTitle, playerDuration, playerPosition;

    private LydAfspiller lydAfspiller;
    private ModelViewController modelViewController;

    private Runnable runnable;
    private Handler handler = new Handler();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.playbar, container, false);
        //View ting
        soundTitle = rod.findViewById(R.id.playbar_soundTitle);
        playerPosition = rod.findViewById(R.id.playBar_playerPosition);
        playerDuration = rod.findViewById(R.id.playBar_playerDuration);
        seekBar = rod.findViewById(R.id.playbar_seekBar);
        play = rod.findViewById(R.id.playbar_playbutton);

        //Vi får mediaplayeren fra mainactivity og tildeler den til objektet lydafspiller
        MainActivity main = (MainActivity) getActivity();
        modelViewController = main.getModelViewController();
        lydAfspiller = modelViewController.getLydAfspiller();



        //Listeners
        lydAfspiller.addOnLydAfspillerListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tjekker om den allerede spiller
                if (lydAfspiller.isPlaying()) {
                    if (lydAfspiller != null) {
                        lydAfspiller.pause();
                        handler.removeCallbacks(mUpdateTimeTask);
                    }
                } else {
                    //Resume sound
                    if (lydAfspiller != null) {
                        lydAfspiller.playSound();
                    }
                }
            }
        });
        //Returnerer viewet
        return rod;
    }

    //Runnable, som står for alt hvad der skal ske imens lyden spilles.
    private Runnable mUpdateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = lydAfspiller.getDuration();
            long currentDuration = lydAfspiller.getCurrentPosition();
            //Viser afspillerens længde
            if (!playerPosition.getText().equals(totalDuration)) {
                playerDuration.setText(Utilities.convertFormat(totalDuration));
            }

            //updates the name of the playing sound
            soundTitle.setText(lydAfspiller.getCurrentSong());

            //Viser den afspillede tid (nuværende)
            playerPosition.setText(Utilities.convertFormat(currentDuration));
            //får procentendelen af den afspillede tid udfra den totale tid
            int progress = Utilities.getProgressPercentage(currentDuration, totalDuration);
            //sæt progress på seekbaren
            seekBar.setProgress(progress);
            Log.d("Progress ", "" + progress);
            //Handler post delay for 0.1 seconds
            handler.postDelayed(this, 100);
        }
    };

    @Override
    public void updateLydAfspiller(LydAfspiller lydAfspiller) {
        System.out.println("Vi er i update lydafspiller" + lydAfspiller.isPlaying());
        if (!lydAfspiller.isPlaying()) {
            //Show play button
            play.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
        } else {
            //Show pause button
            play.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
        }
        handler.postDelayed(mUpdateTimeTask, 100);
    }

    @Override
    public void soundFinished(LydAfspiller lydAfspiller) {
        //Show play button
        play.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
        //Set media player to initial position
        lydAfspiller.seekTo(0);
        System.out.println("lyden er færdig");
        //Pauser lydafspiller, så vi kan starte lyden forfra
        lydAfspiller.pause();

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Fjern message Handler fra at opdatere progress baren
        if(!soundTitle.getText().equals("")){
        handler.removeCallbacks(mUpdateTimeTask);}
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(!soundTitle.getText().equals("")) {
            handler.removeCallbacks(mUpdateTimeTask);

            int totalDuration = lydAfspiller.getDuration();
            int currentPosition = Utilities.progressToTimer(seekBar.getProgress(), totalDuration);
            lydAfspiller.seekTo(currentPosition);
            handler.postDelayed(mUpdateTimeTask, 100);
        }

    }

}
