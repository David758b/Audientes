package com.example.AudientesAPP.UI;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.AudientesAPP.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LibraryMain extends Fragment implements AdapterView.OnItemClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private ListView categoryList,soundlist;
    private View rod;
    private ArrayAdapter arrayAdapterSoundList,arrayAdapterCategoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rod = inflater.inflate(R.layout.library_main, container, false);

        //todo dette er mega hardcoded... sæt det ind i en ressurcefil istedet og referer den
        String[] lande = {"Presets", "Sleep", "Nature", "Ocean", "Music"};

        BottomNavigationView tabNavigationBar = rod.findViewById(R.id.tabMenu);
        tabNavigationBar.setVisibility(View.VISIBLE);



        arrayAdapterCategoryList = new ArrayAdapter(getActivity(),
                R.layout.category_list_element, R.id.textViewCategoryELement, lande) {

            // overskiver getview ( læg mærke til {} parenteserne )
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {

                View view = super.getView(position, cachedView, parent);
                ImageView imageView = view.findViewById(R.id.imageViewCategoryElement);
                TextView textView = rod.findViewById(R.id.textViewCategoryELement);


                //hardcoding for at skifte billeder og tekst for hvert liste element
                switch(position){

                    //preset category
                    case 0: {imageView.setImageResource(R.drawable.ic_baseline_tune_24);
                    //sætter en nu farve uden at ændre formen på objektet
                    view.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.LIGHTEN);}

                    break;
                    //sleep category
                    case 1: {imageView.setImageResource(R.drawable.ic_sleep_category);
                        view.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);}
                    break;
                    //nature category
                    case 2: {imageView.setImageResource(R.drawable.ic_nature2_category);
                        view.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.LIGHTEN);}
                    break;
                    //waves category
                    case 3: {imageView.setImageResource(R.drawable.ic_waves_category);
                        view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.LIGHTEN);}
                    break;
                    //music category
                    case 4: {imageView.setImageResource(R.drawable.ic_music_category);
                        view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.LIGHTEN);}
                    break;
                    default: Log.d("List error",
                            "Error in applying image to list element");
                    break;
                }

                return view;
            }

        };


        //arrayadaper til sound listen (denne skal jo se anderledes ud)
        arrayAdapterSoundList = new ArrayAdapter(getActivity(),
                R.layout.sound_list_element, R.id.textViewCategoryELement, lande) {

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

        categoryList = rod.findViewById(R.id.Library_Listview);
        categoryList.setAdapter(arrayAdapterCategoryList);
        categoryList.setPadding(0,75,0,20);
        categoryList.setDividerHeight(25);

        tabNavigationBar.setOnNavigationItemSelectedListener(this);



        //-------------------------------------------------------------------------------


        return rod;
    }


    //da der ikke skal være funktionalitet endnu, logger vi det bare, så man kan se lortet virker
    //i logcatten
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ListItemClick", "Du klikkede på " + position);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getItemId() == R.id.Category_tab){

        }
        else if(menuItem.getItemId()== R.id.Sounds_tab){

        }

        return false;
    }
}
