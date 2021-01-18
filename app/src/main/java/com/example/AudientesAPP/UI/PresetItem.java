package com.example.AudientesAPP.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.AudientesAPP.R;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class PresetItem extends AppCompatTextView {

    public PresetItem (Context context){
        super(context);
        setGravity(Gravity.CENTER);
        setTextSize(24);
        setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        setTypeface(getTypeface(), Typeface.BOLD);
    }
}
