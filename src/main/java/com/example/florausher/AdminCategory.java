package com.example.florausher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminCategory extends AppCompatActivity implements View.OnClickListener{
    Button addPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        addPlant=(Button)findViewById(R.id.addPlant);
        addPlant.setOnClickListener(this);

    }

    private void addplant()
    {
        Toast.makeText(getApplicationContext(), "Plant Added", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(view==addPlant)
            addplant();
    }
}
