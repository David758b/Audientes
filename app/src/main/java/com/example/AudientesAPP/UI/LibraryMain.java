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

import androidx.fragment.app.Fragment;

import com.example.AudientesAPP.R;

public class LibraryMain extends Fragment implements AdapterView.OnItemClickListener {

    private ListView categoryList;
    int positionInList = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.library_main, container, false);

        String[] lande = {"Danmark", "Norge", "Sverige", "Japan", "Kina", "Afganistan", "Karstenstan"
        ,"DTU-stan", "Irak", "lallal", "lalalalal", "lalalal"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                R.layout.category_list_element, R.id.textViewCategoryELement, lande) {

            // overskiver getview ( læg mærke til {} parenteserne )
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {

                View view = super.getView(position, cachedView, parent);
                ImageView imageView = view.findViewById(R.id.imageViewCategoryElement);

                // todo -- lav switchcases for hver position for at skifte billede og tekst

                imageView.setImageResource(R.drawable.ic_launcher_background);

                return view;
            }

        };



        categoryList = rod.findViewById(R.id.categoryListview);
        categoryList.setAdapter(arrayAdapter);

        categoryList.setPadding(0,75,0,20);
        categoryList.setDividerHeight(25);



        //med setselector kan man vælge hvad der skal ske når man vælger et liste element.
        // i dette tilfælde anvendes en af androids egne features (rød boks når der klikkes)
        //listView.setSelector(android.R.drawable.ic_notification_overlay);
        //TextView text = rod.findViewById(R.id.textViewTest1);

        categoryList.setOnItemClickListener(this);
        return rod;
    }


    //da der ikke skal være funktionalitet endnu, logger vi det bare, så man kan se lortet virker
    //i logcatten
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ListItemClick", "Du klikkede på " + position);
    }
}
