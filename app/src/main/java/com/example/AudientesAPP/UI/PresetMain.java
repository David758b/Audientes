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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.DTO.PresetItemDTO;
import com.example.AudientesAPP.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PresetMain extends Fragment implements AdapterView.OnItemClickListener {
    private TextView presetListTV;
    private FloatingActionButton addPreset;
    private TextView presetName;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter presetItemAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private PresetItem presetItem;

    HashMap<String, PresetItem> presetItems;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.preset_main, container, false);
        initialize(v);

        recyclerView = (RecyclerView) v.findViewById(R.id.presets_RV);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<String> presetNames = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            presetNames.add("Preset " + i);
        }



        presetItemAdapter = new MyAdapter(presetNames);
        recyclerView.setAdapter(presetItemAdapter);

        recyclerView.setPadding(0,75,0,20);

        return v;
    }

    //Skal initialisere vores view
    private void initialize(View v){
        presetListTV = v.findViewById(R.id.presetList_TV);
        addPreset = v.findViewById(R.id.add_preset);
        presetName = v.findViewById(R.id.textViewCategoryELement);

        presetItems = new HashMap<>();


        addPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_presetMain_to_preset_item_new);
                //Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });
    }


    //da der ikke skal være funktionalitet endnu, logger vi det bare, så man kan se lortet virker
    //i logcatten
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ListItemClick", "Du klikkede på " + position);
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private List<String> mPresetSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView presetTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            presetTextView = itemView.findViewById(R.id.presetName_TV);
        }
    }
    public MyAdapter(List<String> myPresetSet){
        mPresetSet = myPresetSet;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preset_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            //Her hentes data objekterne i fremtiden ...

            holder.presetTextView.setText(mPresetSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mPresetSet.size();
    }
}

