package com.example.AudientesAPP.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.model.controller.ModelViewController;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;
import com.example.AudientesAPP.R;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class LibrarySound extends Fragment implements SoundAdapter.OnItemClicked,LydAfspiller.OnLydAfspillerListener {
    LydAfspiller lydAfspiller;
    ImageView imageView1;
    TextView title;
    TextView tag;
    TextView soundLength;
    private RecyclerView recyclerView;
    private SoundAdapter soundItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ModelViewController modelViewController;
    private List<String> sounds = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.library_list_sound_frag, container, false);
        initialize(v);

        MainActivity mainActivity = (MainActivity) getActivity();
        modelViewController = mainActivity.getModelViewController();

        recyclerView = v.findViewById(R.id.sounds_RV);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        sounds = modelViewController.getLibrarySoundLogic().getSoundsList();
        List<String> duration = modelViewController.getLibrarySoundLogic().getDuration(sounds);
        List<String> categories = modelViewController.getLibrarySoundLogic().getCategories(sounds);

        soundItemAdapter = new SoundAdapter(sounds,duration, categories);
        recyclerView.setAdapter(soundItemAdapter);

        lydAfspiller = modelViewController.getLydAfspiller();

        soundItemAdapter.setOnClick(this);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                modelViewController.getNavController().navigate(R.id.action_LibrarySounds_to_LibraryMain);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(mainActivity, callback);

        return v;
    }

    public void initialize(View v){


        title = v.findViewById(R.id.sound_title);
        tag = v.findViewById(R.id.tag_title);
        soundLength = v.findViewById(R.id.sound_duration);
    }


    @Override
    public void onDestroy() {
        lydAfspiller.stop();
        super.onDestroy();
    }

    @Override
    public void onItemClick(int position) {
        onDestroy();
        lydAfspiller.playNewSound(sounds.get(position));

    }

    @Override
    public void updateLydAfspiller(LydAfspiller lydAfspiller) {
        //Skal implementeres
    }

    @Override
    public void soundFinished(LydAfspiller lydAfspiller) {

    }
}

/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
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



        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);

            //Måske tilføje de resterende ting for et sound item
            soundTextView = itemView.findViewById(R.id.sound_title);
            tagTitle = itemView.findViewById(R.id.tag_title);
            soundDuration = itemView.findViewById(R.id.sound_duration);


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
        return new SoundViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, final int position) {
        holder.soundTextView.setText(mSoundSet.get(position));
        holder.soundDuration.setText(mDuration.get(position));
        holder.tagTitle.setText(mCategories.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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