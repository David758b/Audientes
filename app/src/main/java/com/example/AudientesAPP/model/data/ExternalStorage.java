package com.example.AudientesAPP.model.data;

import android.os.Environment;

import com.example.AudientesAPP.model.context.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ExternalStorage {

    private Context context;

    public ExternalStorage(Context context) {
        this.context = context;
    }


    //todo vi skal lige tjekke om den stadig kan kører hvis mappen allerede existerer
    /**
     * Creates the Audientes directory
     * @return the directory path
     */
    public File makeDirectory(){
        File directory = new File(context.getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC),"Audientes");
        directory.mkdir();
        return directory;
    }


    //TODO change RID to new path where the files are
    public void fileSaving(int RID, File directory, String newFileName){

        String[] existingFiles = directory.list();

        //returns if a file is allready existing
        for (String fileName: existingFiles) {
            System.out.println("Files in Audientes dir:   " + fileName);
            if (fileName.equals(newFileName)){
                return;
            }
        }

        //Creates the new file
        File newFile = new File(directory.getAbsolutePath(), newFileName);

        try {
            InputStream in = context.getActivity().getResources().openRawResource(RID);
            FileOutputStream out = new FileOutputStream(newFile.getAbsolutePath());
            byte[] buff = new byte[1024];
            int read = 0;

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
