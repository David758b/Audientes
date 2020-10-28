package com.example.AudientesAPP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button knap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        knap = findViewById(R.id.button1);
        knap.setOnClickListener(this);

        Fragment bottonMenuFrag = new BottomMenu();
        getSupportFragmentManager().beginTransaction().add(R.id.Buttonmenu, bottonMenuFrag).commit();



    }

    @Override
    public void onClick(View v) {

        Fragment bottonMenuFrag = new BottomMenu();
        getSupportFragmentManager().beginTransaction().replace(R.id.Buttonmenu, bottonMenuFrag).commit();
    }
}