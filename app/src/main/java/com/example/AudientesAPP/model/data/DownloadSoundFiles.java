package com.example.AudientesAPP.model.data;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.AudientesAPP.UI.MainActivity;
import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;
import com.example.AudientesAPP.model.funktionalitet.Utilities;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DownloadSoundFiles {
    private StorageReference mStorageRef;
    private ModelViewController modelViewController;
    private Context context;
    private SoundDAO soundDAO;
    List<OnDownloadSoundFilesListener> listeners;

    public DownloadSoundFiles(Context context, SoundDAO soundDAO) {
        this.context = context;
        this.soundDAO = soundDAO;
        this.listeners = new ArrayList<>();
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void downloadSoundFiles(String newFileName, String filePath) {

        StorageReference soundRef = mStorageRef.child(filePath);

        File audientesDirectory = makeDirectory();

        String[] existingFiles = makeDirectory().list();

        //returns if a file is allready existing
        for (String fileName: existingFiles) {
            System.out.println("Files in Audientes dir:   " + fileName);
            if (fileName.equals(newFileName)){
                return;
            }
        }

            File extFileSrc = new File(audientesDirectory,newFileName);

            try {
                extFileSrc.createNewFile();
                soundRef.getFile(extFileSrc).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        System.out.println("Downloaded the file: " + soundRef.getName());
                        File fileSrc = getFile(makeDirectory(),newFileName);
                        Uri uriFileSrc = Uri.parse(extFileSrc.getAbsolutePath());
                        System.out.println("EXISTINGFILESRC---" + extFileSrc.getAbsolutePath());
                        String fileDur = getFileDuration(fileSrc);
                        SoundDTO soundDTO = new SoundDTO(newFileName, uriFileSrc.toString(),fileDur);
                        System.out.println(" SOUND DURATION --" + soundDTO.getSoundDuration());
                        System.out.println(" SOUND FILENAME --" + soundDTO.getSoundName());
                        System.out.println(" SOUND SRC --" + soundDTO.getSoundSrc());
                        soundDAO.add(soundDTO);
                        notifyListeners();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        System.out.println("FAILED TOAST");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

        public File makeDirectory(){
            File directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC),"Audientes");
            directory.mkdir();
            return directory;
        }

    public File getFile(File directory, String fileName){
        File[] files = directory.listFiles();
        for (File file: files) {
            if(file.getName().equals(fileName)){
                return file;
            }
        }
        return null;
    }

    public String getFileDuration(File extFileSrc){
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        System.out.println(extFileSrc.getAbsolutePath());
        mediaMetadataRetriever.setDataSource(extFileSrc.getAbsolutePath());
        String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long durationMilliSec = Long.parseLong(durationStr);
        return Utilities.convertFormat(durationMilliSec);
    }

    public void addDLSoundFilesListener(OnDownloadSoundFilesListener listener){
        listeners.add(listener);
    }


    private void notifyListeners(){
        for (OnDownloadSoundFilesListener listener: listeners) {
            listener.updateDownloadSoundFiles(this);
        }
    }

    public interface OnDownloadSoundFilesListener{
        void updateDownloadSoundFiles(DownloadSoundFiles dlSoundFiles);
    }


}
