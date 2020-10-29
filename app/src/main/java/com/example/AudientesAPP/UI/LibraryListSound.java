package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.AudientesAPP.R;

public class LibraryListSound extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.library_list_sound_frag, container, false);

        ListView soundList = rod.findViewById(R.id.Library_Sound_Listview);
        String[] lande = {"Sound 1", "Sound 2", "Sound 3", "Sound 4", "Sound 5"};

        //arrayadaper til sound listen (denne skal jo se anderledes ud)
        ArrayAdapter arrayAdapterSoundList = new ArrayAdapter(getActivity(),
                R.layout.sound_list_element, R.id.sound_title, lande) {

            // overskiver getview ( læg mærke til {} parenteserne )
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {

                View view = super.getView(position, cachedView, parent);
                ImageView imageView1 = view.findViewById(R.id.sound_list_element_options);
                TextView title = view.findViewById(R.id.sound_title);
                TextView tag = view.findViewById(R.id.tag_title);
                TextView soundLength = view.findViewById(R.id.sound_length);

                return view;
            }

        };


        soundList.setAdapter(arrayAdapterSoundList);
        soundList.setPadding(0,75,0,20);
        soundList.setDividerHeight(25);

        return rod;
    }
}
