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
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.model.funktionalitet.CategorySoundsLogic;
import com.example.AudientesAPP.model.funktionalitet.LydAfspiller;

import java.util.List;

public class CategorySounds extends Fragment implements CategorySoundAdapter.OnItemClicked, LydAfspiller.OnLydAfspillerListener{
    LydAfspiller lydAfspiller;
    ImageView imageViewOptions;
    TextView categoryTitle;
    TextView soundTitle;
    TextView soundLength;
    private RecyclerView recyclerView;
    private CategorySoundAdapter soundItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ModelViewController modelViewController;
    private CategorySoundsLogic logic;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_list_sounds_frag, container, false);
        initialize(v);

        MainActivity mainActivity = (MainActivity) getActivity();
        modelViewController = mainActivity.getModelViewController();

        recyclerView = v.findViewById(R.id.category_sounds_RV);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        logic = modelViewController.getCategorySoundsLogic();
        String category = modelViewController.getPrefs().getString("Category", "Fejl");
        categoryTitle.setText(category);
        System.out.println(category);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
        //String myValue = this.getArguments().getString("Categories");
        //System.out.println(myValue);
        //System.out.println("----------------------------");
        // todo --> igen mega hardcoding og skal laves et andet sted.
        //Bundle bundle = new Bundle();
        //String category = bundle.getString("Categories");

        //categorySoundsLogic = new CategorySoundsLogic(modelViewController);
        //Dette skal laves om til at være en final liste i logic klassen som er en liste af CategorySoundDTOer
        List<String> soundNames = logic.getSoundsList(category);
        List<String> duration = logic.getDuration(soundNames);

        soundItemAdapter = new CategorySoundAdapter(soundNames, duration);
        recyclerView.setAdapter(soundItemAdapter);

        lydAfspiller = modelViewController.getLydAfspiller();


        soundItemAdapter.setOnClick(CategorySounds.this);
        recyclerView.setPadding(0,75,0,20);

        return v;
    }
    public void initialize(View v){

        categoryTitle = v.findViewById(R.id.category_TV);
        soundTitle = v.findViewById(R.id.sound_title);
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

class CategorySoundAdapter extends RecyclerView.Adapter<CategorySoundAdapter.SoundViewHolder> {

    private List<String> mSoundSet;
    private List<String> nDuration;

    public CategorySoundAdapter(List<String> mySoundSet, List<String> nDuration){
        this.mSoundSet = mySoundSet;
        this.nDuration = nDuration;
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder{
        public TextView soundTextView;
        public TextView soundDuration;
        public ImageView soundOption;


        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            //Måske tilføje de resterende ting for et sound item
            soundTextView = itemView.findViewById(R.id.sound_title);
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
    public CategorySoundAdapter.SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_list_element, parent, false);
        return new SoundViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, final int position) {
        holder.soundTextView.setText(mSoundSet.get(position));
        holder.soundDuration.setText(nDuration.get(position+1));
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
