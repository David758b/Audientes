package com.example.AudientesAPP.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.controller.ModelViewController;
import com.example.AudientesAPP.model.funktionalitet.LibrarySoundLogic;
import com.example.AudientesAPP.model.funktionalitet.PresetContentLogic;
import com.example.AudientesAPP.model.funktionalitet.PresetLogic;
import com.example.AudientesAPP.model.funktionalitet.Utilities;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class PresetContent extends Fragment implements PresetContentLogic.OnPresetContentLogicListener, PresetSoundAdapter.OnItemClicked {

    private EditText presetTitle;
    private TextView savePresetTitle;
    private ImageView returnIcon;
    private RecyclerView recyclerView;
    private ImageView addSoundsToPreset;
    private ModelViewController modelViewController;
    private RecyclerView.LayoutManager layoutManager;
    private PresetSoundAdapter presetSoundAdapter;
    private PresetContentLogic presetContentLogic;
    private PresetLogic presetLogic;
    private String preset;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.preset_content, container, false);
        initialize(v);

        MainActivity mainActivity = (MainActivity) getActivity();
        modelViewController = mainActivity.getModelViewController();

        recyclerView = v.findViewById(R.id.preset_content_RV);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);


        presetContentLogic = modelViewController.getPresetContentLogic();
        presetLogic = modelViewController.getPresetLogic();

        presetContentLogic.addPresetContentLogicListener(this);
        preset = presetContentLogic.getCurrentPreset();
        presetTitle.setText(preset);
        presetTitle.clearFocus();


        try {
            presetSoundAdapter = new PresetSoundAdapter(presetContentLogic.getSoundsWithDuration(),presetContentLogic,modelViewController);
        }catch(Exception e){
            e.printStackTrace();
        }
        recyclerView.setAdapter(presetSoundAdapter);

        // Todo: Kigge på at implementere en "play preset" knap
        //lydAfspiller = modelViewController.getLydAfspiller();

        // Vi bruger en setOnFocusChangeListener istedet for en normal OnClickListener på EditText
        // Da der var problemer med at denne ikke fokuseret første gang man klikkede på EditText
        // og derfor kom Save "knappen" og cursoren ikke frem.
        presetTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    presetTitle.setCursorVisible(true);
                    savePresetTitle.setVisibility(View.VISIBLE);
                } else {
                    presetTitle.clearFocus();
                }
            }
        });

        returnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presetTitle.clearFocus();
                Utilities.hideKeyboard(v, modelViewController.getContext());
                modelViewController.getNavController().navigate(R.id.action_presetContent_to_presetMain);
            }
        });

        savePresetTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tjekker om det indtastet navn er det samme som kategori navnet (Uændret kategori navn)
                if (preset.equals(presetTitle.getText().toString())) {
                    Toast.makeText(v.getContext(), "This preset already has the given name", Toast.LENGTH_SHORT).show();
                    // Tjekker om den angivet kategori navn allerede findes som en anden kategori
                } else if (presetLogic.isExisting(presetTitle.getText().toString())) {
                    presetTitle.setText(preset);
                    Toast.makeText(v.getContext(), "A preset with the given name already exists", Toast.LENGTH_SHORT).show();
                } else {
                    presetLogic.updatePreset(preset, presetTitle.getText().toString());
                    Toast.makeText(v.getContext(), "Preset name changed!", Toast.LENGTH_SHORT).show();
                    presetTitle.clearFocus();
                }
                presetTitle.clearFocus();
                // Fjerner keyboard fra skærmen
                Utilities.hideKeyboard(v, modelViewController.getContext());

                savePresetTitle.setVisibility(View.INVISIBLE);
            }
        });

        addSoundsToPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pSoundPickerDialog pSoundPickerDialog = new pSoundPickerDialog(getActivity(), presetContentLogic, modelViewController.getLibrarySoundLogic(), modelViewController);
                pSoundPickerDialog.show();
            }
        });
        presetSoundAdapter.setOnClick(PresetContent.this);
        //modelViewController.getContext().databaseTest();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                presetTitle.clearFocus();
                modelViewController.getNavController().navigate(R.id.action_presetContent_to_presetMain);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(mainActivity, callback);
        return v;
    }

    public void initialize(View v){

         presetTitle = v.findViewById(R.id.preset_TV_content);
         savePresetTitle = v.findViewById(R.id.saveIcon_preset_content);
         returnIcon = v.findViewById(R.id.returnIcon_preset_content);
         recyclerView = v.findViewById(R.id.preset_content_RV);
         addSoundsToPreset = v.findViewById(R.id.preset_addSound);
    }

    @Override
    public void updatePresetElements(PresetContentLogic presetContentLogic) {
        presetSoundAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

    }
}

/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
class PresetSoundAdapter extends RecyclerView.Adapter<PresetSoundAdapter.SoundViewHolder> {

    private List<PresetContentLogic.SoundWithDuration> mSoundSet;
    //private List<String> nDuration;
    private PresetContentLogic presetContentLogic;
    private ModelViewController modelViewController;

    public PresetSoundAdapter(List<PresetContentLogic.SoundWithDuration> mySoundSet,PresetContentLogic presetContentLogic , ModelViewController modelViewController){
        this.mSoundSet = mySoundSet;
        //this.nDuration = nDuration;
        this.presetContentLogic = presetContentLogic;
        this.modelViewController = modelViewController;
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder{
        public TextView soundTextView;
        public TextView soundDuration;
        public ImageView delete;


        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            //Måske tilføje de resterende ting for et sound item
            soundTextView = itemView.findViewById(R.id.rv_sound_title);
            soundDuration = itemView.findViewById(R.id.rv_sound_duration);
            delete = itemView.findViewById(R.id.delete_sound);

            // ...
        }
    }

    //deklarerer interface for onclick
    private OnItemClicked onClick;

    //interface
    public interface OnItemClicked{
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public PresetSoundAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_sounds, parent, false);
        return new SoundViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, final int position) {
        PresetContentLogic.SoundWithDuration soundWithDuration = mSoundSet.get(position);
        holder.soundTextView.setText(soundWithDuration.getSoundName());
        holder.soundDuration.setText(soundWithDuration.getSoundDuration());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setMessage("Do you want to remove this sound?");
                builder.setTitle("Removing sound from category!");
                builder.setCancelable(true);


                builder.setPositiveButton("Remove",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presetContentLogic.deletePresetElement(soundWithDuration.getSoundName());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();



//                alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setBackgroundColor(Color.WHITE);
                nbutton.setTextColor(Color.BLACK);
                Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setBackgroundColor(Color.BLACK);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("SOUND NAME ;" + presetContentLogic.getSoundsList().get(position));

                SoundEditDialog soundEditDialog = new SoundEditDialog(modelViewController.getContext(),presetContentLogic.getSoundsWithDuration().get(position));
                //onClick.onItemClick(position);

                soundEditDialog.show();
                System.out.println("SHOW................DIALog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSoundSet.size();
    }
    public void setOnClick(OnItemClicked onClick){
        this.onClick = onClick;
    }
}

class SoundEditDialog extends Dialog implements View.OnClickListener{

    //Views
    private TextView soundTitle;
    private TextView soundVolumeTV;
    private TextView soundVolumePct;
    private TextView soundIntervalTV;
    private TextView soundIntervalStart;
    private TextView soundIntervalEnd;
    private TextView loopBtnTV;

    private ImageView soundVolumeDec;
    private ImageView soundVolumeInc;
    private ImageView loopBtn;

    private SeekBar volumenSeekBar;
    private RangeSeekBar<Integer> rangeSeekBar;

    private Button saveBtn;

    //Objects
    private Activity contextUI;
    //Listener

    //Variables
    int soundDurInt;

    public SoundEditDialog(Activity contextUI, PresetContentLogic.SoundWithDuration soundWithDuration) {
        super(contextUI);
        this.contextUI = contextUI;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.preset_frag_edit_sound_dialog);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        this.soundTitle = findViewById(R.id.preset_sound_title_TV);
        this.soundVolumeTV = findViewById(R.id.preset_sound_volume_TV);
        this.soundVolumeDec = findViewById(R.id.preset_sound_volume_decrement);
        this.soundVolumeInc = findViewById(R.id.preset_sound_volume_increase);
        this.rangeSeekBar = findViewById(R.id.preset_sound_rangeSeekbar);
        this.soundIntervalTV = findViewById(R.id.preset_sound_interval_TV);
        this.soundIntervalEnd= findViewById(R.id.preset_sound_interval_end);
        this.soundIntervalStart = findViewById(R.id.preset_sound_interval_start);
        this.loopBtnTV = findViewById(R.id.preset_sound_loop_TV);
        this.loopBtn = findViewById(R.id.preset_sound_loop_btn);
        this.saveBtn = findViewById(R.id.preset_sound_save);
        this.volumenSeekBar = findViewById(R.id.preset_sound_vol_seekbar);
        this.soundVolumePct = findViewById(R.id.preset_sound_volume_percentage_TV);
        soundTitle.setText(soundWithDuration.getSoundName());
        soundIntervalEnd.setText(soundWithDuration.getSoundDuration());
        this.soundDurInt = Utilities.convertFormatToMili(soundWithDuration.getSoundDuration());
        rangeSeekBar.setRangeValues(0,100);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                soundIntervalStart.setText(Utilities.convertFormat(Utilities.percentageOfInt(soundDurInt,minValue)*1000));
                soundIntervalEnd.setText(Utilities.convertFormat(Utilities.percentageOfInt(soundDurInt,maxValue)*1000));
            }
        });
        // Get noticed while dragging
        rangeSeekBar.setNotifyWhileDragging(true);

        soundVolumeDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volumenSeekBar.setProgress(volumenSeekBar.getProgress()-1);
            }
        });

        soundVolumeInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volumenSeekBar.setProgress(volumenSeekBar.getProgress()+1);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Saved " + soundTitle.getText(), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        volumenSeekBar.setProgress(100);
        volumenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                soundVolumePct.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        loopBtn.setOnClickListener(new View.OnClickListener() {
            boolean isChecked = true;
            @Override
            public void onClick(View v) {

                if(isChecked){
                    loopBtn.setBackgroundResource(R.drawable.ic_baseline_check_box_24);
                    isChecked = false;
                }
                else{

                    loopBtn.setBackgroundResource(R.drawable.ic_baseline_check_box_outline_blank_24);
                    isChecked = true;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}




/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
class pSoundPickerDialog extends Dialog implements View.OnClickListener{
    // Object reference
    private final PresetContentLogic logic;
    private final LibrarySoundLogic librarySoundLogic;
    private ModelViewController modelViewController;
    private String presetName;

    // View references
    private Button addSoundbtn;
    private RecyclerView dialogSounds;
    private PresetSoundDialogAdapter dialogAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // Listener references


    public pSoundPickerDialog(Activity contextUI, PresetContentLogic logic, LibrarySoundLogic soundLogic, ModelViewController modelViewController) {
        super(contextUI);
        this.modelViewController = modelViewController;
        this.logic = logic;
        this.librarySoundLogic = soundLogic;
        presetName = logic.getCurrentPreset();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.category_add_sound_dialog);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        this.addSoundbtn = findViewById(R.id.dialog_category_addSound);
        this.dialogSounds = findViewById(R.id.allsounds_RV);

        this.layoutManager = new LinearLayoutManager(contextUI);
        this.dialogSounds.setLayoutManager(layoutManager);
        try {
            dialogAdapter = new PresetSoundDialogAdapter(logic.getAvailableSounds(), logic.getDuration(logic.getAvailableSounds()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialogSounds.setAdapter(dialogAdapter);

        addSoundbtn.setOnClickListener(this);

    }

    //Adder de valgte lyde til den category vi har valgt
    @Override
    public void onClick(View v) {

        //Laver nogle lister. En med de valgte positioner og en med de lyde der kan vælges i mellem
        List<Integer> selectedPositions =  new ArrayList<Integer>(dialogAdapter.getChosenSounds());
        List<String> sounds = logic.getAvailableSounds();
        // todo måske skulle dette rykkes et sted hen i logikken ?
        //Et for-loop, hvor vi tager fat i værdien i selectedPositions (Integer), hvilken gemmes i counter.
        //Counter bruges som et index til sounds listen.
        int counter = 0;
        int i;
        for (i = 0; i < selectedPositions.size() ; i++) {
            counter = selectedPositions.get(i);
            logic.addSoundsToPreset(sounds.get(counter));
        }
        Toast.makeText(v.getContext(), i + " sounds were added to "+ presetName, Toast.LENGTH_SHORT).show();
        dismiss();
    }
}


/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
class PresetSoundDialogAdapter extends RecyclerView.Adapter<PresetSoundDialogAdapter.SoundViewHolder> {
    private List<String> mSoundSet;
    private List<String> nDuration;
    private HashSet<Integer> chosenSounds = new HashSet<>();

    public PresetSoundDialogAdapter(List<String> mySoundSet, List<String> nDuration){
        this.mSoundSet = mySoundSet;
        this.nDuration = nDuration;
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder{
        public TextView soundTextView;
        public TextView soundDuration;
        public ImageView addSoundBtn;
        public LinearLayout linearLayout;


        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            //Måske tilføje de resterende ting for et sound item
            soundTextView = itemView.findViewById(R.id.sound_title_dialog_TV);
            soundDuration = itemView.findViewById(R.id.sound_duration_dialog_TV);
            addSoundBtn = itemView.findViewById(R.id.sound_add_btn);
            linearLayout = itemView.findViewById(R.id.LinearLayoutList);
            // ...
        }
    }

    //deklarerer interface for onclick
    private OnItemClicked onClick;

    //interface
    public interface OnItemClicked{
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public PresetSoundDialogAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_dialog_sound_item, parent, false);
        return new SoundViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SoundViewHolder holder, final int position) {
        final int pos = holder.getAdapterPosition();
        holder.soundTextView.setText(mSoundSet.get(position));
        holder.soundDuration.setText(nDuration.get(position));
        holder.itemView.setMinimumWidth(250);


        holder.addSoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Highlighter samt fjerner highlight hvis man dobbelt klikker (klikker igen på et highlightet element)
                boolean chosen = chosenSounds.contains(pos);
                if (!chosen) {
                    holder.linearLayout.setBackgroundResource(R.color.LightGrey);
                    holder.addSoundBtn.setImageResource(R.drawable.ic_baseline_cancel_24);
                    chosenSounds.add(pos);
                } else {
                    holder.linearLayout.setBackgroundResource(R.color.DialogDarkBlue);
                    holder.addSoundBtn.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
                    chosenSounds.remove(pos);
                }

            }
        });

    }

    public HashSet<Integer> getChosenSounds(){
        return chosenSounds;
    }

    @Override
    public int getItemCount() {
        return mSoundSet.size();
    }
    public void setOnClick(OnItemClicked onClick){
        this.onClick = onClick;
    }

}
