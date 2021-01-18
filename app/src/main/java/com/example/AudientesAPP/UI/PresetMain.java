package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.funktionalitet.LibraryCategoryLogic;
import com.example.AudientesAPP.model.funktionalitet.PresetContentLogic;
import com.example.AudientesAPP.model.funktionalitet.PresetLogic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class PresetMain extends Fragment implements AdapterView.OnItemClickListener {
    private TextView presetListTV;
    private FloatingActionButton addPreset;
    private TextView presetName;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter presetItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private PresetItem presetItem;
    private HashMap<String, PresetItem> presetItems;
    private View v;
    private PresetLogic logic;
    private PresetContentLogic presetContentLogic;

    //Objects
    private ModelViewController modelViewController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.preset_main, container, false);
        initialize(v);

        //Tildeler context, mainactivity's context
        MainActivity mainActivity = (MainActivity) getActivity();
        modelViewController = mainActivity.getModelViewController();

        logic = modelViewController.getPresetLogic();
        presetContentLogic = modelViewController.getPresetContentLogic();


        recyclerView = (RecyclerView) v.findViewById(R.id.presets_RV);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<String> presetNames = logic.getPresetNames();


        presetItemAdapter = new PresetAdapter(presetNames, modelViewController.getNavController(), presetContentLogic);
        recyclerView.setAdapter(presetItemAdapter);

        //recyclerView.setPadding(0,75,0,20);


        return v;
    }

    //Skal initialisere vores view
    private void initialize(View v){
        presetListTV = v.findViewById(R.id.presetList_TV);
        addPreset = v.findViewById(R.id.add_preset);
        //presetName = v.findViewById(R.id.textViewCategoryELement);

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

/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
class PresetAdapter extends RecyclerView.Adapter<PresetAdapter.MyViewHolder>{

    private List<String> mPresetSet;
    private NavController navController;
    private PresetContentLogic presetContentLogic;
    //private List<PresetDTO> mPresetSet;

    public PresetAdapter(List<String> myPresetSet, NavController navController, PresetContentLogic presetContentLogic){
        mPresetSet = myPresetSet;
        this.navController = navController;
        this.presetContentLogic = presetContentLogic;

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
    public PresetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preset_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PresetAdapter.MyViewHolder holder, int position) {
            //Her hentes data objekterne i fremtiden ...
            //PresetDTO presetDTO = mPresetSet.get(position)
            holder.presetTextView.setText(mPresetSet.get(position));
            //holder.presetTextView.setText(presetDTO.getPresetName());

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                presetContentLogic.setCurrentPreset(mPresetSet.get(position));
                navController.navigate(R.id.action_presetMain_to_presetContent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPresetSet.size();
    }
}

