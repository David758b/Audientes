package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.AudientesAPP.R;

public class PresetItemNew extends Fragment implements AdapterView.OnItemClickListener {

    private ImageView backarrow;
    private EditText presetTitle_ET;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter presetSoundAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView newSoundBtn;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.preset_item_new, container, false);
        initialize(v);
        return v;
    }

    private void initialize(View v){
        backarrow = v.findViewById(R.id.preset_backarrow);
        presetTitle_ET = v.findViewById(R.id.presetTitle_ET);
        newSoundBtn = v.findViewById(R.id.preset_addSound);

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
