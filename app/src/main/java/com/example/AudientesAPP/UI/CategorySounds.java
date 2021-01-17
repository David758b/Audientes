package com.example.AudientesAPP.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.funktionalitet.CategorySoundsLogic;
import com.example.AudientesAPP.model.funktionalitet.LibraryCategoryLogic;
import com.example.AudientesAPP.model.funktionalitet.LibrarySoundLogic;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.example.AudientesAPP.model.funktionalitet.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CategorySounds extends Fragment implements CategorySoundAdapter.OnItemClicked, LydAfspiller.OnLydAfspillerListener, CategorySoundsLogic.OnCategorySoundsLogicListener {
    private LydAfspiller lydAfspiller;
    private EditText categoryTitle;
    private TextView soundTitle;
    private TextView soundLength;
    private TextView save;
    private ImageView returnIcon;
    private ImageView addSoundBtn;
    private RecyclerView recyclerView;
    private CategorySoundAdapter soundItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ModelViewController modelViewController;
    private CategorySoundsLogic categorySoundsLogic;
    private LibraryCategoryLogic libCategoryLogic;
    private LibrarySoundLogic librarySoundLogic;
    private List<String> soundNames = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_list_sounds_frag, container, false);
        initialize(v);

        MainActivity mainActivity = (MainActivity) getActivity();
        modelViewController = mainActivity.getModelViewController();

        recyclerView = v.findViewById(R.id.category_sounds_RV);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        categorySoundsLogic = modelViewController.getCategorySoundsLogic();
        libCategoryLogic = modelViewController.getLibraryCategoryLogic();
        librarySoundLogic = modelViewController.getLibrarySoundLogic();
        categorySoundsLogic.addCategorySoundsLogicListener(this);
        final String category = modelViewController.getPrefs().getString("Category", "Fejl");
        categoryTitle.setText(category);
        categoryTitle.clearFocus();

        //Dette skal laves om til at være en final liste i logic klassen som er en liste af CategorySoundDTOer
        soundNames = categorySoundsLogic.getSoundsList(category);
        List<String> duration = categorySoundsLogic.getDuration(soundNames);
        List<String> categoryTag = librarySoundLogic.getCategories(soundNames);
        try {
            soundItemAdapter = new CategorySoundAdapter(soundNames, duration,categorySoundsLogic,modelViewController,categoryTag);
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
                modelViewController.getNavController().navigate(R.id.action_categorySounds_to_libraryCategory);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(mainActivity, callback);
        return v;
    }
    public void initialize(View v){

        categoryTitle = v.findViewById(R.id.category_TV);
        save = v.findViewById(R.id.saveIcon);
        returnIcon = v.findViewById(R.id.returnIcon);
        addSoundBtn = v.findViewById(R.id.category_addSound);
        soundTitle = v.findViewById(R.id.sound_title);
        soundLength = v.findViewById(R.id.sound_duration);
    }

    @Override
    public void updateCategorySounds(CategorySoundsLogic categorySoundsLogic) {
        soundItemAdapter.notifyDataSetChanged();
        System.out.println("NOOOTOFYYYYYYY JAJAJAJAJAJAjA");
    }

    @Override
    public void onDestroy() {
        lydAfspiller.stop();
        super.onDestroy();
    }


    @Override
    public void onItemClick(int position) {
        onDestroy();
        lydAfspiller.playNewSound(soundNames.get(position));
    }

    @Override
    public void updateLydAfspiller(LydAfspiller lydAfspiller) {
        //Skal implementeres
    }

    @Override
    public void soundFinished(LydAfspiller lydAfspiller) {

    }
}

//------------------------------------------- CATEGORYSOUNDADAPTER -----------------------------
class CategorySoundAdapter extends RecyclerView.Adapter<CategorySoundAdapter.SoundViewHolder> {

    private List<String> mSoundSet;
    private List<String> nDuration;
    private List<String> categoryTag;
    private CategorySoundsLogic categorySoundsLogic;
    private ModelViewController modelViewController;

    public CategorySoundAdapter(List<String> mySoundSet, List<String> nDuration, CategorySoundsLogic categorySoundsLogic, ModelViewController modelViewController, List<String> categoryTag){
        this.categoryTag = categoryTag;
        this.mSoundSet = mySoundSet;
        this.nDuration = nDuration;
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
        holder.soundTextView.setText(mSoundSet.get(position));
        holder.soundDuration.setText(nDuration.get(position));
        holder.categoryTag.setText(categoryTag.get(position));

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
                        categorySoundsLogic.deleteSoundCategory(modelViewController.getPrefs().getString("Category",null),mSoundSet.get(position));
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


class SoundPickerDialog extends Dialog implements View.OnClickListener{
    // Object reference
    private final CategorySoundsLogic logic;
    private final LibrarySoundLogic librarySoundLogic;
    private ModelViewController modelViewController;
    private String categoryName;

    // View references
    private Button addSoundbtn;
    private RecyclerView dialogSounds;
    private CategorySoundDialogAdapter dialogAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // Listener references


    public SoundPickerDialog(Activity contextUI, CategorySoundsLogic logic, LibrarySoundLogic soundLogic, ModelViewController modelViewController) {
        super(contextUI);
        this.modelViewController = modelViewController;
        this.logic = logic;
        this.librarySoundLogic = soundLogic;
        categoryName = modelViewController.getPrefs().getString("Category", "NOOO");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.category_add_sound_dialog);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        this.addSoundbtn = findViewById(R.id.dialog_category_addSound);
        this.dialogSounds = findViewById(R.id.allsounds_RV);

        this.layoutManager = new LinearLayoutManager(contextUI);
        this.dialogSounds.setLayoutManager(layoutManager);
        try {
            dialogAdapter = new CategorySoundDialogAdapter(this.logic.getAvailableSounds(categoryName), this.logic.getDuration(this.logic.getAvailableSounds(categoryName)));
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
        List<String> sounds = logic.getAvailableSounds(categoryName);
        // todo måske skulle dette rykkes et sted hen i logikken ?
        //Et for-loop, hvor vi tager fat i værdien i selectedPositions (Integer), hvilken gemmes i counter.
        //Counter bruges som et index til sounds listen.
        int counter = 0;
        int i;
        for (i = 0; i < selectedPositions.size() ; i++) {
            counter = selectedPositions.get(i);
            logic.addSoundsToCategory(categoryName, sounds.get(counter));
        }
        Toast.makeText(v.getContext(), i + " sounds were added to "+ categoryName, Toast.LENGTH_SHORT).show();
        dismiss();
    }
}

class CategorySoundDialogAdapter extends RecyclerView.Adapter<CategorySoundDialogAdapter.SoundViewHolder> {
    private List<String> mSoundSet;
    private List<String> nDuration;
    private HashSet<Integer> chosenSounds = new HashSet<>();

    public CategorySoundDialogAdapter(List<String> mySoundSet, List<String> nDuration){
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
    public CategorySoundDialogAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
