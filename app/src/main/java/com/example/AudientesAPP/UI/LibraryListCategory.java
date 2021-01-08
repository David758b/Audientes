package com.example.AudientesAPP.UI;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.DTO.CategoryDTO;
import com.example.AudientesAPP.R;

import java.util.ArrayList;
import java.util.List;

public class LibraryListCategory extends Fragment {
    private ImageView categoryIcon;
    private TextView categoryName;

    //Recyclerview
    private RecyclerView categoryList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.library_list_category_frag, container, false);

        categoryList = (RecyclerView) root.findViewById(R.id.Library_Category_Listview);

        layoutManager = new LinearLayoutManager(this.getContext());
        categoryList.setLayoutManager(layoutManager);

        List<String> currentlist = new ArrayList<>();
        currentlist.add("Preset");
        currentlist.add("Sleep");
        currentlist.add("Nature");
        currentlist.add("Ocean");
        currentlist.add("Music");


        try{
            mAdapter = new CategoryAdapter(currentlist,getContext());
        }catch (Exception e){
            e.printStackTrace();
        }
        categoryList.setAdapter(mAdapter);

        return root;
    }
}

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private final Context context;
    private List<String> mDataset;
    //private List<CategoryDTO> mDataset;

   public CategoryAdapter(List<String> mDataset, Context context) {
        this.context = context;
        this.mDataset = mDataset;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String categoryDTO = mDataset.get(position);
        //CategoryDTO categoryDTO = mDataset.get(position);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.audientes_font);
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
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
