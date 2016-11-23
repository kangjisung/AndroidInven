package com.example.kangjisung.likeroom.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kangjisung.likeroom.R;

public class User extends AppCompatActivity {

    ImageButton ScBtn;
    EditText ScText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ScBtn=(ImageButton)findViewById(R.id.SearchBtn);
        ScText=(EditText)findViewById(R.id.SearchText);
        final Intent mil = new Intent(this, mileage.class);
        ScBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //.execSQL("SELECT Pnum FROM Custom WHERE Pnum like '%"+ ScText +"%';");

                startActivity(mil);


            }
        });
    }
}
