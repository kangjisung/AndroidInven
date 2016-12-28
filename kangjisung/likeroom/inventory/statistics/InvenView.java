package com.example.kangjisung.likeroom.inventory.statistics;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.LocalHostDatabaseManager;
import com.example.kangjisung.likeroom.inventory.InvenList.InvenAdapter;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

/**
 * Created by kangjisung on 2016-12-05.
 */
/////////////////////최적재고량 보여주는 클래스
public class InvenView extends AppCompatActivity {

    ListView Blistview ;
    InvenAdapter ivAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivbread);

        Blistview = (ListView) findViewById(R.id.ivBreadlist);

        ivAdapter = new InvenAdapter();
        Blistview.setAdapter(ivAdapter);
        //제품 이름,날짜,최적재고량 불러오기
        new ClientDataBase("select `제품정보`.`이름`,`최적재고량`.`날짜`,`최적재고량`.`최적재고량` from `제품정보` join `최적재고량` on `제품정보`.`제품코드`= `최적재고량`.`제품코드`;",1,3,getApplicationContext());
        int cnt=0;
        while(true){
            if(DBstring[cnt]!=null) {
                ivAdapter.addItem(DBstring[cnt], DBstring[cnt + 1], DBstring[cnt + 2]);
                cnt += 3;
            }
            else if(DBstring[cnt]==null) break;
        }
    }

}
