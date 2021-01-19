package com.example.AudientesAPP.model.funktionalitet;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.data.DAO.SoundDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Test klasse til afspilning af lyde. skal nok laves om
 *
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class LydAfspiller implements MediaPlayer.OnCompletionListener{
    private Context context;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;
    private List<OnLydAfspillerListener> listeners;
    private SoundDAO soundDAO;
    private List<SoundDTO> soundDTOS;
    private String currentSong;

    public LydAfspiller(Context context, SoundDAO soundDAO){
        this.context = context;
        this.listeners = new ArrayList<>();
        this.soundDAO = soundDAO;
    }

    public void playNewSound(String soundName){
        isPlaying = true;
        if(mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(null);
        }
        soundDTOS = soundDAO.getList();

        SoundDTO currentDTO = new SoundDTO("","","");
        for (SoundDTO dto: soundDTOS) {
            if(dto.getSoundName().equals(soundName)){
                currentDTO = dto;
            }
        }

        mediaPlayer = MediaPlayer.create(context, Uri.parse(currentDTO.getSoundSrc()));
        currentSong = currentDTO.getSoundName();
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(this);
        notifyListeners();
    }

    public void playSound(){
        if (mediaPlayer == null) return;
        mediaPlayer.start();
        isPlaying = true;
        notifyListeners();
    }

    public void pause(){
        if (mediaPlayer == null) return;
        mediaPlayer.pause();
        System.out.println("pause");
        isPlaying = false;
        notifyListeners();
    }

    public void stop(){
        if(mediaPlayer == null) return;
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

    public String getCurrentSong() {
        return currentSong;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        for (OnLydAfspillerListener listener: listeners) {
            listener.soundFinished(this);
        }
    }

    public interface OnLydAfspillerListener{
        void updateLydAfspiller(LydAfspiller lydAfspiller);
        void soundFinished(LydAfspiller lydAfspiller);
    }

}
