package com.example.AudientesAPP.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class SoundDB extends SQLiteOpenHelper {


        public static final int VERSION = 7;
        public static final String DATABASE = "sound.db";
        public static final String TABEL_Sounds = "Sounds";
        public static final String TABEL_Category = "Category";
        public static final String TABEL_Presets = "Presets";
        public static final String TABEL_PresetCategories = "PresetsCategories";
        public static final String TABEL_SoundCategories = "SoundCategories";
        public static final String TABEL_PresetElements = "PresetElements";

        //------------------Sounds----------------------
        public static final String SOUND_NAME = "soundName";
        public static final String PATH = "path";
        public static final String DURATION = "duration";
        //----------------------------------------------

        //---------------Category-----------------------
        public static final String CATEGORY_NAME = "categoryName";
        public static final String CATEGORY_Pic = "categoryPic";
        public static final String CATEGORY_Color = "categoryColor";
        //----------------------------------------------

        //---------------PRESETS-----------------------
        public static final String PRESET_NAME = "presetName";
        //----------------------------------------------

        //---------------PRESET CATEGORIES-----------------------
        //Skal bruge preset_name og category_name som primær og foreign keys
        //----------------------------------------------

        //---------------SOUND CATEGORIES-----------------------
        //Skal bruge sound_name og category_name som primær og foreign keys
        //----------------------------------------------

        //---------------PRESET_ELEMENTS-----------------------
        //Skal bruge preset_name og sound_name som primær og foreign keys

        public static final String CUSTOM_DURATION = "customDuration";
        public static final String VOLUME = "volume";
        public static final String DELAY_BEFORE_PLAYING = "delay";
        public static final String LOOP = "loop";
        //----------------------------------------------


        //konstruktør
        public SoundDB(Context context) {
                super(context, DATABASE, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

                db.execSQL("CREATE TABLE "+TABEL_Sounds+" ("+SOUND_NAME+" TEXT PRIMARY KEY, "+
                        PATH+" TEXT, "+DURATION+" TEXT)");

                db.execSQL("CREATE TABLE "+TABEL_Category+ " ("+CATEGORY_NAME+" TEXT PRIMARY KEY, "+
                        CATEGORY_Pic +" TEXT, "+ CATEGORY_Color + " TEXT) ");
                // CREATE TABLE TABEL_Category (CATEGORY_NAME TEXT PRIMARY KEY, CATEGORY_Pic TEXT, CATEGORY_Color TEXT, ON UPDATE CASCADE)
                db.execSQL("CREATE TABLE "+TABEL_Presets+ " ("+PRESET_NAME+" TEXT PRIMARY KEY)");

                db.execSQL("CREATE TABLE "+TABEL_PresetCategories+ " (" +
                        PRESET_NAME + " TEXT, " +
                        CATEGORY_NAME + " TEXT, PRIMARY KEY(" + PRESET_NAME + ", " + CATEGORY_NAME + "),"
                        + " FOREIGN KEY(" + CATEGORY_NAME + ") REFERENCES " + TABEL_Category + "(" + CATEGORY_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE"
                        + ", FOREIGN KEY(" + PRESET_NAME + ") REFERENCES " + TABEL_Presets + "(" + PRESET_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE)" );

                // CREATE TABLE TABEL_PresetCategories (PRESET_NAME TEXT PRIMARY KEY, CATEGORY_NAME TEXT PRIMARY KEY, FOREIGN KEY(CATEGORY_NAME) REFERENCES TABEL_Category(CATEGORY_NAME),
                // FOREIGN KEY(PRESET_NAME) REFERENCES TABEL_Presets(PRESET_NAME) );
                db.execSQL("CREATE TABLE "+TABEL_SoundCategories+ " (" +
                        SOUND_NAME + " TEXT, " +
                        CATEGORY_NAME + " TEXT, PRIMARY KEY(" + SOUND_NAME + ", " + CATEGORY_NAME + "),"
                        + " FOREIGN KEY(" + CATEGORY_NAME + ") REFERENCES " + TABEL_Category + "(" + CATEGORY_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE" +
                        ", FOREIGN KEY(" + SOUND_NAME + ") REFERENCES " + TABEL_Sounds + "(" + SOUND_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE)" );

                db.execSQL("CREATE TABLE "+TABEL_PresetElements+ " (" +
                        PRESET_NAME + " TEXT, " +
                        SOUND_NAME + " TEXT, " +
                        CUSTOM_DURATION + " INTEGER, " +
                        VOLUME + " INTEGER, " +
                        DELAY_BEFORE_PLAYING + " INTEGER, " +
                        LOOP + " INTEGER, PRIMARY KEY(" + PRESET_NAME + ", " + SOUND_NAME + ")," +
                        " FOREIGN KEY(" + PRESET_NAME + ") REFERENCES " + TABEL_Presets + "(" + PRESET_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE" +
                        ", FOREIGN KEY(" + SOUND_NAME + ") REFERENCES " + TABEL_Sounds + "(" + SOUND_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE)" );


        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                db.execSQL("DROP TABLE IF EXISTS " + TABEL_PresetElements);
                db.execSQL("DROP TABLE IF EXISTS " + TABEL_SoundCategories);
                db.execSQL("DROP TABLE IF EXISTS " + TABEL_PresetCategories);
                db.execSQL("DROP TABLE IF EXISTS " + TABEL_Presets);
                db.execSQL("DROP TABLE IF EXISTS " + TABEL_Category);
                db.execSQL("DROP TABLE IF EXISTS " + TABEL_Sounds);
                this.onCreate(db);
        }
}

