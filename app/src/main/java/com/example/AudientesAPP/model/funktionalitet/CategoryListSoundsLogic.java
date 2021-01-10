package com.example.AudientesAPP.model.funktionalitet;

import com.example.AudientesAPP.DTO.SoundCategoriesDTO;
import com.example.AudientesAPP.DTO.SoundDTO;
import com.example.AudientesAPP.UI.CategoryListSounds;
import com.example.AudientesAPP.model.context.Context;
import com.example.AudientesAPP.model.data.DAO.SoundCategoriesDAO;
import com.example.AudientesAPP.model.data.DAO.SoundDAO;

import java.util.List;

public class CategoryListSoundsLogic {
    private Context context;
    private SoundCategoriesDAO soundCategoriesDAO = new SoundCategoriesDAO(context);

    public CategoryListSoundsLogic (Context context) {
        this.context = context;
    }

    public List<String> getSoundsList (String category) {
        List<SoundCategoriesDTO> soundCategoriesDTOS = soundCategoriesDAO.getList();
        List<String> sounds = null;
        for (SoundCategoriesDTO DTO: soundCategoriesDTOS) {
            if (DTO.getCategoryName().equals(category)) {
                sounds.add(DTO.getSoundName());
            }
        }
        return sounds;
    }

}
