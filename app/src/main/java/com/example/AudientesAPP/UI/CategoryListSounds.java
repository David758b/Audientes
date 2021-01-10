package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.context.Context;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryListSounds extends Fragment implements CategoryListSoundAdapter.OnItemClicked, LydAfspiller.OnLydAfspillerListener{
    LydAfspiller lydAfspiller;
    ImageView imageViewOptions;
    TextView categoryTitle;
    TextView soundTitle;
    TextView tag;
    TextView soundLength;
    private RecyclerView recyclerView;
    private CategoryListSoundAdapter soundItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_list_sounds_frag, container, false);
        initialize(v);

        MainActivity mainActivity = (MainActivity) getActivity();
        context = new Context(mainActivity);

        recyclerView = (RecyclerView) v.findViewById(R.id.category_sounds_RV);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        String category = context.getPrefs().getString("Category", "Fejl");
        categoryTitle.setText(category);

        // todo --> igen mega hardcoding og skal laves et andet sted.
        String[] soundNames = {"Pink noise", "Brown noise", "Train", "Rain", "Cricket", "Chihuahua",
                "Angelo", "JESUS"};
        List<String> sounds = new ArrayList<>(Arrays.asList(soundNames));

        soundItemAdapter = new CategoryListSoundAdapter(sounds);
        recyclerView.setAdapter(soundItemAdapter);



        //test = MediaPlayer.create(getActivity(), R.raw.testlyd);
        //test.setVolume(1,1);

        //MEGA MEGA MEGA hardcoding (lappeløsning for at teste)
        //LibraryMain libraryMain = (LibraryMain) LibraryListSound.this.getParentFragment();

        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        NavHostFragment navHostFragment2 = (NavHostFragment) getParentFragment();
        Fragment parent = (Fragment) navHostFragment2.getParentFragment();

        MainActivity main = (MainActivity) navHostFragment2.getActivity();
        lydAfspiller = main.getLydAfspiller();


        soundItemAdapter.setOnClick(CategoryListSounds.this);
        recyclerView.setPadding(0,75,0,20);

        return v;
    }
    public void initialize(View v){

        categoryTitle = v.findViewById(R.id.category_TV);
        imageViewOptions = v.findViewById(R.id.sound_list_element_options);
        soundTitle = v.findViewById(R.id.sound_title);
        tag = v.findViewById(R.id.tag_title);
        soundLength = v.findViewById(R.id.sound_duration);
    }

    @Override
    public void onDestroy() {
        lydAfspiller.stop();
        super.onDestroy();
    }

    // todo --> Dette er mega meget hardcoding og skal foregå i logikken i stedet
    //  (men dette er bare en test)

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                onDestroy();
                lydAfspiller.playNewSound(0);

                break;
            case 1: {
                onDestroy();
                lydAfspiller.playNewSound(1);
            }
            break;
            case 2: {
                onDestroy();
                lydAfspiller.playNewSound(2);

            }
            break;
            case 3: {
                onDestroy();
                lydAfspiller.playNewSound(3);
            }
            break;
            case 4: {
                onDestroy();
                lydAfspiller.playNewSound(4);
            }
            break;
            case 5: {
                onDestroy();
                lydAfspiller.playNewSound(5);
            }
            break;
            case 6: {
                onDestroy();
                lydAfspiller.playNewSound(6);
            }
            break;
            case 7: {
                onDestroy();
                lydAfspiller.playNewSound(7);
            }
            break;
            default:
                Log.d("lyden kunne ikke afspilles", "onItemClick: ");
        }
    }

    @Override
    public void updateLydAfspiller(LydAfspiller lydAfspiller) {
        //Skal implementeres
    }

    @Override
    public void soundFinished(LydAfspiller lydAfspiller) {

    }

}

class CategoryListSoundAdapter extends RecyclerView.Adapter<CategoryListSoundAdapter.SoundViewHolder> {

    private List<String> mSoundSet;

    public CategoryListSoundAdapter(List<String> mySoundSet){
        mSoundSet = mySoundSet;
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder{
        public TextView soundTextView;
        public TextView tagTitle;
        public TextView soundDuration;
        public ImageView soundOption;


        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            //Måske tilføje de resterende ting for et sound item
            soundTextView = itemView.findViewById(R.id.sound_title);
            tagTitle = itemView.findViewById(R.id.tag_title);
            soundDuration = itemView.findViewById(R.id.sound_duration);
            soundOption = itemView.findViewById(R.id.sound_list_element_options);
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
    public CategoryListSoundAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_list_element, parent, false);
        SoundViewHolder vh = new SoundViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, final int position) {
        holder.soundTextView.setText(mSoundSet.get(position));
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
