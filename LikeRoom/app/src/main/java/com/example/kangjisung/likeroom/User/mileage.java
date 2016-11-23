package com.example.kangjisung.likeroom.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.kangjisung.likeroom.R;

public class mileage extends AppCompatActivity {

    Button CloBtn,Btn1,Btn2,Btn3,Btn4,Btn5,Btn6,Btn7,Btn8,Btn9,Btn0,Btn00;
    EditText NumView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mileage);

        CloBtn=(Button)findViewById(R.id.CloseBtn);
        Btn1=(Button)findViewById(R.id.Num1);
        Btn2=(Button)findViewById(R.id.Num2);
        Btn3=(Button)findViewById(R.id.Num3);
        Btn4=(Button)findViewById(R.id.Num4);
        Btn5=(Button)findViewById(R.id.Num5);
        Btn6=(Button)findViewById(R.id.Num6);
        Btn7=(Button)findViewById(R.id.Num7);
        Btn8=(Button)findViewById(R.id.Num8);
        Btn9=(Button)findViewById(R.id.Num9);
        Btn0=(Button)findViewById(R.id.Num0);
        Btn00=(Button)findViewById(R.id.Num00);
        NumView=(EditText)findViewById(R.id.NumView);

        CloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //.execSQL("UPDATE Custom SET Mileage WHERE Mileage+=)
                finish();
            }
        });
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumView.setText(NumView.getText()+"1");
            }
        });
    }

}
