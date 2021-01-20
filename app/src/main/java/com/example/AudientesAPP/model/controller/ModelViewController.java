package com.example.AudientesAPP.model.controller;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.AudientesAPP.R;
import com.example.AudientesAPP.UI.MainActivity;
import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.DTO.PresetCategoriesDTO;
import com.example.AudientesAPP.model.DTO.PresetDTO;
import com.example.AudientesAPP.model.DTO.PresetElementDTO;
import com.example.AudientesAPP.model.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.data.DAO.CategoryDAO;
import com.example.AudientesAPP.data.DAO.PresetCategoriesDAO;
import com.example.AudientesAPP.data.DAO.PresetDAO;
import com.example.AudientesAPP.data.DAO.PresetElementDAO;
import com.example.AudientesAPP.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.data.DAO.SoundDAO;
import com.example.AudientesAPP.data.DownloadSoundFiles;
import com.example.AudientesAPP.data.ExternalStorage;
import com.example.AudientesAPP.data.SoundDB;
import com.example.AudientesAPP.model.funktionalitet.CategorySoundsLogic;
import com.example.AudientesAPP.model.funktionalitet.LibraryCategoryLogic;
import com.example.AudientesAPP.model.funktionalitet.LibrarySoundLogic;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.example.AudientesAPP.model.funktionalitet.PresetContentLogic;
import com.example.AudientesAPP.model.funktionalitet.PresetLogic;
import com.example.AudientesAPP.model.funktionalitet.SoundSaver;

/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class ModelViewController {
    private MainActivity context;
    private SharedPreferences prefs;
    private LibrarySoundLogic librarySoundLogic;
    private LibraryCategoryLogic libraryCategoryLogic;
    private CategorySoundsLogic categorySoundsLogic;
    private PresetContentLogic presetContentLogic;
    private PresetLogic presetLogic;
    private LydAfspiller lydAfspiller;
    private NavController navController;
    private SoundSaver soundSaver;
    private DownloadSoundFiles dlSoundFiles;

    //Data
    private ExternalStorage externalStorage;
    private SQLiteDatabase db;
    private SoundDB soundDB;
    private SoundDAO soundDAO;
    private CategoryDAO categoryDAO;
    private PresetDAO presetDAO;
    private PresetElementDAO presetElementDAO;
    private PresetCategoriesDAO presetCategoriesDAO;
    private SoundCategoriesDAO soundCategoriesDAO;
    //private CategoryDAO categoryDAO;

    //Burde ikke få en instans af main, men kun context som er overklassen og ikke subklasse
    //Overvej om denne kunne være en singleton
    public ModelViewController(MainActivity context) {
        this.context = context;
        //---------------------DATALAG---------------------
        soundDB = new SoundDB(context);
        db = soundDB.getWritableDatabase();

        //Storage reference til firebase


        externalStorage = new ExternalStorage(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        //---------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------
        //----------------------!!!!!!!!!!!!VIGTIGT!!!!!!!!!!!!----------------------------
        /*
        - DAO'erne SKAL være initialiseret før soundSaver
        - soundSaver skal oprettes før saveRawFiles()
        - saveRawFiles skal laves før fillDBUp()
        - fillDBUp skal laves før logik klasserne
         */
        //----------------------!!!!!!!!!!!!VIGTIGT!!!!!!!!!!!!----------------------------
        //---------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------
        soundCategoriesDAO = new SoundCategoriesDAO(db);
        presetCategoriesDAO = new PresetCategoriesDAO(db);
        presetElementDAO = new PresetElementDAO(db);
        soundDAO = new SoundDAO(db);
        dlSoundFiles = new DownloadSoundFiles(context,soundDAO);
        categoryDAO = new CategoryDAO(db);
        presetDAO = new PresetDAO(db);

        soundSaver = new SoundSaver(externalStorage,soundDAO);
        if (prefs.getBoolean("firstDBrun", true)) {
            saveRawFiles();
            fillDBUp();
            prefs.edit().putBoolean("firstDBrun", false).commit();
        }
        librarySoundLogic = new LibrarySoundLogic(soundCategoriesDAO,soundDAO);
        categorySoundsLogic = new CategorySoundsLogic(soundCategoriesDAO, soundDAO, prefs);
        libraryCategoryLogic = new LibraryCategoryLogic(categoryDAO, categorySoundsLogic);
        presetContentLogic = new PresetContentLogic(presetElementDAO, soundDAO, prefs);
        presetLogic = new PresetLogic(presetDAO, presetContentLogic);


        lydAfspiller = new LydAfspiller(context, soundDAO);

        //Navigations komponenten indeholder en default implementation af Navhost (NavHostFragment)
        //der viser destinationer af typen fragmenter
        NavHostFragment navHostFragment = (NavHostFragment) context.getSupportFragmentManager().
                findFragmentById(R.id.navHost);

        //Navcontrolleren er et objekt der styrer navigatioen inde i Navhosten. dvs den står for
        //koordinationen af at skifte mellem forskellige destinationer
        navController = navHostFragment.getNavController();

//        categoryDAO = new CategoryDAO(this);

        //TESTING
        //categoryDAO.add("te");
       /*
        Cursor cursor = main.getDB().query(SoundDB.TABEL_Category,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String navn = cursor.getString(0);
            System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEST");
            System.out.println(navn);

        }
        cursor.close();

        */
    }

    public MainActivity getContext() {
        return context;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public LibrarySoundLogic getLibrarySoundLogic() {
        return librarySoundLogic;
    }

    public LibraryCategoryLogic getLibraryCategoryLogic() {
        return libraryCategoryLogic;
    }

    public PresetContentLogic getPresetContentLogic() {
        return presetContentLogic;
    }

    public LydAfspiller getLydAfspiller() {
        return lydAfspiller;
    }

    public NavController getNavController() {
        return navController;
    }

    public SoundSaver getSoundSaver() {
        return soundSaver;
    }

    public ExternalStorage getExternalStorage() {
        return externalStorage;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public SoundDB getSoundDB() {
        return soundDB;
    }

    public SoundDAO getSoundDAO() {
        return soundDAO;
    }

    public PresetDAO getPresetDAO() {
        return presetDAO;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public SoundCategoriesDAO getSoundCategoriesDAO() {
        return soundCategoriesDAO;
    }

    public CategorySoundsLogic getCategorySoundsLogic() {
        return categorySoundsLogic;
    }

    public PresetLogic getPresetLogic() {
        return presetLogic;
    }

    public PresetElementDAO getPresetElementDAO() {
        return presetElementDAO;
    }

    public PresetCategoriesDAO getPresetCategoriesDAO() {
        return presetCategoriesDAO;
    }

    public DownloadSoundFiles getDlSoundFiles() {
        return dlSoundFiles;
    }

    public void saveRawFiles(){
        //Gemmer alle lyde i raw mappen i external storage
        getSoundSaver().saveSound(R.raw.bublewater, "Bubble water");
        getSoundSaver().saveSound(R.raw.catmeowing, "Cat meowing");
        getSoundSaver().saveSound(R.raw.dolphins, "Dolphins");
        getSoundSaver().saveSound(R.raw.etherealvoice, "Ethereal voice");
        getSoundSaver().saveSound(R.raw.forestbirds, "Forest birds");
        getSoundSaver().saveSound(R.raw.frogs, "Frogs");
        getSoundSaver().saveSound(R.raw.monkspraying, "Monks praying");
        getSoundSaver().saveSound(R.raw.rainandthunder, "Rain and thunder");
        getSoundSaver().saveSound(R.raw.rainandthunder, "Running water");
    }





    //Fill db with test data
    public void fillDBUp() {
        CategoryDAO categoryDAO = getCategoryDAO();
        CategoryDTO category1 = new CategoryDTO("Animals", "pictureSrc", "Blue");
        categoryDAO.add(category1);
        CategoryDTO category2 = new CategoryDTO("Nature", "pictureSrc", "Green");
        categoryDAO.add(category2);
        CategoryDTO category3 = new CategoryDTO("Music", "pictureSrc", "Yellow");
        categoryDAO.add(category3);

        PresetDAO presetDAO = getPresetDAO();
        PresetDTO presetDTO = new PresetDTO("Sleeping time");
        presetDAO.add(presetDTO);

        SoundCategoriesDAO soundCategoriesDAO = getSoundCategoriesDAO();
        SoundCategoriesDTO soundCategoriesDTO1 = new SoundCategoriesDTO("Cat meowing", "Animals");
        soundCategoriesDAO.add(soundCategoriesDTO1);
        SoundCategoriesDTO soundCategoriesDTO2 = new SoundCategoriesDTO("Dolphins", "Animals");
        soundCategoriesDAO.add(soundCategoriesDTO2);
        SoundCategoriesDTO soundCategoriesDTO3 = new SoundCategoriesDTO("Frogs", "Animals");
        soundCategoriesDAO.add(soundCategoriesDTO3);
        SoundCategoriesDTO soundCategoriesDTO4 = new SoundCategoriesDTO("Forest birds", "Animals");
        soundCategoriesDAO.add(soundCategoriesDTO4);
        SoundCategoriesDTO soundCategoriesDTO5 = new SoundCategoriesDTO("Bubble water", "Nature");
        soundCategoriesDAO.add(soundCategoriesDTO5);
        SoundCategoriesDTO soundCategoriesDTO6 = new SoundCategoriesDTO("Rain and thunder", "Nature");
        soundCategoriesDAO.add(soundCategoriesDTO6);
        SoundCategoriesDTO soundCategoriesDTO7 = new SoundCategoriesDTO("Running water", "Nature");
        soundCategoriesDAO.add(soundCategoriesDTO7);
        SoundCategoriesDTO soundCategoriesDTO8 = new SoundCategoriesDTO("Ethereal voice", "Music");
        soundCategoriesDAO.add(soundCategoriesDTO8);
        SoundCategoriesDTO soundCategoriesDTO9 = new SoundCategoriesDTO("Monks praying", "Music");
        soundCategoriesDAO.add(soundCategoriesDTO9);

        // TODO: Mangler logic klasser til de sidste to tabeller
        PresetCategoriesDAO presetCategoriesDAO = getPresetCategoriesDAO();
        PresetCategoriesDTO presetCategoriesDTO = new PresetCategoriesDTO("Sleeping time", "Nature");
        presetCategoriesDAO.add(presetCategoriesDTO);

        PresetElementDAO presetElementDAO = getPresetElementDAO();
        PresetElementDTO newPresetElement1 = new PresetElementDTO("Sleeping time", "Bubble water", 15);
        presetElementDAO.add(newPresetElement1);
        PresetElementDTO newPresetElement2 = new PresetElementDTO("Sleeping time", "Forest birds", 15);
        presetElementDAO.add(newPresetElement2);
    }
}
