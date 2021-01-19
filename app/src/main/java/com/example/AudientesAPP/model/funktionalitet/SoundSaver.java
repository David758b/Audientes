package com.example.AudientesAPP.model.funktionalitet;

import android.media.MediaMetadataRetriever;
import android.net.Uri;

import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.data.DAO.SoundDAO;
import com.example.AudientesAPP.data.ExternalStorage;

import java.io.File;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class SoundSaver {
    private ExternalStorage externalStorage;
    private SoundDAO soundDAO;

    //denne klasse skal bruge ExternalStorage og ikke lave den
    public SoundSaver(ExternalStorage externalStorage, SoundDAO soundDAO) {
        this.externalStorage = externalStorage;
        this.soundDAO = soundDAO;
    }

    public void saveSound(int Rid, String soundName){

        //to get the soundSrc

        //SoundSaver skal ikke stå for at lave en DAO! Den skal have en DAO :)
        //SoundDAO soundDAO = new SoundDAO(modelViewController);
        //Denne laver en externalstorage, det er bedre vi får den gennem konstruktøren!
        //ExternalStorage externalStorage = new ExternalStorage(modelViewController);

        File dir = externalStorage.makeDirectory();
        externalStorage.fileSaving(Rid, dir, soundName);
        File soundSrc = externalStorage.getFile(dir, soundName);
        Uri uriSoundSrc = Uri.parse(soundSrc.getAbsolutePath());
        //soundDTO.setSoundSrc(uriSoundSrc.toString());

        //to get sound duration
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(soundSrc.getAbsolutePath());
        String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long durationMilliSec = Long.parseLong(durationStr);
        String finalString = Utilities.convertFormat(durationMilliSec);

        //soundDTO.setSoundDuration(finalString);
        //Istedet for settere så laver vi bare objektet hernede efter alt er fundet og beregnet
        SoundDTO soundDTO = new SoundDTO(soundName, uriSoundSrc.toString(), finalString);
        soundDAO.add(soundDTO);
    }
}
