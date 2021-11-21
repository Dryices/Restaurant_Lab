package com.sp.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sp.com.restaurantlist.R;

public class About extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
      button=findViewById(R.id.button_send);
      button.setOnClickListener(onSave);

    }
    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            intent = new Intent(About.this, EmailForm.class);
            startActivity(intent);

        }
    };



}