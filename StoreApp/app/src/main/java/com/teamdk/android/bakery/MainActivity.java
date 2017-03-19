package com.teamdk.android.bakery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

<<<<<<< HEAD
import com.teamdk.android.bakery.objectmanager.ProductMuchStoreListItem;
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;
import com.teamdk.android.bakery.objectmanager.ProductSellTodayListItem;
import com.teamdk.android.bakery.objectmanager.MemberObjectManager;
import com.teamdk.android.bakery.objectmanager.NoticeObjectManager;
import com.teamdk.android.bakery.utility.NetworkManager.NetworkModule;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.DatabaseHelper;
import com.teamdk.android.bakery.utility.ColorTheme;

import java.util.ArrayList;
import java.util.Comparator;
=======
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;
import com.teamdk.android.bakery.objectmanager.MemberObjectManager;
import com.teamdk.android.bakery.objectmanager.NoticeObjectManager;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.DatabaseHelper;
import com.teamdk.android.bakery.utility.ColorTheme;
import com.teamdk.android.bakery.utility.SharedPreferenceManager;

>>>>>>> refs/remotes/origin/store-app-byeongmun
import java.util.Date;

import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class MainActivity extends AppCompatActivity {
    static DatabaseHelper databaseHelperTest;
    public static Context con;
    public static String PriNum;

<<<<<<< HEAD
    ArrayList<ProductSellTodayListItem> sellTodayArrayList = new ArrayList<>();
    ArrayList<ProductMuchStoreListItem> muchStoreArrayList = new ArrayList<>();
=======
>>>>>>> refs/remotes/origin/store-app-byeongmun
    //networkmodule 예제
    //networkModule.InsertNewCustomerInfo("강지성");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorTheme.initTheme(this);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_main);
        con = getApplicationContext();
        databaseHelperTest = new DatabaseHelper(getApplicationContext(), ClientDataBase.testDatabaseName);

<<<<<<< HEAD

=======
>>>>>>> refs/remotes/origin/store-app-byeongmun
        /*
        NetWorkOrderProcessManager netWorkOrderProcessManager = new NetWorkOrderProcessManager();
        netWorkOrderProcessManager.LoadAllStoreInfo();
        */

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run(){
<<<<<<< HEAD
                MainActivity.CheckTypesTask task = new MainActivity.CheckTypesTask();
=======
                CheckTypesTask task = new CheckTypesTask();
>>>>>>> refs/remotes/origin/store-app-byeongmun
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
<<<<<<< HEAD

            // show dialog
=======
>>>>>>> refs/remotes/origin/store-app-byeongmun
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
<<<<<<< HEAD
            Date date = new Date();

            ProductObjectManager.getContext(getApplicationContext());
            ProductObjectManager.productLoad(date);
=======
            // 설정값이 없을 경우 환경설정 초기화
            SharedPreferenceManager mSharedPreferenceManager = new SharedPreferenceManager();
            if(mSharedPreferenceManager.getInt("product_sort_mode_iv", getApplicationContext()) == 0){
                mSharedPreferenceManager.putInt("product_sort_mode_iv", R.id.iv_sort_add, getApplicationContext());
            }
            if(mSharedPreferenceManager.getInt("product_sort_mode_tv", getApplicationContext()) == 0){
                mSharedPreferenceManager.putInt("product_sort_mode_tv", R.id.tv_sort_add, getApplicationContext());
            }
            if(mSharedPreferenceManager.getInt("set_theme", getApplicationContext()) == 0){
                mSharedPreferenceManager.putInt("set_theme", R.style.LikeRoomTheme_BreadTheme, getApplicationContext());
            }

            new ProductObjectManager();
            ProductObjectManager.load(new Date(), getApplicationContext());
>>>>>>> refs/remotes/origin/store-app-byeongmun

            new MemberObjectManager();
            MemberObjectManager.load(getApplicationContext());

            new NoticeObjectManager();
            NoticeObjectManager.load(getApplicationContext());

            //SystemClock.sleep(500);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
            //////매장이 클라이언트 디비에 있는지 검사
<<<<<<< HEAD
            new ClientDataBase("select `매장번호` from `매장`;",1,1,getApplicationContext());
=======
            new ClientDataBase("select `매장번호` from `매장`;", 1, 1, getApplicationContext());
>>>>>>> refs/remotes/origin/store-app-byeongmun
            int cnt=0;
            while(true) {
                if (DBstring[cnt] != null) {
                    PriNum=DBstring[cnt];
                    cnt++;
                }
                else if(DBstring[cnt]==null) break;
            }
            if(PriNum==null) {
                startActivity(new Intent(getApplicationContext(), ActivityStoreAdd.class));
            }
            else {
<<<<<<< HEAD
                // show dialog
                new ClientDataBase("select max(`수정일`) from `회원정보`;",1,1,getApplicationContext());
                NetworkModule networkModule = new NetworkModule();
                networkModule.GetCustomerRegisteredInfo(Integer.parseInt(PriNum), DBstring[0]);
                startActivity(new Intent(getApplicationContext(),ActivityMenu.class));
=======
                startActivity(new Intent(getApplicationContext(), ActivityMenu.class));
>>>>>>> refs/remotes/origin/store-app-byeongmun
            }
            finish();
        }
    }
<<<<<<< HEAD

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
=======
>>>>>>> refs/remotes/origin/store-app-byeongmun
}