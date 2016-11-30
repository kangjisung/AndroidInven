package com.example.kangjisung.likeroom.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.User.MileageManage.mileage;
import com.example.kangjisung.likeroom.User.listView.ListViewAdapter;
import com.example.kangjisung.likeroom.User.listView.ListViewItem;

public class User extends AppCompatActivity {

    ImageButton ScBtn;
    EditText ScText;
    ListView listview ;
    ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        adapter = new ListViewAdapter() ;

        listview = (ListView) findViewById(R.id.userlist);
        listview.setAdapter(adapter);
        // 첫 번째 아이템 추가
        adapter.addItem("강지성", "010-4152-5415") ;
        // 두 번째 아이템 추가.
        adapter.addItem("박동원", "010-5841-4851") ;
        // 세 번째 아이템 추가.
        adapter.addItem("김소연", "010-4156-4615") ;


        final Intent mil = new Intent(this, mileage.class);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String nameStr = item.getTitle() ;
                mil.putExtra("name", nameStr);

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
