package com.example.AudientesAPP.UI;

import android.app.Dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.DTO.CategoryDTO;
import com.example.AudientesAPP.model.context.Context;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;
import com.example.AudientesAPP.model.funktionalitet.LibraryListCategoryLogic;

import java.util.List;

public class LibraryListCategory extends Fragment implements LibraryListCategoryLogic.OnLibraryLCLogicListener{
    // Test
    private LibraryListCategoryLogic logic;
    private ImageView categoryIcon;
    private TextView categoryName;

    //Recyclerview
    private RecyclerView categoryList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //Objects
    private NavController navController;
    private CategoryDAO categoryDAO;
    private Context context;
    private android.content.Context contextUI;
    MainActivity mainActivity;

    //Variables
    List<String> categoryNames;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.library_list_category_frag, container, false);
        //Tildeler context, mainactivity's context
        mainActivity = (MainActivity) getActivity();
        context = mainActivity.getContext();
        //View
        categoryList = (RecyclerView) root.findViewById(R.id.Library_Category_Listview);
        //LayoutManager
        layoutManager = new LinearLayoutManager(this.getContext());
        categoryList.setLayoutManager(layoutManager);
        //Listeners
        logic = mainActivity.getLibraryLCLogic();
        logic.addLibraryLCLogicListener(this);
        // TEST - Test af logik, som nok skal være i funktionalitetsmappen
        logic = new LibraryListCategoryLogic(context);
        categoryNames = logic.getCategoryNames();

        try{
            mAdapter = new CategoryAdapter(categoryNames, getContext(), mainActivity.getNavController(), context);
        }catch (Exception e){
            e.printStackTrace();
        }
        categoryList.setAdapter(mAdapter);
        return root;
    }

    public void criteriaChanged()
    {
        context.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LibraryListCategory())
                .commit();
    }

    @Override
    public void updateLibraryListCategory(LibraryListCategoryLogic libraryListCategoryLogic) {
        //Hvad skal der ske når denne opdateres
        System.out.println("Update LibraryListCategory");
        //categoryNames er en List<String>
        categoryNames = logic.getCategoryNames();
        mAdapter = new CategoryAdapter(categoryNames, getContext(), mainActivity.getNavController(), context);
        mAdapter.notifyDataSetChanged();
        categoryList.setAdapter(mAdapter);
    }
}

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    private final android.content.Context contextUI;
    private List<String> mDataset;
    //private List<CategoryDTO> mDataset;
    private Fragment mFragment;
    private Bundle mBundle;
    private NavController navController;

   public CategoryAdapter(List<String> mDataset, android.content.Context contextUI, NavController navController, Context context) {
        this.contextUI = contextUI;
        this.context = context;
        this.mDataset = mDataset;
        this.navController = navController;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView categoryName;
        public ImageView categoryIcon;

        public MyViewHolder(View v) {
            super(v);
            categoryName = v.findViewById(R.id.categoryName_TV);
            categoryIcon = v.findViewById(R.id.categoryIcon_IV);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_element,parent,false);
       MyViewHolder vh = new MyViewHolder(v);
       return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        String categoryDTO = mDataset.get(position);
        //CategoryDTO categoryDTO = mDataset.get(position);
        Typeface typeface = ResourcesCompat.getFont(context.getActivity(), R.font.audientes_font);
        holder.categoryName.setTypeface(typeface);
        holder.categoryName.setText(categoryDTO);
        //holder.categoryName.setText(categoryDTO.getCategoryName());

        //hardcoding for at skifte billeder og tekst for hvert liste element
        switch(position){
            //preset category
            case 0: {holder.categoryIcon.setImageResource(R.drawable.ic_baseline_tune_24);
                //sætter en nu farve uden at ændre formen på objektet
                holder.itemView.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.LIGHTEN);}
            break;
            //sleep category
            case 1: {holder.categoryIcon.setImageResource(R.drawable.ic_sleep_category);
                holder.itemView.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);}
            break;
            //nature category
            case 2: {holder.categoryIcon.setImageResource(R.drawable.ic_nature2_category);
                holder.itemView.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.LIGHTEN);}
            break;
            //waves category
            case 3: {holder.categoryIcon.setImageResource(R.drawable.ic_waves_category);
                holder.itemView.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.LIGHTEN);}
            break;
            //music category
            case 4: {holder.categoryIcon.setImageResource(R.drawable.ic_music_category);
                holder.itemView.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.LIGHTEN);}
            break;
            default: Log.d("List error",
                    "Error in applying image to list element");
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigering til fragment med category sounds eller presets
                /*switch (position) {
                    case 0:
                        navController.navigate(R.id.action_LibraryMain_to_HearingTest);
                        break;
                    case 1:
                        navController.navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
                        break;

                }*/
                if (position == 0) {
                    // Hop til create category siden
                    CreateCategoryDialog createCategoryDialog = new CreateCategoryDialog(contextUI, context);
                    createCategoryDialog.show();

                } else {
                    // Hop til den rigtige kategori
                    context.getPrefs().edit().putString("Category", mDataset.get(position)).apply();
                    navController.navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void fragmentJump() {
        /*mFragment = new Fragment2();
        mBundle = new Bundle();
        mBundle.putParcelable("item_selected_key", Parcelable);
        mFragment.setArguments(mBundle);
        switchContent(R.id.frag1, mFragment);*/
        //Navigation.findNavController(v).navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
        navController.navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
        /*CategoryListSounds fragment = new CategoryListSounds();
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.libraryMain, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
//        Navigation.findNavController(v).navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
    }
}

class CreateCategoryDialog extends Dialog implements View.OnClickListener {
    // Views
    private EditText editText;
    private TextView textView;
    private GridLayout gridLayout;
    private Button button;
    // Objects
    private CategoryDTO categoryDTO;
    private LibraryListCategoryLogic libraryListCategoryLogic;
    private Context context;
    // Listener
    private DialogInterface.OnDismissListener onDismissListener;

    public CreateCategoryDialog(@NonNull android.content.Context contextUI, Context context) {
        super(contextUI);
        setContentView(R.layout.category_new_dialog);
        this.context = context;
        libraryListCategoryLogic = new LibraryListCategoryLogic(context);
        editText = findViewById(R.id.category_new_name_ET);
        textView = findViewById(R.id.category_new_color_TV);
        gridLayout = findViewById(R.id.category_new_colorpicker_grid);
        button = findViewById(R.id.category_new_create);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String categoryName = editText.getText().toString();
        if(categoryName.length() < 1) {
            String text = "Please enter a category name";
            int time = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(v.getContext(), text, time);
            toast.show();
        } else {
            System.out.println(categoryName);
            System.out.println("----------------------------------");
            libraryListCategoryLogic.addCategory(categoryName);
            Toast.makeText(v.getContext(), "Category " + categoryName + " created", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
