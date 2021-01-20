package com.example.AudientesAPP.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.controller.ModelViewController;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.funktionalitet.CategorySoundsLogic;
import com.example.AudientesAPP.model.funktionalitet.LibraryCategoryLogic;

import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;
/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
public class LibraryCategory extends Fragment implements LibraryCategoryLogic.OnLibraryLCLogicListener {
    // Logic
    private LibraryCategoryLogic logic;
    private CategorySoundsLogic categorySoundsLogic;

    //Recyclerview
    private RecyclerView categoryList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //Objects
    private ModelViewController modelViewController;

    //Variables

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.library_list_category_frag, container, false);
        //Tildeler context, mainactivity's context
        MainActivity mainActivity = (MainActivity) getActivity();
        modelViewController = mainActivity.getModelViewController();
        //View
        categoryList = root.findViewById(R.id.Library_Category_Listview);
        //LayoutManager
        layoutManager = new LinearLayoutManager(this.getContext());
        categoryList.setLayoutManager(layoutManager);
        //Listeners
        logic = modelViewController.getLibraryCategoryLogic();
        logic.addLibraryLCLogicListener(this);
        categorySoundsLogic = modelViewController.getCategorySoundsLogic();


        try {
            mAdapter = new CategoryAdapter(logic.getCategories(), getActivity(), modelViewController.getNavController(), logic, this,categorySoundsLogic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        categoryList.setAdapter(mAdapter);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(mainActivity, callback);

        return root;
    }

    @Override
    public void updateLibraryListCategory(LibraryCategoryLogic libraryCategoryLogic) {
        //Hvad skal der ske når denne opdateres
        System.out.println("Update LibraryListCategory");
        //categoryNames er en List<String>
        mAdapter.notifyDataSetChanged();
    }
}

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Activity contextUI;
    private Fragment mFragment;
    private Bundle mBundle;
    private NavController navController;
    private LibraryCategoryLogic logic;
    private CategorySoundsLogic categorySoundsLogic;

    //
    private List<CategoryDTO> data;

    public CategoryAdapter(List<CategoryDTO> data, Activity contextUI, NavController navController, LibraryCategoryLogic logic, Fragment fragment, CategorySoundsLogic categorySoundsLogic) {
        this.contextUI = contextUI;
        this.data = data;
        this.navController = navController;
        this.categorySoundsLogic = categorySoundsLogic;
        this.logic = logic;
        this.mFragment = fragment;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryNameTV;
        public ImageView categoryIcon;
        public ImageView delete;

        public MyViewHolder(View v) {
            super(v);
            categoryNameTV = v.findViewById(R.id.categoryName_TV);
            categoryIcon = v.findViewById(R.id.categoryIcon_IV);
            delete = v.findViewById(R.id.delete_category);

        }
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_element, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        /* Hardcoded elements, one for creating new categories, and one for presets.
         * there should not be a delete button on the create category and preset category element
         */

        if(position == 0){
            holder.delete.setVisibility(View.GONE);
            holder.categoryIcon.setImageResource(R.drawable.ic_baseline_add_24);
            holder.categoryIcon.setColorFilter(contextUI.getResources().getColor(R.color.white));
        }else if (position == 1){
            holder.delete.setVisibility(View.GONE);
            holder.categoryIcon.setImageResource(R.drawable.ic_baseline_playlist_play_24);
            holder.categoryIcon.setColorFilter(contextUI.getResources().getColor(R.color.white));
        }
        final CategoryDTO categoryDTO = data.get(position);
        Typeface typeface = ResourcesCompat.getFont(contextUI, R.font.audientes_font);
        holder.categoryNameTV.setTypeface(typeface);
        holder.categoryNameTV.setText(categoryDTO.getCategoryName());
        System.out.println("--------------------------------");
        System.out.println(categoryDTO.getCategoryName() + "'s farve er:" + categoryDTO.getColor());
        holder.itemView.getBackground().setTint(Color.parseColor(categoryDTO.getColor()));
        System.out.println(categoryDTO.getCategoryName() + "'s FARVE---------------------" + categoryDTO.getColor());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setMessage("Do you want to remove this category?");
                builder.setTitle("Removing category!");
                builder.setCancelable(true);


                builder.setPositiveButton("Remove",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logic.deleteCategory(categoryDTO.getCategoryName());
                        System.out.println("DELETED CATEGORY ::::::::::::::::");
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
                if (position == 0) {
                    // Hop til create category siden
                    CreateCategoryDialog categoryDialog = new CreateCategoryDialog(contextUI, logic);
                    categoryDialog.show();

                }else if(position == 1){

                    categorySoundsLogic.setCurrentCategory("Presets");
                    navController.navigate(R.id.action_LibraryMain_to_CategoryPreset);

                } else {
                    // Hop til den rigtige kategori
                    // Dette skal overvejes lidt nærmere... smæk det i logik klassen
                    categorySoundsLogic.setCurrentCategory(categoryDTO.getCategoryName());
                    navController.navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}

/**
 * @author Johan Jens Kryger Larsen, Mohammad Tawrat Nafiu Uddin,
 *         Christian Merithz Uhrenfeldt Nielsen, David Lukas Mikkelsen
 */
class CreateCategoryDialog extends Dialog implements View.OnClickListener {
    // Views
    private EditText categoryNameET;
    private TextView colorPickerTV;
    private Button colorBtn;
    private Button createBtn;
    // Objects
    private CategoryDTO categoryDTO;
    private final LibraryCategoryLogic libraryCategoryLogic;
    private ColorPicker colorPicker;
    private Activity contextUI;
    // Listener
    private DialogInterface.OnDismissListener onDismissListener;

    //Variables
    private String categoryColor;
    private String categoryName;

    public CreateCategoryDialog(Activity contextUI, LibraryCategoryLogic libraryCategoryLogic) {
        super(contextUI);
        this.libraryCategoryLogic = libraryCategoryLogic;
        this.contextUI = contextUI;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.category_new_dialog);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        createBtn = findViewById(R.id.category_new_create);
        categoryNameET = findViewById(R.id.category_new_name_ET);
        colorPickerTV = findViewById(R.id.category_new_color_TV);
        colorBtn = findViewById(R.id.colorPicker);
        createBtn.setOnClickListener(this);
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initColorPicker(v);
            }
        });
    }

    @Override
    public void onClick(View v) {
        categoryName = categoryNameET.getText().toString();

        if (categoryName.length() < 1) {
            String text = "Please enter a category name";
            int time = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(v.getContext(), text, time);
            toast.show();
        } else {
            System.out.println(categoryName);
            System.out.println("----------------------------------");

            //sætter en default farve hvis der ikke er valgt en
            if(categoryColor==null){
                categoryColor = "#bbbbbb";
            }
            libraryCategoryLogic.addCategory(categoryName, "", categoryColor);
            Toast.makeText(v.getContext(), "Category " + categoryName + " created", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }

    public void initColorPicker(final View v) {
        //Henter farver fra vores color resources
        int[] colorNumberarray = v.getResources().getIntArray(R.array.colorNumberList);
        colorPicker = new ColorPicker(contextUI);
        //sætter farver på colorpickeren
        colorPicker.setColors(colorNumberarray);
        colorPicker.setRoundColorButton(true);


        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                colorBtn.getBackground().setTint(color);
                categoryColor = String.format("#%06X", (0xFFFFFF & color));
            }

            @Override
            public void onCancel() {

            }
        });
    }
}
