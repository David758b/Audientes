package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.AudientesAPP.R;

public class HearingtestMain extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rod = inflater.inflate(R.layout.preset_main, container, false);
        TextView text = rod.findViewById(R.id.textViewTest3);

        return rod;
    }
}
