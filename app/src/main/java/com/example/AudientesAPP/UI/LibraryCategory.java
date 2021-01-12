package com.example.AudientesAPP.UI;

import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AudientesAPP.model.DTO.CategoryDTO;
import com.example.AudientesAPP.model.context.ModelViewController;
import com.example.AudientesAPP.R;
import com.example.AudientesAPP.model.data.DAO.CategoryDAO;
import com.example.AudientesAPP.model.funktionalitet.LibraryCategoryLogic;

import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;

public class LibraryCategory extends Fragment implements LibraryCategoryLogic.OnLibraryLCLogicListener {
    // Test
    private LibraryCategoryLogic logic;
    private ImageView categoryIcon;
    private TextView categoryName;

    //Recyclerview
    private RecyclerView categoryList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //Objects
    private NavController navController;
    private CategoryDAO categoryDAO;
    private ModelViewController modelViewController;
    private android.content.Context contextUI;
    MainActivity mainActivity;

    //Variables

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.library_list_category_frag, container, false);
        //Tildeler context, mainactivity's context
        mainActivity = (MainActivity) getActivity();
        modelViewController = mainActivity.getModelViewController();
        //View
        categoryList = root.findViewById(R.id.Library_Category_Listview);
        //LayoutManager
        layoutManager = new LinearLayoutManager(this.getContext());
        categoryList.setLayoutManager(layoutManager);
        //Listeners
        logic = mainActivity.getLibraryLCLogic();
        logic.addLibraryLCLogicListener(this);
        System.out.println();
        // TEST - Test af logik, som nok skal være i funktionalitetsmappen
        //logic.initCategoryNames();

        try {
            mAdapter = new CategoryAdapter(logic.getCategories(), getContext(), mainActivity.getNavController(), modelViewController, logic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        categoryList.setAdapter(mAdapter);
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
    private ModelViewController modelViewController;
    private final android.content.Context contextUI;
    private Fragment mFragment;
    private Bundle mBundle;
    private NavController navController;
    private LibraryCategoryLogic logic;

    //
    private List<CategoryDTO> data;

    public CategoryAdapter(List<CategoryDTO> data, Context contextUI, NavController navController, ModelViewController modelViewController, LibraryCategoryLogic logic) {
        this.contextUI = contextUI;
        this.modelViewController = modelViewController;
        this.data = data;
        this.navController = navController;
        this.logic = logic;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryNameTV;
        public ImageView categoryIcon;

        public MyViewHolder(View v) {
            super(v);
            categoryNameTV = v.findViewById(R.id.categoryName_TV);
            categoryIcon = v.findViewById(R.id.categoryIcon_IV);
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
        final CategoryDTO categoryDTO = data.get(position);
        Typeface typeface = ResourcesCompat.getFont(modelViewController.getActivity(), R.font.audientes_font);
        holder.categoryNameTV.setTypeface(typeface);
        holder.categoryNameTV.setText(categoryDTO.getCategoryName());
        holder.itemView.getBackground().setTint(Color.parseColor(categoryDTO.getColor()));
        System.out.println(categoryDTO.getCategoryName() + "'s FARVE---------------------" + categoryDTO.getColor());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    // Hop til create category siden
                    CreateCategoryDialog categoryDialog = new CreateCategoryDialog(contextUI, modelViewController, logic);
                    categoryDialog.show();

                } else {
                    // Hop til den rigtige kategori
                    modelViewController.getPrefs().edit().putString("Category", categoryDTO.getCategoryName()).apply();
                    navController.navigate(R.id.action_libraryListCategory_to_CategoryListSounds);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
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
    private EditText categoryNameET;
    private TextView colorPickerTV;
    private Button colorBtn;
    private Button createBtn;
    // Objects
    private CategoryDTO categoryDTO;
    private final LibraryCategoryLogic libraryCategoryLogic;
    private ModelViewController modelViewController;
    private ColorPicker colorPicker;
    // Listener
    private DialogInterface.OnDismissListener onDismissListener;

    //Variables
    private String categoryColor;
    private String categoryName;

    public CreateCategoryDialog(@NonNull android.content.Context contextUI, ModelViewController modelViewController, LibraryCategoryLogic libraryCategoryLogic) {
        super(contextUI);
        setContentView(R.layout.category_new_dialog);
        this.modelViewController = modelViewController;
        this.libraryCategoryLogic = libraryCategoryLogic;
        categoryNameET = findViewById(R.id.category_new_name_ET);
        colorPickerTV = findViewById(R.id.category_new_color_TV);
        colorBtn = findViewById(R.id.colorPicker);
        createBtn = findViewById(R.id.category_new_create);
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
            libraryCategoryLogic.addCategory(categoryName, "", categoryColor);
            Toast.makeText(v.getContext(), "Category " + categoryName + " created", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }

    public void initColorPicker(final View v) {
        //Henter farver fra vores color resources
        int[] colorNumberarray = v.getResources().getIntArray(R.array.colorNumberList);
        colorPicker = new ColorPicker(modelViewController.getActivity());
        //sætter farver på colorpickeren
        colorPicker.setColors(colorNumberarray);
        colorPicker.setRoundColorButton(true);

        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                colorBtn.setBackgroundColor(color);
                //confirmColor(color,v);
                categoryColor = String.format("#%06X", (0xFFFFFF & color));
            }

            @Override
            public void onCancel() {
                //
            }
        });
    }
}
