package com.example.AudientesAPP.UI;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.example.AudientesAPP.R;

import java.util.concurrent.TimeUnit;


/**
 * test fragment til en playbar (dvs en menu hvor man kan pause og play en lyd)
 */
public class PlayBar_Frag extends Fragment implements LydAfspiller.OnLydAfspillerListener {

    private ImageView play, pause;
    private SeekBar seekBar;
    private TextView soundTitle, playerDuration, playerPosition;
    private LydAfspiller lydAfspiller;

    private Runnable runnable;
    private Handler handler = new Handler();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.playbar, container, false);

        soundTitle = rod.findViewById(R.id.playbar_soundTitle);
        playerPosition = rod.findViewById(R.id.playBar_playerPosition);
        playerDuration = rod.findViewById(R.id.playBar_playerDuration);
        seekBar = rod.findViewById(R.id.playbar_seekBar);

        pause = rod.findViewById(R.id.playbar_pausebutton);
        play = rod.findViewById(R.id.playbar_playbutton);

        MainActivity main = (MainActivity) getActivity();
        lydAfspiller = main.getLydAfspiller();

        lydAfspiller.addOnLydAfspillerListener(this);
        // TODO: 22/11/2020 Der skal laves en eller anden form for listener på lydspiller 
        //Der skal laves en eller anden form for listener på lydspiller
        //så når der bliver valgt en lyd skal knappen ændre sig
        runnable = new Runnable() {
            @Override
            public void run() {
                int totalDuration = lydAfspiller.getDuration();
                int currentDuration = lydAfspiller.getCurrentPosition();

                //sæt progress på seekbaren
                seekBar.setProgress(lydAfspiller.getCurrentPosition());
                seekBar.setMax(lydAfspiller.getDuration());
                //Handler post delay for 0.1 seconds
                handler.postDelayed(this,100);

                playerDuration.setText(convertFormat(totalDuration));
                playerPosition.setText(convertFormat(currentDuration));


                LydAfspiller.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //hide pause button
                        pause.setVisibility(View.INVISIBLE);
                        //Show play button
                        play.setVisibility(View.VISIBLE);
                        //Set media player to initial position
                        lydAfspiller.seekTo(0);
                        System.out.println("lyden er færdig");
                    }
                });
            }
        };

        //Længden af mediaspilleren
        final int duration = lydAfspiller.getDuration();
        //Konverter millisekunder til minut og sekunder.
        final String sDuration = convertFormat(duration);
        //Set duration on textview
        playerDuration.setText(sDuration);

        play.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Start mediaplayer
                lydAfspiller.playSound();
                //Set max på seekbar
                seekBar.setMax(lydAfspiller.getDuration());
                //Start handler
                handler.postDelayed(runnable,0);

                playerDuration.setText(convertFormat(lydAfspiller.getDuration()));
            }
        });

        pause.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //pause mediaplayer
                lydAfspiller.pause();
                //Stop handler
                handler.removeCallbacks(runnable);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //check condition
                if (fromUser){
                    //Når man trækker i seekbaren
                    //sæt progress på seekbar
                    lydAfspiller.seekTo(progress);
                }
                //Sæt den nuværende position på textview
                playerPosition.setText(convertFormat(lydAfspiller.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return rod;
    }


    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration){
        return String.format("%02d:%02d"
                , TimeUnit.MILLISECONDS.toMinutes(duration)
                , TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    @Override
    public void updateLydAfspiller(LydAfspiller lydAfspiller) {
        System.out.println("Vi er i update lydafspiller" + lydAfspiller.isPlaying());
        if (!lydAfspiller.isPlaying()) {
            //Hide pause button
            pause.setVisibility(View.INVISIBLE);
            //Show play button
            play.setVisibility(View.VISIBLE);
        } else {
            //Hide play button
            play.setVisibility(View.INVISIBLE);
            //Show pause button
            pause.setVisibility(View.VISIBLE);
        }
    }
}
