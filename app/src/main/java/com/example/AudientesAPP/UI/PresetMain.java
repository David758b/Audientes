package com.example.AudientesAPP.UI;

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
import com.example.AudientesAPP.R;

public class PresetMain extends Fragment implements AdapterView.OnItemClickListener {

    private ListView presetList;
    private ImageView imageView; //SKAL DER OVERHOVEDET VÆRE ET IMAGEVIEW?
    private TextView presetName;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.preset_main, container, false);
        initialize(v);

        String[] lande = {"Preset 1", "Preset 2", "Preset 3", "Preset 4", "Preset 5"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                R.layout.category_list_element, R.id.textViewCategoryELement, lande) {

            // overskiver getview ( læg mærke til {} parenteserne )
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {

                View view = super.getView(position, cachedView, parent);
                ImageView imageView = view.findViewById(R.id.imageViewCategoryElement);
                TextView textView = view.findViewById(R.id.textViewCategoryELement);

                return view;
            }

        };


        presetList.setAdapter(arrayAdapter);
        presetList.setPadding(0,75,0,20);
        presetList.setDividerHeight(25);



        //med setselector kan man vælge hvad der skal ske når man vælger et liste element.
        // i dette tilfælde anvendes en af androids egne features (rød boks når der klikkes)
        //listView.setSelector(android.R.drawable.ic_notification_overlay);
        //TextView text = rod.findViewById(R.id.textViewTest1);

        presetList.setOnItemClickListener(this);
        return v;
    }

    private void initialize(View v){
        imageView = v.findViewById(R.id.imageViewCategoryElement);
        presetName = v.findViewById(R.id.textViewCategoryELement);
        presetList = v.findViewById(R.id.presetListview);

    }


    //da der ikke skal være funktionalitet endnu, logger vi det bare, så man kan se lortet virker
    //i logcatten
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ListItemClick", "Du klikkede på " + position);
    }
}

