package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.funktionalitet.PresetLogic;

public class PresetItemNew extends Fragment implements AdapterView.OnItemClickListener {

    private ImageView backarrow;
    private ImageView checkMark;
    private EditText presetTitle_ET;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter presetSoundAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView newSoundBtn;
    private View v;
    private ModelViewController modelViewController;
    private PresetLogic logic;
    private Toast toast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.preset_item_new, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        modelViewController = mainActivity.getModelViewController();
        logic = modelViewController.getPresetLogic();
        initialize(v);
        return v;
    }

    private void initialize(View v){
        backarrow = v.findViewById(R.id.preset_backarrow);
        presetTitle_ET = v.findViewById(R.id.presetTitle_ET);
        newSoundBtn = v.findViewById(R.id.preset_addSound);
        checkMark = v.findViewById(R.id.preset_checkMark);


        checkMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If statement checks if the preset name already exists. If it does then it creates a toast alert.
                if(logic.isExisting(presetTitle_ET.getText().toString())){
                    toast = Toast.makeText(v.getContext(),"A preset with the chosen name already exists!  " +
                            "Please choose another name",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP,0,0);
                    toast.show();
                } else {
                    logic.addPreset(presetTitle_ET.getText().toString());
                    Navigation.findNavController(v).navigateUp();
                }

            }
        });


        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
