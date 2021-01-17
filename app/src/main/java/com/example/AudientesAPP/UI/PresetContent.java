package com.example.AudientesAPP.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.funktionalitet.CategorySoundsLogic;
import com.example.AudientesAPP.model.funktionalitet.Utilities;

import java.util.List;

public class PresetContent extends Fragment {

    private EditText presetTitle;
    private TextView savePresetTitle;
    private ImageView returnIcon;
    private RecyclerView recyclerView;
    private ImageView addSoundsToPreset;
    private ModelViewController modelViewController;
    private RecyclerView.LayoutManager layoutManager;







    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.preset_content, container, false);
        initialize(v);

        MainActivity mainActivity = (MainActivity) getActivity();
        modelViewController = mainActivity.getModelViewController();

        recyclerView = v.findViewById(R.id.category_sounds_RV);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);



        categorySoundsLogic = modelViewController.getCategorySoundsLogic();
        libCategoryLogic = modelViewController.getLibraryCategoryLogic();
        categorySoundsLogic.addCategorySoundsLogicListener(this);
        category = categorySoundsLogic.getCurrentCategory();
        categoryTitle.setText(category);
        categoryTitle.clearFocus();


        try {
            soundItemAdapter = new CategorySoundAdapter(categorySoundsLogic.getSoundsWithDuration(),categorySoundsLogic,modelViewController);
            System.out.println("CategorySoundAdapter er lavet-------------");
        }catch(Exception e){
            e.printStackTrace();
        }
        recyclerView.setAdapter(soundItemAdapter);

        lydAfspiller = modelViewController.getLydAfspiller();

        // Vi bruger en setOnFocusChangeListener istedet for en normal OnClickListener på EditText
        // Da der var problemer med at denne ikke fokuseret første gang man klikkede på EditText
        // og derfor kom Save "knappen" og cursoren ikke frem.
        categoryTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    categoryTitle.setCursorVisible(true);
                    save.setVisibility(View.VISIBLE);
                } else {
                    categoryTitle.clearFocus();
                }
            }
        });

        returnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryTitle.clearFocus();
                Utilities.hideKeyboard(v, modelViewController.getContext());
                modelViewController.getNavController().navigate(R.id.action_categorySounds_to_libraryCategory);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tjekker om det indtastet navn er det samme som kategori navnet (Uændret kategori navn)
                if (category.equals(categoryTitle.getText().toString())) {
                    Toast.makeText(v.getContext(), "This category already has the given name", Toast.LENGTH_SHORT).show();
                    // Tjekker om den angivet kategori navn allerede findes som en anden kategori
                } else if (libCategoryLogic.isExisting(categoryTitle.getText().toString())) {
                    categoryTitle.setText(category);
                    Toast.makeText(v.getContext(), "A category with the given name already exists", Toast.LENGTH_SHORT).show();
                } else {
                    libCategoryLogic.updateCategory(category, categoryTitle.getText().toString());
                    Toast.makeText(v.getContext(), "Category name changed!", Toast.LENGTH_SHORT).show();
                    categoryTitle.clearFocus();
                }
                categoryTitle.clearFocus();
                // Fjerner keyboard fra skærmen
                Utilities.hideKeyboard(v, modelViewController.getContext());

                save.setVisibility(View.INVISIBLE);
            }
        });

        addSoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPickerDialog soundPickerDialog = new SoundPickerDialog(getActivity(), categorySoundsLogic, modelViewController.getLibrarySoundLogic(), modelViewController);
                soundPickerDialog.show();
            }
        });
        soundItemAdapter.setOnClick(CategorySounds.this);
        modelViewController.getContext().databaseTest();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                categoryTitle.clearFocus();
                modelViewController.getNavController().navigate(R.id.action_categorySounds_to_libraryCategory);
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
}


class PresetSoundAdapter extends RecyclerView.Adapter<PresetSoundAdapter.SoundViewHolder> {

    private List<CategorySoundsLogic.SoundWithDuration> mSoundSet;
    //private List<String> nDuration;
    private CategorySoundsLogic categorySoundsLogic;
    private ModelViewController modelViewController;

    public CategorySoundAdapter(List<CategorySoundsLogic.SoundWithDuration> mySoundSet, CategorySoundsLogic categorySoundsLogic, ModelViewController modelViewController){
        this.mSoundSet = mySoundSet;
        //this.nDuration = nDuration;
        this.categorySoundsLogic = categorySoundsLogic;
        this.modelViewController = modelViewController;
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder{
        public TextView soundTextView;
        public TextView soundDuration;
        public TextView categoryTag;
        public ImageView delete;


        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            //Måske tilføje de resterende ting for et sound item
            soundTextView = itemView.findViewById(R.id.category_sound_title);
            soundDuration = itemView.findViewById(R.id.category_sound_duration);
            categoryTag = itemView.findViewById(R.id.category_tag_title);
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
    public CategorySoundAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_sound_list_elements, parent, false);
        return new SoundViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, final int position) {
        CategorySoundsLogic.SoundWithDuration soundWithDuration = mSoundSet.get(position);
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
                        categorySoundsLogic.deleteSoundCategory(soundWithDuration.getSoundName());
                        System.out.println("DELETED SOUND FROM CATEGORY ::::::::::::::::");
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


        holder.soundTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
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
