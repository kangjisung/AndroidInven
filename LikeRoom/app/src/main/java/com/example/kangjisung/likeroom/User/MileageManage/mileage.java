package com.example.kangjisung.likeroom.User.MileageManage;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kangjisung.likeroom.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class mileage extends Activity {

    Button Plus;
    Button[] Btn=new Button[12];
    ImageButton CloBtn;
    int[] numBtn={R.id.Num0,R.id.Num1,R.id.Num2,R.id.Num3,R.id.Num4,R.id.Num5,R.id.Num6,R.id.Num7,R.id.Num8,R.id.Num9,R.id.Num10};
    EditText NumView;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mileage);
        Intent mil = getIntent();
        final String name = mil.getExtras().getString("name");

        NumView = (EditText) findViewById(R.id.NumView);

        for (i = 0; i <= 10; i++) {
            Btn[i] = (Button) findViewById(numBtn[i]);
        }

        for (i = 0; i <= 10; i++) {
            final int index;
            index = i;
            Btn[index].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String a;
                    a = NumView.getText().toString()
                            + Btn[index].getText().toString();
                    NumView.setText(a);
                }
            });
        }

        CloBtn = (ImageButton) findViewById(R.id.BtnClose);
        CloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        Plus = (Button) findViewById(R.id.PlusBtn);
        Plus.setOnClickListener(new View.OnClickListener(){
            int point;
            public void onClick(View v) {
                point = Integer.parseInt(NumView.getText().toString());
                point=(int)(point*0.3);
                //.execSQL("UPDATE Custom SET Mileage WHERE Mileage+=)
                Toast.makeText(getApplicationContext(), name+"에게"+point+"빵이 적립되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
