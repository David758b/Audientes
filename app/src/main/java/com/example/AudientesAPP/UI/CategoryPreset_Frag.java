package com.example.AudientesAPP.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.controller.ModelViewController;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.example.AudientesAPP.model.funktionalitet.PresetContentLogic;
import com.example.AudientesAPP.model.funktionalitet.PresetLogic;

import java.util.ArrayList;
import java.util.List;

public class CategoryPreset_Frag extends Fragment {

    private TextView presetTV;
    private TextView presetName;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter presetItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private View v;

    //Objects
    private ModelViewController modelViewController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.preset_category, container, false);
        initialize(v);

        //Tildeler context, mainactivity's context
        MainActivity mainActivity = (MainActivity) getActivity();
        modelViewController = mainActivity.getModelViewController();


        recyclerView = (RecyclerView) v.findViewById(R.id.preset_RycyclerView);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //------hardcode (proof of concept)------------------
        List<String> presetNames = new ArrayList<>();
        presetNames.add("Sleeping time");
       //----------------------------------------------------

        presetItemAdapter = new CategoryPresetAdapter(presetNames, modelViewController.getNavController(), modelViewController.getLydAfspiller());
        recyclerView.setAdapter(presetItemAdapter);


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
              modelViewController.getNavController().navigate(R.id.action_CategoryPreset_to_libraryCategory);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(mainActivity, callback);

        return v;
    }

    //Skal initialisere vores view
    private void initialize(View v){
        presetTV = v.findViewById(R.id.preset_TextView);
    }

}

/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
class CategoryPresetAdapter extends RecyclerView.Adapter<CategoryPresetAdapter.MyViewHolder>{

    private List<String> mPresetSet;
    private NavController navController;
    private LydAfspiller lydAfspiller;

    public CategoryPresetAdapter(List<String> myPresetSet, NavController navController, LydAfspiller lydAfspiller){
        mPresetSet = myPresetSet;
        this.navController = navController;
        this.lydAfspiller = lydAfspiller;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView presetTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            presetTextView = itemView.findViewById(R.id.presetName_TV);
        }
    }


    @NonNull
    @Override
    public CategoryPresetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preset_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPresetAdapter.MyViewHolder holder, int position) {

        holder.presetTextView.setText(mPresetSet.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                lydAfspiller.playMultipleSounds();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPresetSet.size();
    }
}


