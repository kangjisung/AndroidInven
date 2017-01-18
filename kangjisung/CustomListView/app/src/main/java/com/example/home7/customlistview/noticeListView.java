package com.example.home7.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.home7.customlistview.SQLiteDatabaseControl.ClientDataBase;
import com.example.locallistview.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

import static android.R.attr.type;
import static com.example.home7.customlistview.SQLiteDatabaseControl.ClientDataBase.DBstring;

/**
 * Created by kangjisung on 2017-01-16.
 */

public class noticeListView extends AppCompatActivity {

    ListView listview ; //리스트뷰
    UserNoticeListAdapter adapter;
    Button upDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list);

        listview = (ListView) findViewById(R.id.list);
        adapter = new UserNoticeListAdapter() ;
        listview.setAdapter(adapter);
        //////////////////////////검색하여 데이터 넣기
        new ClientDataBase("select `제목`,`내용`,`공지 시작 날짜`,`공지 마감 날짜`,`공지사항종류` from `매장공지`",1,5,getApplicationContext());
        int cnt=0;
        String startDate;
        String EndDate;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date stDate=new Date();
        Date eDate=new Date();
        while(true) {
            if (DBstring[cnt] != null) {
                startDate=DBstring[cnt+2];
                EndDate=DBstring[cnt+3];
                try {
                    stDate = sdf.parse(startDate);
                    eDate = sdf.parse(EndDate);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                adapter.addItem(DBstring[cnt], DBstring[cnt + 1],stDate , eDate,Integer.parseInt(DBstring[cnt+4]));
                cnt += 5;
            }
            else if(DBstring[cnt]==null) break;
        }
    }
}
