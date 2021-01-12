package com.example.AudientesAPP.model.funktionalitet;

import android.media.MediaMetadataRetriever;
import android.net.Uri;

import com.example.AudientesAPP.DTO.SoundDTO;
import com.example.AudientesAPP.model.context.Controller;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;
import com.example.AudientesAPP.model.data.ExternalStorage;

import java.io.File;

public class SoundSaver {
    private Controller controller;

    public SoundSaver(Controller controller) {
        this.controller = controller;
    }

    public void saveSound(int Rid, String soundName){

        //to get the soundSrc
        SoundDTO soundDTO = new SoundDTO(soundName, "", "");
        SoundDAO soundDAO = new SoundDAO(controller);
        ExternalStorage externalStorage = new ExternalStorage(controller);
        File dir = externalStorage.makeDirectory();
        externalStorage.fileSaving(Rid, dir, soundDTO.getSoundName());
        File soundSrc = externalStorage.getFile(dir, soundDTO.getSoundName());
        Uri uriSoundSrc = Uri.parse(soundSrc.getAbsolutePath());
        soundDTO.setSoundSrc(uriSoundSrc.toString());

        //to get sound duration
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(soundSrc.getAbsolutePath());
        String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long durationMilliSec = Long.parseLong(durationStr);
        String finalString = controller.getUtils().convertFormat(durationMilliSec);

        soundDTO.setSoundDuration(finalString);

        soundDAO.add(soundDTO);
    }
}
