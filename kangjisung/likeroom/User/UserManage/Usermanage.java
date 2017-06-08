package com.example.kangjisung.likeroom.User.UserManage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.User.listView.ListViewAdapter;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

/**
 * Created by Home7 on 2016-12-28.
 */

public class Usermanage extends AppCompatActivity {

    ListView listview ; //리스트뷰
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlistview);

        listview = (ListView) findViewById(R.id.userlistview);
        adapter = new ListViewAdapter() ;
        listview.setAdapter(adapter);
        //////////////////////////검색하여 데이터 넣기
        new ClientDataBase("select `회원정보`.`이름`,`회원정보`.`전화번호`,`포인트`.`포인트` from `회원정보` join `포인트` on `회원정보`.`고유회원등록번호`= `포인트`.`고유회원등록번호`;",1,3,getApplicationContext());
        int cnt=0;
        while(true) {
            if (DBstring[cnt] != null) {
                adapter.addItem(DBstring[cnt], DBstring[cnt + 1], DBstring[cnt+2]);
                cnt += 3;
            }
            else if(DBstring[cnt]==null) break;
        }
    }
}
