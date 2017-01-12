package com.example.kangjisung.likeroom.User;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.SQLiteDatabaseControl.LocalHostDatabaseManager;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.User.MileageManage.mileage;
import com.example.kangjisung.likeroom.User.listView.ListViewAdapter;
import com.example.kangjisung.likeroom.User.listView.ListViewItem;

///////////////////////////////마일리지 적립전 유저 검색 클래스
public class User extends AppCompatActivity {

    SearchView scView; //검색 텍스트
    ListView listview ; //리스트뷰
    ListViewAdapter adapter;
    DatabaseHelper databaseHelperTest;
    LocalHostDatabaseManager localHostDatabaseManager;
    String testDatabaseName = "ShopkeeperDatabase.db"; //매장데이터베이스 이름
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //scView=(SearchView) findViewById(R.id.searchview);
        listview = (ListView) findViewById(R.id.userlist);

        /////////////////////////검색 이벤트
        scView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /////검색버튼
            @Override
            public boolean onQueryTextSubmit(String s) {
                databaseHelperTest = new DatabaseHelper(getApplicationContext(), testDatabaseName);
                localHostDatabaseManager = new LocalHostDatabaseManager(getApplicationContext(), getApplicationInfo().dataDir + "/databases/", testDatabaseName);

                sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
                ////////////////////////////////listview
                adapter = new ListViewAdapter() ;
                listview.setAdapter(adapter);
                //////////////////////////검색하여 데이터 넣기
                String View=s;
                Cursor c = sqLiteDatabase.rawQuery("select `이름`,`전화번호` from `회원정보`  where `전화번호` LIKE \"%"+View+"%\"or `이름` LIKE \"%"+View+"%\";",null);
                while(c.moveToNext()) {
                    String reName = c.getString(0);
                    String rePhone = c.getString(1);
                    adapter.addItem(reName, rePhone) ; //결과 리스트뷰에 넣기
                }
                sqLiteDatabase.close();
                return false;
            }
            /////한글자한글자 쓸때마다 검색
            @Override
            public boolean onQueryTextChange(String s) {
                databaseHelperTest = new DatabaseHelper(getApplicationContext(), testDatabaseName);
                localHostDatabaseManager = new LocalHostDatabaseManager(getApplicationContext(), getApplicationInfo().dataDir + "/databases/", testDatabaseName);

                sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
                ////////////////////////////////listview
                adapter = new ListViewAdapter() ;
                listview.setAdapter(adapter);
                //////////////////////////검색하여 데이터 넣기
                String View=s;
                Cursor c = sqLiteDatabase.rawQuery("select `이름`,`전화번호` from `회원정보`  where `전화번호` LIKE \"%"+View+"%\"or `이름` LIKE \"%"+View+"%\";",null);
                while(c.moveToNext()) {
                    String reName = c.getString(0);
                    String rePhone = c.getString(1);
                    adapter.addItem(reName, rePhone) ; //결과 리스트뷰에 넣기
                }
                sqLiteDatabase.close();
                return false;
            }
        });

///////////////////////////////////////팝업창
        final Intent mil = new Intent(this, mileage.class); ///마일리지창
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
