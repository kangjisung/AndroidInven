package com.example.kangjisung.likeroom.User;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SearchView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.LocalHostDatabaseManager;
import com.example.kangjisung.likeroom.User.MileageManage.mileage;
import com.example.kangjisung.likeroom.User.listView.ListViewAdapter;
import com.example.kangjisung.likeroom.User.listView.ListViewItem;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

///////////////////////////////마일리지 적립전 유저 검색 클래스
public class User extends AppCompatActivity {

    SearchView scView; //검색 텍스트
    ListView listview ; //리스트뷰
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        scView=(SearchView) findViewById(R.id.searchview);
        listview = (ListView) findViewById(R.id.userlist);

        /////////////////////////검색 이벤트
        scView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /////검색버튼
            @Override
            public boolean onQueryTextSubmit(String s) {
                ////////////////////////////////listview
                adapter = new ListViewAdapter() ;
                listview.setAdapter(adapter);
                //////////////////////////검색하여 데이터 넣기
                String View=s;
                new ClientDataBase("select `이름`,`전화번호` from `회원정보`  where `전화번호` LIKE \"%"+View+"%\"or `이름` LIKE \"%"+View+"%\";",1,2,getApplicationContext());
                int cnt=0;
                while(true) {
                    if (DBstring[cnt] != null) {
                        adapter.addItem(DBstring[cnt], DBstring[cnt + 1]);
                        cnt += 2;
                    }
                    else if(DBstring[cnt]==null) break;
                }
                return false;
            }
            /////한글자한글자 쓸때마다 검색
            @Override
            public boolean onQueryTextChange(String s) {
                ////////////////////////////////listview
                adapter = new ListViewAdapter() ;
                listview.setAdapter(adapter);
                //////////////////////////검색하여 데이터 넣기
                String View=s;
                new ClientDataBase("select `이름`,`전화번호` from `회원정보`  where `전화번호` LIKE \"%"+View+"%\"or `이름` LIKE \"%"+View+"%\";",1,2,getApplicationContext());
                int cnt=0;
                while(true) {
                    if (DBstring[cnt] != null) {
                        adapter.addItem(DBstring[cnt], DBstring[cnt + 1]);
                        cnt += 2;
                    }
                    else if(DBstring[cnt]==null) break;
                }
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
