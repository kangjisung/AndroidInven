package com.example.kangjisung.likeroom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.example.kangjisung.likeroom.FragmentProduct.ListView.ProductListItem;
import com.example.kangjisung.likeroom.FragmentProduct.ListView.ProductMuchStoreListItem;
import com.example.kangjisung.likeroom.FragmentProduct.ProductObjManager;
import com.example.kangjisung.likeroom.FragmentProduct.ListView.ProductSellTodayListItem;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.Util.ColorTheme;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class MainActivity extends AppCompatActivity
{
    static DatabaseHelper databaseHelperTest;
    public static Context con;
    public static String PriNum;

    ArrayList<ProductSellTodayListItem> sellTodayArrayList = new ArrayList<>();
    ArrayList<ProductMuchStoreListItem> muchStoreArrayList = new ArrayList<>();
    //networkmodule 예제
    //networkModule.InsertNewCustomerInfo("강지성");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_main);
        con = getApplicationContext();
        databaseHelperTest = new DatabaseHelper(getApplicationContext(), ClientDataBase.testDatabaseName);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run(){
                CheckTypesTask task = new CheckTypesTask();
                task.execute();
            }
        }, 500);
    }

    // 로딩창 구현
    private class CheckTypesTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            //////매장이 클라이언트 디비에 있는지 검사
            new ClientDataBase("select `매장번호` from `매장`;",1,1,getApplicationContext());
            int cnt=0;
            while(true) {
                if (DBstring[cnt] != null) {
                    PriNum=DBstring[cnt];
                    cnt++;
                }
                else if(DBstring[cnt]==null) break;
            }
            if(PriNum==null) {
                //Intent StoreAdd = new Intent(this, StoreAdd.class);
                //startActivity(StoreAdd);
            }
            else {
                // show dialog
                asyncDialog.show();
                super.onPreExecute();
            }
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            Date today=new Date();
            //////////////////////////////////////////요기서, 제품 정보 배열 추가.(최근 데이터[오늘x])
            new ClientDataBase("select `제품정보`.`이름`,`최적재고량`.`날짜`,`최적재고량`.`최적재고량` from `제품정보` join `최적재고량` on `제품정보`.`제품코드`= `최적재고량`.`제품코드` group by `최적재고량`.`제품코드` having max(`날짜`);", 1, 3, getApplicationContext());
            int cnt = 0;
            while (true) {
                if (DBstring[cnt] != null) {
                    muchStoreArrayList.add(new ProductMuchStoreListItem(DBstring[cnt], DBstring[cnt + 1], Integer.parseInt(DBstring[cnt + 2])));
                    cnt += 3;
                } else if (DBstring[cnt] == null) break;
            }
            Collections.sort(muchStoreArrayList, new MuchNameAscCompare());

            //제품 이름,날짜,판매량 불러오기(오늘 데이터)
            new ClientDataBase("select `제품정보`.`이름`,`제품판매량`.`년`,`제품판매량`.`월`,`제품판매량`.`일`,`제품판매량`.`판매량` from `제품정보` join `제품판매량` on `제품정보`.`제품코드`= `제품판매량`.`제품코드` where `제품판매량`.`년`="+today.getYear()+1900+"and `제품판매량`.`월`="+today.getMonth()+1+"and `제품판매량`.`일`="+today.getDay()+"", 1, 5, getApplicationContext());
            cnt = 0;
            while (true) {
                if (DBstring[cnt] != null) {
                    sellTodayArrayList.add(new ProductSellTodayListItem(DBstring[cnt], Integer.parseInt(DBstring[cnt + 1]), Integer.parseInt(DBstring[cnt + 2]), Integer.parseInt(DBstring[cnt + 3]), Integer.parseInt(DBstring[cnt + 4])));
                    cnt += 5;
                } else if (DBstring[cnt] == null){
                    //오늘 판매량이 없을시 muchStoreArrayList크기만큼 sellTodayArrayList에 빈값 넣기(데이터 뿌려줄떄 null이면 에러나서)
                    if(cnt==0){
                        for(int i=0; i<muchStoreArrayList.size(); i++) sellTodayArrayList.add(new ProductSellTodayListItem(" ",0, 0, 0, 0));
                    }
                    break;
                }
            }
            Collections.sort(sellTodayArrayList, new SellNameAscCompare());

            ProductObjManager.productInfos = new ArrayList<ProductListItem>();
            for(int i=0; i<sellTodayArrayList.size(); i++){
                ProductSellTodayListItem sellToday = sellTodayArrayList.get(i);
                ProductMuchStoreListItem muchStore;
                if(i<muchStoreArrayList.size()) muchStore = muchStoreArrayList.get(i);
                else break;
                try {
                    ProductObjManager.add(new ProductListItem(muchStore.getName(), false, muchStore.getDate(), new Date(today.getYear()+1900, today.getMonth()+1, today.getDay()),sellToday.getSell(), muchStore.getMuch()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            ProductObjManager.context=getApplicationContext();

            SystemClock.sleep(500);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
            Intent intent = new Intent(getApplicationContext(), ActivityMenu.class);
            startActivity(intent);

            finish();
        }
    }

    static class SellNameAscCompare implements Comparator<ProductSellTodayListItem> {
        @Override
        public int compare(ProductSellTodayListItem arg0, ProductSellTodayListItem arg1) {
            // TODO Auto-generated method stub
            return arg0.getName().compareTo(arg1.getName());
        }
    }

    static class MuchNameAscCompare implements Comparator<ProductMuchStoreListItem> {
        @Override
        public int compare(ProductMuchStoreListItem arg0, ProductMuchStoreListItem arg1) {
            // TODO Auto-generated method stub
            return arg0.getName().compareTo(arg1.getName());
        }
    }
}