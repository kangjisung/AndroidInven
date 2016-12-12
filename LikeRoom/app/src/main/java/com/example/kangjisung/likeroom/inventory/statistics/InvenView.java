package com.example.kangjisung.likeroom.inventory.statistics;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.LocalHostDatabaseManager;
import com.example.kangjisung.likeroom.inventory.InvenList.InvenAdapter;

/**
 * Created by kangjisung on 2016-12-05.
 */
/////////////////////최적재고량 보여주는 클래스
public class InvenView extends AppCompatActivity {

    ListView Blistview ;
    InvenAdapter ivAdapter;
    DatabaseHelper databaseHelperTest;
    LocalHostDatabaseManager localHostDatabaseManager;
    String testDatabaseName = "ShopkeeperDatabase.db";
    SQLiteDatabase sqLiteDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivbread);
        String reName,reDay,reInven;  //제품이름, 최신등록일, 최적재고량

        Blistview = (ListView) findViewById(R.id.ivBreadlist);

        ivAdapter = new InvenAdapter();
        Blistview.setAdapter(ivAdapter);
        databaseHelperTest = new DatabaseHelper(getApplicationContext(), testDatabaseName);
        localHostDatabaseManager = new LocalHostDatabaseManager(getApplicationContext(), getApplicationInfo().dataDir + "/databases/", testDatabaseName);

        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        //제품 이름,날짜,최적재고량 불러오기
        Cursor c=sqLiteDatabase.rawQuery("select `제품정보`.`이름`,`최적재고량`.`날짜`,`최적재고량`.`최적재고량` from `제품정보` join `최적재고량` on `제품정보`.`제품코드`= `최적재고량`.`제품코드`;",null);
        while(c.moveToNext()){
            reName=c.getString(0);
            reDay=c.getString(1);
            reInven=c.getString(2);
            ivAdapter.addItem(reName, reDay, reInven);
        }
        sqLiteDatabase.close();
    }

}
