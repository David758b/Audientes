package com.example.AudientesAPP.data;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class ExternalStorage {

    private Context context;

    public ExternalStorage(Context context) {
        this.context = context;
    }


    /**
     * Creates the Audientes directory
     * @return the directory path
     */
    public File makeDirectory(){
        File directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC),"Audientes");
        directory.mkdir();
        return directory;
    }


    //TODO change RID to new path where the files are

    /**
     * Saves a file from the raw directory in external storage
     * @param RID the R-id of the raw ressource
     * @param directory which the file should be saved in
     * @param newFileName the desired name of the file
     */
    public void fileSaving(int RID, File directory, String newFileName){

        String[] existingFiles = directory.list();

        //returns if a file is allready existing
        for (String fileName: existingFiles) {
            //System.out.println("Files in Audientes dir:   " + fileName);
            if (fileName.equals(newFileName)){
                return;
            }
        }

        //Creates the new file
        File newFile = new File(directory.getAbsolutePath(), newFileName);

        try {
            InputStream in = context.getResources().openRawResource(RID);
            FileOutputStream out = new FileOutputStream(newFile.getAbsolutePath());
            byte[] buff = new byte[1024];
            int read;

            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
                System.out.println("DET VIRKER!!!!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Returns the file with the corresponding name (if it exists)
     * @param directory of the file
     * @param fileName of the file you want from the given directory
     * @return the file (if it exist)
     */
    public File getFile(File directory, String fileName){
        File[] files = directory.listFiles();
        for (File file: files) {
            if(file.getName().equals(fileName)){
                return file;
            }
        }
        return null;
    }
}
