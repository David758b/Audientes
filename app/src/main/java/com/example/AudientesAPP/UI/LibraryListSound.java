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

import com.example.AudientesAPP.model.context.Controller;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.example.AudientesAPP.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryListSound extends Fragment implements SoundAdapter.OnItemClicked,LydAfspiller.OnLydAfspillerListener {
    LydAfspiller lydAfspiller;
    ImageView imageView1;
    TextView title;
    TextView tag;
    TextView soundLength;
    private RecyclerView recyclerView;
    private SoundAdapter soundItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Controller controller;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.library_list_sound_frag, container, false);
        initialize(v);

        MainActivity mainActivity = (MainActivity) getActivity();
        controller = mainActivity.getController();

        recyclerView = (RecyclerView) v.findViewById(R.id.sounds_RV);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // todo --> igen mega hardcoding og skal laves et andet sted.

        List<String> sounds = controller.getSoundListLogic().getSoundsList();
        List<String> duration = controller.getSoundListLogic().getDuration(sounds);
        List<String> categories = controller.getSoundListLogic().getCategories(sounds);


        soundItemAdapter = new SoundAdapter(sounds,duration, categories);
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


        soundItemAdapter.setOnClick(LibraryListSound.this);
        //recyclerView.setPadding(0,75,0,20);

        return v;
    }

    public void initialize(View v){

        imageView1 = v.findViewById(R.id.sound_list_element_options);
        title = v.findViewById(R.id.sound_title);
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

class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {

    private List<String> mSoundSet;
    private List<String> mDuration;
    private List<String> mCategories;

    public SoundAdapter(List<String> mySoundSet, List<String> mDuration, List<String> mCategories ){
        this.mSoundSet = mySoundSet;
        this.mDuration = mDuration;
        this.mCategories = mCategories;

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
    public SoundAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_list_element, parent, false);
        SoundViewHolder vh = new SoundViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, final int position) {
        holder.soundTextView.setText(mSoundSet.get(position));
        holder.soundDuration.setText(mDuration.get(position));
        holder.tagTitle.setText(mCategories.get(position));
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