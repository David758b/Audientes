package com.example.AudientesAPP.model.context;

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
import com.example.AudientesAPP.model.DTO.SoundDTO;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;
import com.example.AudientesAPP.model.data.DAO.PresetCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.PresetDAO;
import com.example.AudientesAPP.model.data.DAO.PresetElementDAO;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;
import com.example.AudientesAPP.model.data.ExternalStorage;
import com.example.AudientesAPP.model.data.SoundDB;
import com.example.AudientesAPP.model.funktionalitet.CategorySoundsLogic;
import com.example.AudientesAPP.model.funktionalitet.LibraryCategoryLogic;
import com.example.AudientesAPP.model.funktionalitet.LibrarySoundLogic;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.example.AudientesAPP.model.funktionalitet.PresetLogic;
import com.example.AudientesAPP.model.funktionalitet.SoundSaver;
import com.example.AudientesAPP.model.funktionalitet.Utilities;

public class ModelViewController {
    private MainActivity context;
    private SharedPreferences prefs;
    private LibrarySoundLogic librarySoundLogic;
    private LibraryCategoryLogic libraryCategoryLogic;
    private CategorySoundsLogic categorySoundsLogic;
    private PresetLogic presetLogic;
    private LydAfspiller lydAfspiller;
    private NavController navController;
    private SoundSaver soundSaver;

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

        externalStorage = new ExternalStorage(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        soundCategoriesDAO = new SoundCategoriesDAO(db);
        presetCategoriesDAO = new PresetCategoriesDAO(db);
        presetElementDAO = new PresetElementDAO(db);
        soundDAO = new SoundDAO(db);
        categoryDAO = new CategoryDAO(db);
        presetDAO = new PresetDAO(db);
        // Giver indhold til databasen før logik klasserne instantieres
        //fillDBUp();

        librarySoundLogic = new LibrarySoundLogic(soundCategoriesDAO,soundDAO);
        categorySoundsLogic = new CategorySoundsLogic(soundCategoriesDAO, soundDAO);
        libraryCategoryLogic = new LibraryCategoryLogic(categoryDAO, categorySoundsLogic);
        presetLogic = new PresetLogic(presetDAO);
        soundSaver = new SoundSaver(externalStorage,soundDAO);

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

    //Fill db with test data
    public void fillDBUp() {
        CategoryDAO categoryDAO = getCategoryDAO();
        CategoryDTO newCategory = new CategoryDTO("I Like to move it", "pictureSrc", "Blue");
        categoryDAO.add(newCategory);

        PresetDAO presetDAO = getPresetDAO();
        PresetDTO presetDTO = new PresetDTO("Sove tid");
        presetDAO.add(presetDTO);

        SoundDAO soundDAO = getSoundDAO();
        SoundDTO soundDTO = new SoundDTO("city noise", "sauce", "420");
        soundDAO.add(soundDTO);

        SoundCategoriesDAO soundCategoriesDAO = getSoundCategoriesDAO();
        SoundCategoriesDTO soundCategoriesDTO = new SoundCategoriesDTO("city noise", "I Like to move it");
        soundCategoriesDAO.add(soundCategoriesDTO);

        PresetCategoriesDAO presetCategoriesDAO = getPresetCategoriesDAO();
        PresetCategoriesDTO presetCategoriesDTO = new PresetCategoriesDTO("Sove tid", "I Like to move it");
        presetCategoriesDAO.add(presetCategoriesDTO);

        PresetElementDAO presetElementDAO = getPresetElementDAO();
        PresetElementDTO newPresetElement = new PresetElementDTO("Sove tid", "city noise", 15);
        presetElementDAO.add(newPresetElement);

    }
}
