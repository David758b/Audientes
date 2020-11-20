package com.example.AudientesAPP.UI;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.AudientesAPP.LydSpiller;
import com.example.AudientesAPP.R;

public class LibraryListSound extends Fragment implements AdapterView.OnItemClickListener {

    MediaPlayer test;
    LydSpiller lydSpiller;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.library_list_sound_frag, container, false);

        ListView soundList = rod.findViewById(R.id.Library_Sound_Listview);

        // todo --> igen mega hardcoding og skal laves et andet sted.
        String[] lande = {"Pink noise", "Brown noise", "Train", "Rain", "Cricket", "Chihuahua",
                            "Angelo", "JESUS"};

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

        test = MediaPlayer.create(getActivity(), R.raw.testlyd);
        test.setVolume(1,1);

        //mega hardcoding (lappeløsning for at teste)
        lydSpiller = new LydSpiller(test);

        soundList.setAdapter(arrayAdapterSoundList);
        soundList.setPadding(0,75,0,20);
        soundList.setDividerHeight(25);
        soundList.setOnItemClickListener(this);

        return rod;
    }

    // todo --> Dette er mega meget hardcoding og skal foregå i logikken i stedet
    //  (men dette er bare en test)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position){
            case 0:
                onDestroy();
                lydSpiller.playNewSound(0,getActivity());

            break;
            case 1: {
                onDestroy();
                lydSpiller.playNewSound(1,getActivity());
            }
            break;
            case 2: {
                onDestroy();
                lydSpiller.playNewSound(2,getActivity());

            }
            break;
            case 3: {
                onDestroy();
                lydSpiller.playNewSound(3,getActivity());
            }
            break;
            case 4: {
                onDestroy();
                lydSpiller.playNewSound(4,getActivity());
            }
            break;
            case 5: {
                onDestroy();
                lydSpiller.playNewSound(5,getActivity());
            }
            break;
            case 6: {
                onDestroy();
                lydSpiller.playNewSound(6,getActivity());
            }
            break;
            case 7: {
                onDestroy();
                lydSpiller.playNewSound(7,getActivity());
            }
            break;
            default:
                Log.d("lyden kunne ikke afspilles", "onItemClick: ");
        }

    }

    @Override
    public void onDestroy() {
        lydSpiller.stop();
        super.onDestroy();
    }


}
