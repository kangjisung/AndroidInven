package com.example.kangjisung.likeroom.inventory.sales;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.LocalHostDatabaseManager;

/**
 * Created by kangjisung on 2016-12-05.
 */

public class salesVolume extends AppCompatActivity {

    ListView Blistview ;
    InvenAdapter ivAdapter;
    DatabaseHelper databaseHelperTest;
    LocalHostDatabaseManager localHostDatabaseManager;
    String testDatabaseName = "ShopkeeperDatabase.db";
    SQLiteDatabase sqLiteDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bread);
        String reName,reDay,reSales;

        Blistview = (ListView) findViewById(R.id.Breadlist);

        ivAdapter = new InvenAdapter();
        Blistview.setAdapter(ivAdapter);
        databaseHelperTest = new DatabaseHelper(getApplicationContext(), testDatabaseName);
        localHostDatabaseManager = new LocalHostDatabaseManager(getApplicationContext(), getApplicationInfo().dataDir + "/databases/", testDatabaseName);

        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        Cursor c=sqLiteDatabase.rawQuery("select '제품정보'.'이름','제품판매량'.'날짜','제품판매량'.'판매량' from '제품정보','제품판매량';",null);
// where '제품정보'.'제품코드' = '제품판매량'.'제품코드'
        Log.d("test2",String.valueOf(c));
        while(c.moveToNext()){
            reName=c.getString(0);
            reDay=c.getString(1);
            reSales=c.getString(2);
            Log.d("test",reName+" "+reDay+" "+reSales);
            ivAdapter.addItem(reName, reDay, reSales);
        }
        Log.d("test", "end");
        sqLiteDatabase.close();
    }

}
