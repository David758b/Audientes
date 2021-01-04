package com.example.AudientesAPP.UI;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.AudientesAPP.R;

public class LibraryListCategory extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.library_list_category_frag, container, false);

        ListView categoryList = rod.findViewById(R.id.Library_Category_Listview);
        // HHAAAARRDDDCOOOODDDDEEEDDD
        String[] lande = {"Presets", "Sleep", "Nature", "Ocean", "Music"};

        ArrayAdapter arrayAdapterCategoryList = new ArrayAdapter(getActivity(),
                R.layout.category_list_element, R.id.textViewCategoryELement, lande) {

            // overskiver getview ( læg mærke til {} parenteserne )
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {

                View view = super.getView(position, cachedView, parent);
                ImageView imageView = view.findViewById(R.id.imageViewCategoryElement);
                TextView textView = view.findViewById(R.id.textViewCategoryELement);

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

        categoryList.setAdapter(arrayAdapterCategoryList);
        categoryList.setPadding(0,75,0,20);
        categoryList.setDividerHeight(25);

        return rod;
    }

}
