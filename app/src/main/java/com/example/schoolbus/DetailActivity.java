package com.example.schoolbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schoolbus.models.Bus;

public class DetailActivity extends AppCompatActivity {

    private Bus emp;
    private ImageView busesImage;
    private TextView busesName, busesPlate, busesType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int id = getIntent().getIntExtra("id", -1);

        init();

        String url = "https://bit.ly/JSONSchoolBus";

        busesName.setText(id + "");

    }

    private void init(){
        busesImage = findViewById(R.id.busImg);
        busesName = findViewById(R.id.busName);
        busesType = findViewById(R.id.busType);
        busesPlate = findViewById(R.id.busPlate);
    }
}

