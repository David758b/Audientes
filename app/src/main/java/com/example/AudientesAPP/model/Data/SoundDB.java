package com.example.AudientesAPP.model.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SoundDB extends SQLiteOpenHelper {

        static final int VERSION = 1;
        static final String DATABASE = "sound.db";
        static final String TABEL_Sounds = "Sounds";
        static final String TABEL_Category = "Category";
        static final String TABEL_Presets = "Presets";
        static final String TABEL_PresetCategories = "PresetsCategories";
        static final String TABEL_SoundCategories = "SoundCategories";
        static final String TABEL_PresetElements = "PresetElements";

        //------------------Sounds----------------------
        static final String SOUND_NAME = "soundName";
        static final String PATH = "path";
        static final String DURATION = "duration";
        //----------------------------------------------

        //---------------Category-----------------------
        static final String CATEGORY_NAME = "categoryName";
        //----------------------------------------------

        //---------------PRESETS-----------------------
        static final String PRESET_NAME = "presetName";
        //----------------------------------------------

        //---------------PRESET CATEGORIES-----------------------
        //Skal bruge preset_name og category_name som primær og foreign keys
        //----------------------------------------------

        //---------------SOUND CATEGORIES-----------------------
        //Skal bruge sound_name og category_name som primær og foreign keys
        //----------------------------------------------

        //---------------PRESET_ELEMENTS-----------------------
        //Skal bruge preset_name og sound_name som primær og foreign keys

        static final String CUSTOM_DURATION = "customDuration";
        static final String VOLUME = "volume";
        static final String DELAY_BEFORE_PLAYING = "delay";
        static final String LOOP = "loop";
        //----------------------------------------------


        //konstruktør
        public SoundDB(Context context) {
                super(context, DATABASE, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

                //Pas nu forhelvede på her! det skal skrives ordenligt ellers bruger man
                // fucking lang tid på at fejlsøge
                // tjek alt fra stavefejl til mellemrum

                /*db.execSQL("CREATE TABLE " + TABEL1 + " (" + ID + " INTEGER PRIMARY KEY, "
                        + NAVN + " TEXT, " + POINT + " INTEGER)");*/

                db.execSQL("CREATE TABLE "+TABEL_Sounds+" ("+SOUND_NAME+" TEXT PRIMARY KEY, "+
                        PATH+" TEXT, "+DURATION+" INTEGER)");

                db.execSQL("CREATE TABLE "+TABEL_Category+ " ("+CATEGORY_NAME+" TEXT PRIMARY KEY)");

                db.execSQL("CREATE TABLE "+TABEL_Presets+ " ("+PRESET_NAME+" TEXT PRIMARY KEY)");

                db.execSQL("CREATE TABLE "+TABEL_PresetCategories+ " (" + PRESET_NAME + " TEXT PRIMARY KEY, " +
                        CATEGORY_NAME + " TEXT PRIMARY KEY, " + " FOREIGN KEY(" + CATEGORY_NAME + ") REFERENCES " +
                                TABEL_Category + "(" + CATEGORY_NAME + ")" + ", FOREIGN KEY(" + PRESET_NAME + ") REFERENCES " +
                        TABEL_Presets + "(" + PRESET_NAME + "))" );

                // CREATE TABLE TABEL_PresetCategories (PRESET_NAME TEXT PRIMARY KEY, CATEGORY_NAME TEXT PRIMARY KEY, FOREIGN KEY(CATEGORY_NAME) REFERENCES TABEL_Category(CATEGORY_NAME),
                // FOREIGN KEY(PRESET_NAME) REFERENCES TABEL_Presets(PRESET_NAME) );
                db.execSQL("CREATE TABLE "+TABEL_SoundCategories+ " (" + SOUND_NAME + " TEXT PRIMARY KEY, " +
                        CATEGORY_NAME + " TEXT PRIMARY KEY, " + " FOREIGN KEY(" + CATEGORY_NAME + ") REFERENCES " +
                        TABEL_Category + "(" + CATEGORY_NAME + ")" + ", FOREIGN KEY(" + SOUND_NAME + ") REFERENCES " +
                        TABEL_Sounds + "(" + SOUND_NAME + "))" );

                db.execSQL("CREATE TABLE "+TABEL_PresetElements+ " (" + PRESET_NAME + " TEXT PRIMARY KEY, " +
                        SOUND_NAME + " TEXT PRIMARY KEY, " + CUSTOM_DURATION + " INTEGER, " + VOLUME + " INTEGER, " +
                        DELAY_BEFORE_PLAYING + " INTEGER, " + LOOP + " INTEGER, " + " FOREIGN KEY(" + PRESET_NAME + ") REFERENCES " +
                        TABEL_Presets + "(" + PRESET_NAME + ")" + ", FOREIGN KEY(" + SOUND_NAME + ") REFERENCES " +
                        TABEL_Sounds + "(" + SOUND_NAME + "))" );


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                db.execSQL("DROP TABLE " + TABEL_Sounds);
                this.onCreate(db);
        }
}

