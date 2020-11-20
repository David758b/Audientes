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
    private ImageView imageView; //SKAL DER OVERHOVEDET VÆRE ET IMAGEVIEW?
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


        /*ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                R.layout.category_list_element, R.id.textViewCategoryELement) {

            // overskiver getview ( læg mærke til {} parenteserne )
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {

                View view = super.getView(position, cachedView, parent);
                ImageView imageView = view.findViewById(R.id.imageViewCategoryElement);
                TextView textView = view.findViewById(R.id.textViewCategoryELement);

                return view;
            }

        };*/

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
        //recyclerView.setDividerHeight(25);
        //presetList.setAdapter(arrayAdapter);
        //presetList.setPadding(0,75,0,20);
        //presetList.setDividerHeight(25);



        //med setselector kan man vælge hvad der skal ske når man vælger et liste element.
        // i dette tilfælde anvendes en af androids egne features (rød boks når der klikkes)
        //listView.setSelector(android.R.drawable.ic_notification_overlay);
        //TextView text = rod.findViewById(R.id.textViewTest1);

        //presetList.setOnItemClickListener(this);
        return v;
    }

    //Skal initialisere vores view
    private void initialize(View v){
        addPreset = v.findViewById(R.id.add_preset);
        presetName = v.findViewById(R.id.textViewCategoryELement);

        presetItems = new HashMap<>();


        addPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

