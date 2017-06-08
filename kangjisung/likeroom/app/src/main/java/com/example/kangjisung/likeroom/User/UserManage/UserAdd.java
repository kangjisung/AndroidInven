package com.example.kangjisung.likeroom.User.UserManage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.server.HttpCommunicationProcess;

import static android.R.attr.level;
import static com.example.kangjisung.likeroom.MainActivity.PriNum;

/**
 * Created by kangjisung on 2016-12-30.
 */

public class UserAdd extends Activity{
    Button User_add;
    EditText UserAddName,UserAddress,UserAddPhone,UserAdd_Email,UserAddBirth;
    HttpCommunicationProcess httpCommunicationProcess;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add);
        UserAddName=(EditText) findViewById(R.id.UserAdd_Name);
        UserAddress=(EditText) findViewById(R.id.User_Address);
        UserAddPhone=(EditText) findViewById(R.id.UserAdd_Phone);
        UserAdd_Email=(EditText) findViewById(R.id.UserAdd_email);
        UserAddBirth=(EditText) findViewById(R.id.UserAdd_Birth);
        User_add = (Button) findViewById(R.id.UserAdd_Btn);

        User_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new ClientDataBase("insert into `회원정보` (`이름`,`전화번호`,`생년월일`,`이메일`) values(\"" + UserAddName.getText().toString() + "\",\""+UserAddPhone.getText().toString()+"\",\""+UserAddBirth.getText().toString()+"\",\""+UserAdd_Email.getText().toString()+"\");", 2, 0, getApplicationContext());
                httpCommunicationProcess=new HttpCommunicationProcess(getApplicationContext());
                httpCommunicationProcess.execute("http://lamb.kangnam.ac.kr:4200/Smoothie/2/InsertNewCustomerInfo/?name=" + UserAddName.getText().toString() + "&phone="+UserAddPhone.getText().toString()+"&email="+UserAdd_Email.getText().toString()+"&birthday="+UserAddBirth.getText().toString()+";");
                Toast.makeText(getApplicationContext(), "클릭.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}