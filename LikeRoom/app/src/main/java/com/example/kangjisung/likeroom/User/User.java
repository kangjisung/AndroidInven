package com.example.kangjisung.likeroom.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.User.MileageManage.mileage;

public class User extends AppCompatActivity {

    ImageButton ScBtn;
    EditText ScText;
    ListView UserView;
    static final String[] UserList = {"강지성", "List", "LIST3"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, UserList);
        UserView=(ListView)findViewById(R.id.userlist);
        UserView.setAdapter(adapter);
        final Intent mil = new Intent(this, mileage.class);
        UserView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                startActivity(mil);
                Toast.makeText(getApplicationContext(), "클릭.", Toast.LENGTH_SHORT).show();

                // TODO : use strText
            }
        }) ;
        ScBtn=(ImageButton)findViewById(R.id.SearchBtn);
        ScText=(EditText)findViewById(R.id.SearchText);

        ScBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //.execSQL("SELECT Pnum FROM Custom WHERE Pnum like '%"+ ScText +"%';");
            }
        });

    }


}
