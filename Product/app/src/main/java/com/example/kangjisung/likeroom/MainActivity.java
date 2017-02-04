package com.example.kangjisung.likeroom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kangjisung.likeroom.Object.MuchStore;
import com.example.kangjisung.likeroom.Object.ProductInfo;
import com.example.kangjisung.likeroom.Object.SellToday;
import com.example.kangjisung.likeroom.ObjectManager.ProductObjManager;
import com.example.kangjisung.likeroom.Util.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.Util.SQLiteDatabaseControl.DatabaseHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.ParseException;


import static com.example.kangjisung.likeroom.Util.SQLiteDatabaseControl.ClientDataBase.DBstring;
import static com.example.kangjisung.likeroom.Util.SQLiteDatabaseControl.ClientDataBase.testDatabaseName;

public class MainActivity extends ActionBarActivity {
    private Handler mHandler;
    private Runnable mRunnable;
    DatabaseHelper databaseHelperTest;
    public static Context con;
    public static String PriNum;


    ArrayList<SellToday> sellTodayArrayList = new ArrayList<>();
    ArrayList<MuchStore> muchStoreArrayList = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelperTest = new DatabaseHelper(getApplicationContext(), testDatabaseName);
        con=getApplicationContext();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ActivityMenu.class);
                startActivity(intent);
            }
        };
        //////////////////////////////////////////요기서, 제품 정보 배열 추가.
        //제품 이름,날짜,판매량 불러오기
        new ClientDataBase("select `제품정보`.`이름`,`제품판매량`.`년`,`제품판매량`.`월`,`제품판매량`.`일`,`제품판매량`.`판매량` from `제품정보` join `제품판매량` on `제품정보`.`제품코드`= `제품판매량`.`제품코드` group by `제품판매량`.`제품코드`", 1, 5, getApplicationContext());
        int cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                sellTodayArrayList.add(new SellToday(DBstring[cnt], Integer.parseInt(DBstring[cnt + 1]), Integer.parseInt(DBstring[cnt + 2]), Integer.parseInt(DBstring[cnt + 3]), Integer.parseInt(DBstring[cnt + 4])));
                cnt += 5;
            } else if (DBstring[cnt] == null) break;
        }
        Collections.sort(sellTodayArrayList, new SellNameAscCompare());

        new ClientDataBase("select `제품정보`.`이름`,`최적재고량`.`날짜`,`최적재고량`.`최적재고량` from `제품정보` join `최적재고량` on `제품정보`.`제품코드`= `최적재고량`.`제품코드` group by `최적재고량`.`제품코드` having max(`날짜`);", 1, 3, getApplicationContext());
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                muchStoreArrayList.add(new MuchStore(DBstring[cnt], DBstring[cnt + 1], Integer.parseInt(DBstring[cnt + 2])));
                cnt += 3;
            } else if (DBstring[cnt] == null) break;
        }
        Collections.sort(muchStoreArrayList, new MuchNameAscCompare());

        for(int i=0; i<sellTodayArrayList.size(); i++){
            SellToday sellToday = sellTodayArrayList.get(i);
            MuchStore muchStore;
            if(i<muchStoreArrayList.size()) muchStore = muchStoreArrayList.get(i);
            else break;
            try {
                ProductObjManager.add(new ProductInfo(sellToday.getName(), false, muchStore.getDate(), new Date(sellToday.getYear(), sellToday.getMonth(), sellToday.getDay()), sellToday.getSell(), muchStore.getMuch()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        System.out.println(muchStoreArrayList.size());
        ProductObjManager.context = getApplicationContext();
        //////////////////////////////////////////
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1500);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void onDestroy() {
        Log.i("test", "onDstory()");
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    static class SellNameAscCompare implements Comparator<SellToday> {
        @Override
        public int compare(SellToday arg0, SellToday arg1) {
            // TODO Auto-generated method stub
            return arg0.getName().compareTo(arg1.getName());
        }
    }

    static class MuchNameAscCompare implements Comparator<MuchStore> {
        @Override
        public int compare(MuchStore arg0, MuchStore arg1) {
            // TODO Auto-generated method stub
            return arg0.getName().compareTo(arg1.getName());
        }
    }
}