package com.example.AudientesAPP.model.funktionalitet;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.AudientesAPP.R;
import com.example.AudientesAPP.UI.PlayBar_Frag;

import java.util.ArrayList;
import java.util.List;

/**
 * Test klasse til afspilning af lyde. skal nok laves om
 */
public class LydAfspiller {

    public static MediaPlayer mediaPlayer;
    boolean isPlaying;
    List<OnLydAfspillerListener> listeners;

    public LydAfspiller(MediaPlayer mediaPlayer, Activity activity){
        this.mediaPlayer = mediaPlayer;
        this.mediaPlayer = MediaPlayer.create(activity, R.raw.testlyd);
        this.listeners = new ArrayList<>();
    }

    public void playNewSound(int position, Activity activity){
        isPlaying = true;
        switch (position){
            case 0:

                mediaPlayer = MediaPlayer.create(activity, R.raw.testlyd);
                mediaPlayer.start();
                break;
            case 1: {

                mediaPlayer = MediaPlayer.create(activity, R.raw.brown_noise);
                mediaPlayer.start();
            }
            break;
            case 2: {

                mediaPlayer = MediaPlayer.create(activity, R.raw.train_nature);
                mediaPlayer.start();
            }
            break;
            case 3: {

                mediaPlayer = MediaPlayer.create(activity, R.raw.rain_street);
                mediaPlayer.start();
            }
            break;
            case 4: {

                mediaPlayer = MediaPlayer.create(activity, R.raw.cricket);
                mediaPlayer.start();
            }
            break;
            case 5: {

                mediaPlayer = MediaPlayer.create(activity, R.raw.chihuahua);
                mediaPlayer.start();
            }
            break;
            case 6: {

                mediaPlayer = MediaPlayer.create(activity, R.raw.andreangelo);
                mediaPlayer.start();
            }
            break;
            case 7: {

                mediaPlayer = MediaPlayer.create(activity, R.raw.melarancida__monks_praying);
                mediaPlayer.start();
            }
            break;
            default:
                Log.d("lyden kunne ikke afspilles", "onItemClick: ");
        }
        notifyListeners();
    }

    public void playSound(){
        mediaPlayer.start();
        isPlaying = true;
        notifyListeners();
    }

    public void pause(){
        mediaPlayer.pause();
        System.out.println("pause");
        isPlaying = false;
        notifyListeners();
    }

    public void stop(){
        mediaPlayer.stop();
        mediaPlayer.release();
        isPlaying = false;
        notifyListeners();
    }

    public boolean isPlaying(){
        return isPlaying;
    }

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public void seekTo(int progress){
        mediaPlayer.seekTo(progress);
    }

    public void addOnLydAfspillerListener(OnLydAfspillerListener listener){
        listeners.add(listener);
    }

    private void notifyListeners(){
        for (OnLydAfspillerListener listener: listeners) {
            listener.updateLydAfspiller(this);
        }
    }

    public interface OnLydAfspillerListener{
        void updateLydAfspiller(LydAfspiller lydAfspiller);

    }

}
