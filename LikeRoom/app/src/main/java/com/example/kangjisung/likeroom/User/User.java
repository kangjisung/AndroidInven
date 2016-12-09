package com.example.kangjisung.likeroom.User;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.LocalHostDatabaseManager;
import com.example.kangjisung.likeroom.User.MileageManage.mileage;
import com.example.kangjisung.likeroom.User.listView.ListViewAdapter;
import com.example.kangjisung.likeroom.User.listView.ListViewItem;

public class User extends AppCompatActivity {

    ImageButton ScBtn; //검색버튼
    EditText ScText; //찾기텍스트
    ListView listview ;
    ListViewAdapter adapter;
    DatabaseHelper databaseHelperTest;
    LocalHostDatabaseManager localHostDatabaseManager;
    String testDatabaseName = "ShopkeeperDatabase.db";
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ScBtn=(ImageButton)findViewById(R.id.SearchBtn);
        ScText=(EditText)findViewById(R.id.SearchText);
        listview = (ListView) findViewById(R.id.userlist);

////////////////////////검색버튼 클릭시 이벤트발생
        ScBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////////////DB
                databaseHelperTest = new DatabaseHelper(getApplicationContext(), testDatabaseName);
                localHostDatabaseManager = new LocalHostDatabaseManager(getApplicationContext(), getApplicationInfo().dataDir + "/databases/", testDatabaseName);

                sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
                ////////////////////////////////listview
                adapter = new ListViewAdapter() ;
                listview.setAdapter(adapter);
                //////////////////////////검색하여 데이터 넣기
                String scText=ScText.getText().toString();
                Cursor c = sqLiteDatabase.rawQuery("select `이름`,`전화번호` from `회원정보` where `이름` LIKE '%`"+scText+"`%';", null);
                while(c.moveToNext()) {
                    String reName = c.getString(0);
                    String rePhone = c.getString(1);
                    adapter.addItem(reName, rePhone) ; //결과 리스트뷰에 넣기
                    Log.d("ex13", "result: " + reName + " " + rePhone);
                }
                sqLiteDatabase.close();
            }
        });
///////////////////////////////////////팝업창
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
    }


}
