package com.example.AudientesAPP.UI;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.DTO.CategoryDTO;
import com.example.AudientesAPP.DTO.PresetElementDTO;
import com.example.AudientesAPP.model.context.Context;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;
import com.example.AudientesAPP.model.data.DAO.PresetElementDAO;
import com.example.AudientesAPP.model.funktionalitet.LibraryListCategoryLogic;

import java.util.ArrayList;
import java.util.List;

public class LibraryListCategory extends Fragment{
    // Test
    private LibraryListCategoryLogic logic;
    private ImageView categoryIcon;
    private TextView categoryName;

    //Recyclerview
    private RecyclerView categoryList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private NavController navController;
    private CategoryDAO categoryDAO;
    private Context context;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.library_list_category_frag, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        context = mainActivity.getContext();

        NavHostFragment navHostFragment = (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.navHost);
        //navController = navHostFragment.getNavController();

        categoryList = (RecyclerView) root.findViewById(R.id.Library_Category_Listview);

        layoutManager = new LinearLayoutManager(this.getContext());
        categoryList.setLayoutManager(layoutManager);



        // TEST - Test af logik, som nok skal være i funktionalitetsmappen
        logic = new LibraryListCategoryLogic(context);
        List<String> categoryNames = logic.getCategoryNames();




        try{
            mAdapter = new CategoryAdapter(categoryNames,this.getActivity(), mainActivity.getNavController());
        }catch (Exception e){
            e.printStackTrace();
        }
        categoryList.setAdapter(mAdapter);

        return root;
    }
}

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    //private final Context context;
    private FragmentActivity fragmentActivity;
    private List<String> mDataset;
    //private List<CategoryDTO> mDataset;
    private Fragment mFragment;
    private Bundle mBundle;
    private NavController navController;


   public CategoryAdapter(List<String> mDataset, FragmentActivity fragmentActivity, NavController navController) {
        this.fragmentActivity = fragmentActivity;
        this.mDataset = mDataset;
        this.navController = navController;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView categoryName;
        public ImageView categoryIcon;


        public MyViewHolder(View v) {
            super(v);
            categoryName = v.findViewById(R.id.categoryName_TV);
            categoryIcon = v.findViewById(R.id.categoryIcon_IV);
        }
    }


    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_element,parent,false);
       MyViewHolder vh = new MyViewHolder(v);
       return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        String categoryDTO = mDataset.get(position);
        //CategoryDTO categoryDTO = mDataset.get(position);
        Typeface typeface = ResourcesCompat.getFont(fragmentActivity, R.font.audientes_font);
        holder.categoryName.setTypeface(typeface);
        holder.categoryName.setText(categoryDTO);
        //holder.categoryName.setText(categoryDTO.getCategoryName());


        //hardcoding for at skifte billeder og tekst for hvert liste element
        switch(position){

            //preset category
            case 0: {holder.categoryIcon.setImageResource(R.drawable.ic_baseline_tune_24);
                //sætter en nu farve uden at ændre formen på objektet
                holder.itemView.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.LIGHTEN);}

            break;
            //sleep category
            case 1: {holder.categoryIcon.setImageResource(R.drawable.ic_sleep_category);
                holder.itemView.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);}
            break;
            //nature category
            case 2: {holder.categoryIcon.setImageResource(R.drawable.ic_nature2_category);
                holder.itemView.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.LIGHTEN);}
            break;
            //waves category
            case 3: {holder.categoryIcon.setImageResource(R.drawable.ic_waves_category);
                holder.itemView.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.LIGHTEN);}
            break;
            //music category
            case 4: {holder.categoryIcon.setImageResource(R.drawable.ic_music_category);
                holder.itemView.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.LIGHTEN);}
            break;
            default: Log.d("List error",
                    "Error in applying image to list element");
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigering til fragment med category sounds eller presets
                /*switch (position) {
                    case 0:
                        navController.navigate(R.id.action_LibraryMain_to_HearingTest);
                        break;
                    case 1:
                        navController.navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
                        break;

                }*/
                if (position == 0) {
                    // Hop til create category siden
                } else {
                    // Hop til den rigtige kategori
                    Bundle bundle = new Bundle();
                    bundle.putString("CategoryName", mDataset.get(position));
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(fragmentActivity);
                    prefs.edit().putString("Category", mDataset.get(position)).apply();
                    navController.navigate(R.id.action_libraryListCategory_to_CategoryListSounds, bundle);
                }
                //fragmentJump();


            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void fragmentJump() {
        /*mFragment = new Fragment2();
        mBundle = new Bundle();
        mBundle.putParcelable("item_selected_key", Parcelable);
        mFragment.setArguments(mBundle);
        switchContent(R.id.frag1, mFragment);*/
        //Navigation.findNavController(v).navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
        navController.navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
        /*CategoryListSounds fragment = new CategoryListSounds();
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.libraryMain, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
//        Navigation.findNavController(v).navigate(R.id.action_libraryListCategory_to_CategoryListSounds);

    }


}
